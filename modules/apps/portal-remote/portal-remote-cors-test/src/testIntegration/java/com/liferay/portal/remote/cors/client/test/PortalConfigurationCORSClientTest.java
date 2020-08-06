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

package com.liferay.portal.remote.cors.client.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.remote.cors.configuration.PortalCORSConfiguration;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import javax.ws.rs.HttpMethod;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marta Medio
 */
@RunWith(Arquillian.class)
public class PortalConfigurationCORSClientTest extends BaseCORSClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCORSUsingBasicWithCustomConfig() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("configuration.name", "test-cors");

		properties.put("filter.mapping.url.pattern", "/api/jsonws/*");

		createFactoryConfiguration(
			PortalCORSConfiguration.class.getName(), properties);

		assertJsonWSUrl("/user/get-current-user", HttpMethod.OPTIONS, true);
		assertJsonWSUrl("/user/get-current-user", HttpMethod.GET, false);
	}

	@Test
	public void testCORSUsingBasicWithDefaultConfig() throws Exception {
		assertJsonWSUrl("/user/get-current-user", HttpMethod.OPTIONS, true);
		assertJsonWSUrl("/user/get-current-user", HttpMethod.GET, false);
	}

}