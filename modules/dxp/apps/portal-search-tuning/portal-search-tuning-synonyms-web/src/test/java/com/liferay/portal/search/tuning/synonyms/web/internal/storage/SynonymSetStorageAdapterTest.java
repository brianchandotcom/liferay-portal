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

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
		MockitoAnnotations.initMocks(this);

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
			Mockito.anyString(), Mockito.anyString()
		);

		Assert.assertEquals(
			"synonymSetDocumentId",
			_synonymSetStorageAdapter.create(
				Mockito.mock(SynonymSetIndexName.class),
				Mockito.mock(SynonymSet.class)));

		Mockito.verify(
			_synonymSetIndexWriter, Mockito.times(1)
		).create(
			Mockito.anyObject(), Mockito.anyObject()
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
			Mockito.anyObject(), Mockito.anyString()
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
			Mockito.anyObject(), Mockito.anyObject()
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

	@Mock
	private SynonymSetIndexWriter _synonymSetIndexWriter;

	@Mock
	private SynonymSetJSONStorageHelper _synonymSetJSONStorageHelper;

	private SynonymSetStorageAdapter _synonymSetStorageAdapter;

}