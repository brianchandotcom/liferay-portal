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
import com.liferay.portal.remote.cors.configuration.WebContextCORSConfiguration;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;

import javax.ws.rs.HttpMethod;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marta Medio
 */
@RunWith(Arquillian.class)
public class WebContextConfigurationCORSClientTest
	extends BaseCORSClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("osgi.jaxrs.name", "test-cors");

		registerJaxRsApplication(new CORSTestApplication(), "cors", properties);

		properties = new HashMapDictionary<>();

		properties.put(
			"servlet.context.helper.select.filter",
			"(osgi.jaxrs.name=test-cors)");

		createFactoryConfiguration(
			WebContextCORSConfiguration.class.getName(), properties);
	}

	@Test
	public void testApplicationCORSForGuestUser() throws Exception {
		assertJaxRSUrl("/cors/cors-app", HttpMethod.OPTIONS, false, true);
		assertJaxRSUrl("/cors/cors-app", HttpMethod.GET, false, true);
	}

	@Test
	public void testApplicationCORSWithoutOAuth2() throws Exception {
		assertJaxRSUrl("/cors/cors-app", HttpMethod.OPTIONS, true, true);
		assertJaxRSUrl("/cors/cors-app", HttpMethod.GET, true, false);
	}

}