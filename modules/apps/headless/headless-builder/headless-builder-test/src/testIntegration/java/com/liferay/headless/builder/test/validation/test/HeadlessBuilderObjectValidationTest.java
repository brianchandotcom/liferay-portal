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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.HTTPTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio Jiménez del Coso
 */
@Ignore
public class HeadlessBuilderObjectValidationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws PortalException {
		_apiApplicationObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"MSOD_API_APPLICATION", CompanyThreadLocal.getCompanyId());
	}

	@Test
	public void testInvalidBaseURLPathAPIApplication() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "draft"
			).put(
				"baseURL",
				RandomTestUtil.randomString() + StringPool.FORWARD_SLASH
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			_apiApplicationObjectDefinition.getRESTContextPath(),
			Http.Method.POST);

		Assert.assertEquals("BAD_REQUEST", jsonObject.get("status"));
		Assert.assertEquals(
			"Base URL should not have blank spaces and special characters " +
				"with a maximum of 255 characters",
			jsonObject.get("title"));
	}

	@Test
	public void testValidBaseURLPathAPIApplication() throws Exception {
		JSONObject jsonObject = HTTPTestUtil.invoke(
			JSONUtil.put(
				"applicationStatus", "draft"
			).put(
				"baseURL", RandomTestUtil.randomString()
			).put(
				"title", RandomTestUtil.randomString()
			).toString(),
			_apiApplicationObjectDefinition.getRESTContextPath(),
			Http.Method.POST);

		Assert.assertEquals(
			0,
			jsonObject.getJSONObject(
				"status"
			).get(
				"code"
			));
	}

	private ObjectDefinition _apiApplicationObjectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}