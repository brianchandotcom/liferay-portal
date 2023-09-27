/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentResponse;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetIndexWriterImplTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetIndexWriterImpl = new SynonymSetIndexWriterImpl();

		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexWriterImpl, "_synonymSetToDocumentTranslator",
			_synonymSetToDocumentTranslator);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexWriterImpl, "_searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testCreate() {
		_setUpSynonymSetToDocumentTranslator();

		setUpSearchEngineAdapter(_setUpIndexDocumentResponse("uid"));

		Assert.assertEquals(
			"uid",
			_synonymSetIndexWriterImpl.create(
				Mockito.mock(SynonymSetIndexName.class),
				Mockito.mock(SynonymSet.class)));
	}

	@Test
	public void testRemove() {
		_synonymSetIndexWriterImpl.remove(
			Mockito.mock(SynonymSetIndexName.class), "id");

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			Mockito.any(DeleteDocumentRequest.class)
		);
	}

	@Test
	public void testUpdate() {
		_synonymSetIndexWriterImpl.update(
			Mockito.mock(SynonymSetIndexName.class),
			Mockito.mock(SynonymSet.class));

		Mockito.verify(
			searchEngineAdapter, Mockito.times(1)
		).execute(
			Mockito.any(IndexDocumentRequest.class)
		);
	}

	private IndexDocumentResponse _setUpIndexDocumentResponse(String uid) {
		IndexDocumentResponse indexDocumentResponse = Mockito.mock(
			IndexDocumentResponse.class);

		Mockito.doReturn(
			uid
		).when(
			indexDocumentResponse
		).getUid();

		return indexDocumentResponse;
	}

	private void _setUpSynonymSetToDocumentTranslator() {
		Mockito.doReturn(
			Mockito.mock(Document.class)
		).when(
			_synonymSetToDocumentTranslator
		).translate(
			Mockito.any()
		);
	}

	private SynonymSetIndexWriterImpl _synonymSetIndexWriterImpl;
	private final SynonymSetToDocumentTranslator
		_synonymSetToDocumentTranslator = Mockito.mock(
			SynonymSetToDocumentTranslator.class);

}