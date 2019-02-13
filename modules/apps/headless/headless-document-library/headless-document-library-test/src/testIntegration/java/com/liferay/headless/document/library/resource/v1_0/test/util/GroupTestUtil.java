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

package com.liferay.headless.document.library.resource.v1_0.test.util;

import com.liferay.portal.kernel.util.StringUtil;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Rubén Pulido
 */
public class GroupTestUtil {

	public static Long addGroup(URL url) throws MalformedURLException {
		RequestSpecification requestSpecification =
			_createRequestSpecification();

		return Long.valueOf(
			requestSpecification.param(
				"virtualHost", "localhost"
			).param(
				"parentGroupId", 0
			).param(
				"liveGroupId", 0
			).param(
				"name", StringUtil.randomString(10)
			).param(
				"description", ""
			).param(
				"type", 1
			).param(
				"manualMembership", true
			).param(
				"membershipRestriction", 0
			).param(
				"friendlyURL", "/" + StringUtil.randomString(10)
			).param(
				"site", true
			).param(
				"active", true
			).get(
				new URL(url, "/api/jsonws/group/add-group")
			).then(
			).statusCode(
				200
			).extract(
			).path(
				"groupId"
			)
		);
	}

	public static void deleteGroup(URL url, long groupId)
		throws MalformedURLException {

		RequestSpecification requestSpecification =
			_createRequestSpecification();

		requestSpecification.param(
			"groupId", String.valueOf(groupId)
		).param(
			"active", true
		).get(
			new URL(url, "/api/jsonws/group/delete-group")
		).then(
		).statusCode(
			200
		);
	}

	private static RequestSpecification _createRequestSpecification() {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when();
	}

}