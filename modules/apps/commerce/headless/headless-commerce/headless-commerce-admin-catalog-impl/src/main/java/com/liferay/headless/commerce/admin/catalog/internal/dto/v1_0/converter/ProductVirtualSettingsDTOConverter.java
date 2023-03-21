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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductVirtualSettings;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Status;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "dto.class.name=com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductVirtualSettings",
	service = {DTOConverter.class, ProductVirtualSettingsDTOConverter.class}
)
public class ProductVirtualSettingsDTOConverter
	implements DTOConverter<CPDefinition, ProductVirtualSettings> {

	@Override
	public String getContentType() {
		return ProductVirtualSettings.class.getSimpleName();
	}

	@Override
	public ProductVirtualSettings toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			(Long)dtoConverterContext.getId());

		String cpTypeName = cpDefinition.getProductTypeName();

		if (VirtualCPTypeConstants.NAME.equals(cpTypeName)) {
			CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					fetchCPDefinitionVirtualSetting(
						CPDefinition.class.getName(),
						cpDefinition.getCPDefinitionId());

			if (cpDefinitionVirtualSetting != null) {
				return new ProductVirtualSettings() {
					{
						activationStatus =
							cpDefinitionVirtualSetting.getActivationStatus();
						duration = TimeUnit.MILLISECONDS.toDays(
							cpDefinitionVirtualSetting.getDuration());
						maxUsages = cpDefinitionVirtualSetting.getMaxUsages();
						sampleUrl = cpDefinitionVirtualSetting.getSampleUrl();
						termsOfUseContent = LanguageUtils.getLanguageIdMap(
							cpDefinitionVirtualSetting.
								getTermsOfUseContentMap());
						termsOfUseRequired =
							cpDefinitionVirtualSetting.isTermsOfUseRequired();
						url = cpDefinitionVirtualSetting.getUrl();
						useSample = cpDefinitionVirtualSetting.isUseSample();

						setActivationStatusInfo(
							() -> {
								String activationStatusLabel =
									CommerceOrderConstants.getOrderStatusLabel(
										cpDefinitionVirtualSetting.
											getActivationStatus());

								return new Status() {
									{
										code =
											cpDefinitionVirtualSetting.
												getActivationStatus();
										label = activationStatusLabel;
										label_i18n = _language.get(
											dtoConverterContext.getLocale(),
											activationStatusLabel);
									}
								};
							});

						setSampleSrc(
							() -> {
								FileEntry fileEntry =
									cpDefinitionVirtualSetting.
										getSampleFileEntry();

								if (fileEntry != null) {
									return _commerceMediaResolver.
										getDownloadVirtualProductSampleURL(
											CommerceAccountConstants.
												ACCOUNT_ID_ADMIN,
											cpDefinition.getCPDefinitionId(),
											fileEntry.getFileEntryId());
								}

								return null;
							});

						setSrc(
							() -> {
								FileEntry fileEntry =
									cpDefinitionVirtualSetting.getFileEntry();

								if (fileEntry != null) {
									return _commerceMediaResolver.
										getDownloadVirtualProductURL(
											CommerceAccountConstants.
												ACCOUNT_ID_ADMIN,
											cpDefinition.getCPDefinitionId(),
											fileEntry.getFileEntryId());
								}

								return null;
							});

						setTermsOfUseJournalArticleId(
							() -> {
								JournalArticle termsOfUseJournalArticle =
									cpDefinitionVirtualSetting.
										getTermsOfUseJournalArticle();

								if (termsOfUseJournalArticle != null) {
									return termsOfUseJournalArticle.
										getResourcePrimKey();
								}

								return null;
							});
					}
				};
			}
		}

		return null;
	}

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

	@Reference
	private Language _language;

}