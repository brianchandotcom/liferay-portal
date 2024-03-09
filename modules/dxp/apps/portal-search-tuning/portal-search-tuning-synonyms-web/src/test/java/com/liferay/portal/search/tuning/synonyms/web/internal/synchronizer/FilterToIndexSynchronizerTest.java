/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterReaderUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class FilterToIndexSynchronizerTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_filterToIndexSynchronizerImpl = new FilterToIndexSynchronizer(
			new String[] {"car,automobile"}, searchEngineAdapter,
			synonymSetStorageAdapter);
	}

	@After
	public void tearDown() {
		_synonymSetFilterReaderUtilMockedStatic.close();
	}

	@Test
	public void testCopyToIndex() {
		_synonymSetFilterReaderUtilMockedStatic.when(
			() -> SynonymSetFilterReaderUtil.getSynonymSets(
				Mockito.any(), Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			new String[] {"car,automobile"}
		);

		_filterToIndexSynchronizerImpl.copyToIndex(
			"companyIndexName", Mockito.mock(SynonymSetIndexName.class));
		Mockito.verify(
			synonymSetStorageAdapter, Mockito.times(1)
		).create(
			Mockito.any(), Mockito.any()
		);
	}

	private FilterToIndexSynchronizer _filterToIndexSynchronizerImpl;
	private final MockedStatic<SynonymSetFilterReaderUtil>
		_synonymSetFilterReaderUtilMockedStatic = Mockito.mockStatic(
			SynonymSetFilterReaderUtil.class);

}