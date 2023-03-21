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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductVirtualSettings;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.upload.UniqueFileNameProvider;

import java.io.File;

import java.net.URL;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Stefano Motta
 */
public class VirtualSettingsUtil {

	public static CPDefinitionVirtualSetting addOrUpdateVirtualSettings(
			CPDefinition cpDefinition,
			ProductVirtualSettings productVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingService.fetchCPDefinitionVirtualSetting(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		if (cpDefinitionVirtualSetting == null) {
			return _addVirtualSettings(
				cpDefinition, productVirtualSettings,
				cpDefinitionVirtualSettingService, uniqueFileNameProvider,
				serviceContext);
		}

		return _updateVirtualSettings(
			cpDefinitionVirtualSetting, productVirtualSettings,
			cpDefinitionVirtualSettingService, uniqueFileNameProvider,
			serviceContext);
	}

	private static FileEntry _addFileEntry(
			File file, String contentType,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		String uniqueFileName = uniqueFileNameProvider.provide(
			file.getName(),
			curFileName -> _exists(
				serviceContext.getScopeGroupId(), serviceContext.getUserId(),
				curFileName));

		if (Validator.isNull(contentType)) {
			contentType = MimeTypesUtil.getContentType(file);
		}

		uniqueFileName = _appendExtension(contentType, uniqueFileName);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			null, serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, uniqueFileName,
			contentType, uniqueFileName, StringPool.BLANK, null,
			StringPool.BLANK, file, null, null, serviceContext);

		FileUtil.delete(file);

		return fileEntry;
	}

