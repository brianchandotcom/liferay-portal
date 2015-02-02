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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.test.rule.MainServletTestRule;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.runner.Description;

/**
 * @author Miguel Pastor
 * @author Shuyang Zhou
 */
public class WebDAVEnvironmentConfigTestRule extends MainServletTestRule {

	public static final WebDAVEnvironmentConfigTestRule INSTANCE =
		new WebDAVEnvironmentConfigTestRule();

	@Override
	protected void afterClass(Description description, Object object) {
		_baseWebDAVTestCase.service(Method.DELETE, "", null, null);

		super.afterClass(description, object);
	}

	@Override
	protected Object beforeClass(Description description) {
		Object object = super.beforeClass(description);

		Tuple tuple = _baseWebDAVTestCase.service(Method.MKCOL, "", null, null);

		int statusCode = BaseWebDAVTestCase.getStatusCode(tuple);

		if (statusCode == HttpServletResponse.SC_METHOD_NOT_ALLOWED) {
			_baseWebDAVTestCase.service(Method.DELETE, "", null, null);

			tuple = _baseWebDAVTestCase.service(Method.MKCOL, "", null, null);

			statusCode = BaseWebDAVTestCase.getStatusCode(tuple);

			Assert.assertEquals(HttpServletResponse.SC_CREATED, statusCode);
		}

		return object;
	}

	private WebDAVEnvironmentConfigTestRule() {
	}

	private final BaseWebDAVTestCase _baseWebDAVTestCase =
		new BaseWebDAVTestCase();

}