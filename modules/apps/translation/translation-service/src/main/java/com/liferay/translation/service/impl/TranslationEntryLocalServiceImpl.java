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

package com.liferay.translation.service.impl;

import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.model.TranslationEntry;
import com.liferay.translation.service.base.TranslationEntryLocalServiceBaseImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.translation.model.TranslationEntry",
	service = AopService.class
)
public class TranslationEntryLocalServiceImpl
	extends TranslationEntryLocalServiceBaseImpl {

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			InfoItemClassPKReference infoItemClassPKReference,
			InfoItemFieldValues infoItemFieldValues,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return addOrUpdateTranslationEntry(
				groupId, infoItemClassPKReference.getClassName(),
				infoItemClassPKReference.getClassPK(),
				StreamUtil.toString(
					_xliffTranslationInfoItemFieldValuesExporter.
						exportInfoItemFieldValues(
							infoItemFieldValues, LocaleUtil.getDefault(),
							LocaleUtil.fromLanguageId(languageId))),
				_xliffTranslationInfoItemFieldValuesExporter.getMimeType(),
				languageId, serviceContext);
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String className, long classPK, String content,
			String contentType, String languageId,
			ServiceContext serviceContext)
		throws PortalException {

		TranslationEntry translationEntry =
			translationEntryPersistence.fetchByC_C_L(
				_portal.getClassNameId(className), classPK, languageId);

		if (translationEntry == null) {
			translationEntry = translationEntryPersistence.create(
				counterLocalService.increment());

			translationEntry.setUuid(serviceContext.getUuid());
			translationEntry.setGroupId(groupId);
			translationEntry.setCompanyId(serviceContext.getCompanyId());
			translationEntry.setUserId(serviceContext.getUserId());
			translationEntry.setClassName(className);
			translationEntry.setClassPK(classPK);
			translationEntry.setLanguageId(languageId);
		}

		translationEntry.setContent(content);
		translationEntry.setContentType(contentType);

		User user = _userLocalService.getUser(serviceContext.getUserId());

		int status = WorkflowConstants.STATUS_APPROVED;

		translationEntry.setStatus(status);

		translationEntry.setStatusByUserId(user.getUserId());
		translationEntry.setStatusByUserName(user.getFullName());
		translationEntry.setStatusDate(
			serviceContext.getModifiedDate(new Date()));

		return _startWorkflowInstance(
			translationEntryPersistence.update(translationEntry),
			serviceContext);
	}

	@Override
	public TranslationEntry fetchTranslationEntry(
		String className, long classPK, String languageId) {

		return translationEntryPersistence.fetchByC_C_L(
			_portal.getClassNameId(className), classPK, languageId);
	}

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
			long groupId, String className, long classPK, String content)
		throws PortalException {

		try {
			return _xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					groupId, new InfoItemClassPKReference(className, classPK),
					new ByteArrayInputStream(content.getBytes()));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Override
	public TranslationEntry updateStatus(
			long userId, long translationEntryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		TranslationEntry translationEntry =
			translationEntryPersistence.findByPrimaryKey(translationEntryId);

		User user = _userLocalService.getUser(userId);

		translationEntry.setStatus(status);
		translationEntry.setStatusByUserId(user.getUserId());
		translationEntry.setStatusByUserName(user.getFullName());
		translationEntry.setStatusDate(
			serviceContext.getModifiedDate(new Date()));

		return translationEntryPersistence.update(translationEntry);
	}

	private TranslationEntry _startWorkflowInstance(
			TranslationEntry entry, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			entry.getCompanyId(), entry.getGroupId(),
			serviceContext.getUserId(), TranslationEntry.class.getName(),
			entry.getTranslationEntryId(), entry, serviceContext,
			workflowContext);
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference(target = "(content.type=application/xliff+xml)")
	private TranslationInfoItemFieldValuesExporter
		_xliffTranslationInfoItemFieldValuesExporter;

	@Reference(target = "(content.type=application/xliff+xml)")
	private TranslationInfoItemFieldValuesImporter
		_xliffTranslationInfoItemFieldValuesImporter;

}