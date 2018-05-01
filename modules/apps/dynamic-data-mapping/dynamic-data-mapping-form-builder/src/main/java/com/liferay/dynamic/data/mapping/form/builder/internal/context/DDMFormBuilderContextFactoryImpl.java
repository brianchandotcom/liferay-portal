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

package com.liferay.dynamic.data.mapping.form.builder.internal.context;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextResponse;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DDMFormBuilderContextFactory.class)
public class DDMFormBuilderContextFactoryImpl
	implements DDMFormBuilderContextFactory {

	public DDMFormBuilderContextResponse create(
		DDMFormBuilderContextRequest ddmFormBuilderContextRequest) {

		Optional<DDMStructure> ddmStructureOptional =
			ddmFormBuilderContextRequest.getProperty("ddmStructureOptional");

		HttpServletRequest request =
			ddmFormBuilderContextRequest.getHttpServletRequest();

		HttpServletResponse response =
			ddmFormBuilderContextRequest.getHttpServletResponse();

		Locale locale = ddmFormBuilderContextRequest.getLocale();

		boolean readOnly = ddmFormBuilderContextRequest.getReadOnly();

		DDMFormBuilderContextFactoryHelper ddmFormBuilderContextFactoryHelper =
			new DDMFormBuilderContextFactoryHelper(
				ddmStructureOptional, _ddmFormFieldTypeServicesTracker,
				_ddmFormTemplateContextFactory, request, response, _jsonFactory,
				locale, readOnly);

		DDMFormBuilderContextResponse ddmFormBuilderContextResponse =
			new DDMFormBuilderContextResponse();

		ddmFormBuilderContextResponse.setContext(
			ddmFormBuilderContextFactoryHelper.create());

		return ddmFormBuilderContextResponse;
	}

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private JSONFactory _jsonFactory;

}