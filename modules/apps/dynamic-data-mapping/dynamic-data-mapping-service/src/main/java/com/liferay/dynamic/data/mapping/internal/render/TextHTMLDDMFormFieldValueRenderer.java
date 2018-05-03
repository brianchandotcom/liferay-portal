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

package com.liferay.dynamic.data.mapping.internal.render;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.render.ValueAccessor;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class TextHTMLDDMFormFieldValueRenderer
	extends BaseTextDDMFormFieldValueRenderer {

	@Override
	public String getSupportedDDMFormFieldType() {
		return DDMFormFieldType.TEXT_HTML;
	}

	@Override
	protected ValueAccessor getValueAcessor(Locale locale) {
		return new ValueAccessor(locale) {

			@Override
			public String get(DDMFormFieldValue ddmFormFieldValue) {
				Value value = ddmFormFieldValue.getValue();

				return StringUtil.replace(
					_HTML,
					new String[] {"[$DDM_FORM_FIELD_VALUE$]", "[$PREVIEW$]"},
					new String[] {
						HtmlUtil.escapeJS(value.getString(locale)),
						LanguageUtil.get(locale, "preview")
					});
			}

		};
	}

	private static final String _HTML =
		"<a href=\"javascript:;\" onclick=\"Liferay.DDLUtil." +
			"openPreviewDialog('[$DDM_FORM_FIELD_VALUE$]');\">([$PREVIEW$])" +
				"</a>";

}