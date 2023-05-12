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

package com.liferay.asset.categories.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.localization.SearchLocalizationHelper;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(
	property = "indexer.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = ModelDocumentContributor.class
)
public class AssetCategoryModelDocumentContributor
	implements ModelDocumentContributor<AssetCategory> {

	@Override
	public void contribute(Document document, AssetCategory assetCategory) {
		document.addKeyword(
			Field.ASSET_CATEGORY_ID, assetCategory.getCategoryId());

		_addSearchAssetCategoryTitles(
			document, Field.ASSET_CATEGORY_TITLE,
			Collections.singletonList(assetCategory));

		document.addKeyword(
			Field.ASSET_PARENT_CATEGORY_ID,
			assetCategory.getParentCategoryId());
		document.addKeyword(
			Field.ASSET_VOCABULARY_ID, assetCategory.getVocabularyId());

		String[] availableLanguageIds = _localization.getAvailableLanguageIds(
			assetCategory.getDescription());

		for (String availableLanguageId : availableLanguageIds) {
			document.addText(
				_localization.getLocalizedName(
					Field.DESCRIPTION, availableLanguageId),
				_html.stripHtml(
					assetCategory.getDescription(availableLanguageId)));
		}

		Locale siteDefaultLocale = getSiteDefaultLocale(assetCategory);

		document.addText(
			Field.DESCRIPTION,
			_html.stripHtml(assetCategory.getDescription(siteDefaultLocale)));

		document.addText(Field.NAME, assetCategory.getName());

		_searchLocalizationHelper.addLocalizedField(
			document, Field.TITLE, siteDefaultLocale,
			assetCategory.getTitleMap());

		document.addKeyword("treePath", assetCategory.getTreePath());
		document.addLocalizedKeyword(
			"localized_title",
			_localization.populateLocalizationMap(
				assetCategory.getTitleMap(),
				assetCategory.getDefaultLanguageId(),
				assetCategory.getGroupId()),
			true, true);
	}

	protected Locale getSiteDefaultLocale(AssetCategory assetCategory) {
		try {
			return portal.getSiteDefaultLocale(assetCategory.getGroupId());
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	@Reference
	protected Portal portal;

	private void _addSearchAssetCategoryTitles(
		Document document, String field, List<AssetCategory> assetCategories) {

		Map<Locale, List<String>> assetCategoryTitles = new HashMap<>();

		Locale defaultLocale = LocaleUtil.getDefault();

		for (AssetCategory assetCategory : assetCategories) {
			Map<Locale, String> titleMap = assetCategory.getTitleMap();

			for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
				String title = entry.getValue();

				if (Validator.isBlank(title)) {
					continue;
				}

				Locale locale = entry.getKey();

				List<String> titles = assetCategoryTitles.get(locale);

				if (titles == null) {
					titles = new ArrayList<>();

					assetCategoryTitles.put(locale, titles);
				}

				titles.add(StringUtil.toLowerCase(title));
			}
		}

		for (Map.Entry<Locale, List<String>> entry :
				assetCategoryTitles.entrySet()) {

			Locale locale = entry.getKey();

			List<String> titles = entry.getValue();

			String[] titlesArray = titles.toArray(new String[0]);

			if (locale.equals(defaultLocale)) {
				document.addText(field, titlesArray);
			}

			document.addText(
				StringBundler.concat(field, StringPool.UNDERLINE, locale),
				titlesArray);
		}
	}

	@Reference
	private Html _html;

	@Reference
	private Localization _localization;

	@Reference
	private SearchLocalizationHelper _searchLocalizationHelper;

}