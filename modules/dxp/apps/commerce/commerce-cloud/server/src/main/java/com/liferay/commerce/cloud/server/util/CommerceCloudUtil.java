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

package com.liferay.commerce.cloud.server.util;

import io.vertx.core.json.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCloudUtil {

	public static final String getHost(JsonObject configJsonObject) {
		String serviceHost = configJsonObject.getString("SERVICE_HOST");

		if (serviceHost == null) {
			serviceHost = getWeDeployServiceHost(
				configJsonObject, "WEDEPLOY_SERVICE_ID", "server");
		}

		return serviceHost;
	}

	public static final int getPort(JsonObject configJsonObject) {
		return configJsonObject.getInteger("PORT", 8080);
	}

	public static final String getServiceAddress(Class<?> clazz) {
		String className = clazz.getName();

		if (className.endsWith("Service")) {
			className = className.substring(0, className.length() - 7);
		}

		StringBuffer sb = new StringBuffer();

		Matcher matcher = _serviceAddressPattern.matcher(className);

		while (matcher.find()) {
			String c = matcher.group(1);

			matcher.appendReplacement(sb, "." + c.toLowerCase());
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	public static String getWeDeployDataServiceHost(
		JsonObject configJsonObject) {

		return getWeDeployServiceHost(
			configJsonObject, "WEDEPLOY_DATA_SERVICE_ID", "data");
	}

	public static final String getWeDeployServiceHost(
		JsonObject configJsonObject, String serviceIdKey,
		String defaultServiceId) {

		StringBuilder sb = new StringBuilder();

		sb.append(configJsonObject.getString(serviceIdKey, defaultServiceId));
		sb.append('-');
		sb.append(configJsonObject.getString("WEDEPLOY_PROJECT_ID"));
		sb.append('.');
		sb.append(
			configJsonObject.getString(
				"WEDEPLOY_SERVICE_DOMAIN", "wedeploy.io"));

		return sb.toString();
	}

	public static String getWeDeployToken(JsonObject configJsonObject) {
		return configJsonObject.getString("WEDEPLOY_PROJECT_MASTER_TOKEN");
	}

	private static final Pattern _serviceAddressPattern = Pattern.compile(
		"\\.?([A-Z])");

}