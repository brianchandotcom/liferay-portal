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

package com.liferay.dynamic.data.mapping.form.builder.internal.settings;

import com.liferay.dynamic.data.mapping.form.builder.internal.converter.DDMFormRuleConverter;
import com.liferay.dynamic.data.mapping.form.builder.internal.converter.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.form.builder.internal.util.DDMExpressionFunctionMetadataHelper;
import com.liferay.dynamic.data.mapping.form.builder.internal.util.DDMExpressionFunctionMetadataHelper.DDMExpressionFunctionMetadata;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.util.comparator.StructureNameComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = DDMFormBuilderSettingsRetrieverHelper.class
)
public class DDMFormBuilderSettingsRetrieverHelper {

	public String getDDMDataProviderInstanceParameterSettingsURL() {
		String servletContextPath = getServletContextPath(
			_ddmDataProviderInstanceParameterSettingsServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-builder-provider-instance-parameter-" +
				"settings/");
	}

	public String getDDMDataProviderInstancesURL() {
		String servletContextPath = getServletContextPath(
			_ddmDataProviderInstancesServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-builder-data-provider-instances/");
	}

	public String getDDMFieldSetDefinitionURL() {
		String servletContextPath = getServletContextPath(
			_ddmFieldSetDefinitionServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-builder-fieldset-definition/");
	}

	public String getDDMFieldSettingsDDMFormContextURL() {
		String servletContextPath = getServletContextPath(
			_ddmFieldSettingsDDMFormContextServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-builder-field-settings-form-context/");
	}

	public String getDDMFormContextProviderURL() {
		String servletContextPath = getServletContextPath(
			_ddmFormContextProviderServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-context-provider/");
	}

	public String getDDMFunctionsURL() {
		String servletContextPath = getServletContextPath(
			_ddmFormFunctionsServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-builder-functions/");
	}

	public JSONArray getFieldSetsMetadata(
		long companyId, long scopeGroupId, long fieldSetClassNameId,
		Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		if (fieldSetClassNameId == 0) {
			return jsonArray;
		}

		List<DDMStructure> ddmStructures = _ddmStructureService.search(
			companyId, new long[] {scopeGroupId}, fieldSetClassNameId,
			StringPool.BLANK, DDMStructureConstants.TYPE_FRAGMENT,
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StructureNameComparator(true));

		for (DDMStructure ddmStructure : ddmStructures) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"description", ddmStructure.getDescription(locale, true));
			jsonObject.put("icon", "forms");
			jsonObject.put("id", ddmStructure.getStructureId());
			jsonObject.put("name", ddmStructure.getName(locale, true));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public String getRolesURL() {
		String servletContextPath = getServletContextPath(_rolesServlet);

		return servletContextPath.concat(
			"/dynamic-data-mapping-form-builder-roles/");
	}

	public String getSerializedDDMExpressionFunctionsMetadata(Locale locale) {
		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		Map<String, List<DDMExpressionFunctionMetadata>>
			ddmExpressionFunctionsMetadata =
				_ddmExpressionFunctionMetadataHelper.
					getDDMExpressionFunctionsMetadata(locale);

		return jsonSerializer.serializeDeep(ddmExpressionFunctionsMetadata);
	}

	public String getSerializedDDMFormRules(DDMForm ddmForm) {
		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		List<DDMFormRule> ddmFormRules =
			_ddmFormRuleToDDMFormRuleConverter.convert(
				ddmForm.getDDMFormRules());

		return jsonSerializer.serializeDeep(ddmFormRules);
	}

	protected String getServletContextPath(Servlet servlet) {
		String proxyPath = _portal.getPathProxy();

		ServletConfig servletConfig = servlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		return proxyPath.concat(servletContext.getContextPath());
	}

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMDataProviderInstanceParameterSettingsServlet)"
	)
	private Servlet _ddmDataProviderInstanceParameterSettingsServlet;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMDataProviderInstancesServlet)"
	)
	private Servlet _ddmDataProviderInstancesServlet;

	@Reference
	private DDMExpressionFunctionMetadataHelper
		_ddmExpressionFunctionMetadataHelper;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMFieldSetDefinitionServlet)"
	)
	private Servlet _ddmFieldSetDefinitionServlet;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMFieldSettingsDDMFormContextServlet)"
	)
	private Servlet _ddmFieldSettingsDDMFormContextServlet;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.renderer.internal.servlet.DDMFormContextProviderServlet)"
	)
	private Servlet _ddmFormContextProviderServlet;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMFormFunctionsServlet)"
	)
	private Servlet _ddmFormFunctionsServlet;

	@Reference
	private DDMFormRuleConverter _ddmFormRuleToDDMFormRuleConverter;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.RolesServlet)"
	)
	private Servlet _rolesServlet;

}