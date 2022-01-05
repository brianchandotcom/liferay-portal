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

		_mockAssetRendererFactoryGetClassName(
			assetRendererFactory1, CLASS_NAME_1);
		_mockAssetRendererFactoryIsSearchable(assetRendererFactory1, true);

		_mockAssetRendererFactoryGetClassName(
			assetRendererFactory2, CLASS_NAME_2);
		_mockAssetRendererFactoryIsSearchable(assetRendererFactory2, true);
	}

	@Test
	public void testGetAssetTypes() {
		_mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		_mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			assetEntryClassNames,
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInRegistry() {
		_mockAssetRendererFactoryRegistry(assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		_mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInSearchEngineHelper() {
		_mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1};

		_mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			assetEntryClassNames,
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotSearchable() {
		_mockAssetRendererFactoryIsSearchable(assetRendererFactory1, false);

		_mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] assetEntryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		_mockSearchEngineHelperassetEntryClassNames(assetEntryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			assetEntriesSearchFacet.getAssetTypes(RandomTestUtil.randomLong()));
	}

	protected static final String CLASS_NAME_1 = "com.liferay.model.Model1";

	protected static final String CLASS_NAME_2 = "com.liferay.model.Model2";

	protected AssetEntriesSearchFacet assetEntriesSearchFacet;

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory1;

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory2;

	private void _mockAssetRendererFactoryGetClassName(
		AssetRendererFactory<?> assetRendererFactory, String className) {

		Mockito.when(
			assetRendererFactory.getClassName()
		).thenReturn(
			className
		);
	}

	private void _mockAssetRendererFactoryIsSearchable(
		AssetRendererFactory<?> assetRendererFactory, boolean searchable) {

		Mockito.when(
			assetRendererFactory.isSearchable()
		).thenReturn(
			searchable
		);
	}

	private void _mockAssetRendererFactoryRegistry(
		AssetRendererFactory<?>... assetRendererFactories) {

		Mockito.when(
			_assetRendererFactoryRegistry.getAssetRendererFactories(
				Matchers.anyLong())
		).thenReturn(
			Arrays.asList(assetRendererFactories)
		);
	}

	private void _mockSearchEngineHelperassetEntryClassNames(
		String[] assetEntryClassNames) {

		Mockito.when(
			_searchEngineHelper.getEntryClassNames()
		).thenReturn(
			assetEntryClassNames
		);
	}

	@Mock
	private AssetRendererFactoryRegistry _assetRendererFactoryRegistry;

	@Mock
	private SearchEngineHelper _searchEngineHelper;

}