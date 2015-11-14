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

import com.liferay.portal.configurator.extender.Configuration;
import com.liferay.portal.configurator.extender.ConfigurationFactory;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.osgi.framework.Bundle;

/**
 * @author Carlos Sierra Andrés
 */
public class ConfigurationFactoryImplTest {

	@BeforeClass
	public static void setUpClass() {
		URL.setURLStreamHandlerFactory(
			new URLStreamHandlerFactory() {

				@Override
				public URLStreamHandler createURLStreamHandler(
					String protocol) {

					if (protocol.equals("string")) {
						return new StringURLStreamHandler();
					}

					return null;
				}

			});
	}

	@Test
	public void testCreateConfiguration() throws IOException {
		ConfigurationFactory configurationFactory =
			new ConfigurationFactoryImpl();

		List<Configuration> configurations = configurationFactory.create(
			_createBundle(
				_createStringURL("config.pid", "key1=value1\nkey2=value2")));

		Assert.assertEquals(1, configurations.size());

		Configuration configuration = configurations.get(0);

		Assert.assertNull(configuration.getFactoryPid());
		Assert.assertEquals("config.pid", configuration.getPid());

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertEquals(2, properties.size());
		Assert.assertEquals("value1", properties.get("key1"));
		Assert.assertEquals("value2", properties.get("key2"));
	}

	@Test
	public void testCreateFactoryConfiguration() throws IOException {
		ConfigurationFactory configurationFactory =
			new ConfigurationFactoryImpl();

		List<Configuration> configurations = configurationFactory.create(
			_createBundle(
				_createStringURL(
					"factory.pid", "config.pid", "key1=value1\nkey2=value2")));

		Assert.assertEquals(1, configurations.size());

		Configuration configuration = configurations.get(0);

		Assert.assertEquals("factory.pid", configuration.getFactoryPid());
		Assert.assertEquals("config.pid", configuration.getPid());

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertEquals(2, properties.size());
		Assert.assertEquals("value1", properties.get("key1"));
		Assert.assertEquals("value2", properties.get("key2"));
	}

	@Test
	public void testCreateMultiConfigurationsWithNestedDir()
		throws IOException {

		ConfigurationFactory configurationFactory =
			new ConfigurationFactoryImpl();

		List<Configuration> configurations = configurationFactory.create(
			_createBundle(
				_createStringURL(
					"/configs/nested/", null, "config.pid1",
					"key1=value1\nkey2=value2"),
				_createStringURL(
					"factory.pid", "config.pid2", "key3=value3\nkey4=value4")));

		Assert.assertEquals(2, configurations.size());

		Configuration configuration = configurations.get(0);

		Assert.assertNull(configuration.getFactoryPid());
		Assert.assertEquals("config.pid1", configuration.getPid());

		Dictionary<String, Object> properties = configuration.getProperties();

		Assert.assertEquals(2, properties.size());
		Assert.assertEquals("value1", properties.get("key1"));
		Assert.assertEquals("value2", properties.get("key2"));

		configuration = configurations.get(1);

		Assert.assertEquals("factory.pid", configuration.getFactoryPid());
		Assert.assertEquals("config.pid2", configuration.getPid());

		properties = configuration.getProperties();

		Assert.assertEquals(2, properties.size());
		Assert.assertEquals("value3", properties.get("key3"));
		Assert.assertEquals("value4", properties.get("key4"));
	}

	private static Bundle _createBundle(final URL... urls) {
		return (Bundle)ProxyUtil.newProxyInstance(
			Bundle.class.getClassLoader(), new Class<?>[] {Bundle.class},
			new InvocationHandler() {

				@Override
				public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {

					if (method.equals(
							Bundle.class.getMethod(
								"findEntries", String.class, String.class,
								boolean.class))) {

						return Collections.enumeration(Arrays.asList(urls));
					}

					if (method.equals(Bundle.class.getMethod("getHeaders"))) {
						Dictionary<String, String> dictionary =
							new HashMapDictionary<>();

						dictionary.put("ConfigurationPath", StringPool.BLANK);

						return dictionary;
					}

					throw new UnsupportedOperationException(method.toString());
				}

			});
	}

	private static URL _createStringURL(String pid, String content)
		throws MalformedURLException {

		return _createStringURL(null, null, pid, content);
	}

	private static URL _createStringURL(
			String factoryPid, String pid, String content)
		throws MalformedURLException {

		return _createStringURL(null, factoryPid, pid, content);
	}

	private static URL _createStringURL(
			String prefixPath, String factoryPid, String pid, String content)
		throws MalformedURLException {

		String file = pid;

		if (factoryPid != null) {
			file = factoryPid.concat(StringPool.DASH).concat(pid);
		}

		if (prefixPath != null) {
			file = prefixPath.concat(file);
		}

		URL url = new URL("string", StringPool.BLANK, file);

		_urlStrings.put(url, content);

		return url;
	}

	private static final Map<URL, String> _urlStrings = new HashMap<>();

	private static class StringURLStreamHandler extends URLStreamHandler {

		@Override
		protected URLConnection openConnection(URL url) throws IOException {
			return new URLConnection(url) {

				@Override
				public void connect() {
				}

				@Override
				public InputStream getInputStream() throws IOException {
					String s = _urlStrings.get(url);

					return new UnsyncByteArrayInputStream(
						s.getBytes(StringPool.ISO_8859_1));
				}

			};
		}

	}

}