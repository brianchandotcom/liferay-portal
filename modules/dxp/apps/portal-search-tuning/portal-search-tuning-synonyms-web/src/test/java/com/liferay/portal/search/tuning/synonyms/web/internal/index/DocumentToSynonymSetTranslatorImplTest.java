/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		SynonymSet synonymSet = _documentToSynonymSetTranslatorImpl.translate(
			searchHitsList.get(0));

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