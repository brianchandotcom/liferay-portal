/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class MBMessageStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBMessage> {

	public static final String[] CLASS_NAMES = {MBMessage.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		MBMessage message =
			MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
				uuid, groupId);

		if (message != null) {
			MBMessageLocalServiceUtil.deleteMessage(message);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MBMessage message) {
		return message.getSubject();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		if (message.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {

			return;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, message, message.getCategory(),
			PortletDataContext.REFERENCE_TYPE_PARENT);

		if (!message.isRoot()) {
			MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
				message.getParentMessageId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, message, parentMessage,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		message.setPriority(message.getPriority());

		MBThread thread = message.getThread();

		Element messageElement = portletDataContext.getExportDataElement(
			message);

		messageElement.addAttribute(
			"question", String.valueOf(thread.isQuestion()));

		boolean hasAttachmentsFileEntries =
			message.getAttachmentsFileEntriesCount() > 0;

		messageElement.addAttribute(
			"hasAttachmentsFileEntries",
			String.valueOf(hasAttachmentsFileEntries));

		if (hasAttachmentsFileEntries) {
			for (FileEntry fileEntry : message.getAttachmentsFileEntries()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, message, fileEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}

			long folderId = message.getAttachmentsFolderId();

			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				message.setAttachmentsFolderId(folderId);
			}
		}

		portletDataContext.addClassedModel(
			messageElement, ExportImportPathUtil.getModelPath(message),
			message);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		if (!message.isRoot()) {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, message, MBMessage.class,
				message.getParentMessageId());
		}

		long userId = portletDataContext.getUserId(message.getUserUuid());

		String userName = message.getUserName();

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, message.getCategoryId(), message.getCategoryId());

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		long threadId = MapUtil.getLong(threadIds, message.getThreadId(), 0);

		Map<Long, Long> messageIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBMessage.class);

		long parentMessageId = MapUtil.getLong(
			messageIds, message.getParentMessageId(),
			message.getParentMessageId());

		Element element = portletDataContext.getImportDataStagedModelElement(
			message);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			getAttachments(portletDataContext, element, message);

		try {
			ServiceContext serviceContext =
				portletDataContext.createServiceContext(message);

			if ((parentCategoryId !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
				(parentCategoryId !=
					MBCategoryConstants.DISCUSSION_CATEGORY_ID) &&
				(parentCategoryId == message.getCategoryId())) {

				StagedModelDataHandlerUtil.importReferenceStagedModel(
					portletDataContext, message, MBCategory.class,
					parentCategoryId);

				parentCategoryId = MapUtil.getLong(
					categoryIds, message.getCategoryId(),
					message.getCategoryId());
			}

			MBMessage importedMessage = null;

			if (portletDataContext.isDataStrategyMirror()) {
				MBMessage existingMessage =
					MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
						message.getUuid(),
						portletDataContext.getScopeGroupId());

				if (existingMessage == null) {
					serviceContext.setUuid(message.getUuid());

					importedMessage = MBMessageLocalServiceUtil.addMessage(
						userId, userName, portletDataContext.getScopeGroupId(),
						parentCategoryId, threadId, parentMessageId,
						message.getSubject(), message.getBody(),
						message.getFormat(), inputStreamOVPs,
						message.getAnonymous(), message.getPriority(),
						message.getAllowPingbacks(), serviceContext);
				}
				else {
					importedMessage = MBMessageLocalServiceUtil.updateMessage(
						userId, existingMessage.getMessageId(),
						message.getSubject(), message.getBody(),
						inputStreamOVPs, new ArrayList<String>(),
						message.getPriority(), message.getAllowPingbacks(),
						serviceContext);
				}
			}
			else {
				importedMessage = MBMessageLocalServiceUtil.addMessage(
					userId, userName, portletDataContext.getScopeGroupId(),
					parentCategoryId, threadId, parentMessageId,
					message.getSubject(), message.getBody(),
					message.getFormat(), inputStreamOVPs,
					message.getAnonymous(), message.getPriority(),
					message.getAllowPingbacks(), serviceContext);
			}

			importedMessage.setAnswer(message.getAnswer());

			if (importedMessage.isRoot()) {
				MBThreadLocalServiceUtil.updateQuestion(
					importedMessage.getThreadId(),
					GetterUtil.getBoolean(element.attributeValue("question")));
			}

			threadIds.put(message.getThreadId(), importedMessage.getThreadId());

			portletDataContext.importClassedModel(message, importedMessage);
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				InputStream inputStream = inputStreamOVP.getValue();

				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		long userId = portletDataContext.getUserId(message.getUserUuid());

		MBMessage existingMessage =
			MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
				message.getUuid(), portletDataContext.getScopeGroupId());

		if (existingMessage == null) {
			return;
		}

		if (existingMessage.isInTrash()) {
			TrashHandler trashHandler = existingMessage.getTrashHandler();

			if (trashHandler.isRestorable(existingMessage.getMessageId())) {
				trashHandler.restoreTrashEntry(
					userId, existingMessage.getMessageId());
			}
		}

		if (existingMessage.isInTrashContainer()) {
			MBThread existingThread = existingMessage.getThread();

			TrashHandler trashHandler = existingThread.getTrashHandler();

			if (trashHandler.isRestorable(existingThread.getThreadId())) {
				trashHandler.restoreTrashEntry(
					userId, existingThread.getThreadId());
			}
		}
	}

	protected List<ObjectValuePair<String, InputStream>> getAttachments(
		PortletDataContext portletDataContext, Element messageElement,
		MBMessage message) {

		boolean hasAttachmentsFileEntries = GetterUtil.getBoolean(
			messageElement.attributeValue("hasAttachmentsFileEntries"));

		if (!hasAttachmentsFileEntries) {
			return Collections.emptyList();
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>();

		List<Element> attachmentElements =
			portletDataContext.getReferenceDataElements(
				messageElement, DLFileEntry.class,
				PortletDataContext.REFERENCE_TYPE_WEAK);

		for (Element attachmentElement : attachmentElements) {
			String path = attachmentElement.attributeValue("path");

			FileEntry fileEntry =
				(FileEntry)portletDataContext.getZipEntryAsObject(path);

			InputStream inputStream = null;

			String binPath = attachmentElement.attributeValue("bin-path");

			if (Validator.isNull(binPath) &&
				portletDataContext.isPerformDirectBinaryImport()) {

				try {
					inputStream = FileEntryUtil.getContentStream(fileEntry);
				}
				catch (Exception e) {
				}
			}
			else {
				inputStream = portletDataContext.getZipEntryAsInputStream(
					binPath);
			}

			if (inputStream == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to import attachment for file entry " +
							fileEntry.getFileEntryId());
				}

				continue;
			}

			ObjectValuePair<String, InputStream> inputStreamOVP =
				new ObjectValuePair<String, InputStream>(
					fileEntry.getTitle(), inputStream);

			inputStreamOVPs.add(inputStreamOVP);
		}

		if (inputStreamOVPs.isEmpty()) {
			_log.error(
				"Could not find attachments for message " +
					message.getMessageId());
		}

		return inputStreamOVPs;
	}

	private static Log _log = LogFactoryUtil.getLog(
		MBMessageStagedModelDataHandler.class);

}