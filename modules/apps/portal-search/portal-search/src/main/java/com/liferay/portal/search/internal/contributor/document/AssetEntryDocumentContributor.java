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

package com.liferay.portal.search.internal.contributor.document;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentContributor;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DocumentContributor.class)
public class AssetEntryDocumentContributor implements DocumentContributor {

	@Override
	public void contribute(Document document, BaseModel baseModel) {
		String className = document.get(Field.ENTRY_CLASS_NAME);

		long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSelectable()) {

			return;
		}

		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
			className, classPK);

		if (assetEntry == null) {
			return;
		}

		if (!document.hasField(Field.CREATE_DATE)) {
			document.addDate(Field.CREATE_DATE, assetEntry.getCreateDate());
		}

		if (assetEntry.getExpirationDate() != null) {
			document.addDate(
				Field.EXPIRATION_DATE, assetEntry.getExpirationDate());
		}
		else {
			document.addDate(Field.EXPIRATION_DATE, new Date(Long.MAX_VALUE));
		}

		if (!document.hasField(Field.MODIFIED_DATE)) {
			document.addDate(Field.MODIFIED_DATE, assetEntry.getModifiedDate());
		}

		document.addNumber(Field.PRIORITY, assetEntry.getPriority());

		if (assetEntry.getPublishDate() != null) {
			document.addDate(Field.PUBLISH_DATE, assetEntry.getPublishDate());
		}
		else {
			document.addDate(Field.PUBLISH_DATE, new Date(0));
		}

		document.addLocalizedKeyword(
			"localized_title",
			populateMap(assetEntry, assetEntry.getTitleMap()), true, true);
		document.addKeyword("visible", assetEntry.isVisible());
	}

	protected Map<Locale, String> populateMap(
		AssetEntry assetEntry, Map<Locale, String> map) {

		String defaultValue = map.get(
			LocaleUtil.fromLanguageId(assetEntry.getDefaultLanguageId()));

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(assetEntry.getGroupId())) {

			if (!map.containsKey(availableLocale) ||
				Validator.isNull(map.get(availableLocale))) {

				map.put(availableLocale, defaultValue);
			}
		}

		return map;
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

}