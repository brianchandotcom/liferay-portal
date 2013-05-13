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

package com.liferay.portlet.portletdisplaytemplate.util;

import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo Garcia
 */
public interface PortletDisplayTemplate {

	public DDMTemplate fetchDDMTemplate(long groupId, String displayStyle);

	public long getDDMTemplateGroupId(ThemeDisplay themeDisplay);

	public long getPortletDisplayTemplateDDMTemplateId(
		ThemeDisplay themeDisplay, String displayStyle);

	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language);

	public String renderDDMTemplate(
			PageContext pageContext, long ddmTemplateId, List<?> entries)
		throws Exception;

	public String renderDDMTemplate(
			PageContext pageContext, long ddmTemplateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception;

}