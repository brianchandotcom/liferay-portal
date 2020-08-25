/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.portlet.action.test.util;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.util.HttpImpl;

import java.io.IOException;

import java.util.Map;

/**
 * @author Cristina González
 */
public class MockHttpUtil {

	public static Http geHttp(
		Map<String, UnsafeSupplier<String, Exception>> mockRequest) {

		return new HttpImpl() {

			@Override
			public String URLtoString(Options options) throws IOException {
				try {
					String location = options.getLocation();

					String endpoint = location.substring(
						location.lastIndexOf("/api"),
						_getLastPosition(location));

					if (mockRequest.containsKey(endpoint)) {
						Response httpResponse = new Response();

						httpResponse.setResponseCode(200);

						options.setResponse(httpResponse);

						UnsafeSupplier<String, Exception> unsafeSupplier =
							mockRequest.get(endpoint);

						return unsafeSupplier.get();
					}

					Response httpResponse = new Response();

					httpResponse.setResponseCode(400);

					options.setResponse(httpResponse);

					return "error";
				}
				catch (Throwable throwable) {
					Response httpResponse = new Response();

					httpResponse.setResponseCode(400);

					options.setResponse(httpResponse);

					throw new RuntimeException(throwable);
				}
			}

		};
	}

	private static int _getLastPosition(String location) {
		if (location.contains("?")) {
			return location.indexOf("?");
		}

		return location.length();
	}

}