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

package com.liferay.portal.search.web.internal.facet;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.internal.asset.AssetRendererFactoryRegistry;
import com.liferay.portal.search.internal.asset.SearchableAssetClassNamesProviderImpl;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Adam Brandizzi
 */
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.liferay.portal.kernel.search.BaseIndexer")
public class AssetEntriesSearchFacetTest {

	@Before
	public void setUp() {
		assetEntriesSearchFacet = new AssetEntriesSearchFacet() {
			{
				searchableAssetClassNamesProvider =
					new SearchableAssetClassNamesProviderImpl() {
						{
							assetRendererFactoryRegistry =
								_assetRendererFactoryRegistry;
							searchEngineHelper = _searchEngineHelper;
						}
					};
			}
		};

		mockAssetRendererFactoryGetClassName(
			assetRendererFactory1, CLASS_NAME_1);
		mockAssetRendererFactoryIsSearchable(assetRendererFactory1, true);

		mockAssetRendererFactoryGetClassName(
			assetRendererFactory2, CLASS_NAME_2);
		mockAssetRendererFactoryIsSearchable(assetRendererFactory2, true);
	}

	@Test
	public void testGetAssetTypes() {
		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			assetEntryClassNames,
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInRegistry() {
		mockAssetRendererFactoryRegistry(assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInSearchEngineHelper() {
		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1};

		mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			assetEntryClassNames,
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotSearchable() {
		mockAssetRendererFactoryIsSearchable(assetRendererFactory1, false);

		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	protected void mockAssetRendererFactoryGetClassName(
		AssetRendererFactory<?> assetRendererFactory, String className) {

		Mockito.when(
			assetRendererFactory.getClassName()
		).thenReturn(
			className
		);
	}

	protected void mockAssetRendererFactoryIsSearchable(
		AssetRendererFactory<?> assetRendererFactory, boolean searchable) {

		Mockito.when(
			assetRendererFactory.isSearchable()
		).thenReturn(
			searchable
		);
	}

	protected void mockAssetRendererFactoryRegistry(
		AssetRendererFactory<?>... assetRendererFactories) {

		Mockito.when(
			_assetRendererFactoryRegistry.getAssetRendererFactories(
				Matchers.anyLong())
		).thenReturn(
			Arrays.asList(assetRendererFactories)
		);
	}

	protected void mockSearchEngineHelperassetEntryClassNames(
		String[] assetEntryClassNames) {

		Mockito.when(
			_searchEngineHelper.getEntryClassNames()
		).thenReturn(
			assetEntryClassNames
		);
	}

	protected static final String CLASS_NAME_1 = "com.liferay.model.Model1";

	protected static final String CLASS_NAME_2 = "com.liferay.model.Model2";

	protected AssetEntriesSearchFacet assetEntriesSearchFacet;

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory1;

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory2;

	@Mock
	private AssetRendererFactoryRegistry _assetRendererFactoryRegistry;

	@Mock
	private SearchEngineHelper _searchEngineHelper;

}