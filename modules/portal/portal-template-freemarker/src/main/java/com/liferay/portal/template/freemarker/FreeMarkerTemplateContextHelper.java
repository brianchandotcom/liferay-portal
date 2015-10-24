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

package com.liferay.portal.template.freemarker;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Theme;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplatePortletPreferences;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import freemarker.ext.beans.BeansWrapper;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = {
		FreeMarkerTemplateContextHelper.class, TemplateContextHelper.class
	}
)
public class FreeMarkerTemplateContextHelper extends TemplateContextHelper {

	@Override
	public Set<String> getRestrictedVariables() {
		return SetUtil.fromArray(
			_freemarkerEngineConfiguration.restrictedVariables());
	}

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {

		super.prepare(contextObjects, request);

		// Theme display

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Theme theme = themeDisplay.getTheme();

			// Full css and templates path

			String servletContextName = GetterUtil.getString(
				theme.getServletContextName());

			contextObjects.put(
				"fullCssPath",
				StringPool.SLASH + servletContextName +
					theme.getFreeMarkerTemplateLoader() + theme.getCssPath());

			contextObjects.put(
				"fullTemplatesPath",
				StringPool.SLASH + servletContextName +
					theme.getFreeMarkerTemplateLoader() +
						theme.getTemplatesPath());

			// Init

			contextObjects.put(
				"init",
				StringPool.SLASH + themeDisplay.getPathContext() +
					TemplateConstants.SERVLET_SEPARATOR +
						"/classic/templates/init.ftl");
		}

		// Insert custom ftl variables

		Map<String, Object> ftlVariables =
			(Map<String, Object>)request.getAttribute(WebKeys.FTL_VARIABLES);

		if (ftlVariables != null) {
			for (Map.Entry<String, Object> entry : ftlVariables.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				if (Validator.isNotNull(key)) {
					contextObjects.put(key, value);
				}
			}
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_freemarkerEngineConfiguration = Configurable.createConfigurable(
			FreeMarkerEngineConfiguration.class, properties);
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

	private volatile FreeMarkerEngineConfiguration
		_freemarkerEngineConfiguration;

}