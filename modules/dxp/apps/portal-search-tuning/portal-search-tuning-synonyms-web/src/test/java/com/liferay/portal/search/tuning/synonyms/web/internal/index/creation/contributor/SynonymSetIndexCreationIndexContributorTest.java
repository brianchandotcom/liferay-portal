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

package com.liferay.portal.search.tuning.synonyms.web.internal.index.creation.contributor;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.IndexToFilterSynchronizer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetIndexCreationIndexContributorTest
	extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_synonymSetIndexCreationIndexContributor =
			new SynonymSetIndexCreationIndexContributor();

		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexCreationIndexContributor,
			"_indexToFilterSynchronizer", _indexToFilterSynchronizer);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexCreationIndexContributor, "_synonymSetIndexReader",
			synonymSetIndexReader);
	}

	@Test
	public void testOnAfterCreateIndexNameExists() {
		setUpSynonymSetIndexReader(true);

		_synonymSetIndexCreationIndexContributor.onAfterCreate(
			"companyIndexName");

		Mockito.verify(
			_indexToFilterSynchronizer, Mockito.times(1)
		).copyToFilter(
			Mockito.any(), Mockito.anyString(), Mockito.anyBoolean()
		);
	}

	@Test
	public void testOnAfterCreateIndexNameNotExists() {
		_synonymSetIndexCreationIndexContributor.onAfterCreate(
			"companyIndexName");

		Mockito.verify(
			_indexToFilterSynchronizer, Mockito.never()
		).copyToFilter(
			Mockito.any(), Mockito.anyString(), Mockito.anyBoolean()
		);
	}

	private final IndexToFilterSynchronizer _indexToFilterSynchronizer =
		Mockito.mock(IndexToFilterSynchronizer.class);
	private SynonymSetIndexCreationIndexContributor
		_synonymSetIndexCreationIndexContributor;

}