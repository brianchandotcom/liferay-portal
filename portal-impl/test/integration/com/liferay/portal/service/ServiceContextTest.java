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

package com.liferay.portal.service;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Michael C. Han
 */
public class ServiceContextTest extends PowerMockito {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testJSONSerialization() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("TestName", "TestValue");

		Map<String, String> headers = new HashMap<>();

		headers.put("TestHeaderName", "TestHeaderValue");

		serviceContext.setHeaders(headers);

		serviceContext.setRequest(mock(HttpServletRequest.class));

		String json = JSONFactoryUtil.serialize(serviceContext);

		ServiceContext deserializedServiceContext =
			(ServiceContext)JSONFactoryUtil.deserialize(json);

		Assert.assertEquals(
			deserializedServiceContext.getAttributes(),
			serviceContext.getAttributes());
		Assert.assertNull(deserializedServiceContext.getHeaders());
		Assert.assertNull(deserializedServiceContext.getRequest());
	}

}