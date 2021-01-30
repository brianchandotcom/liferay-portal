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
import com.liferay.dynamic.data.mapping.form.builder.internal.configuration.FFDDMFormSidebarConfigurationActivator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DDMFormBuilderContextFactory.class)
public class DDMFormBuilderContextFactoryImpl
	implements DDMFormBuilderContextFactory {

	@Override
	public DDMFormBuilderContextResponse create(
		DDMFormBuilderContextRequest ddmFormBuilderContextRequest) {

		Optional<DDMStructure> ddmStructureOptional = Optional.ofNullable(
			ddmFormBuilderContextRequest.getProperty("ddmStructure"));
		Optional<DDMStructureVersion> ddmStructureVersionOptional =
			Optional.ofNullable(
				ddmFormBuilderContextRequest.getProperty(
					"ddmStructureVersion"));
		String portletNamespace = GetterUtil.getString(
			ddmFormBuilderContextRequest.getProperty("portletNamespace"));

		DDMFormBuilderContextFactoryHelper ddmFormBuilderContextFactoryHelper =
			new DDMFormBuilderContextFactoryHelper(
				ddmStructureOptional, ddmStructureVersionOptional,
				_ddmFormFieldTypeServicesTracker,
				_ffDDMFormSidebarConfigurationActivator,
				_ddmFormTemplateContextFactory,
				ddmFormBuilderContextRequest.getHttpServletRequest(),
				ddmFormBuilderContextRequest.getHttpServletResponse(),
				_jsonFactory, ddmFormBuilderContextRequest.getLocale(),
				_npmResolver, portletNamespace,
				ddmFormBuilderContextRequest.getReadOnly());

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
	private FFDDMFormSidebarConfigurationActivator
		_ffDDMFormSidebarConfigurationActivator;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

}