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

package com.liferay.dynamic.data.mapping.form.field.type.internal.rich.text;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true, property = "ddm.form.field.type.name=rich_text",
	service = {
		DDMFormFieldValueRenderer.class, RichTextDDMFormFieldValueRenderer.class
	}
)
public class RichTextDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		Value value = ddmFormFieldValue.getValue();

		if (value == null) {
			return StringPool.BLANK;
		}

		return GetterUtil.getString(_html.extractText(value.getString(locale)));
	}

	@Reference
	private Html _html;

}