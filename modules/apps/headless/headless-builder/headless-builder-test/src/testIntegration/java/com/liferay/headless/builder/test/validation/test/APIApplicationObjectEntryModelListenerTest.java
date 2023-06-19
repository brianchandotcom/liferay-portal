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

package com.liferay.headless.builder.test.validation.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio Jiménez del Coso
 */
@Ignore
@RunWith(Arquillian.class)
public class APIApplicationObjectEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testInvalidBaseURLPathAPIApplication() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL",
				RandomTestUtil.randomString() + StringPool.FORWARD_SLASH
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"Base URL can have a maximum of 255 alphanumeric characters",
			jsonObject.get("title"));
	}

	@Test
	public void testValidBaseURLPathAPIApplication() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "published"
			).put(
				"baseURL", RandomTestUtil.randomString()
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			"headless-builder/applications", Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));
	}

}