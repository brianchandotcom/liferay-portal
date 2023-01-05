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

package com.liferay.segments.web.internal.field.customizer;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.segments.field.Field;
import com.liferay.segments.field.customizer.SegmentsFieldCustomizer;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = {
		"segments.field.customizer.entity.name=Context",
		"segments.field.customizer.key=" + LanguageSegmentsFieldCustomizer.KEY,
		"segments.field.customizer.priority:Integer=50"
	},
	service = SegmentsFieldCustomizer.class
)
public class LanguageSegmentsFieldCustomizer
	extends BaseSegmentsFieldCustomizer {

	public static final String KEY = "language";

	@Override
	public List<String> getFieldNames() {
		return _fieldNames;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public List<Field.Option> getOptions(Locale locale) {
		return TransformUtil.transform(
			_language.getAvailableLocales(),
			availableLocale -> new Field.Option(
				availableLocale.getDisplayName(locale),
				_language.getLanguageId(availableLocale)));
	}

	private static final List<String> _fieldNames = ListUtil.fromArray(
		"languageId");

	@Reference
	private Language _language;

}