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

package com.liferay.portlet.dynamicdatamapping.template;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.template.BaseTemplateHandler;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.io.Writer;

import java.net.URL;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jorge Ferrer
 * @author Marcellus Tavares
 */
public abstract class BaseDDMTemplateHandler extends BaseTemplateHandler
	implements TemplateVariableCodeHandler {

	@Override
	public String[] generate(
			TemplateVariableDefinition templateVariableDefinition,
			String language)
		throws Exception {

		String resourceName = getResourceName(
			templateVariableDefinition.getDataType());

		Template template = getTemplate(resourceName);

		String content = getTemplateContent(
			template, templateVariableDefinition, language);

		String[] lines = getContentLines(content);

		String[] dataContentArray = getDataContentArray(lines);

		if (!templateVariableDefinition.isRepeatable()) {
			return dataContentArray;
		}

		return handleRepeatableField(
			templateVariableDefinition, language, dataContentArray);
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		Map<String, TemplateVariableGroup> templateVariableGroups =
			new LinkedHashMap<String, TemplateVariableGroup>();

		addTemplateVariableGroup(
			templateVariableGroups, getGeneralVariablesTemplateVariableGroup());
		addTemplateVariableGroup(
			templateVariableGroups,
			getStructureFieldsTemplateVariableGroup(classPK, locale));
		addTemplateVariableGroup(
			templateVariableGroups, getUtilTemplateVariableGroup());

		return templateVariableGroups;
	}

	protected void addTemplateVariableGroup(
		Map<String, TemplateVariableGroup> templateVariableGroups,
		TemplateVariableGroup templateVariableGroup) {

		if (templateVariableGroup == null) {
			return;
		}

		templateVariableGroups.put(
			templateVariableGroup.getLabel(), templateVariableGroup);
	}

	protected String[] getContentLines(String content) {
		String[] lines = StringUtil.splitLines(content);

		for (int i = 0; i < lines.length; i++) {
			lines[i] = StringUtil.trim(lines[i]);
		}

		return lines;
	}

	protected String[] getDataContentArray(String[] lines) {
		String[] dataContentArray = new String[] {
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK};

		for (int i = 0; i < lines.length; i++) {
			dataContentArray[i] = lines[i];
		}

		return dataContentArray;
	}

	protected Class<?> getFieldVariableClass() {
		return TemplateNode.class;
	}

	protected TemplateVariableGroup getGeneralVariablesTemplateVariableGroup() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"general-variables");

		templateVariableGroup.addVariable(
			"portal-instance", Company.class, "company");
		templateVariableGroup.addVariable(
			"portal-instance-id", null, "companyId");
		templateVariableGroup.addVariable("device", Device.class, "device");
		templateVariableGroup.addVariable("site-id", null, "groupId");
		templateVariableGroup.addVariable(
			"view-mode", String.class, "viewMode");

		return templateVariableGroup;
	}

	protected String getResourceName(String dataType) {
		if (isCommonResource(dataType)) {
			dataType = "common";
		}

		StringBundler sb = new StringBundler(3);

		sb.append(getTemplatePath());
		sb.append(dataType);
		sb.append(".ftl");

		return sb.toString();
	}

	protected TemplateVariableGroup getStructureFieldsTemplateVariableGroup(
			long ddmStructureId, Locale locale)
		throws PortalException, SystemException {

		if (ddmStructureId <= 0) {
			return null;
		}

		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"fields");

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		List<String> fieldNames = ddmStructure.getRootFieldNames();

		for (String fieldName : fieldNames) {
			if (fieldName.startsWith(StringPool.UNDERLINE)) {
				continue;
			}

			String label = ddmStructure.getFieldLabel(fieldName, locale);
			String tip = ddmStructure.getFieldTip(fieldName, locale);
			String dataType = ddmStructure.getFieldDataType(fieldName);
			boolean repeatable = ddmStructure.getFieldRepeatable(fieldName);

			templateVariableGroup.addFieldVariable(
				label, getFieldVariableClass(), fieldName, tip, dataType,
				repeatable, this);
		}

		return templateVariableGroup;
	}

	protected Template getTemplate(String resource) throws Exception {
		TemplateResource templateResource = getTemplateResource(resource);

		return TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL, templateResource, false);
	}

	protected String getTemplateContent(
			Template template,
			TemplateVariableDefinition templateVariableDefinition,
			String language)
		throws Exception {

		populateContext(template, templateVariableDefinition, language);

		Writer writer = new UnsyncStringWriter();

		template.processTemplate(writer);

		return writer.toString();
	}

	protected abstract String getTemplatePath();

	protected TemplateResource getTemplateResource(String resource) {
		ClassLoader classLoader = getClassLoader();

		URL url = classLoader.getResource(resource);

		return new URLTemplateResource(resource, url);
	}

	protected TemplateVariableGroup getUtilTemplateVariableGroup() {
		TemplateVariableGroup templateVariableGroup = new TemplateVariableGroup(
			"util");

		templateVariableGroup.addVariable(
			"permission-checker", PermissionChecker.class, "permissionChecker");
		templateVariableGroup.addVariable(
			"random-namespace", String.class, "randomNamespace");
		templateVariableGroup.addVariable(
			"templates-path", String.class, "templatesPath");

		return templateVariableGroup;
	}

	protected String[] handleRepeatableField(
			TemplateVariableDefinition templateVariableDefinition,
			String language, String[] dataContentArray)
		throws Exception {

		Template template = getTemplate(getTemplatePath() + "repeatable.ftl");

		String content = getTemplateContent(
			template, templateVariableDefinition, language);

		String[] lines = getContentLines(content);

		String tmpDataContent = dataContentArray[0];

		dataContentArray[0] =
			lines[0] + StringPool.NEW_LINE + StringPool.TAB + lines[1];
		dataContentArray[1] =
			tmpDataContent + dataContentArray[1] + dataContentArray[2];
		dataContentArray[2] = lines[2] + StringPool.NEW_LINE + lines[3];

		return dataContentArray;
	}

	protected boolean isCommonResource(String dataType) {
		if (dataType.equals("boolean") || dataType.equals("date") ||
			dataType.equals("document-library") ||
			dataType.equals("file-upload") || dataType.equals("image") ||
			dataType.equals("link-to-page") ||
			dataType.equals("service-locator")) {

			return false;
		}

		return true;
	}

	protected void populateContext(
		Template template,
		TemplateVariableDefinition templateVariableDefinition,
		String language) {

		template.put("dataType", templateVariableDefinition.getDataType());
		template.put("help", templateVariableDefinition.getHelp());
		template.put("isRepeatable", templateVariableDefinition.isRepeatable());
		template.put("label", templateVariableDefinition.getLabel());
		template.put("language", language);
		template.put("name", templateVariableDefinition.getName());
	}

}