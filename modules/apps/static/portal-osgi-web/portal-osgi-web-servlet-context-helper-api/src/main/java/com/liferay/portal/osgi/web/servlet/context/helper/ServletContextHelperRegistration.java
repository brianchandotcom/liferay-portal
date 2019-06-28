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

package com.liferay.portal.osgi.web.servlet.context.helper;

import com.liferay.portal.osgi.web.servlet.context.helper.definition.WebXMLDefinition;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Raymond Augé
 */
@ProviderType
public interface ServletContextHelperRegistration {

	public void close();

	public Set<Class<?>> getAnnotatedClasses();

	public Set<Class<?>> getClasses();

	public ServletContext getServletContext();

	public WebXMLDefinition getWebXMLDefinition();

	public boolean isWabShapedBundle();

	public void setProperties(Map<String, String> contextParameters);

}