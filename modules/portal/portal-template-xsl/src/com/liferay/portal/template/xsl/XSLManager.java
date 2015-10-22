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

package com.liferay.portal.template.xsl;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.BaseSingleTemplateManager;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.xsl.configuration.XSLEngineConfiguration;
import com.liferay.portal.xsl.XSLTemplateResource;

import java.util.Map;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.template.xsl.configuration.XSLEngineConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"language.type=" + TemplateConstants.LANG_TYPE_XSL},
	service = TemplateManager.class
)
public class XSLManager extends BaseSingleTemplateManager {

	@Override
	public void destroy() {
		if (templateContextHelper == null) {
			return;
		}

		templateContextHelper.removeAllHelperUtilities();

		templateContextHelper = null;
	}

	@Override
	public void destroy(ClassLoader classLoader) {
		templateContextHelper.removeHelperUtilities(classLoader);
	}

	@Override
	public String getName() {
		return TemplateConstants.LANG_TYPE_XSL;
	}

	@Override
	public void init() {
		templateContextHelper = new TemplateContextHelper();
	}

	@Activate
	@Modified
	protected void activate(ComponentContext componentContext) {
		_xslEngineConfiguration = Configurable.createConfigurable(
			XSLEngineConfiguration.class, componentContext.getProperties());
	}

	@Override
	protected Template doGetTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted,
		Map<String, Object> helperUtilities, boolean privileged) {

		XSLTemplateResource xslTemplateResource =
			(XSLTemplateResource)templateResource;

		return new XSLTemplate(
			xslTemplateResource, errorTemplateResource, templateContextHelper,
			_xslEngineConfiguration);
	}

	private volatile XSLEngineConfiguration _xslEngineConfiguration;

}