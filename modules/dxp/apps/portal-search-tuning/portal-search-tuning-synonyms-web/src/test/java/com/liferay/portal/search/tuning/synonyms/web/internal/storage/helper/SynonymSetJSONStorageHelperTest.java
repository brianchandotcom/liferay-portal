/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.mockito.Mockito;

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
			Mockito.eq(SynonymSet.class)
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
			Mockito.eq(SynonymSet.class)
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

	private final ClassNameLocalService _classNameLocalService = Mockito.mock(
		ClassNameLocalService.class);
	private final CounterLocalService _counterLocalService = Mockito.mock(
		CounterLocalService.class);
	private final JSONStorageEntryLocalService _jsonStorageEntryLocalService =
		Mockito.mock(JSONStorageEntryLocalService.class);
	private SynonymSetJSONStorageHelper _synonymSetJSONStorageHelper;

}