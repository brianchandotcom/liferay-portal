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

package com.liferay.portal.kernel.portletdisplaytemplate;

import com.liferay.portal.kernel.template.BaseTemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public abstract class BasePortletDisplayTemplateHandler
	extends BaseTemplateHandler {

	@Override
	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
			long classPK, String language, Locale locale)
		throws Exception {

		return PortletDisplayTemplateUtil.getTemplateVariableGroups(language);
	}

}