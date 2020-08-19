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

package com.liferay.translation.web.internal.display.context;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;
import com.liferay.translation.snapshot.TranslationSnapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class ViewTranslationDisplayContext {

	public ViewTranslationDisplayContext(
		HttpServletRequest httpServletRequest, InfoForm infoForm,
		TranslationInfoFieldChecker translationInfoFieldChecker,
		TranslationSnapshot translationSnapshot) {

		_httpServletRequest = httpServletRequest;
		_infoForm = infoForm;
		_translationInfoFieldChecker = translationInfoFieldChecker;
		_translationSnapshot = translationSnapshot;
	}

	public boolean getBooleanValue(
		InfoField infoField,
		InfoFieldType.Attribute<TextInfoFieldType, Boolean> attribute) {

		Optional<Boolean> attributeOptional = infoField.getAttributeOptional(
			attribute);

		return attributeOptional.orElse(false);
	}

	public String getInfoFieldLabel(InfoField infoField) {
		InfoLocalizedValue<String> labelInfoLocalizedValue =
			infoField.getLabelInfoLocalizedValue();

		return labelInfoLocalizedValue.getValue(
			PortalUtil.getLocale(_httpServletRequest));
	}

	public List<InfoField> getInfoFields(InfoFieldSetEntry infoFieldSetEntry) {
		if (infoFieldSetEntry instanceof InfoField) {
			InfoField infoField = (InfoField)infoFieldSetEntry;

			if (_translationInfoFieldChecker.isTranslatable(infoField)) {
				return Arrays.asList(infoField);
			}
		}
		else if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			return ListUtil.filter(
				infoFieldSet.getAllInfoFields(),
				_translationInfoFieldChecker::isTranslatable);
		}

		return Collections.emptyList();
	}

	public List<InfoFieldSetEntry> getInfoFieldSetEntries() {
		return _infoForm.getInfoFieldSetEntries();
	}

	public String getInfoFieldSetLabel(
		InfoFieldSetEntry infoFieldSetEntry, Locale locale) {

		if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			return infoFieldSet.getLabel(locale);
		}

		return null;
	}

	public String getLanguageIdTitle(String languageId) {
		return StringUtil.replace(
			languageId, CharPool.UNDERLINE, CharPool.DASH);
	}

	public String getSourceLanguageId() {
		return LanguageUtil.getLanguageId(getSourceLocale());
	}

	public Locale getSourceLocale() {
		return _translationSnapshot.getSourceLocale();
	}

	public String getStringValue(InfoField infoField, Locale locale) {
		InfoItemFieldValues infoItemFieldValues =
			_translationSnapshot.getInfoItemFieldValues();

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(infoField.getName());

		if (infoFieldValue != null) {
			return GetterUtil.getString(infoFieldValue.getValue(locale));
		}

		return null;
	}

	public String getTargetLanguageId() {
		return LanguageUtil.getLanguageId(getTargetLocale());
	}

	public Locale getTargetLocale() {
		return _translationSnapshot.getTargetLocale();
	}

	private final HttpServletRequest _httpServletRequest;
	private final InfoForm _infoForm;
	private final TranslationInfoFieldChecker _translationInfoFieldChecker;
	private final TranslationSnapshot _translationSnapshot;

}