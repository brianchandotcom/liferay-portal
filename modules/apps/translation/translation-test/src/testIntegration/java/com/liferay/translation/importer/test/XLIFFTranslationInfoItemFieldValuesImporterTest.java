/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.importer.TranslationInfoItemFieldValuesImporter;
import com.liferay.translation.test.util.TranslationTestUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
public class XLIFFTranslationInfoItemFieldValuesImporterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = XLIFFFileException.MustHaveCorrectEncoding.class)
	public void testImportXLIFF2FailsFileIncorrectEncoding() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importInfoItemFieldValues(
			_group.getGroupId(),
			new InfoItemReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-122-iso-8859-encoding.xlf"));
	}

	@Test(expected = XLIFFFileException.MustHaveValidId.class)
	public void testImportXLIFF12FailsFileInvalidId() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importInfoItemFieldValues(
			_group.getGroupId(),
			new InfoItemReference(
				JournalArticle.class.getName(), RandomTestUtil.randomInt(1, 3)),
			TranslationTestUtil.readFileToInputStream(
				"example-1_2-simple.xlf"));
	}

	@Test(expected = XLIFFFileException.MustBeWellFormed.class)
	public void testImportXLIFF12FailsFileInvalidVersion() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importInfoItemFieldValues(
			_group.getGroupId(),
			new InfoItemReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream(
				"example-1_2-bad-formed.xlf"));
	}

	@Test
	public void testImportXLIFF12VersionDocument() throws Exception {
		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"example-1_2-oasis.xlf"));

		Assert.assertNotNull(infoItemFieldValues);
		Assert.assertNotNull(infoItemFieldValues.getInfoFieldValues());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertFalse(infoFieldValues.isEmpty());
	}

	@Test
	public void testImportXLIFF12VersionSimpleDocument() throws Exception {
		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"example-1_2-simple.xlf"));

		Assert.assertNotNull(infoItemFieldValues);
		Assert.assertNotNull(infoItemFieldValues.getInfoFieldValues());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertFalse(infoFieldValues.isEmpty());
	}

	@Test(expected = XLIFFFileException.MustBeSupportedLanguage.class)
	public void testImportXLIFF20FailsFileInvalidGroupLanguage()
		throws Exception {

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), _locales, LocaleUtil.US);

		_xliffTranslationInfoItemFieldValuesImporter.importInfoItemFieldValues(
			_group.getGroupId(),
			new InfoItemReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-122-ja-JP.xlf"));
	}

	@Test(expected = XLIFFFileException.MustHaveValidId.class)
	public void testImportXLIFF20FailsFileInvalidId() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importInfoItemFieldValues(
			_group.getGroupId(),
			new InfoItemReference(
				JournalArticle.class.getName(), RandomTestUtil.randomInt(1, 3)),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-122.xlf"));
	}

	@Test(expected = XLIFFFileException.MustBeSupportedLanguage.class)
	public void testImportXLIFF20FailsFileInvalidLanguage() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importInfoItemFieldValues(
			_group.getGroupId(),
			new InfoItemReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-122-pt-PT.xlf"));
	}

	@Test(expected = XLIFFFileException.MustBeWellFormed.class)
	public void testImportXLIFF20FailsFileNoTarget() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importInfoItemFieldValues(
			_group.getGroupId(),
			new InfoItemReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-no-target.xlf"));
	}

	@Test
	public void testImportXLIFF20VersionDocument() throws Exception {
		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.
				importInfoItemFieldValues(
					_group.getGroupId(),
					new InfoItemReference(JournalArticle.class.getName(), 122),
					TranslationTestUtil.readFileToInputStream(
						"test-journal-article-122.xlf"));

		Assert.assertNotNull(infoItemFieldValues);
		Assert.assertNotNull(infoItemFieldValues.getInfoFieldValues());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertFalse(infoFieldValues.isEmpty());
	}

	private static final Set<Locale> _locales = new HashSet<>(
		Arrays.asList(
			LocaleUtil.BRAZIL, LocaleUtil.HUNGARY, LocaleUtil.SPAIN,
			LocaleUtil.US));

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "content.type=application/xliff+xml")
	private TranslationInfoItemFieldValuesImporter
		_xliffTranslationInfoItemFieldValuesImporter;

}