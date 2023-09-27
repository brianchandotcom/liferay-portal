/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.builder.internal.context;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextResponse;
import com.liferay.dynamic.data.mapping.form.builder.internal.context.helper.DDMFormBuilderContextFactoryHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.json.JSONFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = DDMFormBuilderContextFactory.class)
public class DDMFormBuilderContextFactoryImpl
	implements DDMFormBuilderContextFactory {

	@Override
	public DDMFormBuilderContextResponse create(
		DDMFormBuilderContextRequest ddmFormBuilderContextRequest) {

		DDMFormBuilderContextFactoryHelper ddmFormBuilderContextFactoryHelper =
			new DDMFormBuilderContextFactoryHelper(
				ddmFormBuilderContextRequest.getProperty("ddmStructure"),
				ddmFormBuilderContextRequest.getProperty("ddmStructureVersion"),
				_ddmFormFieldTypeServicesRegistry,
				_ddmFormTemplateContextFactory,
				ddmFormBuilderContextRequest.getHttpServletRequest(),
				ddmFormBuilderContextRequest.getHttpServletResponse(),
				_jsonFactory, ddmFormBuilderContextRequest.getLocale(),
				_npmResolver,
				ddmFormBuilderContextRequest.getProperty("portletNamespace"),
				ddmFormBuilderContextRequest.getReadOnly());

		DDMFormBuilderContextResponse ddmFormBuilderContextResponse =
			new DDMFormBuilderContextResponse();

		ddmFormBuilderContextResponse.setContext(
			ddmFormBuilderContextFactoryHelper.create());

		return ddmFormBuilderContextResponse;
	}

	@Reference
	private DDMFormFieldTypeServicesRegistry _ddmFormFieldTypeServicesRegistry;

	@Reference
	private DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private NPMResolver _npmResolver;

}