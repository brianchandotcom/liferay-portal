/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.web.internal.BaseSynonymsWebTestCase;
import com.liferay.portal.search.tuning.synonyms.web.internal.index.SynonymSetIndexCreator;
import com.liferay.portal.search.tuning.synonyms.web.internal.synchronizer.FilterToIndexSynchronizer;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SynonymSetIndexPortalInstanceLifecycleListenerTest
	extends BaseSynonymsWebTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.doReturn(
			true
		).when(
			_searchCapabilities
		).isSynonymsSupported();

		_synonymSetIndexPortalInstanceLifecycleListener =
			new SynonymSetIndexPortalInstanceLifecycleListener();

		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexPortalInstanceLifecycleListener,
			"_filterToIndexSynchronizer", _filterToIndexSynchronizer);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexPortalInstanceLifecycleListener,
			"_indexNameBuilder", _indexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexPortalInstanceLifecycleListener,
			"_searchCapabilities", _searchCapabilities);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexPortalInstanceLifecycleListener,
			"_synonymSetIndexCreator", _synonymSetIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexPortalInstanceLifecycleListener,
			"_synonymSetIndexNameBuilder", _synonymSetIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_synonymSetIndexPortalInstanceLifecycleListener,
			"_synonymSetIndexReader", synonymSetIndexReader);
	}

	@Test
	public void testPortalInstanceRegisteredExistFalse() throws Exception {
		setUpSynonymSetIndexReader(false);

		_synonymSetIndexPortalInstanceLifecycleListener.
			portalInstanceRegistered(Mockito.mock(Company.class));

		Mockito.verify(
			_synonymSetIndexCreator, Mockito.times(1)
		).create(
			Mockito.any()
		);
		Mockito.verify(
			_filterToIndexSynchronizer, Mockito.times(1)
		).copyToIndex(
			Mockito.nullable(String.class), Mockito.any()
		);
	}

	@Test
	public void testPortalInstanceRegisteredExistTrue() throws Exception {
		setUpSynonymSetIndexReader(true);

		_synonymSetIndexPortalInstanceLifecycleListener.
			portalInstanceRegistered(Mockito.mock(Company.class));

		Mockito.verify(
			_synonymSetIndexCreator, Mockito.never()
		).create(
			Mockito.any()
		);
		Mockito.verify(
			_filterToIndexSynchronizer, Mockito.never()
		).copyToIndex(
			Mockito.anyString(), Mockito.any()
		);
	}

	private final FilterToIndexSynchronizer _filterToIndexSynchronizer =
		Mockito.mock(FilterToIndexSynchronizer.class);
	private final IndexNameBuilder _indexNameBuilder = Mockito.mock(
		IndexNameBuilder.class);
	private final SearchCapabilities _searchCapabilities = Mockito.mock(
		SearchCapabilities.class);
	private final SynonymSetIndexCreator _synonymSetIndexCreator = Mockito.mock(
		SynonymSetIndexCreator.class);
	private final SynonymSetIndexNameBuilder _synonymSetIndexNameBuilder =
		Mockito.mock(SynonymSetIndexNameBuilder.class);
	private SynonymSetIndexPortalInstanceLifecycleListener
		_synonymSetIndexPortalInstanceLifecycleListener;

}