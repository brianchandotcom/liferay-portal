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

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.instance.lifecycle;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class RankingIndexPortalInstanceLifecycleListenerTest {

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
		).isResultRankingsSupported();

		_rankingIndexPortalInstanceLifecycleListener =
			new RankingIndexPortalInstanceLifecycleListener();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener,
			"_rankingIndexCreator", _rankingIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener,
			"_rankingIndexNameBuilder", _rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener, "_rankingIndexReader",
			_rankingIndexReader);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexPortalInstanceLifecycleListener, "_searchCapabilities",
			_searchCapabilities);
	}

	@Test
	public void testPortalInstanceRegisteredExistFalse() throws Exception {
		_setUpRankingIndexReader(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).create(
			Mockito.any()
		);
	}

	@Test
	public void testPortalInstanceRegisteredExistTrue() throws Exception {
		_setUpRankingIndexReader(true);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceRegistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(0)
		).create(
			Mockito.any()
		);
	}

	@Test
	public void testPortalInstanceUnregisteredExistsFalse() throws Exception {
		_setUpRankingIndexReader(false);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceUnregistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(0)
		).delete(
			Mockito.any()
		);
	}

	@Test
	public void testPortalInstanceUnregisteredExistsTrue() throws Exception {
		_setUpRankingIndexReader(true);

		_rankingIndexPortalInstanceLifecycleListener.portalInstanceUnregistered(
			Mockito.mock(Company.class));

		Mockito.verify(
			_rankingIndexReader, Mockito.times(1)
		).isExists(
			Mockito.any()
		);

		Mockito.verify(
			_rankingIndexCreator, Mockito.times(1)
		).delete(
			Mockito.any()
		);
	}

	private void _setUpRankingIndexReader(boolean exist) {
		Mockito.doReturn(
			exist
		).when(
			_rankingIndexReader
		).isExists(
			Mockito.any()
		);
	}

	private final RankingIndexCreator _rankingIndexCreator = Mockito.mock(
		RankingIndexCreator.class);
	private final RankingIndexNameBuilder _rankingIndexNameBuilder =
		Mockito.mock(RankingIndexNameBuilder.class);
	private RankingIndexPortalInstanceLifecycleListener
		_rankingIndexPortalInstanceLifecycleListener;
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);
	private final SearchCapabilities _searchCapabilities = Mockito.mock(
		SearchCapabilities.class);

}