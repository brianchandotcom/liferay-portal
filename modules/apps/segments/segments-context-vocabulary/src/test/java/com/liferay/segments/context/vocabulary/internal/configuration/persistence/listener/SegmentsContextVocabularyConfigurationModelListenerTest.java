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

package com.liferay.segments.context.vocabulary.internal.configuration.persistence.listener;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.context.Context;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ResourceBundle;

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
				"assetVocabulary", "topic"
			).put(
				"companyId", "123"
			).put(
				"entityField", Context.BROWSER
			).build();

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			properties
		);

		Configuration[] configurationList = {configuration};

		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.any())
		).thenReturn(
			configurationList
		);

		ReflectionTestUtil.getAndSetFieldValue(
			_segmentsContextVocabularyConfigurationModelListener,
			"_configurationAdmin", configurationAdmin);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		ReflectionTestUtil.getAndSetFieldValue(
			_segmentsContextVocabularyConfigurationModelListener,
			"_resourceBundle", resourceBundle);

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
				"assetVocabulary", "topic"
			).put(
				"companyId", "123"
			).put(
				"entityField", Context.BROWSER
			).build();

		Dictionary<String, Object> propertiesConfiguration = new Hashtable<>();

		propertiesConfiguration.put("entityField", Context.BROWSER);
		propertiesConfiguration.put("assetVocabulary", "audience");
		propertiesConfiguration.put("companyId", "123");

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			propertiesConfiguration
		);

		Configuration[] configurationList = {configuration};

		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.any())
		).thenReturn(
			configurationList
		);

		ReflectionTestUtil.getAndSetFieldValue(
			_segmentsContextVocabularyConfigurationModelListener,
			"_configurationAdmin", configurationAdmin);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		ReflectionTestUtil.getAndSetFieldValue(
			_segmentsContextVocabularyConfigurationModelListener,
			"_resourceBundle", resourceBundle);

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
				"assetVocabulary", "topic"
			).put(
				"companyId", "123"
			).put(
				"entityField", Context.BROWSER
			).build();

		Dictionary<String, Object> propertiesConfiguration = new Hashtable<>();

		propertiesConfiguration.put("entityField", Context.BROWSER);
		propertiesConfiguration.put("assetVocabulary", "audience");
		propertiesConfiguration.put("companyId", "678");

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			propertiesConfiguration
		);

		Configuration[] configurationList = {configuration};

		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.any())
		).thenReturn(
			configurationList
		);

		ReflectionTestUtil.getAndSetFieldValue(
			_segmentsContextVocabularyConfigurationModelListener,
			"_configurationAdmin", configurationAdmin);

		ReflectionTestUtil.getAndSetFieldValue(
			_segmentsContextVocabularyConfigurationModelListener,
			"_resourceBundle", null);

		_segmentsContextVocabularyConfigurationModelListener.onBeforeSave(
			pid, properties);
	}

	private final SegmentsContextVocabularyConfigurationModelListener
		_segmentsContextVocabularyConfigurationModelListener =
			new SegmentsContextVocabularyConfigurationModelListener();

}