/*******************************************************************************
 * Copyright (c) 2011, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 *******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet;

import java.util.Dictionary;

import javax.servlet.Filter;
import javax.servlet.ServletException;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

/**
 * @author IBM Corporation
 * @author Raymond Augé
 */
@ProviderType
public interface ExtendedHttpService extends HttpService {

	public void registerFilter(
			String alias, Filter filter, Dictionary<String, String> initParams,
			HttpContext httpContext)
		throws NamespaceException, ServletException;

	public void unregisterFilter(Filter filter);

}