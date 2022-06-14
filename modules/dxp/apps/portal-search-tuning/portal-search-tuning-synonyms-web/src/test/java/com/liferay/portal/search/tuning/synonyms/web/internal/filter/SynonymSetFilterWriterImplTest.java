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

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetFilterWriterImplTest extends BaseSynonymsWebTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

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
			Matchers.any(IndexRequest.class)
		);
	}

	@Test
	public void testUpdateSynonymSetsWithEmptySynonymSetFalseDeletion() {
		_synonymSetFilterWriterImpl.updateSynonymSets(
			"companyIndexName", "filterName", new String[0], false);

		Mockito.verify(
			searchEngineAdapter, Mockito.never()
		).execute(
			Matchers.any(CloseIndexRequest.class)
		);
	}

	@Mock
	private JSONFactory _jsonFactory;

	private SynonymSetFilterWriterImpl _synonymSetFilterWriterImpl;

}