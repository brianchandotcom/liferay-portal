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

package com.liferay.asset.auto.tagger.opennlp.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.opennlp.util.MockAssetRendererFactory;
import com.liferay.asset.auto.tagger.text.extractor.TextExtractor;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class OpenNLPDocumentAssetAutoTaggerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() {
		if (_assetRendererFactoryServiceRegistration != null) {
			_assetRendererFactoryServiceRegistration.unregister();

			_assetRendererFactoryServiceRegistration = null;
		}

		if (_textExtractorServiceRegistration != null) {
			_textExtractorServiceRegistration.unregister();

			_textExtractorServiceRegistration = null;
		}
	}

	@Test
	public void testAutoTagsAnAssetWithATextExtractor() throws Exception {
		String className = RandomTestUtil.randomString();

		_registerAssetRendererFactory(
			new MockAssetRendererFactory(
				TestPropsValues.getGroupId(), className,
				new String(
					FileUtil.getBytes(
						getClass(), "dependencies/" + _FILE_NAME))));

		_registerTextExtractor(
			new TextExtractor<String>() {

				@Override
				public String extract(String text, Locale locale) {
					return text;
				}

				@Override
				public String getClassName() {
					return className;
				}

			});

		_testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			className,
			() -> {
				AssetEntry assetEntry = _assetEntryLocalService.updateEntry(
					TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
					className, RandomTestUtil.randomLong(), new long[0],
					new String[0]);

				Collection<String> actualTagNames = Arrays.asList(
					assetEntry.getTagNames());

				Collection<String> expectedTagNames = Arrays.asList(
					"adventures", "at all.", "ah", "alice", "alice .",
					"archive foundation", "australia", "beau--ootiful", "bill",
					"chapter", "cheshire cat",
					"dr. gregory b. newby chief executive", "edgar atheling",
					"foundation", "general information about project",
					"general terms", "geography", "herald", "i", "if", "irs",
					"internal revenue service", "king", "latitude", "laughing",
					"lewis carroll", "lewis carroll posting date",
					"lewis carroll this", "lizard", "london", "mine", "ma !",
					"mary ann", "michael hart", "michael s. hart",
					"mississippi", "not", "new zealand", "paris", "pat",
					"pat !", "pepper", "pray",
					"project gutenberg literary archive foundation",
					"project gutenberg literary archive foundation project " +
						"gutenberg-tm",
					"project gutenberg-tm", "public domain", "queen", "queens",
					"rabbit", "rome", "salt lake city", "shakespeare", "shark",
					"soup", "there", "the", "united states", "very", "would",
					"white rabbit", "whoever", "william", "you.--come", "your");

				Assert.assertEquals(
					actualTagNames.toString(), expectedTagNames.size(),
					actualTagNames.size());
				Assert.assertTrue(actualTagNames.containsAll(expectedTagNames));
			});
	}

	@Test
	public void testDoesNotAutoTagAnAssetWithNoTextExtractor()
		throws Exception {

		String className = RandomTestUtil.randomString();

		_registerAssetRendererFactory(
			new MockAssetRendererFactory(
				TestPropsValues.getGroupId(), className, null));

		_testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			className,
			() -> {
				AssetEntry assetEntry = _assetEntryLocalService.updateEntry(
					TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
					className, RandomTestUtil.randomLong(), new long[0],
					new String[0]);

				Collection<String> tagNames = Arrays.asList(
					assetEntry.getTagNames());

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	private void _registerAssetRendererFactory(
		AssetRendererFactory assetRendererFactory) {

		Registry registry = RegistryUtil.getRegistry();

		_assetRendererFactoryServiceRegistration = registry.registerService(
			AssetRendererFactory.class, assetRendererFactory);
	}

	private void _registerTextExtractor(TextExtractor textExtractor) {
		Registry registry = RegistryUtil.getRegistry();

		_textExtractorServiceRegistration = registry.registerService(
			TextExtractor.class, textExtractor);
	}

	private void _testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			String className, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_OPEN_NLP_AUTO_TAG_CONFIGURATION_PID,
					new HashMapDictionary<String, Object>() {
						{
							put("enabledClassNames", new String[] {className});
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String _FILE_NAME =
		"Alice's Adventures in Wonderland, by Lewis Carroll.txt";

	private static final String _OPEN_NLP_AUTO_TAG_CONFIGURATION_PID =
		"com.liferay.asset.auto.tagger.opennlp.internal.configuration." +
			"OpenNLPDocumentAssetAutoTaggerCompanyConfiguration";

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private ServiceRegistration<AssetRendererFactory>
		_assetRendererFactoryServiceRegistration;
	private ServiceRegistration<TextExtractor>
		_textExtractorServiceRegistration;

}