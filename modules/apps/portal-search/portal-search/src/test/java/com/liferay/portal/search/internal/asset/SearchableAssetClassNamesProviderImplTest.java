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

package com.liferay.portal.search.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Adam Brandizzi
 */
public class SearchableAssetClassNamesProviderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		searchableAssetClassNamesProviderImpl =
			new SearchableAssetClassNamesProviderImpl() {
				{
					assetRendererFactoryRegistry =
						_assetRendererFactoryRegistry;
					searchEngineHelper = _searchEngineHelper;
				}
			};

		mockAssetRendererFactoryGetClassName(
			assetRendererFactory1, CLASS_NAME_1);

		mockAssetRendererFactoryGetClassName(
			assetRendererFactory2, CLASS_NAME_2);

		mockAssetRendererFactoryIsSearchable(assetRendererFactory1, true);

		mockAssetRendererFactoryIsSearchable(assetRendererFactory2, true);
	}

	@Test
	public void testGetAssetTypes() {
		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			entryClassNames,
			searchableAssetClassNamesProviderImpl.getClassNames(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInRegistry() {
		mockAssetRendererFactoryRegistry(assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			searchableAssetClassNamesProviderImpl.getClassNames(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotInSearchEngineHelper() {
		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			entryClassNames,
			searchableAssetClassNamesProviderImpl.getClassNames(
				RandomTestUtil.randomLong()));
	}

	@Test
	public void testGetAssetTypesNotSearchable() {
		mockAssetRendererFactoryIsSearchable(assetRendererFactory1, false);

		mockAssetRendererFactoryRegistry(
			assetRendererFactory1, assetRendererFactory2);

		String[] entryClassNames = {CLASS_NAME_1, CLASS_NAME_2};

		mockSearchEngineHelperEntryClassNames(entryClassNames);

		Assert.assertArrayEquals(
			new String[] {CLASS_NAME_2},
			searchableAssetClassNamesProviderImpl.getClassNames(
				RandomTestUtil.randomLong()));
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

	protected void mockSearchEngineHelperEntryClassNames(
		String[] entryClassNames) {

		Mockito.when(
			_searchEngineHelper.getEntryClassNames()
		).thenReturn(
			entryClassNames
		);
	}

	protected static final String CLASS_NAME_1 = "com.liferay.model.Model1";

	protected static final String CLASS_NAME_2 = "com.liferay.model.Model2";

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory1;

	@Mock
	protected AssetRendererFactory<?> assetRendererFactory2;

	protected SearchableAssetClassNamesProviderImpl
		searchableAssetClassNamesProviderImpl;

	@Mock
	private AssetRendererFactoryRegistry _assetRendererFactoryRegistry;

	@Mock
	private SearchEngineHelper _searchEngineHelper;

}