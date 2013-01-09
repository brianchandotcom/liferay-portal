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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Theme;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplatePortletPreferences;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;

import freemarker.ext.beans.BeansWrapper;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class FreeMarkerTemplateContextHelper extends TemplateContextHelper {

	@Override
	public Set<String> getRestrictedVariables() {
		return SetUtil.fromArray(
			PropsValues.JOURNAL_TEMPLATE_FREEMARKER_RESTRICTED_VARIABLES);
	}

	@Override
	public void prepare(
		TemplateContext templateContext, HttpServletRequest request) {

		super.prepare(templateContext, request);

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Theme theme = themeDisplay.getTheme();

			// Full css and templates path

			String servletContextName = GetterUtil.getString(
				theme.getServletContextName());

			templateContext.put(
				"fullCssPath",
				StringPool.SLASH + servletContextName +
					theme.getFreeMarkerTemplateLoader() + theme.getCssPath());

			templateContext.put(
				"fullTemplatesPath",
				StringPool.SLASH + servletContextName +
					theme.getFreeMarkerTemplateLoader() +
						theme.getTemplatesPath());

			// Init

			templateContext.put(
				"init",
				StringPool.SLASH + themeDisplay.getPathContext() +
					TemplateResource.SERVLET_SEPARATOR +
						"/html/themes/_unstyled/templates/init.ftl");
		}

		// Insert custom ftl variables

		Map<String, Object> ftlVariables =
			(Map<String, Object>)request.getAttribute(WebKeys.FTL_VARIABLES);

		if (ftlVariables != null) {
			for (Map.Entry<String, Object> entry : ftlVariables.entrySet()) {
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
		Map<String, Object> helperUtilities) {

		// Enum util

		helperUtilities.put(
			"enumUtil", BeansWrapper.getDefaultInstance().getEnumModels());

		// Object util

		helperUtilities.put("objectUtil", new LiferayObjectConstructor());

		// Portlet preferences

		helperUtilities.put(
			"freeMarkerPortletPreferences", new TemplatePortletPreferences());

		// Static class util

		helperUtilities.put(
			"staticUtil", BeansWrapper.getDefaultInstance().getStaticModels());
	}

}