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

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public interface DDMDisplay {

	public String getDDMResource();

	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, String portletResource)
		throws Exception;

	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale);

	public String getEditTemplateTitle(long classNameId, Locale locale);

	public String getPortletId();

	public String getTemplateDDMResource(
		HttpServletRequest request, DDMTemplate template);

	public String getTemplateDDMResourceActionId();

	public Set<String> getTemplateLanguageTypes();

	public String getTemplateType(DDMTemplate template, Locale locale);

	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception;

	public Set<String> getViewTemplatesExcludedColumnNames();

	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, Locale locale);

	public String getViewTemplatesTitle(DDMStructure structure, Locale locale);

	public boolean isShowStructureSelector();

}