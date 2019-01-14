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

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.taglib.DDMFormFieldTypesDynamicInclude;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerTracker;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
public abstract class BaseDDMFormFieldTypesDynamicInclude
	extends BaseDynamicInclude {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected String getJavaScriptPackageName(Bundle bundle) throws Exception {
		JSONObject jsonObject = getPackageJSONObject(bundle);

		if (jsonObject == null) {
			return null;
		}

		return jsonObject.getString("name");
	}

	protected String getJavaScriptTemplateDependencies(
		List<DDMFormFieldType> ddmFormFieldTypes) {

		Set<String> javaScriptDependencies = new HashSet<>();

		for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
			Map<String, Object> ddmFormFieldTypeProperties =
				ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
					ddmFormFieldType.getName());

			long bundleId = GetterUtil.getLong(
				ddmFormFieldTypeProperties.get("service.bundleid"), -1L);

			Bundle bundle = _bundleContext.getBundle(bundleId);

			try {
				String javaScriptPackageName = getJavaScriptPackageName(bundle);

				for (JSPackage jsPackage :
						npmRegistry.getResolvedJSPackages()) {

					if (StringUtil.equals(
							jsPackage.getName(), javaScriptPackageName)) {

						for (JSModule jsModule : jsPackage.getJSModules()) {
							javaScriptDependencies.add(
								jsModule.getResolvedId());
						}

						break;
					}
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}

		return jsonFactory.looseSerialize(
			ListUtil.fromCollection(javaScriptDependencies));
	}

	protected JSONObject getPackageJSONObject(Bundle bundle) throws Exception {
		URL url = bundle.getEntry("package.json");

		if (url == null) {
			return null;
		}

		String json = StringUtil.read(url.openStream());

		return JSONFactoryUtil.createJSONObject(json);
	}

	protected void include(HttpServletResponse response) throws IOException {
		ScriptData scriptData = new ScriptData();

		DDMFormFieldTypesSerializer ddmFormFieldTypesSerializer =
			ddmFormFieldTypesSerializerTracker.getDDMFormFieldTypesSerializer(
				"json");

		List<DDMFormFieldType> ddmFormFieldTypes =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		DDMFormFieldTypesSerializerSerializeRequest.Builder builder =
			DDMFormFieldTypesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormFieldTypes);

		DDMFormFieldTypesSerializerSerializeResponse
			ddmFormFieldTypesSerializerSerializeResponse =
				ddmFormFieldTypesSerializer.serialize(builder.build());

		Map<String, String> values = new HashMap<>();

		values.put(
			"fieldTypes",
			ddmFormFieldTypesSerializerSerializeResponse.getContent());

		values.put(
			"javaScriptTemplateDependencies",
			getJavaScriptTemplateDependencies(ddmFormFieldTypes));

		scriptData.append(
			null,
			StringUtil.replaceToStringBundler(
				_TMPL_CONTENT, StringPool.POUND, StringPool.POUND, values),
			_MODULES, ScriptData.ModulesType.AUI);

		scriptData.writeTo(response.getWriter());
	}

	@Reference
	protected DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker;

	@Reference
	protected DDMFormFieldTypesSerializerTracker
		ddmFormFieldTypesSerializerTracker;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected NPMRegistry npmRegistry;

	private static final String _MODULES =
		"liferay-ddm-form-renderer-types,liferay-ddm-soy-template-util";

	private static final String _TMPL_CONTENT = StringUtil.read(
		DDMFormFieldTypesDynamicInclude.class,
		"/META-INF/resources/dynamic_include/field_types.tmpl");

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDDMFormFieldTypesDynamicInclude.class);

	private BundleContext _bundleContext;

}