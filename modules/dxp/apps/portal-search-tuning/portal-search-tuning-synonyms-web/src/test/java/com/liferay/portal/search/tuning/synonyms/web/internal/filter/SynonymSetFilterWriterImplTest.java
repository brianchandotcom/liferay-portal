/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.index.CloseIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndexRequest;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetFilterWriterImplTest extends BaseSynonymsWebTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetFilterWriterImpl = new SynonymSetFilterWriterImpl();

		ReflectionTestUtil.setFieldValue(
			_synonymSetFilterWriterImpl, "jsonFactory", _jsonFactory);
		ReflectionTestUtil.setFieldValue(
			_synonymSetFilterWriterImpl, "searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testUpdateSynonymSets() {
		_synonymSetFilterWriterImpl.updateSynonymSets(
			"companyIndexName", "filterName", new String[] {"car,automobile"},
			true);

		Mockito.verify(
			searchEngineAdapter, Mockito.times(3)
		).execute(
			Mockito.nullable(IndexRequest.class)
		);
	}

	@Test
	public void testUpdateSynonymSetsWithEmptySynonymSetFalseDeletion() {
		_synonymSetFilterWriterImpl.updateSynonymSets(
			"companyIndexName", "filterName", new String[0], false);

		Mockito.verify(
			searchEngineAdapter, Mockito.never()
		).execute(
			Mockito.any(CloseIndexRequest.class)
		);
	}

	private final JSONFactory _jsonFactory = Mockito.mock(JSONFactory.class);
	private SynonymSetFilterWriterImpl _synonymSetFilterWriterImpl;

}