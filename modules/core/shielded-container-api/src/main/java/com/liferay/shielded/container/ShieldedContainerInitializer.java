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

package com.liferay.shielded.container;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author Shuyang Zhou
 */
public interface ShieldedContainerInitializer {

	public static final String SHIELDED_CONTAINER_LIB =
		"/WEB-INF/shielded-container-lib";

	public static final String SHIELDED_CONTAINER_WEB_XML =
		"/WEB-INF/shielded-container-web.xml";

	public void initialize(ServletContext servletContext)
		throws ServletException;

}