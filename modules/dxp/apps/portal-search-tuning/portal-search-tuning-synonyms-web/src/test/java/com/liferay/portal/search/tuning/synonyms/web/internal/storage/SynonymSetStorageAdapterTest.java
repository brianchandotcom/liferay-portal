/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSet;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexWriter;
import com.liferay.portal.search.tuning.synonyms.web.internal.storage.helper.SynonymSetJSONStorageHelper;
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
public class SynonymSetStorageAdapterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetStorageAdapter = new SynonymSetStorageAdapter();

		ReflectionTestUtil.setFieldValue(
			_synonymSetStorageAdapter, "synonymSetIndexWriter",
			_synonymSetIndexWriter);
		ReflectionTestUtil.setFieldValue(
			_synonymSetStorageAdapter, "synonymSetJSONStorageHelper",
			_synonymSetJSONStorageHelper);
	}

	@Test
	public void testCreate() {
		Mockito.doReturn(
			"synonymSetDocumentId"
		).when(
			_synonymSetJSONStorageHelper
		).addJSONStorageEntry(
			Mockito.nullable(String.class), Mockito.nullable(String.class)
		);

		Assert.assertEquals(
			"synonymSetDocumentId",
			_synonymSetStorageAdapter.create(
				Mockito.mock(SynonymSetIndexName.class),
				Mockito.mock(SynonymSet.class)));

		Mockito.verify(
			_synonymSetIndexWriter, Mockito.times(1)
		).create(
			Mockito.any(), Mockito.any()
		);
	}

	@Test
	public void testDelete() throws Exception {
		_synonymSetStorageAdapter.delete(
			Mockito.mock(SynonymSetIndexName.class),
			"synonymSetDocumentId_PORTLET_1112");

		Mockito.verify(
			_synonymSetIndexWriter, Mockito.times(1)
		).remove(
			Mockito.any(), Mockito.anyString()
		);
	}

	@Test(expected = PortalException.class)
	public void testDeleteException() throws Exception {
		_synonymSetStorageAdapter.delete(
			Mockito.mock(SynonymSetIndexName.class),
			"synonymSetDocumentId_PORTLET");
	}

	@Test
	public void testUpdate() throws Exception {
		SynonymSet synonymSet = Mockito.mock(SynonymSet.class);

		Mockito.doReturn(
			"synonymSetDocumentId_PORTLET_1112"
		).when(
			synonymSet
		).getSynonymSetDocumentId();

		_synonymSetStorageAdapter.update(
			Mockito.mock(SynonymSetIndexName.class), synonymSet);

		Mockito.verify(
			_synonymSetIndexWriter, Mockito.times(1)
		).update(
			Mockito.any(), Mockito.any()
		);
	}

	@Test(expected = PortalException.class)
	public void testUpdateException() throws Exception {
		SynonymSet synonymSet = Mockito.mock(SynonymSet.class);

		Mockito.doReturn(
			"synonymSetDocumentId_PORTLET"
		).when(
			synonymSet
		).getSynonymSetDocumentId();

		_synonymSetStorageAdapter.update(
			Mockito.mock(SynonymSetIndexName.class), synonymSet);
	}

	private final SynonymSetIndexWriter _synonymSetIndexWriter = Mockito.mock(
		SynonymSetIndexWriter.class);
	private final SynonymSetJSONStorageHelper _synonymSetJSONStorageHelper =
		Mockito.mock(SynonymSetJSONStorageHelper.class);
	private SynonymSetStorageAdapter _synonymSetStorageAdapter;

}