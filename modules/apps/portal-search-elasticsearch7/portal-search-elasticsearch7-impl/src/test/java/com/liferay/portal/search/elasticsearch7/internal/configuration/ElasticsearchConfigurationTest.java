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

package com.liferay.portal.search.elasticsearch7.internal.configuration;

import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;
import java.util.Properties;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ElasticsearchConfigurationTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConfigurationsFromBuildTestXmlAntFile() throws Exception {
		Map<String, Object> configurationProperties =
			_loadConfigurationProperties(
				"ElasticsearchConfigurationTest-build-test-xml.cfg");

		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchConfigurationTest.class.getSimpleName()
			).elasticsearchConfigurationProperties(
				configurationProperties
			).build();

		try {
			elasticsearchConnectionFixture.createNode();
		}
		finally {
			elasticsearchConnectionFixture.destroyNode();
		}
	}

	private Map<String, Object> _loadConfigurationProperties(String fileName)
		throws Exception {

		Properties properties = new Properties();

		Class<?> clazz = getClass();

		properties.load(clazz.getResourceAsStream(fileName));

		return PropertiesUtil.toMap(properties);
	}

}