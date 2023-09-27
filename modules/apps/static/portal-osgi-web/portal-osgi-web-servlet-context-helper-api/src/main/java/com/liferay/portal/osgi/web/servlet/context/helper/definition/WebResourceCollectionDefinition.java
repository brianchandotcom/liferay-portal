/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.servlet.context.helper.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan González
 */
public class WebResourceCollectionDefinition {

	public WebResourceCollectionDefinition(String name) {
		_name = name;
	}

	public void addHttpMethod(String httpMethod) {
		_httpMethods.add(httpMethod);
	}

	public void addHttpMethodException(String httpMethodException) {
		_httpMethodExceptions.add(httpMethodException);
	}

	public void addURLPattern(String urlPattern) {
		_urlPatterns.add(urlPattern);
	}

	public List<String> getHttpMethodExceptions() {
		return _httpMethodExceptions;
	}

	public List<String> getHttpMethods() {
		return _httpMethods;
	}

	public String getName() {
		return _name;
	}

	public List<String> getUrlPatterns() {
		return _urlPatterns;
	}

	private final List<String> _httpMethodExceptions = new ArrayList<>();
	private final List<String> _httpMethods = new ArrayList<>();
	private final String _name;
	private final List<String> _urlPatterns = new ArrayList<>();

}