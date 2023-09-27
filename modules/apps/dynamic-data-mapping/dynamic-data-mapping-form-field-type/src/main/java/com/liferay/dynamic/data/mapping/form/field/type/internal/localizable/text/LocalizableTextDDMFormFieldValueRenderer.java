/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.localizable.text;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.LOCALIZABLE_TEXT,
	service = DDMFormFieldValueRenderer.class
)
public class LocalizableTextDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		JSONObject jsonObject =
			localizableTextDDMFormFieldValueAccessor.getValue(
				ddmFormFieldValue, locale);

		return jsonObject.toString();
	}

	@Reference(
		target = "(ddm.form.field.type.name=" + DDMFormFieldTypeConstants.LOCALIZABLE_TEXT + ")"
	)
	protected DDMFormFieldValueAccessor<JSONObject>
		localizableTextDDMFormFieldValueAccessor;

}