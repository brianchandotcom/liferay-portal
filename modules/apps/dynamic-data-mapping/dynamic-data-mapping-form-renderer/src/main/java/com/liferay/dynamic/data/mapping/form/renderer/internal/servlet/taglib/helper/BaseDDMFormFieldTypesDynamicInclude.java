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

package com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.taglib.helper;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.taglib.DDMFormFieldTypesDynamicInclude;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
public abstract class BaseDDMFormFieldTypesDynamicInclude
	extends BaseDynamicInclude {

	protected void include(HttpServletResponse response) throws IOException {
		ScriptData scriptData = new ScriptData();

		DDMFormFieldTypesSerializer ddmFormFieldTypesSerializer =
			ddmFormFieldTypesSerializerTracker.getDDMFormFieldTypesSerializer(
				"json");

		DDMFormFieldTypesSerializerSerializeRequest.Builder builder =
			DDMFormFieldTypesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes());

		DDMFormFieldTypesSerializerSerializeResponse
			ddmFormFieldTypesSerializerSerializeResponse =
				ddmFormFieldTypesSerializer.serialize(builder.build());

		scriptData.append(
			null,
			StringUtil.replaceToStringBundler(
				_TMPL_CONTENT, StringPool.POUND, StringPool.POUND,
				Collections.singletonMap(
					"fieldTypes",
					ddmFormFieldTypesSerializerSerializeResponse.getContent())),
			_MODULES, ScriptData.ModulesType.AUI);

		scriptData.writeTo(response.getWriter());
	}

	@Reference
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	@Reference
	protected DDMFormFieldTypesSerializerTracker
		ddmFormFieldTypesSerializerTracker;

	private static final String _MODULES =
		"liferay-ddm-form-renderer-types,liferay-ddm-soy-template-util";

	private static final String _TMPL_CONTENT = StringUtil.read(
		DDMFormFieldTypesDynamicInclude.class,
		"/META-INF/resources/dynamic_include/field_types.tmpl");

}