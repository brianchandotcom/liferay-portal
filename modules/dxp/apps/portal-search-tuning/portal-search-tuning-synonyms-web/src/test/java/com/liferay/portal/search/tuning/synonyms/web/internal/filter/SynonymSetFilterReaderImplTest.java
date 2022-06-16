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