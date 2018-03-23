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

package com.liferay.commerce.cloud.server.util;

import com.liferay.commerce.cloud.server.service.ForecastConfigurationService;
import com.liferay.commerce.cloud.server.service.ProjectService;

import io.vertx.core.json.JsonObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCloudUtilTest {

	@Test
	public void testGetDataServiceHost() {
		JsonObject configJsonObject = new JsonObject();

		configJsonObject.put("WEDEPLOY_PROJECT_ID", "commercecloud");

		Assert.assertEquals(
			"data-commercecloud.wedeploy.io",
			CommerceCloudUtil.getWeDeployServiceHost(
				configJsonObject, _WEDEPLOY_DATA_SERVICE_ID,
				_DEFAULT_WEDEPLOY_DATA_SERVICE_ID));

		configJsonObject.put(_WEDEPLOY_DATA_SERVICE_ID, "foo");

		Assert.assertEquals(
			"foo-commercecloud.wedeploy.io",
			CommerceCloudUtil.getWeDeployServiceHost(
				configJsonObject, _WEDEPLOY_DATA_SERVICE_ID,
				_DEFAULT_WEDEPLOY_DATA_SERVICE_ID));
	}

	@Test
	public void testGetPort() {
		JsonObject configJsonObject = new JsonObject();

		Assert.assertEquals(8080, CommerceCloudUtil.getPort(configJsonObject));

		configJsonObject.put("PORT", 1234);

		Assert.assertEquals(1234, CommerceCloudUtil.getPort(configJsonObject));
	}

	@Test
	public void testGetServiceAddress() {
		Assert.assertEquals(
			"com.liferay.commerce.cloud.server.service.forecast.configuration",
			CommerceCloudUtil.getServiceAddress(
				ForecastConfigurationService.class));
		Assert.assertEquals(
			"com.liferay.commerce.cloud.server.service.project",
			CommerceCloudUtil.getServiceAddress(ProjectService.class));
	}

	@Test
	public void testGetServiceHost() {
		JsonObject configJsonObject = new JsonObject();

		configJsonObject.put("WEDEPLOY_PROJECT_ID", "commercecloud");

		Assert.assertEquals(
			"server-commercecloud.wedeploy.io",
			CommerceCloudUtil.getHost(configJsonObject));

		configJsonObject.put("WEDEPLOY_SERVICE_ID", "foo");

		Assert.assertEquals(
			"foo-commercecloud.wedeploy.io",
			CommerceCloudUtil.getHost(configJsonObject));

		configJsonObject.put("SERVICE_HOST", "example.com");

		Assert.assertEquals(
			"example.com", CommerceCloudUtil.getHost(configJsonObject));
	}

	private static final String _DEFAULT_WEDEPLOY_DATA_SERVICE_ID = "data";

	private static final String _WEDEPLOY_DATA_SERVICE_ID =
		"WEDEPLOY_DATA_SERVICE_ID";

}