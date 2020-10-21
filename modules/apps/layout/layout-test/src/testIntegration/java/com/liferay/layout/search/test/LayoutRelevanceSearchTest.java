/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.util.comparator.LayoutRelevanceComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
@Sync
public class LayoutRelevanceSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_addLayouts();
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testLayoutRelevanceSearch() throws Exception {
		List<Layout> fooSearchLayouts = _layoutLocalService.getLayouts(
			_group.getGroupId(), false, "foo",
			new String[] {LayoutConstants.TYPE_CONTENT}, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new LayoutRelevanceComparator());

		Assert.assertNotNull(fooSearchLayouts);
		Assert.assertEquals(
			fooSearchLayouts.toString(), 2, fooSearchLayouts.size());

		List<Layout> barSearchLayouts = _layoutLocalService.getLayouts(
			_group.getGroupId(), false, "bar",
			new String[] {LayoutConstants.TYPE_CONTENT}, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new LayoutRelevanceComparator());

		Assert.assertNotNull(barSearchLayouts);
		Assert.assertEquals(
			barSearchLayouts.toString(), 2, barSearchLayouts.size());

		Assert.assertEquals(fooSearchLayouts.get(0), barSearchLayouts.get(1));
		Assert.assertEquals(fooSearchLayouts.get(1), barSearchLayouts.get(0));
	}

	private void _addLayouts() throws Exception {
		Indexer<Layout> indexer = IndexerRegistryUtil.getIndexer(Layout.class);

		Layout layout1 = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "foo foo foo bar",
			RandomTestUtil.randomString(), StringPool.BLANK,
			LayoutConstants.TYPE_CONTENT, false, StringPool.BLANK,
			_serviceContext);

		indexer.reindex(layout1);

		Layout layout2 = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "bar bar bar foo",
			RandomTestUtil.randomString(), StringPool.BLANK,
			LayoutConstants.TYPE_CONTENT, false, StringPool.BLANK,
			_serviceContext);

		indexer.reindex(layout2);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private ServiceContext _serviceContext;

}