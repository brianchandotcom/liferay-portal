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

package com.liferay.dynamic.data.lists.web.template;

import com.liferay.dynamic.data.mapping.template.DDMTemplateVariableCodeHandler;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateVariableDefinition;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Leonardo Barros
 */
public class DDLTemplateVariableCodeHandler
		extends DDMTemplateVariableCodeHandler {

	public DDLTemplateVariableCodeHandler(
		ClassLoader classLoader, String templatePath) {

		super(classLoader, templatePath);
	}

	protected String handleRepeatableField(
			TemplateVariableDefinition templateVariableDefinition,
			String language, String templateContent)
		throws Exception {

		Template template = getTemplate(getTemplatePath() + "common.ftl");

		templateContent = StringUtil.replace(
			templateContent, StringPool.NEW_LINE,
			StringPool.NEW_LINE + StringPool.TAB + StringPool.TAB);

		template.put("templateContent", templateContent);

		return getTemplateContent(
			template, templateVariableDefinition, language);
	}

	@Override
	protected boolean isCommonResource(String dataType) {
		if (dataType.equals("document-library") || dataType.equals("image") ||
			dataType.equals("link-to-page")) {

			return false;
		}

		return true;
	}

}