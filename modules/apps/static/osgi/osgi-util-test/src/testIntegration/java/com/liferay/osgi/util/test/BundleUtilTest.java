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

package com.liferay.osgi.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.osgi.util.BundleUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.net.URL;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(Arquillian.class)
public class BundleUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_bundle = FrameworkUtil.getBundle(BundleUtilTest.class);
	}

	@Test
	public void testGetResourceInBundleOrFragments() throws IOException {
		URL url = BundleUtil.getResourceInBundleOrFragments(
			_bundle, "/bundle-util/file.properties");

		Properties properties = new Properties();

		properties.load(url.openStream());

		Assert.assertEquals("aValue", properties.getProperty("aKey"));
	}

	@Test
	public void testGetResourceInBundleOrFragmentsWhenDir() {
		URL url = BundleUtil.getResourceInBundleOrFragments(
			_bundle, "/bundle-util");

		Assert.assertNotNull(url);
	}

	@Test
	public void testGetResourceInBundleOrFragmentsWhenInRoot()
		throws IOException {

		URL url = BundleUtil.getResourceInBundleOrFragments(
			_bundle, "/fileInRoot.properties");

		Properties properties = new Properties();

		properties.load(url.openStream());

		Assert.assertEquals("aRootValue", properties.getProperty("aRootKey"));
	}

	@Test
	public void testGetResourceInBundleOrFragmentsWhenMissing() {
		URL url = BundleUtil.getResourceInBundleOrFragments(
			_bundle, "/fileMissing.properties");

		Assert.assertNull(url);
	}

	@Test
	public void testGetResourceInBundleOrFragmentsWhenRoot() {
		URL url = BundleUtil.getResourceInBundleOrFragments(_bundle, "/");

		Assert.assertNotNull(url);
	}

	private Bundle _bundle;

}