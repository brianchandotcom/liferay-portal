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

package com.liferay.asset.info.internal.item.provider;

import com.liferay.asset.info.internal.item.AssetEntryInfoItemFields;
import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.text.Format;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemFieldValuesProvider.class)
public class AssetEntryInfoItemFieldValuesProvider
	implements InfoItemFieldValuesProvider<AssetEntry> {

	@Override
	public InfoItemFieldValues getInfoItemFieldValues(AssetEntry assetEntry) {
		InfoItemFieldValues infoItemFieldValues = new InfoItemFieldValues(
			new InfoItemClassPKReference(
				AssetEntry.class.getName(), assetEntry.getEntryId()));

		infoItemFieldValues.addAll(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(assetEntry));
		infoItemFieldValues.addAll(_getAssetEntryInfoFieldValues(assetEntry));

		return infoItemFieldValues;
	}

	private List<InfoFieldValue<Object>> _getAssetEntryInfoFieldValues(
		AssetEntry assetEntry) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return Arrays.asList(
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.titleInfoField,
				assetEntry.getTitle(locale)),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.descriptionInfoField,
				assetEntry.getDescription(locale)),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.summaryInfoField,
				assetEntry.getSummary(locale)),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.userNameInfoField,
				assetEntry.getUserName()),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.createDateInfoField,
				_getDateValue(assetEntry.getCreateDate())),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.modifiedDateInfoField,
				_getDateValue(assetEntry.getModifiedDate())),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.expirationDateInfoField,
				_getDateValue(assetEntry.getExpirationDate())),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.viewCountInfoField,
				assetEntry.getViewCount()),
			new InfoFieldValue<>(
				AssetEntryInfoItemFields.urlInfoField, assetEntry.getUrl()));
	}

	private String _getDateValue(Date date) {
		if (date == null) {
			return StringPool.BLANK;
		}

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(
			locale);

		return dateFormatDateTime.format(date);
	}

	@Reference
	private AssetEntryInfoItemFieldSetProvider
		_assetEntryInfoItemFieldSetProvider;

}