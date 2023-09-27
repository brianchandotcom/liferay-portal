/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class FilterToIndexSynchronizerImplTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_filterToIndexSynchronizerImpl = new FilterToIndexSynchronizerImpl();

		ReflectionTestUtil.setFieldValue(
			_filterToIndexSynchronizerImpl, "_synonymSetFilterNameHolder",
			synonymSetFilterNameHolder);
		ReflectionTestUtil.setFieldValue(
			_filterToIndexSynchronizerImpl, "_synonymSetFilterReader",
			synonymSetFilterReader);
		ReflectionTestUtil.setFieldValue(
			_filterToIndexSynchronizerImpl, "_synonymSetStorageAdapter",
			synonymSetStorageAdapter);
	}

	@Test
	public void testCopyToIndex() {
		setUpSynonymSetFilterNameHolder(new String[] {"car,automobile"});
		setUpSynonymSetFilterReader(new String[] {"car,automobile"});

		_filterToIndexSynchronizerImpl.copyToIndex(
			"companyIndexName", Mockito.mock(SynonymSetIndexName.class));
		Mockito.verify(
			synonymSetStorageAdapter, Mockito.times(1)
		).create(
			Mockito.any(), Mockito.any()
		);
	}

	private FilterToIndexSynchronizerImpl _filterToIndexSynchronizerImpl;

}