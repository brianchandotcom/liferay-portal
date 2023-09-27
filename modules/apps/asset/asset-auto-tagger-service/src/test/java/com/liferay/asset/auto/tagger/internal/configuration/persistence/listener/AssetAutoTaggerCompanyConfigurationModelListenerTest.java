/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.configuration.persistence.listener;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alicia Garcia
 */
public class AssetAutoTaggerCompanyConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpAssetAutoTaggerCompanyConfigurationModelListener();
		_setUpResourceBundleUtil();
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testMaximumNumberOfTagsPerAssetGreaterThanSystem()
		throws ConfigurationModelListenerException {

		_setUpAssetAutoTaggerConfigurationFactory();

		ReflectionTestUtil.setFieldValue(
			_assetAutoTaggerCompanyConfigurationModelListener,
			"_assetAutoTaggerConfigurationFactory",
			_assetAutoTaggerConfigurationFactory);

		_assetAutoTaggerCompanyConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"maximumNumberOfTagsPerAsset", 11
			).build());
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testMaximumNumberOfTagsPerAssetNegative()
		throws ConfigurationModelListenerException {

		_assetAutoTaggerCompanyConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"maximumNumberOfTagsPerAsset", -1
			).build());
	}

	private void _setUpAssetAutoTaggerCompanyConfigurationModelListener() {
		_assetAutoTaggerCompanyConfigurationModelListener =
			new AssetAutoTaggerCompanyConfigurationModelListener();
	}

	private void _setUpAssetAutoTaggerConfigurationFactory() {
		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
			new AssetAutoTaggerConfiguration() {

				@Override
				public int getMaximumNumberOfTagsPerAsset() {
					return 10;
				}

				@Override
				public boolean isAvailable() {
					return true;
				}

				@Override
				public boolean isEnabled() {
					return true;
				}

				@Override
				public boolean isUpdateAutoTags() {
					return false;
				}

			};

		Mockito.doReturn(
			assetAutoTaggerConfiguration
		).when(
			_assetAutoTaggerConfigurationFactory
		).getSystemAssetAutoTaggerConfiguration();
	}

	private void _setUpResourceBundleUtil() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(
				Mockito.nullable(Locale.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private AssetAutoTaggerCompanyConfigurationModelListener
		_assetAutoTaggerCompanyConfigurationModelListener;
	private final AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory = Mockito.mock(
			AssetAutoTaggerConfigurationFactory.class);

}