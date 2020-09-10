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

package com.liferay.translation.internal.importer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.translation.snapshot.TranslationSnapshot;
import com.liferay.translation.snapshot.TranslationSnapshotProvider;
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
public class XLIFFInfoFormTranslationImporterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetTranslationSnapshot() throws Exception {
		TranslationSnapshot translationSnapshot =
			_translationSnapshotProvider.getTranslationSnapshot(
				_group.getGroupId(),
				new InfoItemReference(JournalArticle.class.getName(), 122),
				TranslationTestUtil.readFileToInputStream(
					"test-journal-article-122-zh-Hans-CN.xlf"));

		Assert.assertEquals(
			LocaleUtil.SIMPLIFIED_CHINESE,
			translationSnapshot.getTargetLocale());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private TranslationSnapshotProvider _translationSnapshotProvider;

}