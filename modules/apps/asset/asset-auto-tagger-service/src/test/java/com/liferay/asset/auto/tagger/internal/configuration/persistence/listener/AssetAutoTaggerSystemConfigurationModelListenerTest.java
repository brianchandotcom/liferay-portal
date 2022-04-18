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

package com.liferay.asset.auto.tagger.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Alicia Garcia
 */
public class AssetAutoTaggerSystemConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpAssetAutoTaggerSystemConfigurationModelListener();
		_setUpResourceBundleUtil();
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testMaximumNumberOfTagsPerAssetNegative()
		throws ConfigurationModelListenerException {

		_assetAutoTaggerSystemConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"maximumNumberOfTagsPerAsset", -1
			).build());
	}

	private void _setUpAssetAutoTaggerSystemConfigurationModelListener() {
		_assetAutoTaggerSystemConfigurationModelListener =
			new AssetAutoTaggerSystemConfigurationModelListener();
	}

	private void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private AssetAutoTaggerSystemConfigurationModelListener
		_assetAutoTaggerSystemConfigurationModelListener;

}