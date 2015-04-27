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

package com.liferay.portlet.dynamicdatamapping.render.bundle.ddmformfieldrendererregistryimpl;

import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {
		"layout.type=testDDMFormFieldRenderer",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestDDMFormFieldRenderer implements DDMFormFieldRenderer {

	public static final String SUPPORTED_DDM_FIELD_TYPE = "text";

	@Override
	public String[] getSupportedDDMFormFieldTypes() {
		List<String> supportedDDMFormFieldTypes = Arrays.asList(
			SUPPORTED_DDM_FIELD_TYPE);

		return (String[])supportedDDMFormFieldTypes.toArray();
	}

	@Override
	public String render(DDMFormField ddmFormField, DDMFormFieldRenderingContext
		ddmFormFieldRenderingContext) {

		return null;
	}

}