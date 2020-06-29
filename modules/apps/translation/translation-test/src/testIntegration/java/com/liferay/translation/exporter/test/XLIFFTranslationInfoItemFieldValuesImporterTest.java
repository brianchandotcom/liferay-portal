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

package com.liferay.translation.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.translation.exception.XLIFFFileException;
import com.liferay.translation.exporter.TranslationInfoItemFieldValuesExporter;
import com.liferay.translation.test.util.TranslationTestUtil;

import java.util.Collection;

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
	public static final LiferayIntegrationTestRule testRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = XLIFFFileException.class)
	public void testImportXLIFF2FailsFileInvalidId() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importXLIFF(
			_group.getGroupId(),
			new InfoItemClassPKReference(
				JournalArticle.class.getName(), RandomTestUtil.randomInt(1, 3)),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-122.xlf"));
	}

	@Test(expected = XLIFFFileException.MustBeSupportedLanguage.class)
	public void testImportXLIFF2FailsFileInvalidLanguage() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importXLIFF(
			_group.getGroupId(),
			new InfoItemClassPKReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-122-pt-PT.xlf"));
	}

	@Test(expected = XLIFFFileException.MustBeValid.class)
	public void testImportXLIFF2FailsFileInvalidVersion() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importXLIFF(
			_group.getGroupId(),
			new InfoItemClassPKReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream("example-1_2-oasis.xlf"));
	}

	@Test(expected = XLIFFFileException.MustHaveValidId.class)
	public void testImportXLIFF2FailsFileNoTarget() throws Exception {
		_xliffTranslationInfoItemFieldValuesImporter.importXLIFF(
			_group.getGroupId(),
			new InfoItemClassPKReference(
				JournalArticle.class.getName(), RandomTestUtil.randomInt(1, 3)),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article-no-target.xlf"));
	}

	@Test
	public void testImportXLIFFXLIFFDocument() throws Exception {
		InfoItemFieldValues infoItemFieldValues =
			_xliffTranslationInfoItemFieldValuesImporter.importXLIFF(
				_group.getGroupId(),
				new InfoItemClassPKReference(
					JournalArticle.class.getName(), 122),
				TranslationTestUtil.readFileToInputStream(
					"test-journal-article-122.xlf"));

		Assert.assertNotNull(infoItemFieldValues);
		Assert.assertNotNull(infoItemFieldValues.getInfoFieldValues());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertFalse(infoFieldValues.isEmpty());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "content.type=application/xliff+xml")
	private TranslationInfoItemFieldValuesExporter
		_xliffTranslationInfoItemFieldValuesImporter;

}