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

package com.liferay.data.engine.rest.resource.v2_0.factory.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import javax.ws.rs.ClientErrorException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DataDefinitionResourceFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = UserTestUtil.addUser();
	}

	@Test
	public void testAcceptLanguage1() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).httpServletRequest(
				new MockHttpServletRequest() {

					@Override
					public String getHeader(String name) {
						if (StringUtil.equals(name, "Accept-Language")) {
							return "pt-BR";
						}

						return super.getHeader(name);
					}

				}
			).user(
				_user
			).build();

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			dataDefinitionResource.
				getDataDefinitionDataDefinitionFieldFieldTypes());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Assert.assertEquals(
				"pt_BR",
				JSONUtil.getValue(
					jsonObject, "JSONObject/settingsContext", "JSONArray/pages",
					"Object/0", "JSONArray/rows", "Object/0",
					"JSONArray/columns", "Object/0", "JSONArray/fields",
					"Object/0", "Object/locale"));
		}
	}

	@Test(expected = ClientErrorException.class)
	public void testAcceptLanguage2() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).httpServletRequest(
				new MockHttpServletRequest() {

					@Override
					public String getHeader(String name) {
						if (StringUtil.equals(name, "Accept-Language")) {
							return "INVALID";
						}

						return super.getHeader(name);
					}

				}
			).user(
				_user
			).build();

		dataDefinitionResource.getDataDefinitionDataDefinitionFieldFieldTypes();
	}

	@Test(expected = NullPointerException.class)
	public void testHttpServletRequest() throws Exception {
		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				_user
			).build();

		dataDefinitionResource.getDataDefinitionDataDefinitionFieldFieldTypes();
	}

	@Inject(type = JSONFactory.class)
	private JSONFactory _jsonFactory;

	@DeleteAfterTestRun
	private User _user;

}