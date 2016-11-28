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

package com.liferay.portal.kernel.template.bundle.templatemanagerutil;

import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Philip Jones
 */
@Component(
	immediate = true,
	property = {
		"language.type=English", "service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestTemplateManager implements TemplateManager {

	public static final String TEST_TEMPLATE_MANAGER_NAME =
		"TEST_TEMPLATE_MANAGER_NAME";

	@Override
	public void addContextObjects(
		Map<String, Object> contextObjects,
		Map<String, Object> newContextObjects) {

		return;
	}

	@Override
	public void addStaticClassSupport(
		Map<String, Object> contextObjects, String variableName,
		Class<?> variableClass) {

		return;
	}

	@Override
	public void addTaglibApplication(
		Map<String, Object> contextObjects, String applicationName,
		ServletContext servletContext) {

		return;
	}

	@Override
	public void addTaglibFactory(
		Map<String, Object> contextObjects, String taglibLiferayHash,
		ServletContext servletContext) {

		return;
	}

	@Override
	public void addTaglibRequest(
		Map<String, Object> contextObjects, String applicationName,
		HttpServletRequest request, HttpServletResponse response) {

		return;
	}

	@Override
	public void addTaglibSupport(
		Map<String, Object> contextObjects, HttpServletRequest request,
		HttpServletResponse response) {
	}

	@Override
	public void addTaglibTheme(
		Map<String, Object> contextObjects, String string,
		HttpServletRequest request, HttpServletResponse response) {

		return;
	}

	@Override
	public void destroy() {
		return;
	}

	@Override
	public void destroy(ClassLoader classLoader) {
		return;
	}

	@Override
	public String getName() {
		return TEST_TEMPLATE_MANAGER_NAME;
	}

	@Override
	public String[] getRestrictedVariables() {
		return null;
	}

	@Override
	public Template getTemplate(
		List<TemplateResource> templateResources, boolean restricted) {

		return getTemplate(templateResources.get(0), restricted);
	}

	@Override
	public Template getTemplate(
		List<TemplateResource> templateResources,
		TemplateResource errorTemplateResource, boolean restricted) {

		return getTemplate(templateResources, restricted);
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource, boolean restricted) {

		String templateId = templateResource.getTemplateId();

		if (templateId.equals(
				TestTemplateResource.TEST_TEMPLATE_RESOURCE_TEMPLATE_ID)) {

			return new TestTemplate();
		}

		return null;
	}

	@Override
	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted) {

		return getTemplate(templateResource, restricted);
	}

	@Override
	public void init() {
		return;
	}

}