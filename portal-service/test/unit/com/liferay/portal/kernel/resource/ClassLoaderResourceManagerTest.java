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

package com.liferay.portal.kernel.resource;

import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.resource.manager.ResourceManager;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class ClassLoaderResourceManagerTest {

	@Test
	public void testLoadExistingResource() {
		ResourceRetriever resourceRetriever =
			_resourceManager.getResourceRetriever(_RESOURCE_LOCATION);

		Assert.assertNotNull(resourceRetriever);
		Assert.assertNotNull(resourceRetriever.getInputStream());
	}

	@Test
	public void testLoadNonExistingResource() {
		ResourceRetriever resourceRetriever =
			_resourceManager.getResourceRetriever(
				"resource-not-found.properties");

		Assert.assertNotNull(resourceRetriever);
		Assert.assertNull(resourceRetriever.getInputStream());
	}

	private static final String _RESOURCE_LOCATION =
		"com/liferay/portal/kernel/resource/dependencies/" +
			"classpath-resource.properties";

	private final ResourceManager _resourceManager =
		new ClassLoaderResourceManager(
			ClassLoaderResourceManagerTest.class.getClassLoader());

}