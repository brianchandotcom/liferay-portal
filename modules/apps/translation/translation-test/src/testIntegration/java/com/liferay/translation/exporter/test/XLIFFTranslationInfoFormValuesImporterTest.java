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
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.item.InfoItemClassPKReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.translation.exception.InvalidXLIFFFileException;
import com.liferay.translation.exporter.TranslationInfoFormValuesExporter;
import com.liferay.translation.test.util.TranslationTestUtil;

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
public class XLIFFTranslationInfoFormValuesImporterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule testRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(expected = InvalidXLIFFFileException.class)
	public void testImportXLIFF2FailsFileInvalidId() throws Exception {
		_xliffTranslationInfoFormValuesImporter.importXLIFF(
			_group.getGroupId(),
			new InfoItemClassPKReference(
				JournalArticle.class.getName(), RandomTestUtil.randomInt(1, 3)),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article_122.xlf"));
	}

	@Test(expected = InvalidXLIFFFileException.class)
	public void testImportXLIFF2FailsFileInvalidVersion() throws Exception {
		_xliffTranslationInfoFormValuesImporter.importXLIFF(
			_group.getGroupId(),
			new InfoItemClassPKReference(JournalArticle.class.getName(), 122),
			TranslationTestUtil.readFileToInputStream("example_1_2_oasis.xlf"));
	}

	@Test(expected = InvalidXLIFFFileException.class)
	public void testImportXLIFF2FailsFileNoTarget() throws Exception {
		_xliffTranslationInfoFormValuesImporter.importXLIFF(
			_group.getGroupId(),
			new InfoItemClassPKReference(
				JournalArticle.class.getName(), RandomTestUtil.randomInt(1, 3)),
			TranslationTestUtil.readFileToInputStream(
				"test-journal-article_no_target.xlf"));
	}

	@Test
	public void testImportXLIFFXLIFFDocument() throws Exception {
		InfoFormValues infoFormValues =
			_xliffTranslationInfoFormValuesImporter.importXLIFF(
				_group.getGroupId(),
				new InfoItemClassPKReference(
					JournalArticle.class.getName(), 122),
				TranslationTestUtil.readFileToInputStream(
					"test-journal-article_122.xlf"));

		Assert.assertNotNull(infoFormValues);
		Assert.assertNotNull(infoFormValues.getInfoFieldValues());
		Assert.assertFalse(
			infoFormValues.getInfoFieldValues(
			).isEmpty());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "content.type=application/xliff+xml")
	private TranslationInfoFormValuesExporter
		_xliffTranslationInfoFormValuesImporter;

}