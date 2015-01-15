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

package com.liferay.portal.kernel.template;

import java.io.Writer;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public interface TemplateManager {

	public void addStaticClassSupport(
		Map<String, Object> contextObjects, String variableName,
		Class<?> variableClass);

	public void addStaticClassSupport(
		Template template, String variableName, Class<?> variableClass);

	public void addTaglibApplication(
		Map<String, Object> contextObjects, String applicationName,
		ServletContext servletContext);

	public void addTaglibApplication(
		Template template, String applicationName,
		ServletContext servletContext);

	public void addTaglibFactory(
		Map<String, Object> contextObjects, String taglibLiferayHash,
		ServletContext servletContext);

	public void addTaglibFactory(
		Template template, String taglibLiferayHash,
		ServletContext servletContext);

	public void addTaglibRequest(
		Map<String, Object> contextObjects, String applicationName,
		HttpServletRequest request, HttpServletResponse response);

	public void addTaglibRequest(
		Template template, String applicationName, HttpServletRequest request,
		HttpServletResponse response);

	public void addTaglibTheme(
		Template template, String string, HttpServletRequest request,
		HttpServletResponse response, Writer writer);

	public void destroy();

	public void destroy(ClassLoader classLoader);

	public String getName();

	public String[] getRestrictedVariables();

	public Template getTemplate(
		TemplateResource templateResource, boolean restricted);

	public Template getTemplate(
		TemplateResource templateResource,
		TemplateResource errorTemplateResource, boolean restricted);

	public void init() throws TemplateException;

}