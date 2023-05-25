/*******************************************************************************
 * Copyright (c) 2005, 2014 Cognos Incorporated, IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Cognos Incorporated - initial API and implementation
 *     IBM Corporation - bug fixes and enhancements
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author Cognos Incorporated
 * @author IBM Corporation
 * @author Raymond Augé
 */
public class ServletConfigImpl implements ServletConfig {

	public ServletConfigImpl(
		String servletName, Map<String, String> initParams,
		ServletContext servletContext) {

		_servletName = servletName;

		if (initParams != null) {
			_initParams = initParams;
		}
		else {
			_initParams = Collections.emptyMap();
		}

		_servletContext = servletContext;
	}

	@Override
	public String getInitParameter(String name) {
		return _initParams.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(_initParams.keySet());
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public String getServletName() {
		return _servletName;
	}

	private final Map<String, String> _initParams;
	private final ServletContext _servletContext;
	private final String _servletName;

}