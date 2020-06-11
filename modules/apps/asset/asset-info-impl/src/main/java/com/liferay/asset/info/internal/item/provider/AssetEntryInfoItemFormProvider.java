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

import com.liferay.asset.info.item.provider.AssetEntryInfoItemFieldSetProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
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
@Component(service = InfoItemFormProvider.class)
public class AssetEntryInfoItemFormProvider
	implements InfoItemFormProvider<AssetEntry> {

	@Override
	public InfoForm getInfoForm() {
		return new InfoForm.Builder(
			AssetEntry.class.getName()
		).add(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldSet(
				AssetEntry.class.getName())
		).addAll(
			_getAssetEntryFieldSetEntries()
		).build();
	}

	@Override
	public InfoFormValues getInfoFormValues(AssetEntry assetEntry) {
		return new InfoFormValues.Builder().addAll(
			_assetEntryInfoItemFieldSetProvider.getInfoFieldValues(assetEntry)
		).addAll(
			_getAssetEntryInfoFieldValues(assetEntry)
		).setInfoItemClassPKReference(
			new InfoItemClassPKReference(
				AssetEntry.class.getName(), assetEntry.getEntryId())
		).build();
	}

	private List<InfoFieldSetEntry> _getAssetEntryFieldSetEntries() {
		return Arrays.asList(
			_titleInfoField, _descriptionInfoField, _summaryInfoField,
			_userNameInfoField, _createDateInfoField, _modifiedDateInfoField,
			_expirationDateInfoField, _viewCountInfoField, _urlInfoField);
	}

	private List<InfoFieldValue<Object>> _getAssetEntryInfoFieldValues(
		AssetEntry assetEntry) {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return Arrays.asList(
			new InfoFieldValue<>(_titleInfoField, assetEntry.getTitle(locale)),
			new InfoFieldValue<>(
				_descriptionInfoField, assetEntry.getDescription(locale)),
			new InfoFieldValue<>(
				_summaryInfoField, assetEntry.getSummary(locale)),
			new InfoFieldValue<>(_userNameInfoField, assetEntry.getUserName()),
			new InfoFieldValue<>(
				_createDateInfoField,
				_getDateValue(assetEntry.getCreateDate())),
			new InfoFieldValue<>(
				_modifiedDateInfoField,
				_getDateValue(assetEntry.getModifiedDate())),
			new InfoFieldValue<>(
				_expirationDateInfoField,
				_getDateValue(assetEntry.getExpirationDate())),
			new InfoFieldValue<>(
				_viewCountInfoField, assetEntry.getViewCount()),
			new InfoFieldValue<>(_urlInfoField, assetEntry.getUrl()));
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

	private final InfoField _createDateInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "create-date"), "createDate"
	).build();
	private final InfoField _descriptionInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "description"), "description"
	).build();
	private final InfoField _expirationDateInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "expiration-date"),
		"expirationDate"
	).build();
	private final InfoField _modifiedDateInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "modified-date"), "modifiedDate"
	).build();
	private final InfoField _summaryInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "summary"), "summary"
	).build();
	private final InfoField _titleInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "title"), "title"
	).build();
	private final InfoField _urlInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "url"), "url"
	).build();
	private final InfoField _userNameInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "user-name"), "userName"
	).build();
	private final InfoField _viewCountInfoField = new InfoField.Builder(
		TextInfoFieldType.INSTANCE,
		InfoLocalizedValue.localize(getClass(), "view-count"), "viewName"
	).build();

}