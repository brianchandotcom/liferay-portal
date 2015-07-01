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

package com.liferay.portlet.display.template.service.impl;

import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portlet.display.template.service.PortletDisplayTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true)
public class PortletDisplayTemplateManagerImpl
	implements PortletDisplayTemplateManager {

	@Override
	public DDMTemplate getDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault) {

		return _portletDisplayTemplate.getPortletDisplayTemplateDDMTemplate(
			groupId, classNameId, displayStyle, useDefault);
	}

	@Override
	public long getDDMTemplateGroupId(long groupId) {
		return _portletDisplayTemplate.getDDMTemplateGroupId(groupId);
	}

	@Override
	public List<TemplateHandler> getPortletDisplayTemplateHandlers() {
		return _portletDisplayTemplate.getPortletDisplayTemplateHandlers();
	}

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language) {

		return _portletDisplayTemplate.getTemplateVariableGroups(language);
	}

	@Override
	public String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long templateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getDDMTemplate(
			templateId);
		
		return _portletDisplayTemplate.renderDDMTemplate(
			request, response, ddmTemplate, entries, contextObjects);
	}

	@Reference
	protected void setPortletDisplayTemplate(
		PortletDisplayTemplate portletDisplayTemplate) {

		_portletDisplayTemplate = portletDisplayTemplate;
	}

	protected PortletDisplayTemplate _portletDisplayTemplate;

}