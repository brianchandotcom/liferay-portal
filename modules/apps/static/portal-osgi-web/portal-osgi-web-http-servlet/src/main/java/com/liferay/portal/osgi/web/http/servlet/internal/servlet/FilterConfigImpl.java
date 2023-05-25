/*******************************************************************************
 * Copyright (c) 2010, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class FilterConfigImpl implements FilterConfig {

	public FilterConfigImpl(
		String name, Map<String, String> initParams,
		ServletContext servletContext) {

		_filterName = name;

		if (initParams != null) {
			_initParams = initParams;
		}
		else {
			_initParams = Collections.emptyMap();
		}

		_servletContext = servletContext;
	}

	@Override
	public String getFilterName() {
		return _filterName;
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

	private final String _filterName;
	private final Map<String, String> _initParams;
	private final ServletContext _servletContext;

}