/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplatePortletPreferences;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.IteratorTool;
import org.apache.velocity.tools.generic.ListTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.SortTool;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class VelocityTemplateContextHelper extends TemplateContextHelper {

	@Override
	public Set<String> getRestrictedVariables() {
		return SetUtil.fromArray(
			PropsValues.JOURNAL_TEMPLATE_VELOCITY_RESTRICTED_VARIABLES);
	}

	@Override
	public void prepare(
		TemplateContext templateContext, HttpServletRequest request) {

		super.prepare(templateContext, request);

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {

			// Init

			templateContext.put(
				"init",
				themeDisplay.getPathContext() +
					TemplateResource.SERVLET_SEPARATOR +
						"/html/themes/_unstyled/templates/init.vm");
		}

		// Theme

		Theme theme = (Theme)request.getAttribute(WebKeys.THEME);

		if ((theme == null) && (themeDisplay != null)) {
			theme = themeDisplay.getTheme();
		}

		if (theme != null) {

			// Full css and templates path

			String servletContextName = GetterUtil.getString(
				theme.getServletContextName());

			templateContext.put(
				"fullCssPath",
				servletContextName + theme.getVelocityResourceListener() +
					theme.getCssPath());

			templateContext.put(
				"fullTemplatesPath",
				servletContextName + theme.getVelocityResourceListener() +
					theme.getTemplatesPath());
		}

		// Insert custom vm variables

		Map<String, Object> vmVariables =
			(Map<String, Object>)request.getAttribute(WebKeys.VM_VARIABLES);

		if (vmVariables != null) {
			for (Map.Entry<String, Object> entry : vmVariables.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (Validator.isNotNull(key)) {
					templateContext.put(key, value);
				}
			}
		}
	}

	@Override
	protected void populateExtraHelperUtilities(
		Map<String, Object> velocityContext) {

		// Date tool

		velocityContext.put("dateTool", new DateTool());

		// Escape tool

		velocityContext.put("escapeTool", new EscapeTool());

		// Iterator tool

		velocityContext.put("iteratorTool", new IteratorTool());

		// List tool

		velocityContext.put("listTool", new ListTool());

		// Math tool

		velocityContext.put("mathTool", new MathTool());

		// Number tool

		velocityContext.put("numberTool", new NumberTool());

		// Portlet preferences

		velocityContext.put(
			"velocityPortletPreferences", new TemplatePortletPreferences());

		// Sort tool

		velocityContext.put("sortTool", new SortTool());

		// Permissions

		try {
			velocityContext.put(
				"rolePermission", RolePermissionUtil.getRolePermission());
		}
		catch (SecurityException se) {
			_log.error(se, se);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VelocityTemplateContextHelper.class);

}