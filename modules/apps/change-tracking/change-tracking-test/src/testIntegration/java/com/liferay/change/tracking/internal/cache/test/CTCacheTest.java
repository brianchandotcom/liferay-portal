/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.cache.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.persistence.impl.LayoutPersistenceImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author David Truong
 */
@RunWith(Arquillian.class)
public class CTCacheTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testEntityCacheResults() throws Exception {
		Layout productionLayout = LayoutTestUtil.addTypePortletLayout(_group);

		Layout cachedLayout = (Layout)EntityCacheUtil.getResult(
			LayoutImpl.class, productionLayout.getPrimaryKey());

		Assert.assertEquals(
			productionLayout.getCtCollectionId(),
			cachedLayout.getCtCollectionId());

		Assert.assertEquals(
			productionLayout.getMvccVersion(), cachedLayout.getMvccVersion());

		CTCollection ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Layout ctCollection1Layout = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection1.getCtCollectionId())) {

			ctCollection1Layout = _layoutLocalService.updateLayout(
				productionLayout.getGroupId(),
				productionLayout.isPrivateLayout(),
				productionLayout.getLayoutId(), new Date());

			Assert.assertEquals(
				productionLayout.getPrimaryKey(),
				ctCollection1Layout.getPrimaryKey());

			Assert.assertNotEquals(
				productionLayout.getCtCollectionId(),
				ctCollection1Layout.getCtCollectionId());

			cachedLayout = (Layout)EntityCacheUtil.getResult(
				LayoutImpl.class, productionLayout.getPrimaryKey());

			Assert.assertNotEquals(
				productionLayout.getCtCollectionId(),
				cachedLayout.getCtCollectionId());

			Assert.assertEquals(
				ctCollection1Layout.getCtCollectionId(),
				cachedLayout.getCtCollectionId());
		}

		cachedLayout = (Layout)EntityCacheUtil.getResult(
			LayoutImpl.class, productionLayout.getPrimaryKey());

		Assert.assertEquals(
			productionLayout.getCtCollectionId(),
			cachedLayout.getCtCollectionId());

		Assert.assertNotEquals(
			ctCollection1Layout.getCtCollectionId(),
			cachedLayout.getCtCollectionId());

		CTCollection ctCollection2 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Layout ctCollection2Layout = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection2.getCtCollectionId())) {

			ctCollection2Layout = _layoutLocalService.updateLayout(
				productionLayout.getGroupId(),
				productionLayout.isPrivateLayout(),
				productionLayout.getLayoutId(), new Date());

			Assert.assertEquals(
				productionLayout.getPrimaryKey(),
				ctCollection2Layout.getPrimaryKey());

			Assert.assertNotEquals(
				productionLayout.getCtCollectionId(),
				ctCollection2Layout.getCtCollectionId());

			cachedLayout = (Layout)EntityCacheUtil.getResult(
				LayoutImpl.class, productionLayout.getPrimaryKey());

			Assert.assertNotEquals(
				productionLayout.getCtCollectionId(),
				cachedLayout.getCtCollectionId());

			Assert.assertNotEquals(
				ctCollection1Layout.getCtCollectionId(),
				cachedLayout.getCtCollectionId());

			Assert.assertEquals(
				ctCollection2Layout.getCtCollectionId(),
				cachedLayout.getCtCollectionId());
		}
	}

	@Test
	public void testFinderCacheResults() throws Exception {
		LayoutTestUtil.addTypePortletLayout(_group);

		FinderPath finderPath = new FinderPath(
			LayoutPersistenceImpl.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			new String[] {"companyId"}, true);

		Object[] finderArgs = {TestPropsValues.getCompanyId()};

		CTPersistence<?> ctPersistence = _layoutLocalService.getCTPersistence();

		List<Layout> productionLayouts = _layoutLocalService.getLayouts(
			TestPropsValues.getCompanyId());

		List<Layout> cachedLayouts = (List<Layout>)FinderCacheUtil.getResult(
			finderPath, finderArgs, ctPersistence);

		Assert.assertEquals(
			cachedLayouts.toString(), productionLayouts.size(),
			cachedLayouts.size());

		CTCollection ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		List<Layout> ctCollection1Layouts = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection1.getCtCollectionId())) {

			LayoutTestUtil.addTypePortletLayout(_group);

			ctCollection1Layouts = _layoutLocalService.getLayouts(
				TestPropsValues.getCompanyId());

			cachedLayouts = (List<Layout>)FinderCacheUtil.getResult(
				finderPath, finderArgs, ctPersistence);

			Assert.assertEquals(
				cachedLayouts.toString(), ctCollection1Layouts.size(),
				cachedLayouts.size());

			Assert.assertNotEquals(
				cachedLayouts.toString(), cachedLayouts.size(),
				productionLayouts.size());
		}

		CTCollection ctCollection2 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), RandomTestUtil.randomString());

		List<Layout> ctCollection2Layouts = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection2.getCtCollectionId())) {

			LayoutTestUtil.addTypePortletLayout(_group);

			LayoutTestUtil.addTypePortletLayout(_group);

			ctCollection2Layouts = _layoutLocalService.getLayouts(
				TestPropsValues.getCompanyId());

			cachedLayouts = (List<Layout>)FinderCacheUtil.getResult(
				finderPath, finderArgs, ctPersistence);

			Assert.assertEquals(
				cachedLayouts.toString(), ctCollection2Layouts.size(),
				cachedLayouts.size());

			Assert.assertNotEquals(
				cachedLayouts.toString(), cachedLayouts.size(),
				productionLayouts.size());

			Assert.assertNotEquals(
				cachedLayouts.toString(), cachedLayouts.size(),
				ctCollection1Layouts.size());
		}
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

}