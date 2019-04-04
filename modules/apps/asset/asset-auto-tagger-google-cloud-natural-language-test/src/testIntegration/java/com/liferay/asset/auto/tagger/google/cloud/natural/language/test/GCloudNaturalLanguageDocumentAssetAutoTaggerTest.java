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
import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.GCloudNaturalLanguageDocumentAssetAutoTagger;
import com.liferay.asset.auto.tagger.google.cloud.natural.language.api.configuration.GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Collections;

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
		GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
			configuration =
				new GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration() {

					@Override
					public String apiKey() {
						return null;
					}

					@Override
					public boolean classificationEndpointEnabled() {
						return false;
					}

					@Override
					public float confidence() {
						return 0;
					}

					@Override
					public boolean entityEndpointEnabled() {
						return false;
					}

					@Override
					public float salience() {
						return 0;
					}

				};

		Collection<String> tagNames =
			_gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
				configuration, RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN);

		Assert.assertEquals(
			tagNames.toString(), Collections.emptySet(), tagNames);
	}

	@Test
	public void testGetTagNamesWithInvalidApiKey() {
		GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
			configuration =
				new GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration() {

					@Override
					public String apiKey() {
						return "invalidApiKey";
					}

					@Override
					public boolean classificationEndpointEnabled() {
						return true;
					}

					@Override
					public float confidence() {
						return 0;
					}

					@Override
					public boolean entityEndpointEnabled() {
						return true;
					}

					@Override
					public float salience() {
						return 0;
					}

				};

		try {
			_gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
				configuration, RandomTestUtil.randomString(),
				ContentTypes.TEXT_PLAIN);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof PortalException);
			Assert.assertEquals(
				"Unable to generate tags with the Google Natural Language " +
					"service. Response code 400: API key not valid. Please " +
						"pass a valid API key.",
				e.getMessage());
		}
	}

	@Test
	public void testGetTagNamesWithUnsupportedType() throws Exception {
		GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration
			configuration =
				new GCloudNaturalLanguageAssetAutoTagProviderCompanyConfiguration() {

					@Override
					public String apiKey() {
						return null;
					}

					@Override
					public boolean classificationEndpointEnabled() {
						return true;
					}

					@Override
					public float confidence() {
						return 0;
					}

					@Override
					public boolean entityEndpointEnabled() {
						return true;
					}

					@Override
					public float salience() {
						return 0;
					}

				};

		Collection<String> tagNames =
			_gCloudNaturalLanguageDocumentAssetAutoTagger.getTagNames(
				configuration, RandomTestUtil.randomString(),
				ContentTypes.IMAGE_JPEG);

		Assert.assertEquals(
			tagNames.toString(), Collections.emptySet(), tagNames);
	}

	@Inject
	private GCloudNaturalLanguageDocumentAssetAutoTagger
		_gCloudNaturalLanguageDocumentAssetAutoTagger;

}