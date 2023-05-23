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
package org.eclipse.equinox.http.servlet.internal.servlet;

import java.util.*;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

public class FilterConfigImpl implements FilterConfig {

	private final Map<String, String> initparams;
	private final String filterName;
	private final ServletContext servletContext;

	public FilterConfigImpl(
		String name, Map<String, String> initparams,
		ServletContext servletContext) {

		this.filterName = name;

		if (initparams != null) {
			this.initparams = initparams;
		}
		else {
			this.initparams = Collections.emptyMap();
		}

		this.servletContext = servletContext;
	}

	public String getFilterName() {
		return filterName;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getInitParameter(String name) {
		return initparams.get(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(initparams.keySet());
	}

}
