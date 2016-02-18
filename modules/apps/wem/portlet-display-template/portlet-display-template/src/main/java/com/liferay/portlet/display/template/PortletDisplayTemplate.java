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

package com.liferay.portlet.display.template;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Garcia
 */
public interface PortletDisplayTemplate {

	public static final String DISPLAY_STYLE_PREFIX = "ddmTemplate_";

	public DDMTemplate fetchDDMTemplate(long groupId, String displayStyle);

	public long getDDMTemplateGroupId(long groupId);

	public String getDDMTemplateKey(String displayStyle);

	@Deprecated
	public String getDDMTemplateUuid(String displayStyle);

	public DDMTemplate getDefaultPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId);

	public String getDisplayStyle(String ddmTemplateKey);

	public DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle);

	public DDMTemplate getPortletDisplayTemplateDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault);

	@Deprecated
	public long getPortletDisplayTemplateDDMTemplateId(
		long groupId, String displayStyle);

	public List<TemplateHandler> getPortletDisplayTemplateHandlers();

	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language);

	public String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			DDMTemplate ddmTemplate, List<?> entries)
		throws Exception;

	public String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			DDMTemplate ddmTemplate, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception;

	public String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long ddmTemplateId, List<?> entries)
		throws Exception;

	public String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long ddmTemplateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception;

}