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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Alicia Garcia
 */
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class AssetAutoTaggerSystemConfigurationModelListenerTest {

	@Before
	public void setUp() {
		_setUpAssetAutoTaggerSystemConfigurationModelListener();
		_setUpResourceBundleUtil();
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testMaximumNumberOfTagsPerAssetNegative()
		throws ConfigurationModelListenerException {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("maximumNumberOfTagsPerAsset", -1);

		_assetAutoTaggerSystemConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(), properties);
	}

	private void _setUpAssetAutoTaggerSystemConfigurationModelListener() {
		_assetAutoTaggerSystemConfigurationModelListener =
			new AssetAutoTaggerSystemConfigurationModelListener();
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private AssetAutoTaggerSystemConfigurationModelListener
		_assetAutoTaggerSystemConfigurationModelListener;

}