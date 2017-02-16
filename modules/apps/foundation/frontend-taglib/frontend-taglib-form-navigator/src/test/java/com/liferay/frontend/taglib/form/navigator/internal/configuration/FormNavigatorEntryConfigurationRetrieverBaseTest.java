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

package com.liferay.frontend.taglib.form.navigator.internal.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import org.junit.After;
import org.junit.Before;

import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

/**
 * @author Alejandro Tardín
 */
public abstract class FormNavigatorEntryConfigurationRetrieverBaseTest {

	@Before
	public void setUp() throws Exception {
		FrameworkFactory factory = ServiceLoader.load(
			FrameworkFactory.class).iterator().next();

		Map<String, String> configuration = new HashMap<>();

		Path cacheFolder = Paths.get(
			System.getProperty("java.io.tmpdir"), "osgi-cache");

		configuration.put("org.osgi.framework.storage", cacheFolder.toString());

		_framework = factory.newFramework(configuration);

		_framework.start();

		formNavigatorEntryConfigurationRetriever.activate(
			_framework.getBundleContext());
	}

	@After
	public final void tearDown() {
		try {
			_framework.stop();
			_framework.waitForStop(0);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void createConfiguration(
		String formNavigatorId, String[] formNavigatorEntryKeys) {

		Map<String, Object> properties = new HashMap<>();

		properties.put("formNavigatorEntryKeys", formNavigatorEntryKeys);
		properties.put("formNavigatorId", formNavigatorId);

		FormNavigatorEntryConfigurationParser
			formNavigatorEntryConfigurationParser =
				new FormNavigatorEntryConfigurationParser();

		formNavigatorEntryConfigurationParser.activate(properties);

		BundleContext bundleContext = _framework.getBundleContext();

		bundleContext.registerService(
			FormNavigatorEntryConfigurationParser.class,
			formNavigatorEntryConfigurationParser, null);
	}

	protected FormNavigatorEntryConfigurationRetriever
		formNavigatorEntryConfigurationRetriever =
			new FormNavigatorEntryConfigurationRetriever();

	private Framework _framework;

}