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

package com.liferay.site.initializer.testray.extra.java.function.http;

import com.liferay.site.initializer.testray.extra.java.function.util.PropsValues;

/**
 * @author José Abelenda
 */
public class HttpUtil {

	public static void get(String objectName, long groupId) throws Exception {
		System.out.println("objectName: " + objectName);

		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(PropsValues.TESTRAY_BASE_URL + objectName + "/scopes/"+ groupId);

		System.out.println("path: " + PropsValues.TESTRAY_BASE_URL + objectName + "/scopes/"+ groupId);

			// httpInvoker.path("objectDefinitionId", objectDefinitionId);

			httpInvoker.userNameAndPassword(
				"test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();
		
		// System.out.println("response:" + httpResponse);

		String content = httpResponse.getContent();

		// System.out.println("content:" + content);

		if ((httpResponse.getStatusCode() / 100) != 2) {
			System.out.println(
				"Unable to process HTTP response content: " + content);

			System.out.println(
				"HTTP response message: " + httpResponse.getMessage());

			System.out.println(
				"HTTP response status code: " +
					httpResponse.getStatusCode());

		}

	}
}