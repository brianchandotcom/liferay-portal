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

package com.liferay.portal.kernel.search.facet.util;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacetFactory;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.test.TestIndexerRegistry;
import com.liferay.portal.util.PropsImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Raymond Augé
 */
@PrepareOnlyThisForTest(SearchEngineHelperUtil.class)
@RunWith(PowerMockRunner.class)
public class FacetFactoryTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		PropsUtil.setProps(new PropsImpl());

		Registry registry = RegistryUtil.getRegistry();

		registry.registerService(
			IndexerRegistry.class, new TestIndexerRegistry());

		SearchEngineHelper searchEngineHelper = Mockito.mock(
			SearchEngineHelper.class);

		Mockito.when(
			searchEngineHelper.getDefaultSearchEngineId()
		).thenReturn(
			SearchEngineHelper.SYSTEM_ENGINE_ID
		);

		Mockito.when(
			searchEngineHelper.getEntryClassNames()
		).thenReturn(
			new String[] {
				"com.liferay.portal.kernel.plugin.PluginPackage",
				"com.liferay.asset.kernel.model.AssetEntry"
			}
		);

		registry.registerService(SearchEngineHelper.class, searchEngineHelper);
	}

	@Test(expected = NullPointerException.class)
	public void testEmptyFacetConfiguration() throws Exception {
		FacetFactoryUtil.create(new SearchContext(), new FacetConfiguration());
	}

	@Test
	public void testMissingFacetFactory() throws Exception {
		FacetConfiguration facetConfiguration = new FacetConfiguration();

		facetConfiguration.setClassName(AssetEntriesFacet.class.getName());

		Facet facet = FacetFactoryUtil.create(
			new SearchContext(), facetConfiguration);

		Assert.assertNull(facet);
	}

	@Test
	public void testRegisteredFacetFactory() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<?> serviceRegistration = registry.registerService(
			FacetFactory.class, new AssetEntriesFacetFactory());

		try {
			FacetConfiguration facetConfiguration = new FacetConfiguration();

			facetConfiguration.setClassName(AssetEntriesFacet.class.getName());

			Facet facet = FacetFactoryUtil.create(
				new SearchContext(), facetConfiguration);

			Assert.assertNotNull(facet);
		}
		finally {
			serviceRegistration.unregister();
		}
	}

}