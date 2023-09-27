/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.filter;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndexResponse;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetFilterReaderImplTest extends BaseSynonymsWebTestCase {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetFilterReaderImpl = new SynonymSetFilterReaderImpl();

		ReflectionTestUtil.setFieldValue(
			_synonymSetFilterReaderImpl, "jsonFactory", _jsonFactory);
		ReflectionTestUtil.setFieldValue(
			_synonymSetFilterReaderImpl, "searchEngineAdapter",
			searchEngineAdapter);
	}

	@Test
	public void testGetSynonymSets() throws Exception {
		setUpSearchEngineAdapter();

		JSONObject jsonObject = Mockito.mock(JSONObject.class);

		Mockito.doReturn(
			JSONUtil.putAll(JSONUtil.put("alpha"), JSONUtil.put("beta"))
		).when(
			jsonObject
		).getJSONArray(
			Mockito.nullable(String.class)
		);

		Mockito.doReturn(
			jsonObject
		).when(
			_jsonFactory
		).createJSONObject(
			Mockito.nullable(String.class)
		);

		Assert.assertArrayEquals(
			new String[] {"[\"alpha\"]", "[\"beta\"]"},
			_synonymSetFilterReaderImpl.getSynonymSets(
				"companyIndexName", "filterName"));
	}

	@Test(expected = RuntimeException.class)
	public void testGetSynonymSetsException() throws Exception {
		setUpSearchEngineAdapter();

		Mockito.doThrow(
			JSONException.class
		).when(
			_jsonFactory
		).createJSONObject(
			Mockito.anyString()
		);

		_synonymSetFilterReaderImpl.getSynonymSets(
			"companyIndexName", "filterName");
	}

	@Override
	protected IndexResponse setUpIndexResponse() {
		return Mockito.mock(GetIndexIndexResponse.class);
	}

	private final JSONFactory _jsonFactory = Mockito.mock(JSONFactory.class);
	private SynonymSetFilterReaderImpl _synonymSetFilterReaderImpl;

}