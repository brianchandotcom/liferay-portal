/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.google.cloud.natural.language.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
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
				Collection<String> tagNames = ReflectionTestUtil.invoke(
					_gCloudNaturalLanguageDocumentAssetAutoTagger,
					"getTagNames",
					new Class<?>[] {long.class, String.class, String.class},
					RandomTestUtil.randomLong(),
					Arrays.toString(RandomTestUtil.randomStrings(20)),
					ContentTypes.TEXT_PLAIN);

				Assert.assertEquals(
					tagNames.toString(), Collections.emptySet(), tagNames);
			});
	}

	@Test
	public void testGetTagNamesWithInvalidAPIKey() throws Exception {
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
					ReflectionTestUtil.invoke(
						_gCloudNaturalLanguageDocumentAssetAutoTagger,
						"getTagNames",
						new Class<?>[] {long.class, String.class, String.class},
						RandomTestUtil.randomLong(),
						Arrays.toString(RandomTestUtil.randomStrings(20)),
						ContentTypes.TEXT_PLAIN);

					Assert.fail();
				}
				catch (Exception exception) {
					Assert.assertTrue(exception instanceof PortalException);
					Assert.assertEquals(
						"Unable to generate tags with the Google Natural " +
							"Language service. Response code 400: API key " +
								"not valid. Please pass a valid API key.",
						exception.getMessage());
				}
				finally {
					ReflectionTestUtil.setFieldValue(
						_gCloudNaturalLanguageDocumentAssetAutoTagger, "_http",
						originalHttp);
				}
			},
			"invalidAPIKey");
	}

	@Test
	public void testGetTagNamesWithUnsupportedType() throws Exception {
		_testWithGCloudNaturalLanguageAutoTagEntitiesEndpointEnabledAndClassificationEndpointEnabled(
			() -> {
				Collection<String> tagNames = ReflectionTestUtil.invoke(
					_gCloudNaturalLanguageDocumentAssetAutoTagger,
					"getTagNames",
					new Class<?>[] {long.class, String.class, String.class},
					RandomTestUtil.randomLong(),
					Arrays.toString(RandomTestUtil.randomStrings(20)),
					ContentTypes.IMAGE_JPEG);

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
					_CLASS_NAME_G_CLOUD_NATURAL_LANGUAGE_ASSET_AUTO_TAGGER_COMPANY_CONFIGURATION,
					HashMapDictionaryBuilder.<String, Object>put(
						"classificationEndpointEnabled", false
					).put(
						"entityEndpointEnabled", false
					).build())) {

			unsafeRunnable.run();
		}
	}

	private void
			_testWithGCloudNaturalLanguageAutoTagEntitiesEndpointEnabledAndClassificationEndpointEnabled(
				UnsafeRunnable<Exception> unsafeRunnable, String apiKey)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CLASS_NAME_G_CLOUD_NATURAL_LANGUAGE_ASSET_AUTO_TAGGER_COMPANY_CONFIGURATION,
					HashMapDictionaryBuilder.<String, Object>put(
						"apiKey", apiKey
					).put(
						"classificationEndpointEnabled", true
					).put(
						"entityEndpointEnabled", true
					).build())) {

			unsafeRunnable.run();
		}
	}

	private static final String
		_CLASS_NAME_G_CLOUD_NATURAL_LANGUAGE_ASSET_AUTO_TAGGER_COMPANY_CONFIGURATION =
			"com.liferay.asset.auto.tagger.google.cloud.natural.language." +
				"internal.configuration." +
					"GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration";

	@Inject(
		filter = "component.name=com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.GCloudNaturalLanguageDocumentAssetAutoTagger",
		type = Inject.NoType.class
	)
	private Object _gCloudNaturalLanguageDocumentAssetAutoTagger;

}