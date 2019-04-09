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

package com.liferay.journal.asset.auto.tagger.opennlp.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class OpenNLPDocumentAssetAutoTagProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetTagNamesWithTextFile() throws Exception {
		String fileName = _FILE_NAME + ".txt";

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), _FILE_NAME,
			new String(
				FileUtil.getBytes(getClass(), "dependencies/" + fileName)));

		_testWithOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> expectedTagNames = Arrays.asList(
					"AT ALL.", "Adventures", "Alice", "Australia", "Bill",
					"General Information About Project", "IRS",
					"Internal Revenue Service", "Lewis Carroll", "Mary Ann",
					"Michael Hart", "Michael S. Hart", "Mississippi", "NOT",
					"Paris", "Pat", "Pepper", "Queens", "Rabbit", "Rome",
					"Salt Lake City", "THERE", "United States", "White Rabbit",
					"William", "YOUR");

				Collection<String> actualTagNames =
					_assetAutoTagProvider.getTagNames(journalArticle);

				Assert.assertEquals(
					actualTagNames.toString(), expectedTagNames.size(),
					actualTagNames.size());
				Assert.assertTrue(
					actualTagNames.toString(),
					actualTagNames.containsAll(expectedTagNames));
			});
	}

	@Test
	public void testGetTagNamesWithTextFileAndDisabledConfiguration()
		throws Exception {

		String fileName = _FILE_NAME + ".txt";

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), _FILE_NAME,
			new String(
				FileUtil.getBytes(getClass(), "dependencies/" + fileName)));

		_testWithOpenNLPAutoTagProviderDisabled(
			() -> {
				Collection<String> tagNames = _assetAutoTagProvider.getTagNames(
					journalArticle);

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	@Test
	public void testGetTagNamesWithTextInNoEnglishLanguage() throws Exception {
		String fileName = _FILE_NAME + ".txt";

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, _FILE_NAME,
			new String(
				FileUtil.getBytes(getClass(), "dependencies/" + fileName)),
			LocaleUtil.SPAIN, false, false);

		_testWithOpenNLPAutoTagProviderEnabled(
			() -> {
				Collection<String> tagNames = _assetAutoTagProvider.getTagNames(
					journalArticle);

				Assert.assertEquals(tagNames.toString(), 0, tagNames.size());
			});
	}

	private void _testWithOpenNLPAutoTagProviderDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_OPENNLP_AUTO_TAG_CONFIGURATION_CLASS_NAME,
					new HashMapDictionary<String, Object>() {
						{
							put("enabled", false);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private void _testWithOpenNLPAutoTagProviderEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_OPENNLP_AUTO_TAG_CONFIGURATION_CLASS_NAME,
					new HashMapDictionary<String, Object>() {
						{
							put("confidenceThreshold", 0.9);
							put("enabled", true);
						}
					})) {

			unsafeRunnable.run();
		}
	}

	private static final String _FILE_NAME =
		"Alice's Adventures in Wonderland, by Lewis Carroll";

	private static final String _OPENNLP_AUTO_TAG_CONFIGURATION_CLASS_NAME =
		"com.liferay.journal.asset.auto.tagger.opennlp.internal." +
			"configuration." +
				"OpenNLPDocumentAssetAutoTagProviderCompanyConfiguration";

	@Inject(
		filter = "component.name=com.liferay.journal.asset.auto.tagger.opennlp.internal.OpenNLPDocumentAssetAutoTagProvider"
	)
	private AssetAutoTagProvider _assetAutoTagProvider;

	@DeleteAfterTestRun
	private Group _group;

}