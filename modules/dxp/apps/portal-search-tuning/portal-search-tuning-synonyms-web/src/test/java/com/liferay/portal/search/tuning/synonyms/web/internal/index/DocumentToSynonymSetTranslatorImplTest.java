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

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class DocumentToSynonymSetTranslatorImplTest
	extends BaseSynonymsWebTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_documentToSynonymSetTranslatorImpl =
			new DocumentToSynonymSetTranslatorImpl();
	}

	@Test
	public void testTranslateWithDocumentSynonymSetDocumentId() {
		Document document = setUpDocument("car,automobile");

		SynonymSet synonymSet = _documentToSynonymSetTranslatorImpl.translate(
			document, "synonymSetDocumentId");

		Assert.assertEquals("car,automobile", synonymSet.getSynonyms());
		Assert.assertEquals(
			"synonymSetDocumentId", synonymSet.getSynonymSetDocumentId());
	}

	@Test
	public void testTranslateWithSearchHit() {
		SearchHits searchHits = setUpSearchHits("car,automobile");

		List<SearchHit> searchHitList = searchHits.getSearchHits();

		SynonymSet synonymSet = _documentToSynonymSetTranslatorImpl.translate(
			searchHitList.get(0));

		Assert.assertEquals("car,automobile", synonymSet.getSynonyms());
		Assert.assertEquals("id", synonymSet.getSynonymSetDocumentId());
	}

	@Test
	public void testTranslateWithSearchHits() {
		SearchHits searchHits = setUpSearchHits("car,automobile");

		List<SynonymSet> synonymSets =
			_documentToSynonymSetTranslatorImpl.translateAll(
				searchHits.getSearchHits());

		Assert.assertEquals(synonymSets.toString(), 1, synonymSets.size());

		SynonymSet synonymSet = synonymSets.get(0);

		Assert.assertEquals("car,automobile", synonymSet.getSynonyms());
		Assert.assertEquals("id", synonymSet.getSynonymSetDocumentId());
	}

	private DocumentToSynonymSetTranslatorImpl
		_documentToSynonymSetTranslatorImpl;

}