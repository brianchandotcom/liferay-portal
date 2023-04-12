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
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuVirtualSettings;
import com.liferay.headless.commerce.admin.catalog.internal.util.FileEntryUtil;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.upload.UniqueFileNameProvider;

import java.net.URL;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Stefano Motta
 */
public class SkuVirtualSettingsUtil {

	public static CPDefinitionVirtualSetting addOrUpdateSkuVirtualSettings(
			CPInstance cpInstance, SkuVirtualSettings skuVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			cpDefinitionVirtualSettingService.fetchCPDefinitionVirtualSetting(
				CPInstance.class.getName(), cpInstance.getCPInstanceId());

		if (cpDefinitionVirtualSetting == null) {
			return _addSkuVirtualSettings(
				cpInstance, skuVirtualSettings,
				cpDefinitionVirtualSettingService, uniqueFileNameProvider,
				serviceContext);
		}

		return _updateSkuVirtualSettings(
			cpInstance, cpDefinitionVirtualSetting, skuVirtualSettings,
			cpDefinitionVirtualSettingService, uniqueFileNameProvider,
			serviceContext);
	}

	private static CPDefinitionVirtualSetting _addSkuVirtualSettings(
			CPInstance cpInstance, SkuVirtualSettings skuVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		if (!GetterUtil.getBoolean(skuVirtualSettings.getOverride())) {
			return null;
		}

		String attachmentUrl = _validateUrl(skuVirtualSettings.getUrl());

		long attachmentFileEntryId = FileEntryUtil.getFileEntryId(
			skuVirtualSettings.getAttachment(), attachmentUrl,
			uniqueFileNameProvider, serviceContext);

		String sampleAttachmentUrl = null;
		long sampleFileEntryId = 0;

		boolean useSample = GetterUtil.getBoolean(
			skuVirtualSettings.getUseSample());

		if (useSample) {
			sampleAttachmentUrl = _validateUrl(
				skuVirtualSettings.getSampleUrl());

			sampleFileEntryId = FileEntryUtil.getFileEntryId(
				skuVirtualSettings.getSampleAttachment(), sampleAttachmentUrl,
				uniqueFileNameProvider, serviceContext);
		}

		Map<Locale, String> termsOfUseContentMap = null;
		long termsOfUseJournalArticleId = 0;

		boolean termsOfUseRequired = GetterUtil.getBoolean(
			skuVirtualSettings.getTermsOfUseRequired());

		if (termsOfUseRequired) {
			termsOfUseContentMap = LanguageUtils.getLocalizedMap(
				skuVirtualSettings.getTermsOfUseContent());
			termsOfUseJournalArticleId = GetterUtil.getLong(
				skuVirtualSettings.getTermsOfUseJournalArticleId());
		}

		return cpDefinitionVirtualSettingService.addCPDefinitionVirtualSetting(
			CPInstance.class.getName(), cpInstance.getCPInstanceId(),
			attachmentFileEntryId, attachmentUrl,
			_getActivationStatus(
				GetterUtil.getInteger(
					skuVirtualSettings.getActivationStatus(),
					CommerceOrderConstants.ORDER_STATUS_COMPLETED)),
			TimeUnit.DAYS.toMillis(
				GetterUtil.getLong(skuVirtualSettings.getDuration())),
			GetterUtil.getInteger(skuVirtualSettings.getMaxUsages()), useSample,
			sampleFileEntryId, sampleAttachmentUrl, termsOfUseRequired,
			termsOfUseContentMap, termsOfUseJournalArticleId, true,
			serviceContext);
	}

	private static int _getActivationStatus(int activationStatus) {
		if (ArrayUtil.contains(
				VirtualCPTypeConstants.ACTIVATION_STATUSES, activationStatus)) {

			return activationStatus;
		}

		return CommerceOrderConstants.ORDER_STATUS_COMPLETED;
	}

	private static CPDefinitionVirtualSetting _updateSkuVirtualSettings(
			CPInstance cpInstance,
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting,
			SkuVirtualSettings skuVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		if (!GetterUtil.getBoolean(
				skuVirtualSettings.getOverride(),
				cpDefinitionVirtualSetting.isOverride())) {

			return cpDefinitionVirtualSettingService.
				deleteCPDefinitionVirtualSetting(
					CPInstance.class.getName(), cpInstance.getCPInstanceId());
		}

		long attachmentFileEntryId = 0;
		String attachmentUrl = _validateUrl(skuVirtualSettings.getUrl());

		if (Validator.isNull(attachmentUrl)) {
			if (Validator.isNull(skuVirtualSettings.getAttachment())) {
				attachmentUrl = cpDefinitionVirtualSetting.getUrl();
			}
			else {
				attachmentFileEntryId = FileEntryUtil.getFileEntryId(
					skuVirtualSettings.getAttachment(), attachmentUrl,
					uniqueFileNameProvider, serviceContext);
			}

			if (attachmentFileEntryId == 0) {
				attachmentFileEntryId =
					cpDefinitionVirtualSetting.getFileEntryId();
			}
		}

		Long duration = skuVirtualSettings.getDuration();

		if (duration != null) {
			duration = TimeUnit.DAYS.toMillis(duration);
		}

		String sampleAttachmentUrl = null;
		long sampleFileEntryId = 0;

		boolean useSample = GetterUtil.getBoolean(
			skuVirtualSettings.getUseSample(),
			cpDefinitionVirtualSetting.isUseSample());

		if (useSample) {
			sampleAttachmentUrl = _validateUrl(
				skuVirtualSettings.getSampleUrl());

			if (Validator.isNull(sampleAttachmentUrl)) {
				if (Validator.isNull(
						skuVirtualSettings.getSampleAttachment())) {

					sampleAttachmentUrl =
						cpDefinitionVirtualSetting.getSampleUrl();
				}
				else {
					sampleFileEntryId = FileEntryUtil.getFileEntryId(
						skuVirtualSettings.getSampleAttachment(),
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
			skuVirtualSettings.getTermsOfUseRequired(),
			cpDefinitionVirtualSetting.isTermsOfUseRequired());

		if (termsOfUseRequired) {
			termsOfUseContentMap = LanguageUtils.getLocalizedMap(
				skuVirtualSettings.getTermsOfUseContent());
			termsOfUseJournalArticleId = GetterUtil.getLong(
				skuVirtualSettings.getTermsOfUseJournalArticleId());

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
						skuVirtualSettings.getActivationStatus(),
						cpDefinitionVirtualSetting.getActivationStatus())),
				GetterUtil.getLong(
					duration, cpDefinitionVirtualSetting.getDuration()),
				GetterUtil.getInteger(
					skuVirtualSettings.getMaxUsages(),
					cpDefinitionVirtualSetting.getMaxUsages()),
				useSample, sampleFileEntryId, sampleAttachmentUrl,
				termsOfUseRequired, termsOfUseContentMap,
				termsOfUseJournalArticleId, true, serviceContext);
	}

	private static String _validateUrl(String value) throws Exception {
		if (Validator.isNotNull(value)) {
			URL url = new URL(value);

			return url.toString();
		}

		return null;
	}

}