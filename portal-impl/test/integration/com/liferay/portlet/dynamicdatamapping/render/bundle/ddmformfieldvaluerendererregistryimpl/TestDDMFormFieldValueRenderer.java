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

package com.liferay.portlet.dynamicdatamapping.render.bundle.ddmformfieldvaluerendererregistryimpl;

import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldValueRenderer;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {
		"layout.type=testDDMFormFieldValueRenderer",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	public static final String DDM_FORM_FIELD_TYPE = "checkbox";

	@Override
	public String getSupportedDDMFormFieldType() {
		return DDM_FORM_FIELD_TYPE;
	}

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValues, Locale locale) {
		return null;
	}

	@Override
	public String render(List<DDMFormFieldValue> ddmFormFieldValue, Locale
		locale) {

		return null;
	}

}