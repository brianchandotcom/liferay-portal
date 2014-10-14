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

package com.liferay.portlet.dynamicdatamapping.template;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Writer;

import java.net.URL;

/**
 * @author Marcellus Tavares
 */
public class DDMTemplateVariableCodeHandler
	implements TemplateVariableCodeHandler {

	public DDMTemplateVariableCodeHandler(String templatePath) {
		_templatePath = templatePath;
	}

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

		if (templateVariableDefinition.isRepeatable()) {
			content = handleRepeatableField(
				templateVariableDefinition, language, content);
		}

		return new String[] {content};
	}

	protected String getResourceName(String dataType) {
		if (isCommonResource(dataType)) {
			dataType = "common";
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_templatePath);
		sb.append(dataType);
		sb.append(".ftl");

		return sb.toString();
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

		prepareTemplate(template, templateVariableDefinition, language);

		Writer writer = new UnsyncStringWriter();

		template.processTemplate(writer);

		return StringUtil.trim(writer.toString());
	}

	protected TemplateResource getTemplateResource(String resource) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(resource);

		return new URLTemplateResource(resource, url);
	}

	protected String handleRepeatableField(
			TemplateVariableDefinition templateVariableDefinition,
			String language, String templateContent)
		throws Exception {

		Template template = getTemplate(_templatePath + "repeatable.ftl");

		templateContent = StringUtil.replace(
			templateContent, StringPool.NEW_LINE,
			StringPool.NEW_LINE + StringPool.TAB + StringPool.TAB);

		template.put("templateContent", templateContent);

		return getTemplateContent(
			template, templateVariableDefinition, language);
	}

	protected boolean isCommonResource(String dataType) {
		if (dataType.equals("boolean") || dataType.equals("date") ||
			dataType.equals("document-library") || dataType.equals("image") ||
			dataType.equals("link-to-page")) {

			return false;
		}

		return true;
	}

	protected void prepareTemplate(
		Template template,
		TemplateVariableDefinition templateVariableDefinition,
		String language) {

		template.put("dataType", templateVariableDefinition.getDataType());
		template.put("help", templateVariableDefinition.getHelp());
		template.put("label", templateVariableDefinition.getLabel());
		template.put("language", language);
		template.put("name", templateVariableDefinition.getName());
		template.put("repeatable", templateVariableDefinition.isRepeatable());
	}

	private final String _templatePath;

}