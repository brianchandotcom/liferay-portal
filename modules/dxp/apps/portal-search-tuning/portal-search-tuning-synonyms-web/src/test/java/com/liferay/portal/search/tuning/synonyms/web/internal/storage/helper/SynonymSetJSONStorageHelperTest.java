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

package com.liferay.portal.search.tuning.synonyms.web.internal.storage.helper;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 */
public class SynonymSetJSONStorageHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_synonymSetJSONStorageHelper = new SynonymSetJSONStorageHelper();

		ReflectionTestUtil.setFieldValue(
			_synonymSetJSONStorageHelper, "classNameLocalService",
			_classNameLocalService);
		ReflectionTestUtil.setFieldValue(
			_synonymSetJSONStorageHelper, "counterLocalService",
			_counterLocalService);
		ReflectionTestUtil.setFieldValue(
			_synonymSetJSONStorageHelper, "jsonStorageEntryLocalService",
			_jsonStorageEntryLocalService);
	}

	@Test
	public void testAddJSONStorageEntry() {
		_setUpCounterLocalService();

		Assert.assertEquals(
			"com.liferay.portal.search.tuning.synonyms.web.internal.index." +
				"SynonymSet_PORTLET_1234",
			_synonymSetJSONStorageHelper.addJSONStorageEntry(
				111L, "indexName", "car,automobile"));
	}

	@Test
	public void testDeleteJSONStorageEntry() {
		_synonymSetJSONStorageHelper.deleteJSONStorageEntry(1234L);

		Mockito.verify(
			_classNameLocalService, Mockito.times(1)
		).getClassNameId(
			Matchers.eq(SynonymSet.class)
		);
		Mockito.verify(
			_jsonStorageEntryLocalService, Mockito.times(1)
		).deleteJSONStorageEntries(
			Mockito.anyLong(), Mockito.anyLong()
		);
	}

	@Test
	public void testUpdateJSONStorageEntry() {
		_setUpJSONStorageEntryLocalService();

		_synonymSetJSONStorageHelper.updateJSONStorageEntry(
			1234L, "car,automobile");

		Mockito.verify(
			_classNameLocalService, Mockito.times(2)
		).getClassNameId(
			Matchers.eq(SynonymSet.class)
		);
		Mockito.verify(
			_jsonStorageEntryLocalService, Mockito.times(1)
		).updateJSONStorageEntries(
			Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong(),
			Mockito.anyString()
		);
	}

	private void _setUpCounterLocalService() {
		Mockito.doReturn(
			1234L
		).when(
			_counterLocalService
		).increment();
	}

	private void _setUpJSONStorageEntryLocalService() {
		Mockito.doReturn(
			Mockito.mock(JSONObject.class)
		).when(
			_jsonStorageEntryLocalService
		).getJSONObject(
			Mockito.anyLong(), Mockito.anyLong()
		);
	}

	@Mock
	private ClassNameLocalService _classNameLocalService;

	@Mock
	private CounterLocalService _counterLocalService;

	@Mock
	private JSONStorageEntryLocalService _jsonStorageEntryLocalService;

	private SynonymSetJSONStorageHelper _synonymSetJSONStorageHelper;

}