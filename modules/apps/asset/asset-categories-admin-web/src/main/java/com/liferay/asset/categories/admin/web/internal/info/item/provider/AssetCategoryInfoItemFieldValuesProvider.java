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

package com.liferay.asset.categories.admin.web.internal.info.item.provider;

import com.liferay.asset.categories.admin.web.internal.info.item.AssetCategoryInfoItemFields;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.field.reader.InfoItemFieldReaderFieldSetProvider;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = InfoItemFieldValuesProvider.class)
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

		assetCategoryInfoFieldValues.add(
			new InfoFieldValue<>(
				AssetCategoryInfoItemFields.nameInfoField,
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

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.fetchAssetVocabulary(
				assetCategory.getVocabularyId());

		if (assetVocabulary != null) {
			assetCategoryInfoFieldValues.add(
				new InfoFieldValue<>(
					AssetCategoryInfoItemFields.vocabularyInfoField,
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.fromLanguageId(
							assetVocabulary.getDefaultLanguageId())
					).values(
						assetVocabulary.getTitleMap()
					).build()));
		}

		ThemeDisplay themeDisplay = _getThemeDisplay();

		if (themeDisplay != null) {
			try {
				assetCategoryInfoFieldValues.add(
					new InfoFieldValue<>(
						AssetCategoryInfoItemFields.displayPageURLInfoField,
						_getDisplayPageURL(assetCategory, themeDisplay)));
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}
			}
		}

		return assetCategoryInfoFieldValues;
	}

	private String _getDisplayPageURL(
			AssetCategory assetCategory, ThemeDisplay themeDisplay)
		throws PortalException {

		LayoutDisplayPageProvider layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					AssetCategory.class.getName());

		if (!AssetDisplayPageUtil.hasAssetDisplayPage(
				assetCategory.getGroupId(),
				_portal.getClassNameId(AssetCategory.class.getName()),
				assetCategory.getCategoryId(), 0) ||
			(layoutDisplayPageProvider == null)) {

			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(themeDisplay.getPathContext());
		sb.append(themeDisplay.getPathFriendlyURLPublic());

		Group group = themeDisplay.getScopeGroup();

		sb.append(group.getFriendlyURL());

		sb.append(layoutDisplayPageProvider.getURLSeparator());
		sb.append(assetCategory.getCategoryId());

		return sb.toString();
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
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private InfoItemFieldReaderFieldSetProvider
		_infoItemFieldReaderFieldSetProvider;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

}