	private static CPDefinitionVirtualSetting _addVirtualSettings(
			CPDefinition cpDefinition,
			ProductVirtualSettings productVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		String attachmentUrl = _validateUrl(productVirtualSettings.getUrl());

		long attachmentFileEntryId = _getFileEntryId(
			productVirtualSettings.getAttachment(), attachmentUrl,
			uniqueFileNameProvider, serviceContext);

		String sampleAttachmentUrl = null;
		long sampleFileEntryId = 0;

		boolean useSample = GetterUtil.getBoolean(
			productVirtualSettings.getUseSample());

		if (useSample) {
			sampleAttachmentUrl = _validateUrl(
				productVirtualSettings.getSampleUrl());

			sampleFileEntryId = _getFileEntryId(
				productVirtualSettings.getSampleAttachment(),
				sampleAttachmentUrl, uniqueFileNameProvider, serviceContext);
		}

		Map<Locale, String> termsOfUseContentMap = null;
		long termsOfUseJournalArticleId = 0;

		boolean termsOfUseRequired = GetterUtil.getBoolean(
			productVirtualSettings.getTermsOfUseRequired());

		if (termsOfUseRequired) {
			termsOfUseContentMap = LanguageUtils.getLocalizedMap(
				productVirtualSettings.getTermsOfUseContent());
			termsOfUseJournalArticleId = GetterUtil.getLong(
				productVirtualSettings.getTermsOfUseJournalArticleId());
		}

		return cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
			attachmentFileEntryId, attachmentUrl,
			_getActivationStatus(
				GetterUtil.getInteger(
					productVirtualSettings.getActivationStatus(),
					CommerceOrderConstants.ORDER_STATUS_COMPLETED)),
			TimeUnit.DAYS.toMillis(
				GetterUtil.getLong(productVirtualSettings.getDuration())),
			GetterUtil.getInteger(productVirtualSettings.getMaxUsages()),
			useSample, sampleFileEntryId, sampleAttachmentUrl,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleId, serviceContext);
	}

	private static String _appendExtension(
		String contentType, String uniqueFileName) {

		String extension = StringPool.BLANK;

		Set<String> extensions = MimeTypesUtil.getExtensions(contentType);

		if (!extensions.isEmpty()) {
			Iterator<String> iterator = extensions.iterator();

			if (iterator.hasNext()) {
				extension = iterator.next();
			}
		}

		return uniqueFileName.concat(extension);
	}

	private static boolean _exists(
		long groupId, long userId, String curFileName) {

		try {
			FileEntry fileEntry = TempFileEntryUtil.getTempFileEntry(
				groupId, userId, _TEMP_FILE_NAME, curFileName);

			if (fileEntry != null) {
				return true;
			}

			return false;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private static int _getActivationStatus(int activationStatus) {
		if (ArrayUtil.contains(
				VirtualCPTypeConstants.ACTIVATION_STATUSES, activationStatus)) {

			return activationStatus;
		}

		return CommerceOrderConstants.ORDER_STATUS_COMPLETED;
	}

	private static long _getFileEntryId(
			String attachment, String url,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		if (Validator.isNotNull(attachment) && Validator.isNull(url)) {
			serviceContext.setExpandoBridgeAttributes(new HashMap<>());

			FileEntry fileEntry = _addFileEntry(
				FileUtil.createTempFile(Base64.decode(attachment)), null,
				uniqueFileNameProvider, serviceContext);

			return fileEntry.getFileEntryId();
		}

		return 0;
	}

	private static CPDefinitionVirtualSetting _updateVirtualSettings(
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting,
			ProductVirtualSettings productVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		long attachmentFileEntryId = 0;
		String attachmentUrl = _validateUrl(productVirtualSettings.getUrl());

		if (Validator.isNull(attachmentUrl)) {
			if (Validator.isNull(productVirtualSettings.getAttachment())) {
				attachmentUrl = cpDefinitionVirtualSetting.getUrl();
			}
			else {
				attachmentFileEntryId = _getFileEntryId(
					productVirtualSettings.getAttachment(), attachmentUrl,
					uniqueFileNameProvider, serviceContext);
			}

			if (attachmentFileEntryId == 0) {
				attachmentFileEntryId =
					cpDefinitionVirtualSetting.getFileEntryId();
			}
		}

		Long duration = productVirtualSettings.getDuration();

		if (duration != null) {
			duration = TimeUnit.DAYS.toMillis(duration);
		}

		String sampleAttachmentUrl = null;
		long sampleFileEntryId = 0;

		boolean useSample = GetterUtil.getBoolean(
			productVirtualSettings.getUseSample(),
			cpDefinitionVirtualSetting.isUseSample());

		if (useSample) {
			sampleAttachmentUrl = _validateUrl(
				productVirtualSettings.getSampleUrl());

			if (Validator.isNull(sampleAttachmentUrl)) {
				if (Validator.isNull(
						productVirtualSettings.getSampleAttachment())) {

					sampleAttachmentUrl =
						cpDefinitionVirtualSetting.getSampleUrl();
				}
				else {
					sampleFileEntryId = _getFileEntryId(
						productVirtualSettings.getSampleAttachment(),
						sampleAttachmentUrl, uniqueFileNameProvider,
						serviceContext);
				}

				if (sampleFileEntryId == 0) {
					sampleFileEntryId =
						cpDefinitionVirtualSetting.getSampleFileEntryId();
				}
			}
		}

		Map<Locale, String> termsOfUseContentMap = null;
		long termsOfUseJournalArticleId = 0;

		boolean termsOfUseRequired = GetterUtil.get(
			productVirtualSettings.getTermsOfUseRequired(),
			cpDefinitionVirtualSetting.isTermsOfUseRequired());

		if (termsOfUseRequired) {
			termsOfUseContentMap = LanguageUtils.getLocalizedMap(
				productVirtualSettings.getTermsOfUseContent());
			termsOfUseJournalArticleId = GetterUtil.getLong(
				productVirtualSettings.getTermsOfUseJournalArticleId());

			if ((termsOfUseContentMap == null) &&
				(termsOfUseJournalArticleId == 0)) {

				JournalArticle termsOfUseJournalArticle =
					cpDefinitionVirtualSetting.getTermsOfUseJournalArticle();

				if (termsOfUseJournalArticle != null) {
					termsOfUseJournalArticleId =
						termsOfUseJournalArticle.getResourcePrimKey();
				}
				else {
					termsOfUseContentMap =
						cpDefinitionVirtualSetting.getTermsOfUseContentMap();
				}
			}
		}

		return cpDefinitionVirtualSettingService.
			updateCPDefinitionVirtualSetting(
				cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId(),
				attachmentFileEntryId, attachmentUrl,
				_getActivationStatus(
					GetterUtil.getInteger(
						productVirtualSettings.getActivationStatus(),
						cpDefinitionVirtualSetting.getActivationStatus())),
				GetterUtil.getLong(
					duration, cpDefinitionVirtualSetting.getDuration()),
				GetterUtil.getInteger(
					productVirtualSettings.getMaxUsages(),
					cpDefinitionVirtualSetting.getMaxUsages()),
				useSample, sampleFileEntryId, sampleAttachmentUrl,
				termsOfUseRequired, termsOfUseContentMap,
				termsOfUseJournalArticleId, serviceContext);
	}

	private static String _validateUrl(String value) throws Exception {
		if (Validator.isNotNull(value)) {
			URL url = new URL(value);

			return url.toString();
		}

		return null;
	}

	private static final String _TEMP_FILE_NAME =
		VirtualSettingsUtil.class.getName();

	private static final Log _log = LogFactoryUtil.getLog(
		VirtualSettingsUtil.class);

}