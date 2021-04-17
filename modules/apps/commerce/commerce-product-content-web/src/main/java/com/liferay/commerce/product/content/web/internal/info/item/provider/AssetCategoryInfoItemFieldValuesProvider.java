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

package com.liferay.commerce.product.content.web.internal.info.item.provider;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.media.CommerceMediaResolver;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.content.web.internal.info.AssetCategoryInfoItemFields;
import com.liferay.commerce.product.content.web.internal.info.CProductInfoItemFields;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.url.CPFriendlyURL;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true,
	service = InfoItemFieldValuesProvider.class
)
public class AssetCategoryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<AssetCategory> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(
		AssetCategory assetCategory) {

		return InfoItemFieldValues.builder(
		).infoFieldValues(
			_getAssetCategoryInfoFieldValues(assetCategory)
		).infoFieldValues(
			_infoItemFieldReaderFieldSetProvider.getInfoFieldValues(
				AssetCategory.class.getName(), assetCategory)
		).infoItemReference(
			new InfoItemReference(
				AssetCategory.class.getName(), assetCategory.getCategoryId())
		).build();
	}

	private List<InfoFieldValue<Object>> _getAssetCategoryInfoFieldValues(
		AssetCategory assetCategory) {

		List<InfoFieldValue<Object>> assetCategoryInfoFieldValues =
			new ArrayList<>();

		try {
			assetCategoryInfoFieldValues.add(
				new InfoFieldValue<>(
					AssetCategoryInfoItemFields.titleInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							assetCategory.getDefaultLanguageId())
					).values(
						assetCategory.getTitleMap()
					).build()));

			assetCategoryInfoFieldValues.add(
				new InfoFieldValue<>(
					AssetCategoryInfoItemFields.descriptionInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							assetCategory.getDefaultLanguageId())
					).values(
						assetCategory.getDescriptionMap()
					).build()));

			List<CPAttachmentFileEntry> cpAttachmentFileEntries =
				_cpAttachmentFileEntryService.getCPAttachmentFileEntries(
					_portal.getClassNameId(AssetCategory.class),
					assetCategory.getCategoryId(),
					CPAttachmentFileEntryConstants.TYPE_IMAGE,
					WorkflowConstants.STATUS_APPROVED, 0, 1);

			if (!cpAttachmentFileEntries.isEmpty()) {
				CPAttachmentFileEntry cpAttachmentFileEntry =
					cpAttachmentFileEntries.get(0);

				if (cpAttachmentFileEntry != null) {
					long commerceAccountId = 0;

					ThemeDisplay themeDisplay = _getThemeDisplay();

					HttpServletRequest httpServletRequest =
						themeDisplay.getRequest();

					CommerceContext commerceContext =
						(CommerceContext)httpServletRequest.getAttribute(
							CommerceWebKeys.COMMERCE_CONTEXT);

					CommerceAccount commerceAccount =
						commerceContext.getCommerceAccount();

					if (commerceAccount != null) {
						commerceAccountId =
							commerceAccount.getCommerceAccountId();
					}

					assetCategoryInfoFieldValues.add(
						new InfoFieldValue<>(
							CProductInfoItemFields.defaultImage,
							_commerceMediaResolver.getURL(
								commerceAccountId,
								cpAttachmentFileEntry.
									getCPAttachmentFileEntryId())));
				}
			}

			ThemeDisplay themeDisplay = _getThemeDisplay();

			if (themeDisplay != null) {
				FriendlyURLEntry friendlyURLEntry = null;

				try {
					friendlyURLEntry =
						_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
							_portal.getClassNameId(AssetCategory.class),
							assetCategory.getCategoryId());
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}
				}

				String groupFriendlyURL = _portal.getGroupFriendlyURL(
					themeDisplay.getLayoutSet(), themeDisplay);

				String separator = _cpFriendlyURL.getAssetCategoryURLSeparator(
					themeDisplay.getCompanyId());

				String languageId = LanguageUtil.getLanguageId(
					themeDisplay.getLocale());

				String friendlyUrl =
					groupFriendlyURL + separator +
						friendlyURLEntry.getUrlTitle(languageId);

				assetCategoryInfoFieldValues.add(
					new InfoFieldValue<>(
						AssetCategoryInfoItemFields.displayPageUrlInfoField,
						friendlyUrl));
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return assetCategoryInfoFieldValues;
	}

	private ThemeDisplay _getThemeDisplay() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext != null) {
			return serviceContext.getThemeDisplay();
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryInfoItemFieldValuesProvider.class);

	@Reference
	private CommerceMediaResolver _commerceMediaResolver;

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPFriendlyURL _cpFriendlyURL;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private Portal _portal;

}