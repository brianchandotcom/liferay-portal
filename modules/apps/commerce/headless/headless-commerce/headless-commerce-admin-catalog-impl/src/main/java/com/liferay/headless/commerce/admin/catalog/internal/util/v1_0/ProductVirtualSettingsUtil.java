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
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductVirtualSettings;
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
public class ProductVirtualSettingsUtil {

	public static CPDefinitionVirtualSetting addOrUpdateProductVirtualSettings(
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
			return _addProductVirtualSettings(
				cpDefinition, productVirtualSettings,
				cpDefinitionVirtualSettingService, uniqueFileNameProvider,
				serviceContext);
		}

		return _updateProductVirtualSettings(
			cpDefinitionVirtualSetting, productVirtualSettings,
			cpDefinitionVirtualSettingService, uniqueFileNameProvider,
			serviceContext);
	}

	private static CPDefinitionVirtualSetting _addProductVirtualSettings(
			CPDefinition cpDefinition,
			ProductVirtualSettings productVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		String attachmentURL = _validateURL(productVirtualSettings.getUrl());

		long attachmentFileEntryId = FileEntryUtil.getFileEntryId(
			productVirtualSettings.getAttachment(), attachmentURL,
			uniqueFileNameProvider, serviceContext);

		String sampleAttachmentURL = null;
		long sampleFileEntryId = 0;

		boolean useSample = GetterUtil.getBoolean(
			productVirtualSettings.getUseSample());

		if (useSample) {
			sampleAttachmentURL = _validateURL(
				productVirtualSettings.getSampleUrl());

			sampleFileEntryId = FileEntryUtil.getFileEntryId(
				productVirtualSettings.getSampleAttachment(),
				sampleAttachmentURL, uniqueFileNameProvider, serviceContext);
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
			attachmentFileEntryId, attachmentURL,
			_getActivationStatus(
				GetterUtil.getInteger(
					productVirtualSettings.getActivationStatus(),
					CommerceOrderConstants.ORDER_STATUS_COMPLETED)),
			TimeUnit.DAYS.toMillis(
				GetterUtil.getLong(productVirtualSettings.getDuration())),
			GetterUtil.getInteger(productVirtualSettings.getMaxUsages()),
			useSample, sampleFileEntryId, sampleAttachmentURL,
			termsOfUseRequired, termsOfUseContentMap,
			termsOfUseJournalArticleId, serviceContext);
	}

	private static int _getActivationStatus(int activationStatus) {
		if (ArrayUtil.contains(
				VirtualCPTypeConstants.ACTIVATION_STATUSES, activationStatus)) {

			return activationStatus;
		}

		return CommerceOrderConstants.ORDER_STATUS_COMPLETED;
	}

	private static CPDefinitionVirtualSetting _updateProductVirtualSettings(
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting,
			ProductVirtualSettings productVirtualSettings,
			CPDefinitionVirtualSettingService cpDefinitionVirtualSettingService,
			UniqueFileNameProvider uniqueFileNameProvider,
			ServiceContext serviceContext)
		throws Exception {

		long attachmentFileEntryId = 0;
		String attachmentURL = _validateURL(productVirtualSettings.getUrl());

		if (Validator.isNull(attachmentURL)) {
			if (Validator.isNull(productVirtualSettings.getAttachment())) {
				attachmentURL = cpDefinitionVirtualSetting.getUrl();
			}
			else {
				attachmentFileEntryId = FileEntryUtil.getFileEntryId(
					productVirtualSettings.getAttachment(), attachmentURL,
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

		String sampleAttachmentURL = null;
		long sampleFileEntryId = 0;

		boolean useSample = GetterUtil.getBoolean(
			productVirtualSettings.getUseSample(),
			cpDefinitionVirtualSetting.isUseSample());

		if (useSample) {
			sampleAttachmentURL = _validateURL(
				productVirtualSettings.getSampleUrl());

			if (Validator.isNull(sampleAttachmentURL)) {
				if (Validator.isNull(
						productVirtualSettings.getSampleAttachment())) {

					sampleAttachmentURL =
						cpDefinitionVirtualSetting.getSampleUrl();
				}
				else {
					sampleFileEntryId = FileEntryUtil.getFileEntryId(
						productVirtualSettings.getSampleAttachment(),
						sampleAttachmentURL, uniqueFileNameProvider,
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
				attachmentFileEntryId, attachmentURL,
				_getActivationStatus(
					GetterUtil.getInteger(
						productVirtualSettings.getActivationStatus(),
						cpDefinitionVirtualSetting.getActivationStatus())),
				GetterUtil.getLong(
					duration, cpDefinitionVirtualSetting.getDuration()),
				GetterUtil.getInteger(
					productVirtualSettings.getMaxUsages(),
					cpDefinitionVirtualSetting.getMaxUsages()),
				useSample, sampleFileEntryId, sampleAttachmentURL,
				termsOfUseRequired, termsOfUseContentMap,
				termsOfUseJournalArticleId, serviceContext);
	}

	private static String _validateURL(String value) throws Exception {
		if (Validator.isNotNull(value)) {
			URL url = new URL(value);

			return url.toString();
		}

		return null;
	}

}