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

package com.liferay.journal.web.internal.display.context;

import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.translation.info.field.TranslationInfoFieldChecker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alejandro Tardín
 */
public class JournalTranslateDisplayContext {

	public JournalTranslateDisplayContext(
		LiferayPortletRequest liferayPortletRequest) {

		_liferayPortletRequest = liferayPortletRequest;

		_translationInfoFieldChecker =
			(TranslationInfoFieldChecker)liferayPortletRequest.getAttribute(
				TranslationInfoFieldChecker.class.getName());
	}

	public String getInfoFieldSetLabel(
		InfoFieldSetEntry infoFieldSetEntry, Locale locale) {

		if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			return infoFieldSet.getLabel(locale);
		}

		return null;
	}

	public List<InfoFieldValue<Object>> getInfoFieldValues(
		InfoFieldSetEntry infoFieldSetEntry,
		InfoItemFieldValues infoItemFieldValues) {

		if (infoFieldSetEntry instanceof InfoField) {
			InfoField infoField = (InfoField)infoFieldSetEntry;

			if (_translationInfoFieldChecker.isTranslatable(infoField)) {
				return Arrays.asList(
					infoItemFieldValues.getInfoFieldValue(infoField.getName()));
			}
		}
		else if (infoFieldSetEntry instanceof InfoFieldSet) {
			InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

			List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

			Stream<InfoField> stream = infoFields.stream();

			return stream.filter(
				_translationInfoFieldChecker::isTranslatable
			).map(
				InfoField::getName
			).map(
				infoItemFieldValues::getInfoFieldValue
			).collect(
				Collectors.toList()
			);
		}

		return Collections.emptyList();
	}

	private final LiferayPortletRequest _liferayPortletRequest;
	private final TranslationInfoFieldChecker _translationInfoFieldChecker;

}