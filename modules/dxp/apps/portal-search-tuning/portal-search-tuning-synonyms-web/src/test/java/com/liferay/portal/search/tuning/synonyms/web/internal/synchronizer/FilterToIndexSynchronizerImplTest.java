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
			_filterToIndexSynchronizerImpl, "_synonymSetIndexNameBuilder",
			synonymSetIndexNameBuilder);
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