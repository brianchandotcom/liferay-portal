/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.manager.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.translation.manager.TranslationManager;
import com.liferay.translation.test.util.TranslationTestUtil;

import java.io.File;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class TranslationManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_journalArticle = TranslationTestUtil.getJournalArticle(
			_group, _ddmFormDeserializer);
	}

	@Test
	public void testGetXLIFFZipFile() throws Exception {
		_validateZipContent(
			"test-journal-article-v12.xlf", _MIMETYPE_XLIFF_1_2);

		_validateZipContent("test-journal-article.xlf", _MIMETYPE_XLIFF_2_0);
	}

	private void _validateZipContent(String fileName, String mimetype)
		throws Exception {

		File xliffZipFile = _translationManager.getXLIFFZipFile(
			JournalArticle.class.getName(),
			new long[] {_journalArticle.getResourcePrimKey()}, mimetype,
			LocaleUtil.US, LocaleUtil.toLanguageId(LocaleUtil.US),
			_TARGET_LANGUAGE_IDS);

		try (ZipFile zipFile = new ZipFile(xliffZipFile)) {
			int count = 0;

			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				if (!zipEntry.isDirectory()) {
					Assert.assertEquals(
						StringUtil.replace(
							TranslationTestUtil.readFileToString(fileName),
							"[$JOURNAL_ARTICLE_ID$]",
							String.valueOf(
								_journalArticle.getResourcePrimKey())),
						StringUtil.read(zipFile.getInputStream(zipEntry)));

					count++;
				}
			}

			Assert.assertEquals(1, count);

			String xliffZipFileName = xliffZipFile.getName();

			Assert.assertTrue(
				xliffZipFileName.contains(_journalArticle.getTitle()));
			Assert.assertTrue(
				xliffZipFileName.contains(
					LocaleUtil.toLanguageId(LocaleUtil.US)));
			Assert.assertTrue(xliffZipFileName.contains(".xlf"));
		}
		finally {
			if ((xliffZipFile != null) && xliffZipFile.exists()) {
				xliffZipFile.delete();
			}
		}
	}

	private static final String _MIMETYPE_XLIFF_1_2 = "application/x-xliff+xml";

	private static final String _MIMETYPE_XLIFF_2_0 = "application/xliff+xml";

	private static final String[] _TARGET_LANGUAGE_IDS = {"es_ES"};

	@Inject(filter = "ddm.form.deserializer.type=json")
	private DDMFormDeserializer _ddmFormDeserializer;

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;

	@Inject
	private TranslationManager _translationManager;

}