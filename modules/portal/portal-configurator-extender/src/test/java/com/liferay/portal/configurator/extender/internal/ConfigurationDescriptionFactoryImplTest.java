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

package com.liferay.portal.configurator.extender.internal;

import com.liferay.portal.configurator.extender.ConfigurationDescription;
import com.liferay.portal.configurator.extender.ConfigurationDescriptionFactory;
import com.liferay.portal.configurator.extender.NamedConfigurationContent;
import com.liferay.portal.configurator.extender.PropertiesFileNamedConfigurationContent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.Dictionary;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Carlos Sierra Andrés
 */
public class ConfigurationDescriptionFactoryImplTest {

	@Test
	public void testCreateFactoryConfiguration() throws IOException {
		ConfigurationDescriptionFactory configurationDescriptionFactory =
			new ConfigurationDescriptionFactoryImpl();

		String configurationContent = "key=value\nanotherKey=anotherValue";

		ConfigurationDescription configurationDescription =
			configurationDescriptionFactory.create(
				new PropertiesFileNamedConfigurationContent(
					"factory.pid-config.pid",
					new ByteArrayInputStream(
						configurationContent.getBytes(
							Charset.forName("UTF-8")))));

		Assert.assertEquals(
			"factory.pid", configurationDescription.getFactoryPid());
		Assert.assertEquals("config.pid", configurationDescription.getPid());

		Dictionary<String, Object> properties =
			configurationDescription.getProperties();

		Assert.assertEquals("value", properties.get("key"));
		Assert.assertEquals("anotherValue", properties.get("anotherKey"));
	}

	@Test
	public void testCreateReturnsNullWhenNotPropertiesFileNamedConfigurationContent()
		throws IOException {

		ConfigurationDescriptionFactory configurationDescriptionFactory =
			new ConfigurationDescriptionFactoryImpl();

		ConfigurationDescription configurationDescription =
			configurationDescriptionFactory.create(
				new NamedConfigurationContent() {

					@Override
					public String getName() {
						return "aName";
					}

					@Override
					public InputStream getInputStream() {
						return new ByteArrayInputStream(new byte[0]);
					}
				});

		Assert.assertNull(configurationDescription);
	}

	@Test
	public void testCreateSimpleConfiguration() throws IOException {
		ConfigurationDescriptionFactory configurationDescriptionFactory =
			new ConfigurationDescriptionFactoryImpl();

		String configurationContent = "key=value\nanotherKey=anotherValue";

		ConfigurationDescription configurationDescription =
			configurationDescriptionFactory.create(
				new PropertiesFileNamedConfigurationContent(
					"config.pid",
					new ByteArrayInputStream(
						configurationContent.getBytes(
							Charset.forName("UTF-8")))));

		Assert.assertNull(configurationDescription.getFactoryPid());
		Assert.assertEquals("config.pid", configurationDescription.getPid());

		Dictionary<String, Object> properties =
			configurationDescription.getProperties();

		Assert.assertEquals("value", properties.get("key"));
		Assert.assertEquals("anotherValue", properties.get("anotherKey"));
	}

}