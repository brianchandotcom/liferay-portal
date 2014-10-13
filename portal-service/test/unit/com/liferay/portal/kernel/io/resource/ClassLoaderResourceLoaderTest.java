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

package com.liferay.portal.kernel.io.resource;

import com.liferay.portal.kernel.io.resource.loader.ClassLoaderResourceLoader;
import com.liferay.portal.kernel.io.resource.loader.ResourceLoader;
import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class ClassLoaderResourceLoaderTest {

	@Test
	public void testLoadExistingResource() {
		Resource resource = _resourceLoader.getResource(_RESOURCE_LOCATION);

		Assert.assertNotNull(resource);
		Assert.assertTrue(resource.exists());
		Assert.assertEquals(_RESOURCE_LOCATION, resource.getName());
		Assert.assertEquals("classpath.resource=true", resource.readContent());
	}

	@Test
	public void testLoadNonExistingResource() {
		Resource resource = _resourceLoader.getResource(
			"resource-not-found.properties");

		Assert.assertNotNull(resource);
		Assert.assertFalse(resource.exists());
		Assert.assertEquals(
			"resource-not-found.properties", resource.getName());
		Assert.assertEquals(StringPool.BLANK, resource.readContent());
	}

	private static final String _RESOURCE_LOCATION =
		"com/liferay/portal/kernel/io/resource/dependencies/" +
			"classpath-resource.properties";

	private ResourceLoader _resourceLoader = new ClassLoaderResourceLoader(
		ClassLoaderResourceLoaderTest.class.getClassLoader());

}