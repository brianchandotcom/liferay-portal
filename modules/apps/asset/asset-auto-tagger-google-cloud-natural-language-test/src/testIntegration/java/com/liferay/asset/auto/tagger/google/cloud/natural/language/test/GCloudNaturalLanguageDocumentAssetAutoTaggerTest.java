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

package com.liferay.asset.auto.tagger.google.cloud.natural.language.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.GCloudNaturalLanguageDocumentAssetAutoTagger;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class GCloudNaturalLanguageDocumentAssetAutoTaggerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetTagNamesWithAllEndpointDisabled() throws Exception {
		_testWithGCloudNaturalLanguageAutoTagAndEntitiesEndpointDisabledAndClassificationEndpointDisabled(
			() -> {
				Collection<String> tagNames =
					_gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN);

				Assert.assertEquals(
					tagNames.toString(), Collections.emptySet(), tagNames);
			});
	}

	@Test
	public void testGetTagNamesWithInvalidApiKey() throws Exception {
		_testWithGCloudNaturalLanguageAutoTagEntitiesEndpointEnabledAndClassificationEndpointEnabled(
			() -> {
				Object originalHttp = ReflectionTestUtil.getAndSetFieldValue(
					_gCloudNaturalLanguageDocumentAssetAutoTagger, "_http",
					ProxyUtil.newProxyInstance(
						Http.class.getClassLoader(),
						new Class<?>[] {Http.class},
						(proxy, method, args) -> {
							if (!Objects.equals(
									method.getName(), "URLtoString")) {

								throw new UnsupportedOperationException();
							}

							Http.Options options = (Http.Options)args[0];

							Http.Response response = new Http.Response();

							response.setResponseCode(400);

							options.setResponse(response);

							return "{\"error\": {\"message\": \"API key not " +
								"valid. Please pass a valid API key.\"}}";
						}));

				try {
					_gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN);

					Assert.fail();
				}
				catch (Exception e) {
					Assert.assertTrue(e instanceof PortalException);
					Assert.assertEquals(
						"Unable to generate tags with the Google Natural " +
							"Language service. Response code 400: API key " +
								"not valid. Please pass a valid API key.",
						e.getMessage());
				}
				finally {
					ReflectionTestUtil.setFieldValue(
						_gCloudNaturalLanguageDocumentAssetAutoTagger, "_http",
						originalHttp);
				}
			},
			"invalidApiKey");
	}

	@Test
	public void testGetTagNamesWithUnsupportedType() throws Exception {
		_testWithGCloudNaturalLanguageAutoTagEntitiesEndpointEnabledAndClassificationEndpointEnabled(
			() -> {
				Collection<String> tagNames =
					_gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG);

				Assert.assertEquals(
					tagNames.toString(), Collections.emptySet(), tagNames);
			},
			RandomTestUtil.randomString());
	}

	private void
			_testWithGCloudNaturalLanguageAutoTagAndEntitiesEndpointDisabledAndClassificationEndpointDisabled(
				UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_G_CLOUD_NATURAL_LANGUAGE_AUTO_TAG_CONFIGURATION_CLASS_NAME,
					new HashMapDictionary<String, Object>() {
						{
							put("entityEndpointEnabled", false);
							put("classificationEndpointEnabled", false);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private void
			_testWithGCloudNaturalLanguageAutoTagEntitiesEndpointEnabledAndClassificationEndpointEnabled(
				UnsafeRunnable<Exception> unsafeRunnable, String apiKey)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_G_CLOUD_NATURAL_LANGUAGE_AUTO_TAG_CONFIGURATION_CLASS_NAME,
					new HashMapDictionary<String, Object>() {
						{
							put("apiKey", apiKey);
							put("classificationEndpointEnabled", true);
							put("entityEndpointEnabled", true);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String
		_G_CLOUD_NATURAL_LANGUAGE_AUTO_TAG_CONFIGURATION_CLASS_NAME =
			"com.liferay.asset.auto.tagger.google.cloud.natural.language." +
				"internal.configuration." +
					"GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration";

	@Inject
	private GCloudNaturalLanguageDocumentAssetAutoTagger
		_gCloudNaturalLanguageDocumentAssetAutoTagger;

}