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
import com.liferay.portal.kernel.util.ProxyUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Cristina González
 */
public class MockHttpUtil {

	public static Http geHttp(
		Map<String, UnsafeSupplier<String, Exception>> mockRequest) {

		return (Http)ProxyUtil.newProxyInstance(
			Http.class.getClassLoader(), new Class<?>[] {Http.class},
			(proxy, method, args) -> {
				if (!Objects.equals("URLtoString", method.getName()) ||
					(args.length != 1) || !(args[0] instanceof Http.Options)) {

					return null;
				}

				Http.Options options = (Http.Options)args[0];

				try {
					String location = options.getLocation();

					String endpoint = location.substring(
						location.lastIndexOf("/api"),
						_getLastPosition(location));

					if (mockRequest.containsKey(endpoint)) {
						Http.Response httpResponse = new Http.Response();

						httpResponse.setResponseCode(200);

						options.setResponse(httpResponse);

						UnsafeSupplier<String, Exception> unsafeSupplier =
							mockRequest.get(endpoint);

						return unsafeSupplier.get();
					}

					Http.Response httpResponse = new Http.Response();

					httpResponse.setResponseCode(400);

					options.setResponse(httpResponse);

					return "error";
				}
				catch (Throwable throwable) {
					Http.Response httpResponse = new Http.Response();

					httpResponse.setResponseCode(400);

					options.setResponse(httpResponse);

					throw new RuntimeException(throwable);
				}
			});
	}

	private static int _getLastPosition(String location) {
		if (location.contains("?")) {
			return location.indexOf("?");
		}

		return location.length();
	}

}