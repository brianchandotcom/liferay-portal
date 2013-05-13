/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
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
			ServletContext servletContext, String resourcePath)
		throws IOException {

		_servletContext = servletContext;

		String rootPath = ServletContextUtil.getRootPath(_servletContext);

		int pos = resourcePath.lastIndexOf(StringPool.SLASH);

		if (pos > 0) {
			resourcePath = resourcePath.substring(0, resourcePath.length() - 1);
		}

		pos = resourcePath.lastIndexOf(StringPool.SLASH);

		resourcePath = resourcePath.substring(0, pos);

		pos = resourcePath.lastIndexOf(rootPath);

		if (pos == 0) {
			resourcePath = resourcePath.substring(rootPath.length());
		}

		_stack.push(resourcePath);
	}

	@Override
	public String getContent(String path) {
		try {
			String stackPath = _generatePathFromStack();

			URL resourceURL = null;

			if (Validator.isUrl(path)) {
				resourceURL = new URL(path);
			}
			else {
				resourceURL = _servletContext.getResource(
					stackPath.concat(path));
			}

			if (resourceURL == null) {
				return null;
			}

			URLConnection urlConnection = resourceURL.openConnection();

			return StringUtil.read(urlConnection.getInputStream());
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}

		return null;
	}

	@Override
	public String getFullPath(String path) {
		String stackPath = _generatePathFromStack();

		return stackPath.concat(path);
	}

	@Override
	public void popPath(String path) {
		if (Validator.isNotNull(path)) {
			_stack.pop();
		}
	}

	@Override
	public void pushPath(String path) {
		if (Validator.isNotNull(path)) {
			_stack.push(path);
		}
	}

	private String _generatePathFromStack() {
		StringBundler sb = new StringBundler();

		for (String path : _stack) {
			sb.append(path);

			if (!path.endsWith(StringPool.SLASH)) {
				sb.append(StringPool.SLASH);
			}
		}

		return StringUtil.replace(
			sb.toString(), StringPool.DOUBLE_SLASH, StringPool.SLASH);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ServletAggregateContext.class);

	private ServletContext _servletContext;
	private Stack<String> _stack = new Stack<String>();

}