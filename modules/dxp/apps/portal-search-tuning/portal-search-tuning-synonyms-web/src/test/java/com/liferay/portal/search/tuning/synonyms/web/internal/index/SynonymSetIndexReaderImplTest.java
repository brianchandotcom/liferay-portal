/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
import java.util.Optional;

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
	public void testFetchOptionalWithDocExistsFalse() {
		setUpSearchEngineAdapter(_setUpGetDocumentResponse(false));

		SynonymSetIndexName synonymSetIndexName = Mockito.mock(
			SynonymSetIndexName.class);

		Optional<SynonymSet> synonymSetOptional =
			_synonymSetIndexReaderImpl.fetchOptional(synonymSetIndexName, "id");

		Assert.assertFalse(synonymSetOptional.isPresent());
	}

	@Test
	public void testFetchOptionalWithDocExistsTrue() {
		setUpSearchEngineAdapter(_setUpGetDocumentResponse(true));

		SynonymSetIndexName synonymSetIndexName = Mockito.mock(
			SynonymSetIndexName.class);

		Optional<SynonymSet> synonymSetOptional =
			_synonymSetIndexReaderImpl.fetchOptional(synonymSetIndexName, "id");

		Assert.assertTrue(synonymSetOptional.isPresent());

		synonymSetOptional.ifPresent(
			synonymSet -> {
				Assert.assertEquals("car,automobile", synonymSet.getSynonyms());
				Assert.assertEquals("id", synonymSet.getSynonymSetDocumentId());
			});
	}

	@Test
	public void testFetchOptionalWithNullId() {
		Optional<SynonymSet> synonymSetOptional =
			_synonymSetIndexReaderImpl.fetchOptional(
				Mockito.mock(SynonymSetIndexName.class), null);

		Assert.assertFalse(synonymSetOptional.isPresent());
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

		List<SynonymSet> synonymSetList = _synonymSetIndexReaderImpl.search(
			Mockito.mock(SynonymSetIndexName.class));

		Assert.assertEquals(1, synonymSetList.size(), 0.0);

		SynonymSet synonymSet = synonymSetList.get(0);

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