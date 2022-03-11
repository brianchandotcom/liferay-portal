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
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Ricardo Couso
 */
@RunWith(Arquillian.class)
public class LayoutPublishedSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_setUpLayoutIndexerFixture();
	}

	@Test
	public void testPublishedPageSearch() throws Exception {
		String name = RandomTestUtil.randomString();

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group, name);

		_layoutIndexerFixture.searchNoOne(name);

		_publishLayout(layout);

		_layoutIndexerFixture.searchOnlyOne(name);
	}

	private void _publishLayout(Layout layout) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				layout.getGroup(), TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "_publishLayout",
			new Class<?>[] {
				Layout.class, Layout.class, ServiceContext.class, long.class
			},
			layout.fetchDraftLayout(), layout, serviceContext,
			TestPropsValues.getUserId());
	}

	private void _setUpLayoutIndexerFixture() {
		_layoutIndexerFixture = new IndexerFixture<>(Layout.class);
	}

	@DeleteAfterTestRun
	private Group _group;

	private IndexerFixture<Layout> _layoutIndexerFixture;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/publish_layout"
	)
	private MVCActionCommand _mvcActionCommand;

}