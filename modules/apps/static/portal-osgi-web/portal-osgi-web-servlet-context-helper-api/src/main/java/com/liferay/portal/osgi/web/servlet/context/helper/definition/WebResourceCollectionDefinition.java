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