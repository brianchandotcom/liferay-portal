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

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.taxonomy.client.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.client.http.HttpInvoker;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class KeywordResourceTest extends BaseKeywordResourceTestCase {

	@Test
	public void testSomething() throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.contentType("application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.parameter("page", "1");
		httpInvoker.parameter("pageSize", "2");
		httpInvoker.path(
			"http://localhost:8080/o/headless-admin-taxonomy/v1.0/sites/{siteId}/keywords",
			testGroup.getGroupId());
		httpInvoker.userNameAndPassword("test@liferay.com", "test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		System.out.println(
			"## HTTP Response content: " + httpResponse.getContent());
		System.out.println(
			"## HTTP Response message: " + httpResponse.getMessage());
		System.out.println(
			"## HTTP Response status: " + httpResponse.getStatus());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"name"};
	}

	@Override
	protected Keyword randomKeyword() {
		Keyword keyword = super.randomKeyword();

		keyword.setName(StringUtil.toLowerCase(keyword.getName()));

		return keyword;
	}

}