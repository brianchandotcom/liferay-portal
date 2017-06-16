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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplatePortletPreferences;
import com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration;

import freemarker.ext.beans.BeansWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service =
		{FreeMarkerTemplateContextHelper.class, TemplateContextHelper.class}
)
public class FreeMarkerTemplateContextHelper extends TemplateContextHelper {

	@Override
	public Set<String> getRestrictedVariables() {
		return _restrictedVariables;
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

			String fullTemplatesPath =
				StringPool.SLASH + servletContextName +
					theme.getFreeMarkerTemplateLoader() +
						theme.getTemplatesPath();

			contextObjects.put("fullTemplatesPath", fullTemplatesPath);

			// Init

			contextObjects.put("init", fullTemplatesPath + "/init.ftl");
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

		// Custom template context contributors

		for (TemplateContextContributor templateContextContributor :
				_templateContextContributors) {

			templateContextContributor.prepare(contextObjects, request);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_freemarkerEngineConfiguration = ConfigurableUtil.createConfigurable(
			FreeMarkerEngineConfiguration.class, properties);

		_restrictedVariables = SetUtil.fromArray(
			_freemarkerEngineConfiguration.restrictedVariables());
	}

	@Override
	protected void populateExtraHelperUtilities(
		Map<String, Object> helperUtilities) {

		// Enum util

		BeansWrapper beansWrapper = FreeMarkerManager.getBeansWrapper();

		helperUtilities.put("enumUtil", beansWrapper.getEnumModels());

		// Object util

		helperUtilities.put("objectUtil", new LiferayObjectConstructor());

		// Portlet preferences

		helperUtilities.put(
			"freeMarkerPortletPreferences", new TemplatePortletPreferences());

		// Static class util

		helperUtilities.put("staticUtil", beansWrapper.getStaticModels());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + TemplateContextContributor.TYPE_GLOBAL + ")",
		unbind = "unregisterTemplateContextContributor"
	)
	protected void registerTemplateContextContributor(
		TemplateContextContributor templateContextContributor) {

		_templateContextContributors.add(templateContextContributor);
	}

	protected void unregisterTemplateContextContributor(
		TemplateContextContributor templateContextContributor) {

		_templateContextContributors.remove(templateContextContributor);
	}

	private volatile FreeMarkerEngineConfiguration
		_freemarkerEngineConfiguration;
	private Set<String> _restrictedVariables;
	private final List<TemplateContextContributor>
		_templateContextContributors = new CopyOnWriteArrayList<>();

}