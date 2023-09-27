/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.filter.SynonymSetFilterWriter;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class IndexToFilterSynchronizerImplTest extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_indexToFilterSynchronizerImpl = new IndexToFilterSynchronizerImpl();

		ReflectionTestUtil.setFieldValue(
			_indexToFilterSynchronizerImpl, "_synonymSetFilterNameHolder",
			synonymSetFilterNameHolder);
		ReflectionTestUtil.setFieldValue(
			_indexToFilterSynchronizerImpl, "_synonymSetFilterWriter",
			_synonymSetFilterWriter);
		ReflectionTestUtil.setFieldValue(
			_indexToFilterSynchronizerImpl, "_synonymSetIndexReader",
			synonymSetIndexReader);
	}

	@Test
	public void testCopyToFilter() {
		setUpSynonymSetFilterNameHolder(new String[] {"car,automobile"});
		setUpSynonymSetIndexReader("id", "car,automobile");

		_indexToFilterSynchronizerImpl.copyToFilter(
			Mockito.mock(SynonymSetIndexName.class), "companyIndexName", true);
		Mockito.verify(
			_synonymSetFilterWriter, Mockito.times(1)
		).updateSynonymSets(
			Mockito.anyString(), Mockito.anyString(), Mockito.any(),
			Mockito.anyBoolean()
		);
	}

	private IndexToFilterSynchronizerImpl _indexToFilterSynchronizerImpl;
	private final SynonymSetFilterWriter _synonymSetFilterWriter = Mockito.mock(
		SynonymSetFilterWriter.class);

}