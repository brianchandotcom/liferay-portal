/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateContextType;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 * @author Jorge Ferrer
 */
public class DDMTemplateHelperImpl implements DDMTemplateHelper {

	public DDMStructure fetchStructure(DDMTemplate template) {
		try {
			long classNameId = PortalUtil.getClassNameId(DDMStructure.class);

			if (template.getClassNameId() == classNameId) {
				return DDMStructureLocalServiceUtil.fetchDDMStructure(
					template.getClassPK());
			}
		}
		catch (Exception e) {
		}

		return null;
	}

	public String getAutocompleteJSON(
			HttpServletRequest request, String language)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		JSONObject typesJSONObject = JSONFactoryUtil.createJSONObject();
		JSONObject variablesJSONObject = JSONFactoryUtil.createJSONObject();

		for (TemplateVariableDefinition templateVariableDefinition :
				getAutocompleteTemplateVariableDefinitions(request, language)) {

			Class<?> clazz = templateVariableDefinition.getClazz();

			if (clazz == null) {
				variablesJSONObject.put(
					templateVariableDefinition.getName(), StringPool.BLANK);
			}
			else {
				if (!typesJSONObject.has(clazz.getName())) {
					typesJSONObject.put(
						clazz.getName(), getAutocompleteClassJSONObject(clazz));
				}

				variablesJSONObject.put(
					templateVariableDefinition.getName(),
					getAutocompleteVariableJSONObject(clazz));
			}
		}

		jsonObject.put("types", typesJSONObject);
		jsonObject.put("variables", variablesJSONObject);

		return jsonObject.toString();
	}

	protected JSONObject getAutocompleteClassJSONObject(Class<?> clazz) {
		JSONObject typeJSONObject = JSONFactoryUtil.createJSONObject();

		for (Field field : clazz.getFields()) {
			JSONObject fieldJSONObject = getAutocompleteVariableJSONObject(
				field.getType());

			typeJSONObject.put(field.getName(), fieldJSONObject);
		}

		for (Method method : clazz.getMethods()) {
			JSONObject methodJSONObject = JSONFactoryUtil.createJSONObject();

			JSONArray parametersTypesArray = JSONFactoryUtil.createJSONArray();

			Class<?>[] parameterTypes = method.getParameterTypes();

			for (Class<?> parameterType : parameterTypes) {
				parametersTypesArray.put(parameterType.getCanonicalName());
			}

			methodJSONObject.put("argumentTypes", parametersTypesArray);

			Class<?> returnTypeClass = method.getReturnType();

			methodJSONObject.put("returnType", returnTypeClass.getName());

			methodJSONObject.put("type", "Method");

			typeJSONObject.put(method.getName(), methodJSONObject);
		}

		return typeJSONObject;
	}

	protected List<TemplateVariableDefinition>
			getAutocompleteTemplateVariableDefinitions(
				HttpServletRequest request, String language)
		throws Exception {

		List<TemplateVariableDefinition> templateVariableDefinitions =
			new UniqueList<TemplateVariableDefinition>();

		// Declared variables

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMTemplate ddmTemplate = (DDMTemplate)request.getAttribute(
			WebKeys.DYNAMIC_DATA_MAPPING_TEMPLATE);

		long classPK = BeanParamUtil.getLong(ddmTemplate, request, "classPK");
		long classNameId = BeanParamUtil.getLong(
			ddmTemplate, request, "classNameId");

		if (classPK > 0) {
			DDMStructure ddmStructure = DDMStructureServiceUtil.getStructure(
				classPK);

			classNameId = ddmStructure.getClassNameId();
		}
		else if (ddmTemplate != null) {
			classNameId = ddmTemplate.getClassNameId();
		}

		Map<String, TemplateVariableGroup> templateVariableGroups =
			TemplateContextHelper.getTemplateVariableGroups(
				classNameId, classPK, language, themeDisplay.getLocale());

		for (TemplateVariableGroup templateVariableGroup :
				templateVariableGroups.values()) {

			if (!templateVariableGroup.isAutocompleteEnabled()) {
				continue;
			}

			templateVariableDefinitions.addAll(
				templateVariableGroup.getTemplateVariableDefinitions());
		}

		// Other variables

		TemplateResource templateResource = new StringTemplateResource(
			_TEMPLATE_ID, _TEMPLATE_CONTENT);

		Template template = TemplateManagerUtil.getTemplate(
			language, templateResource, TemplateContextType.STANDARD);

		for (String key : template.getKeys()) {
			Object value = template.get(key);

			if (value == null) {
				continue;
			}

			TemplateVariableDefinition variableDefinition =
				new TemplateVariableDefinition(key, value.getClass(), key);

			templateVariableDefinitions.add(variableDefinition);
		}

		return templateVariableDefinitions;
	}

	protected JSONObject getAutocompleteVariableJSONObject(Class<?> clazz) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("type", clazz.getName());

		return jsonObject;
	}

	private static final String _TEMPLATE_CONTENT = "# Placeholder";

	private static final String _TEMPLATE_ID = "0";

}