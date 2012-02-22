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

package com.liferay.portal.webdav;

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.webdav.methods.Method;

import javax.servlet.http.HttpServletResponse;

import junit.extensions.TestSetup;

import junit.framework.Test;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Alexander Chow
 */
public class WebDAVTestSetup extends TestSetup {

	public WebDAVTestSetup(Test test) {
		super(test);
	}

	@Override
	public void setUp() {
		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();

		Logger logger = Logger.getLogger(WebDAVServlet.class);

		logger.setLevel(Level.toLevel(Level.INFO_INT));

		Tuple tuple = _baseWebDAVTestCase.service(Method.MKCOL, "", null, null);

		int statusCode = BaseWebDAVTestCase.getStatusCode(tuple);

		if (statusCode == HttpServletResponse.SC_METHOD_NOT_ALLOWED) {
			_baseWebDAVTestCase.service(Method.DELETE, "", null, null);

			tuple = _baseWebDAVTestCase.service(Method.MKCOL, "", null, null);

			statusCode = BaseWebDAVTestCase.getStatusCode(tuple);

			assertEquals(HttpServletResponse.SC_CREATED, statusCode);
		}
	}

	@Override
	public void tearDown() {
		_baseWebDAVTestCase.service(Method.DELETE, "", null, null);

		ServiceTestUtil.destroyServices();
	}

	private BaseWebDAVTestCase _baseWebDAVTestCase = new BaseWebDAVTestCase();

}