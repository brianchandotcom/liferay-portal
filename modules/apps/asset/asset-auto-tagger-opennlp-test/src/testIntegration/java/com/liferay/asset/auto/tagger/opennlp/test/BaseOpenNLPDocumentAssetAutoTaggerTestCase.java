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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 * @author Alejandro Tardín
 */
public abstract class BaseOpenNLPDocumentAssetAutoTaggerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAutoTagsAnAsset() throws Exception {
		testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			getClassName(),
			() -> {
				AssetEntry assetEntry = getAssetEntry(getTaggableText());

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
	public void testAutoTagsAnAssetInNotEnglishLanguage() throws Exception {
		testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
			getClassName(),
			() -> {
				AssetEntry assetEntry = getAssetEntry(
					new String(
						FileUtil.getBytes(
							getClass(),
							"dependencies/" + _FILE_NAME_NOT_ENGLISH)));

				Collection<String> tagNames = Arrays.asList(
					assetEntry.getTagNames());

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	@Test
	public void testDoesNotAutoTagAnAssetWhenNotEnabled() throws Exception {
		testWithOpenNLPDocumentAssetAutoTagProviderDisabled(
			() -> {
				AssetEntry assetEntry = getAssetEntry(getTaggableText());

				Collection<String> tagNames = Arrays.asList(
					assetEntry.getTagNames());

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	protected abstract AssetEntry getAssetEntry(String text) throws Exception;

	protected abstract String getClassName();

	protected final String getTaggableText() throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + _FILE_NAME));
	}

	protected void testWithOpenNLPDocumentAssetAutoTagProviderDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_OPEN_NLP_AUTO_TAG_CONFIGURATION_PID,
					new HashMapDictionary<String, Object>() {
						{
							put("enabledClassNames", new String[0]);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	protected void testWithOpenNLPDocumentAssetAutoTagProviderEnabled(
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

	@Inject
	protected AssetEntryLocalService assetEntryLocalService;

	@DeleteAfterTestRun
	protected Group group;

	private static final String _FILE_NAME =
		"Alice's Adventures in Wonderland, by Lewis Carroll.txt";

	private static final String _FILE_NAME_NOT_ENGLISH = "25328-0.txt";

	private static final String _OPEN_NLP_AUTO_TAG_CONFIGURATION_PID =
		"com.liferay.asset.auto.tagger.opennlp.internal.configuration." +
			"OpenNLPDocumentAssetAutoTaggerCompanyConfiguration";

}