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

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.updater.InfoItemFieldValuesUpdater;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
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

import java.nio.charset.StandardCharsets;

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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public TranslationEntry addOrUpdateTranslationEntry(
			long groupId, String languageId,
			InfoItemReference infoItemReference,
			InfoItemFieldValues infoItemFieldValues,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			return addOrUpdateTranslationEntry(
				groupId, infoItemReference.getClassName(),
				infoItemReference.getClassPK(),
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

	@Indexable(type = IndexableType.REINDEX)
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
			translationEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		translationEntry.setContent(content);
		translationEntry.setContentType(contentType);

		if (!translationEntry.isDraft() && !translationEntry.isPending()) {
			translationEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}

		User user = _userLocalService.getUser(serviceContext.getUserId());

		translationEntry.setStatusByUserId(user.getUserId());
		translationEntry.setStatusByUserName(user.getFullName());

		translationEntry.setStatusDate(
			serviceContext.getModifiedDate(new Date()));

		translationEntry = translationEntryPersistence.update(translationEntry);

		_assetEntryLocalService.updateEntry(
			translationEntry.getUserId(), translationEntry.getGroupId(),
			translationEntry.getCreateDate(),
			translationEntry.getModifiedDate(),
			TranslationEntry.class.getName(),
			translationEntry.getTranslationEntryId(),
			translationEntry.getUuid(), 0, null, null, false, false, null, null,
			null, null, null, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, null, null, 0, 0, 0D);

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			translationEntry.getCompanyId(), translationEntry.getGroupId(),
			serviceContext.getUserId(), TranslationEntry.class.getName(),
			translationEntry.getTranslationEntryId(), translationEntry,
			serviceContext, new HashMap<>());
	}

	@Override
	public void deleteTranslationEntries(long classNameId, long classPK) {
		translationEntryPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public void deleteTranslationEntries(String className, long classPK) {
		translationEntryLocalService.deleteTranslationEntries(
			_portal.getClassNameId(className), classPK);
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
					groupId, new InfoItemReference(className, classPK),
					new ByteArrayInputStream(content.getBytes()));
		}
		catch (IOException ioException) {
			throw new PortalException(ioException);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public TranslationEntry updateStatus(
			long userId, long translationEntryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		TranslationEntry translationEntry =
			translationEntryPersistence.findByPrimaryKey(translationEntryId);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			_updateInfoItem(translationEntry);
		}

		translationEntry.setStatus(status);

		User user = _userLocalService.getUser(userId);

		translationEntry.setStatusByUserId(user.getUserId());
		translationEntry.setStatusByUserName(user.getFullName());

		translationEntry.setStatusDate(
			serviceContext.getModifiedDate(new Date()));

		return translationEntryPersistence.update(translationEntry);
	}

	private void _updateInfoItem(TranslationEntry translationEntry)
		throws PortalException {

		try {
			InfoItemFieldValuesUpdater<Object> infoItemFieldValuesUpdater =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesUpdater.class,
					translationEntry.getClassName());

			InfoItemObjectProvider<Object> infoItemObjectProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					translationEntry.getClassName());

			String content = translationEntry.getContent();

			infoItemFieldValuesUpdater.updateFromInfoItemFieldValues(
				infoItemObjectProvider.getInfoItem(
					translationEntry.getClassPK()),
				_xliffTranslationInfoItemFieldValuesImporter.
					importInfoItemFieldValues(
						translationEntry.getGroupId(),
						new InfoItemReference(
							translationEntry.getClassName(),
							translationEntry.getClassPK()),
						new ByteArrayInputStream(
							content.getBytes(StandardCharsets.UTF_8))));
		}
		catch (PortalException | RuntimeException exception) {
			throw exception;
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

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