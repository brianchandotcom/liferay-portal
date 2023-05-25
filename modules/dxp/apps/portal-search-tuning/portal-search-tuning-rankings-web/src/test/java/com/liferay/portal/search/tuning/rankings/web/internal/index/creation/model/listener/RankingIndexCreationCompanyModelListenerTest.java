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

package com.liferay.portal.search.tuning.rankings.web.internal.index.creation.model.listener;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexCreator;
import com.liferay.portal.search.tuning.rankings.web.internal.index.RankingIndexReader;
import com.liferay.portal.search.tuning.rankings.web.internal.index.name.RankingIndexNameBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Wade Cao
 */
public class RankingIndexCreationCompanyModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		_searchEngineInformationServiceRegistration =
			bundleContext.registerService(
				SearchEngineInformation.class,
				Mockito.mock(SearchEngineInformation.class), null);
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
		_searchEngineInformationServiceRegistration.unregister();
	}

	@Before
	public void setUp() throws Exception {
		_rankingIndexCreationCompanyModelListener =
			new RankingIndexCreationCompanyModelListener();

		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationCompanyModelListener, "_rankingIndexCreator",
			_rankingIndexCreator);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationCompanyModelListener,
			"_rankingIndexNameBuilder", _rankingIndexNameBuilder);
		ReflectionTestUtil.setFieldValue(
			_rankingIndexCreationCompanyModelListener, "_rankingIndexReader",
			_rankingIndexReader);
	}

	@Test
	public void testOnAfterCreateRankingIndexReaderFalse() {
		_setUpRankingIndexReader(false);

		_rankingIndexCreationCompanyModelListener.onAfterCreate(
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
	public void testOnAfterCreateRankingIndexReaderTrue() {
		_setUpRankingIndexReader(true);

		_rankingIndexCreationCompanyModelListener.onAfterCreate(
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
	public void testOnBeforeRemoveRankingIndexReaderExistsFalse() {
		_setUpRankingIndexReader(false);

		_rankingIndexCreationCompanyModelListener.onBeforeRemove(
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
	public void testOnBeforeRemoveRankingIndexReaderExistsTrue() {
		_setUpRankingIndexReader(true);

		_rankingIndexCreationCompanyModelListener.onBeforeRemove(
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

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);
	private static ServiceRegistration<SearchEngineInformation>
		_searchEngineInformationServiceRegistration;

	private RankingIndexCreationCompanyModelListener
		_rankingIndexCreationCompanyModelListener;
	private final RankingIndexCreator _rankingIndexCreator = Mockito.mock(
		RankingIndexCreator.class);
	private final RankingIndexNameBuilder _rankingIndexNameBuilder =
		Mockito.mock(RankingIndexNameBuilder.class);
	private final RankingIndexReader _rankingIndexReader = Mockito.mock(
		RankingIndexReader.class);

}