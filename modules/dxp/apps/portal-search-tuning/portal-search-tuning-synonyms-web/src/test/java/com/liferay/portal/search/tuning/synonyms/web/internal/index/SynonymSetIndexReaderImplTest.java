/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.document.GetDocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetIndexReaderImplTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetIndexReaderImpl = new SynonymSetIndexReaderImpl();

		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexReaderImpl, "_documentToSynonymSetTranslator",
			_documentToSynonymSetTranslator);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexReaderImpl, "_searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testFetchWithDocExistsFalse() {
		setUpSearchEngineAdapter(_setUpGetDocumentResponse(false));

		Assert.assertNull(
			_synonymSetIndexReaderImpl.fetch(
				Mockito.mock(SynonymSetIndexName.class), "id"));
	}

	@Test
	public void testFetchWithDocExistsTrue() {
		setUpSearchEngineAdapter(_setUpGetDocumentResponse(true));

		SynonymSetIndexName synonymSetIndexName = Mockito.mock(
			SynonymSetIndexName.class);

		SynonymSet synonymSet = _synonymSetIndexReaderImpl.fetch(
			synonymSetIndexName, "id");

		Assert.assertNotNull(synonymSet);
		Assert.assertEquals("car,automobile", synonymSet.getSynonyms());
		Assert.assertEquals("id", synonymSet.getSynonymSetDocumentId());
	}

	@Test
	public void testFetchWithNullId() {
		Assert.assertNull(
			_synonymSetIndexReaderImpl.fetch(
				Mockito.mock(SynonymSetIndexName.class), null));
	}

	@Test
	public void testIsExists() {
		setUpSearchEngineAdapter();

		Assert.assertTrue(
			_synonymSetIndexReaderImpl.isExists(
				Mockito.mock(SynonymSetIndexName.class)));
	}

	@Test
	public void testSearch() {
		setUpSearchEngineAdapter(setUpSearchHits("car,automobile"));

		List<SynonymSet> synonymSets = _synonymSetIndexReaderImpl.search(
			Mockito.mock(SynonymSetIndexName.class));

		Assert.assertEquals(1, synonymSets.size(), 0.0);

		SynonymSet synonymSet = synonymSets.get(0);

		Assert.assertEquals("car,automobile", synonymSet.getSynonyms());
		Assert.assertEquals("id", synonymSet.getSynonymSetDocumentId());
	}

	@Override
	protected IndexResponse setUpIndexResponse() {
		IndicesExistsIndexResponse indicesExistsIndexResponse = Mockito.mock(
			IndicesExistsIndexResponse.class);

		Mockito.doReturn(
			true
		).when(
			indicesExistsIndexResponse
		).isExists();

		return indicesExistsIndexResponse;
	}

	private GetDocumentResponse _setUpGetDocumentResponse(boolean exists) {
		GetDocumentResponse getDocumentResponse = Mockito.mock(
			GetDocumentResponse.class);

		Mockito.doReturn(
			exists
		).when(
			getDocumentResponse
		).isExists();

		Mockito.doReturn(
			setUpDocument("car,automobile")
		).when(
			getDocumentResponse
		).getDocument();

		return getDocumentResponse;
	}

	private final DocumentToSynonymSetTranslator
		_documentToSynonymSetTranslator =
			new DocumentToSynonymSetTranslatorImpl();
	private SynonymSetIndexReaderImpl _synonymSetIndexReaderImpl;

}