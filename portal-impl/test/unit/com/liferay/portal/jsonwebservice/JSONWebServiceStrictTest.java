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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceAction;
import com.liferay.portal.kernel.jsonwebservice.NoSuchJSONWebServiceException;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Igor Spasic
 */
public class JSONWebServiceStrictTest extends BaseJSONWebServiceTestCase {

	@Test
	public void testStrictHttpMethod() throws Exception {
		PropsUtil.set(PropsKeys.JSONWS_WEB_SERVICE_STRICT_HTTP_METHOD, "true");

		initPortalServices();

		registerActionClass(CamelFooService.class);

		MockHttpServletRequest mockHttpServletRequest = createHttpRequest(
			"/camelfoo/post/value/123");

		try {
			lookupJSONWebServiceAction(mockHttpServletRequest);

			Assert.fail();
		}
		catch (NoSuchJSONWebServiceException nsjsonwse) {
		}

		mockHttpServletRequest = createHttpRequest(
			"/camelfoo/post/value/123", HttpMethods.POST);

		JSONWebServiceAction jsonWebServiceAction = lookupJSONWebServiceAction(
			mockHttpServletRequest);

		Assert.assertNotNull(jsonWebServiceAction);

		Assert.assertEquals("post 123", jsonWebServiceAction.invoke());
	}

}