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

package com.liferay.portal.search.elasticsearch;

import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CheckElasticsearchModuleStartedTest {

	@Test
	public void testElasticsearchIsTheSearchEngine() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		SearchEngine searchEngineService = registry.getService(
			SearchEngine.class);

		Assert.assertNotNull(searchEngineService);

		Class<? extends SearchEngine> searchEngineServiceClass =
			searchEngineService.getClass();

		String searchEngineServiceClassName =
			searchEngineServiceClass.getCanonicalName();

		Assert.assertTrue(
			"The service registered as SearchEngine is " +
				searchEngineServiceClassName,
			searchEngineServiceClassName.endsWith("ElasticsearchSearchEngine"));
	}

}