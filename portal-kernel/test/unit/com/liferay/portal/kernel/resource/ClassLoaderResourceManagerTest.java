/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void testGetResourceRetriever() {
		ResourceRetriever resourceRetriever =
			_resourceManager.getResourceRetriever(
				"com/liferay/portal/kernel/resource/dependencies" +
					"/test.properties");

		Assert.assertNotNull(resourceRetriever);
		Assert.assertNotNull(resourceRetriever.getInputStream());

		resourceRetriever = _resourceManager.getResourceRetriever(
			"resource-not-found.properties");

		Assert.assertNotNull(resourceRetriever);
		Assert.assertNull(resourceRetriever.getInputStream());
	}

	private final ResourceManager _resourceManager =
		new ClassLoaderResourceManager(
			ClassLoaderResourceManagerTest.class.getClassLoader());

}