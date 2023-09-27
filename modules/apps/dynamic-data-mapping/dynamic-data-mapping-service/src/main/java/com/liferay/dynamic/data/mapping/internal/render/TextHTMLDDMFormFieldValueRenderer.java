/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	protected ValueAccessor getValueAccessor(Locale locale) {
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
		"<a href=\"javascript:void(0);\" onclick=\"Liferay.DDLUtil." +
			"openPreviewDialog('[$DDM_FORM_FIELD_VALUE$]');\">([$PREVIEW$])" +
				"</a>";

}