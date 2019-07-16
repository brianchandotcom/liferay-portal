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

package com.liferay.dynamic.data.mapping.template;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.template.TemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Writer;

import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMTemplateVariableCodeHandler
	implements TemplateVariableCodeHandler {

	public DDMTemplateVariableCodeHandler(
		ClassLoader classLoader, String templatePath,
		Set<String> templateNames) {

		_classLoader = classLoader;
		_templatePath = templatePath;
		_templateNames = templateNames;
	}

	@Override
	public String[] generate(
			TemplateVariableDefinition templateVariableDefinition,
			String language)
		throws Exception {

		String templateId = getTemplateId(
			templateVariableDefinition.getDataType());

		String content = getTemplateContent(
			getTemplate(templateId), templateVariableDefinition, language);

		if (templateVariableDefinition.isRepeatable()) {
			content = handleRepeatableField(
				templateVariableDefinition, language, content);
		}

		return new String[] {content};
	}

	protected Template getTemplate(String templateId) throws Exception {
		TemplateResource templateResource =
			TemplateResourceLoaderUtil.getTemplateResource(
				TemplateConstants.LANG_TYPE_FTL,
				StringBundler.concat(
					ClassLoaderPool.getContextName(_classLoader),
					TemplateConstants.CLASS_LOADER_SEPARATOR, templateId));

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

	protected String getTemplateId(String dataType) {
		if (!_templateNames.contains(dataType)) {
			dataType = "common";
		}

		return getTemplatePath() + dataType + ".ftl";
	}

	protected String getTemplatePath() {
		return _templatePath;
	}

	protected String handleRepeatableField(
			TemplateVariableDefinition templateVariableDefinition,
			String language, String templateContent)
		throws Exception {

		Template template = getTemplate(getTemplatePath() + "repeatable.ftl");

		templateContent = StringUtil.replace(
			templateContent, CharPool.NEW_LINE,
			StringPool.NEW_LINE + StringPool.TAB + StringPool.TAB);

		template.put("templateContent", templateContent);

		return getTemplateContent(
			template, templateVariableDefinition, language);
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

	private final ClassLoader _classLoader;
	private final Set<String> _templateNames;
	private final String _templatePath;

}