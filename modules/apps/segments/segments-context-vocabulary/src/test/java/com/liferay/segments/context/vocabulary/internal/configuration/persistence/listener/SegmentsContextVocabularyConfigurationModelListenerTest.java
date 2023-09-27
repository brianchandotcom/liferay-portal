/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener;

import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.context.Context;

import java.util.Dictionary;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Yurena Cabrera
 */
public class SegmentsContextVocabularyConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		ResourceBundleLoader resourceBundleLoader = Mockito.mock(
			ResourceBundleLoader.class);

		ResourceBundleLoaderUtil.setPortalResourceBundleLoader(
			resourceBundleLoader);

		Mockito.when(
			resourceBundleLoader.loadResourceBundle(Mockito.any())
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	@Test(
		expected = DuplicatedSegmentsContextVocabularyConfigurationModelListenerException.class
	)
	public void testCannotCreateSameVocabularyForSameCompany()
		throws Exception {

		String pid =
			"com.liferay.segments.context.vocabulary.internal.configuration." +
				"SegmentsContextVocabularyConfiguration";

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"assetVocabularyName", "topic"
			).put(
				"companyId", "123"
			).put(
				"entityFieldName", Context.BROWSER
			).build();

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			properties
		);

		_setUpConfigurationAdmin(configuration);

		_segmentsContextVocabularyConfigurationModelListener.onBeforeSave(
			pid, properties);
	}

	@Test(
		expected = DuplicatedSegmentsContextVocabularyConfigurationModelListenerException.class
	)
	public void testCannotCreateVocabularyThatOverwritesPreviousOneForSameCompany()
		throws Exception {

		String pid =
			"com.liferay.segments.context.vocabulary.internal.configuration." +
				"SegmentsContextVocabularyConfiguration";

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"assetVocabularyName", "topic"
			).put(
				"companyId", "123"
			).put(
				"entityFieldName", Context.BROWSER
			).build();

		Dictionary<String, Object> propertiesConfiguration =
			HashMapDictionaryBuilder.<String, Object>put(
				"assetVocabularyName", "audience"
			).put(
				"companyId", "123"
			).put(
				"entityFieldName", Context.BROWSER
			).build();

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			propertiesConfiguration
		);

		_setUpConfigurationAdmin(configuration);

		_segmentsContextVocabularyConfigurationModelListener.onBeforeSave(
			pid, properties);
	}

	@Test(expected = Test.None.class)
	public void testCreateValidVocabularyForCompanyWhenOtherAlreadyCreatedForOtherCompany()
		throws Exception {

		String pid =
			"com.liferay.segments.context.vocabulary.internal.configuration." +
				"SegmentsContextVocabularyCompanyConfiguration";

		Dictionary<String, Object> properties =
			HashMapDictionaryBuilder.<String, Object>put(
				"assetVocabularyName", "topic"
			).put(
				"companyId", "123"
			).put(
				"entityFieldName", Context.BROWSER
			).build();

		Dictionary<String, Object> propertiesConfiguration =
			HashMapDictionaryBuilder.<String, Object>put(
				"assetVocabularyName", "audience"
			).put(
				"companyId", "456"
			).put(
				"entityFieldName", Context.BROWSER
			).build();

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			propertiesConfiguration
		);

		_setUpConfigurationAdmin(configuration);

		_segmentsContextVocabularyConfigurationModelListener.onBeforeSave(
			pid, properties);
	}

	private void _setUpConfigurationAdmin(Configuration configuration)
		throws Exception {

		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.any())
		).thenReturn(
			new Configuration[] {configuration}
		);

		ReflectionTestUtil.getAndSetFieldValue(
			_segmentsContextVocabularyConfigurationModelListener,
			"configurationAdmin", configurationAdmin);
	}

	private final SegmentsContextVocabularyConfigurationModelListener
		_segmentsContextVocabularyConfigurationModelListener =
			new SegmentsContextVocabularyConfigurationModelListener();

}