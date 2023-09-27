/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuVirtualSettings;
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
	property = "dto.class.name=com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuVirtualSettings",
	service = DTOConverter.class
)
public class SkuVirtualSettingsDTOConverter
	implements DTOConverter<CPInstance, SkuVirtualSettings> {

	@Override
	public String getContentType() {
		return SkuVirtualSettings.class.getSimpleName();
	}

	@Override
	public SkuVirtualSettings toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (!VirtualCPTypeConstants.NAME.equals(
				cpDefinition.getProductTypeName())) {

			return null;
		}

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			_cpDefinitionVirtualSettingService.fetchCPDefinitionVirtualSetting(
				CPInstance.class.getName(), cpInstance.getCPInstanceId());

		if (cpDefinitionVirtualSetting == null) {
			return null;
		}

		return new SkuVirtualSettings() {
			{
				activationStatus =
					cpDefinitionVirtualSetting.getActivationStatus();
				duration = TimeUnit.MILLISECONDS.toDays(
					cpDefinitionVirtualSetting.getDuration());
				maxUsages = cpDefinitionVirtualSetting.getMaxUsages();
				override = cpDefinitionVirtualSetting.isOverride();
				sampleURL = cpDefinitionVirtualSetting.getSampleURL();
				termsOfUseContent = LanguageUtils.getLanguageIdMap(
					cpDefinitionVirtualSetting.getTermsOfUseContentMap());
				termsOfUseRequired =
					cpDefinitionVirtualSetting.isTermsOfUseRequired();
				url = cpDefinitionVirtualSetting.getUrl();
				useSample = cpDefinitionVirtualSetting.isUseSample();

				setActivationStatusInfo(
					() -> {
						String orderStatusLabel =
							CommerceOrderConstants.getOrderStatusLabel(
								cpDefinitionVirtualSetting.
									getActivationStatus());

						return new Status() {
							{
								code =
									cpDefinitionVirtualSetting.
										getActivationStatus();
								label = orderStatusLabel;
								label_i18n = _language.get(
									dtoConverterContext.getLocale(),
									orderStatusLabel);
							}
						};
					});
				setSampleSrc(
					() -> {
						FileEntry fileEntry =
							cpDefinitionVirtualSetting.getSampleFileEntry();

						if (fileEntry == null) {
							return null;
						}

						return _commerceMediaResolver.
							getDownloadVirtualProductSampleURL(
								CPInstance.class.getName(),
								cpInstance.getCPInstanceId(),
								AccountConstants.ACCOUNT_ENTRY_ID_ADMIN,
								fileEntry.getFileEntryId());
					});
				setSrc(
					() -> {
						FileEntry fileEntry =
							cpDefinitionVirtualSetting.getFileEntry();

						if (fileEntry == null) {
							return null;
						}

						return _commerceMediaResolver.
							getDownloadVirtualProductURL(
								CPInstance.class.getName(),
								cpInstance.getCPInstanceId(),
								AccountConstants.ACCOUNT_ENTRY_ID_ADMIN,
								fileEntry.getFileEntryId());
					});
				setTermsOfUseJournalArticleId(
					() -> {
						JournalArticle journalArticle =
							cpDefinitionVirtualSetting.
								getTermsOfUseJournalArticle();

						if (journalArticle == null) {
							return null;
						}

						return journalArticle.getResourcePrimKey();
					});
			}
		};
	}

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private Language _language;

}