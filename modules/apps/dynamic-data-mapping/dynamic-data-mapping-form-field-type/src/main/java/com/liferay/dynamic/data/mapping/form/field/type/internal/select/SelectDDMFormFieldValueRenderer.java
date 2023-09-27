/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueAccessor;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldValueUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.Html;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Renato Rego
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SELECT,
	service = DDMFormFieldValueRenderer.class
)
public class SelectDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		return _html.escape(
			DDMFormFieldValueUtil.getOptionsLabels(
				ddmFormFieldValue, locale,
				DDMFormFieldValueUtil::isManualDataSourceType));
	}

	@Reference(
		target = "(ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SELECT + ")"
	)
	protected DDMFormFieldValueAccessor<JSONArray>
		selectDDMFormFieldValueAccessor;

	@Reference
	private Html _html;

}