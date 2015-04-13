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

package com.liferay.dynamic.data.mapping.type;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.dynamicdatamapping.registry.BaseDDMFormFieldWithOptionsRenderer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldRenderer;

import java.net.URL;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Renato Rego
 */
@Component(
	immediate = true, property = {"templatePath=/META-INF/resources/radio.soy"},
	service = {
		RadioDDMFormFieldRenderer.class, DDMFormFieldRenderer.class
	}
)
public class RadioDDMFormFieldRenderer
	extends BaseDDMFormFieldWithOptionsRenderer {

	@Override
	public String getTemplateLanguage() {
		return TemplateConstants.LANG_TYPE_SOY;
	}

	@Override
	public String getTemplateNamespace() {
		return "ddm.radio";
	}

	@Override
	public TemplateResource getTemplateResource() {
		String templatePath = MapUtil.getString(_properties, "templatePath");

		return getTemplateResource(templatePath);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_properties = properties;
	}

	@Override
	protected String getActiveOptionText() {
		return "checked";
	}

	protected TemplateResource getTemplateResource(String templatePath) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL templateURL = classLoader.getResource(templatePath);

		return new URLTemplateResource(templateURL.getPath(), templateURL);
	}

	private Map<String, Object> _properties;

}