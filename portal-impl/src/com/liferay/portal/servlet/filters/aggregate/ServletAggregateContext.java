/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.aggregate;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URL;
import java.net.URLConnection;

import java.util.Stack;

import javax.servlet.ServletContext;

/**
 * @author Raymond Augé
 */
public class ServletAggregateContext implements AggregateContext {

	public ServletAggregateContext(
		ServletContext servletContext, URL resourceURL) throws IOException {

		_servletContext = servletContext;
		_stack = new Stack<String>();

		String resourcePath = resourceURL.getPath();

		URL rootURL = _servletContext.getResource(StringPool.SLASH);

		String rootPath = rootURL.getPath();

		if (resourcePath.endsWith(StringPool.SLASH)) {
			resourcePath = resourcePath.substring(0, resourcePath.length());
		}

		int pos = resourcePath.lastIndexOf(StringPool.SLASH);

		resourcePath = resourcePath.substring(0, pos);

		String currentPath = resourcePath.substring(rootPath.length() - 1);

		_stack.push(currentPath);
	}

	public void popPath(String path) {
		if (Validator.isNotNull(path)) {
			_stack.pop();
		}
	}

	public void pushPath(String path) {
		if (Validator.isNotNull(path)) {
			_stack.push(path);
		}
	}

	public String getContent(String path) {
		try {
			URL resourceURL = _servletContext.getResource(
				generatePathFromStack().concat(path));

			if (resourceURL == null) {
				return null;
			}

			URLConnection urlConnection = resourceURL.openConnection();

			return StringUtil.read(urlConnection.getInputStream());
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return null;
	}

	public String getFullPath(String path) {
		return generatePathFromStack().concat(path);
	}

	private String generatePathFromStack() {
		StringBundler sb = new StringBundler();

		for (String item : _stack) {
			sb.append(item);

			if (!item.endsWith(StringPool.SLASH)) {
				sb.append(StringPool.SLASH);
			}
		}

		return sb.toString();
	}

	private ServletContext _servletContext;
	private Stack<String> _stack;

}