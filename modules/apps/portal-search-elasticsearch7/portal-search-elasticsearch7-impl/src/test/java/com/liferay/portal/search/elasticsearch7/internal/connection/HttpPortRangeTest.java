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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.util.PropsImpl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 * @author André de Oliveira
 */
public class HttpPortRangeTest {

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testEmbeddedHttpPort() {
		mockEmbeddedHttpPort(4400);

		assertSidecarHttpPort("4400");
	}

	@Test
	public void testHasDefaultHttpPort() {
		assertSidecarHttpPort("9201");
	}

	@Test
	public void testSidecarHttpPort() {
		mockSidecarHttpPort("3100-3199");

		assertSidecarHttpPort("3100-3199");
	}

	@Test
	public void testSidecarHttpPortAuto() {
		mockEmbeddedHttpPort(4400);
		mockSidecarHttpPort(HttpPortRange.AUTO);

		assertSidecarHttpPort("9201-9300");
	}

	@Test
	public void testSidecarHttpPortHasPrecedenceOverEmbeddedHttpPort() {
		mockEmbeddedHttpPort(4400);
		mockSidecarHttpPort("3100-3199");

		assertSidecarHttpPort("3100-3199");
	}

	protected void assertSidecarHttpPort(String expected) {
		ElasticsearchConfigurationWrapper elasticsearchConfigurationWrapper =
			new ElasticsearchConfigurationWrapper() {
				{
					setElasticsearchConfiguration(
						ConfigurableUtil.createConfigurable(
							ElasticsearchConfiguration.class,
							HashMapBuilder.<String, Object>put(
								"embeddedHttpPort", _embeddedHttpPort
							).put(
								"sidecarHttpPort", _sidecarHttpPort
							).build()));
				}
			};

		HttpPortRange httpPortRange = new HttpPortRange(
			elasticsearchConfigurationWrapper);

		Assert.assertEquals(expected, httpPortRange.toSettingsString());
	}

	protected void mockEmbeddedHttpPort(int embeddedHttpPort) {
		_embeddedHttpPort = embeddedHttpPort;
	}

	protected void mockSidecarHttpPort(String sidecarHttpPort) {
		_sidecarHttpPort = sidecarHttpPort;
	}

	private Integer _embeddedHttpPort;
	private String _sidecarHttpPort;

}