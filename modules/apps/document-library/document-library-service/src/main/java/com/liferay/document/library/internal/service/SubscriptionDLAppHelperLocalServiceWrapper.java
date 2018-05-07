/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceWrapper;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.util.DLAppHelperThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.EscapableLocalizableFunction;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.portlet.documentlibrary.DLGroupServiceSettings;
import com.liferay.portlet.documentlibrary.constants.DLConstants;
import com.liferay.subscription.service.SubscriptionLocalService;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class SubscriptionDLAppHelperLocalServiceWrapper
	extends DLAppHelperLocalServiceWrapper {

	public SubscriptionDLAppHelperLocalServiceWrapper() {
		super(null);
	}

	public SubscriptionDLAppHelperLocalServiceWrapper(
		DLAppHelperLocalService dlAppHelperLocalService) {

		super(dlAppHelperLocalService);
	}

	@Override
	public void deleteFileEntry(FileEntry fileEntry) throws PortalException {
		if (!_isEnabled(fileEntry)) {
			return;
		}

		super.deleteFileEntry(fileEntry);

		_subscriptionLocalService.deleteSubscriptions(
			fileEntry.getCompanyId(), DLFileEntryConstants.getClassName(),
			fileEntry.getFileEntryId());
	}

	@Override
	public void deleteFolder(Folder folder) throws PortalException {
		if (!_isEnabled(folder)) {
			return;
		}

		super.deleteFolder(folder);

		_subscriptionLocalService.deleteSubscriptions(
			folder.getCompanyId(), DLFolderConstants.getClassName(),
			folder.getFolderId());
	}

	@Override
	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int oldStatus, int newStatus, ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		if (!_isEnabled(fileEntry)) {
			return;
		}

		super.updateStatus(
			userId, fileEntry, latestFileVersion, oldStatus, newStatus,
			serviceContext, workflowContext);

		if ((newStatus == WorkflowConstants.STATUS_APPROVED) &&
			(oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
			!fileEntry.isInTrash()) {

			// Subscriptions

			notifySubscribers(
				userId, latestFileVersion,
				(String)workflowContext.get(WorkflowConstants.CONTEXT_URL),
				serviceContext);
		}
	}

	protected void notifySubscribers(
			long userId, FileVersion fileVersion, String entryURL,
			ServiceContext serviceContext)
		throws PortalException {

		if (!fileVersion.isApproved() || Validator.isNull(entryURL)) {
			return;
		}

		DLGroupServiceSettings dlGroupServiceSettings =
			DLGroupServiceSettings.getInstance(fileVersion.getGroupId());

		boolean commandUpdate = false;

		if (serviceContext.isCommandUpdate() ||
			Constants.CHECKIN.equals(serviceContext.getCommand())) {

			commandUpdate = true;
		}

		if (serviceContext.isCommandAdd() &&
			dlGroupServiceSettings.isEmailFileEntryAddedEnabled()) {
		}
		else if (commandUpdate &&
				 dlGroupServiceSettings.isEmailFileEntryUpdatedEnabled()) {
		}
		else {
			return;
		}

		String entryTitle = fileVersion.getTitle();

		String fromName = dlGroupServiceSettings.getEmailFromName();
		String fromAddress = dlGroupServiceSettings.getEmailFromAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (commandUpdate) {
			subjectLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryUpdatedSubject();
			bodyLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryAddedSubject();
			bodyLocalizedValuesMap =
				dlGroupServiceSettings.getEmailFileEntryAddedBody();
		}

		FileEntry fileEntry = fileVersion.getFileEntry();

		Folder folder = null;

		long folderId = fileEntry.getFolderId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folder = _dlAppLocalService.getFolder(folderId);
		}

		SubscriptionSender subscriptionSender =
			new GroupSubscriptionCheckSubscriptionSender(
				DLConstants.RESOURCE_NAME);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.getDLFileEntryType(
				dlFileEntry.getFileEntryTypeId());

		subscriptionSender.setClassPK(fileVersion.getFileEntryId());
		subscriptionSender.setClassName(DLFileEntryConstants.getClassName());
		subscriptionSender.setCompanyId(fileVersion.getCompanyId());

		if (folder != null) {
			subscriptionSender.setContextAttribute(
				"[$FOLDER_NAME$]", folder.getName(), true);
		}
		else {
			subscriptionSender.setLocalizedContextAttribute(
				"[$FOLDER_NAME$]",
				new EscapableLocalizableFunction(
					locale -> LanguageUtil.get(locale, "home")));
		}

		subscriptionSender.setContextAttributes(
			"[$DOCUMENT_STATUS_BY_USER_NAME$]",
			fileVersion.getStatusByUserName(), "[$DOCUMENT_TITLE$]", entryTitle,
			"[$DOCUMENT_URL$]", entryURL);
		subscriptionSender.setContextCreatorUserPrefix("DOCUMENT");
		subscriptionSender.setCreatorUserId(fileVersion.getUserId());
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(entryURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(
			LocalizationUtil.getMap(bodyLocalizedValuesMap));
		subscriptionSender.setLocalizedContextAttribute(
			"[$DOCUMENT_TYPE$]",
			new EscapableLocalizableFunction(
				locale -> dlFileEntryType.getName(locale)));
		subscriptionSender.setLocalizedSubjectMap(
			LocalizationUtil.getMap(subjectLocalizedValuesMap));
		subscriptionSender.setMailId(
			"file_entry", fileVersion.getFileEntryId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (commandUpdate) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		String portletId = PortletProviderUtil.getPortletId(
			FileEntry.class.getName(), PortletProvider.Action.VIEW);

		subscriptionSender.setPortletId(portletId);

		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(fileVersion.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.addPersistedSubscribers(
			DLFolder.class.getName(), fileVersion.getGroupId());

		if (folder != null) {
			subscriptionSender.addPersistedSubscribers(
				DLFolder.class.getName(), folder.getFolderId());

			for (Long ancestorFolderId : folder.getAncestorFolderIds()) {
				subscriptionSender.addPersistedSubscribers(
					DLFolder.class.getName(), ancestorFolderId);
			}
		}

		if (dlFileEntryType.getFileEntryTypeId() ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			subscriptionSender.addPersistedSubscribers(
				DLFileEntryType.class.getName(), fileVersion.getGroupId());
		}
		else {
			subscriptionSender.addPersistedSubscribers(
				DLFileEntryType.class.getName(),
				dlFileEntryType.getFileEntryTypeId());
		}

		subscriptionSender.addPersistedSubscribers(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		subscriptionSender.flushNotificationsAsync();
	}

	private boolean _isEnabled(FileEntry fileEntry) {
		if (!DLAppHelperThreadLocal.isEnabled()) {
			return false;
		}

		if (RepositoryUtil.isExternalRepository(fileEntry.getRepositoryId())) {
			return false;
		}

		return true;
	}

	private boolean _isEnabled(Folder folder) {
		if (!DLAppHelperThreadLocal.isEnabled()) {
			return false;
		}

		if (!folder.isMountPoint() &&
			RepositoryUtil.isExternalRepository(folder.getRepositoryId())) {

			return false;
		}

		return true;
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}