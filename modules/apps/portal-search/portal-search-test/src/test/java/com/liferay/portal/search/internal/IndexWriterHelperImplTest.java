/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal;

import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class IndexWriterHelperImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCommitImmediately() throws Exception {
		_assertCommitImmediately(false, 0);
		_assertCommitImmediately(true, 1);
	}

	private void _assertCommitImmediately(boolean commitImmediately, int times)
		throws Exception {

		IndexWriter indexWriter = Mockito.spy(IndexWriter.class);

		IndexWriterHelper indexWriterHelper = new IndexWriterHelperImpl();

		SearchEngine searchEngine = Mockito.mock(SearchEngine.class);

		SearchEngineHelper searchEngineHelper = Mockito.mock(
			SearchEngineHelper.class);

		Mockito.when(
			searchEngine.getIndexWriter()
		).thenReturn(
			indexWriter
		);

		Mockito.when(
			searchEngineHelper.getSearchEngine()
		).thenReturn(
			searchEngine
		);

		ReflectionTestUtil.setFieldValue(
			indexWriterHelper, "_commitImmediately", commitImmediately);
		ReflectionTestUtil.setFieldValue(
			indexWriterHelper, "_searchEngineHelper", searchEngineHelper);

		indexWriterHelper.commit(RandomTestUtil.randomLong());

		Mockito.verify(
			indexWriter, Mockito.times(times)
		).commit(
			Mockito.any()
		);
	}

}