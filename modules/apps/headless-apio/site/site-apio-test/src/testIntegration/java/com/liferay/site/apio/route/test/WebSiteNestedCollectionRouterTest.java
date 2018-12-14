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

package com.liferay.site.apio.route.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.lang.reflect.Method;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class WebSiteNestedCollectionRouterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_parentGroup = GroupTestUtil.addGroup();

		_group = GroupTestUtil.addGroup(_parentGroup.getGroupId());
	}

	@Test
	public void testGetPageItemsWithChildGroup() throws Throwable {
		PageItems<Group> pageItems = _getPageItems(
			PaginationRequest.of(1, 1), _group.getGroupId());

		List<Group> groups = (List<Group>)pageItems.getItems();

		Assert.assertEquals(groups.toString(), 0, groups.size());
	}

	@Test
	public void testGetPageItemsWithParentGroup() throws Throwable {
		PageItems<Group> pageItems = _getPageItems(
			PaginationRequest.of(1, 1), _group.getParentGroupId());

		List<Group> groups = (List<Group>)pageItems.getItems();

		Assert.assertTrue(groups.contains(_group));
	}

	private PageItems<Group> _getPageItems(
			Pagination pagination, long parentGroupId)
		throws Exception {

		Class<? extends NestedCollectionRouter> clazz =
			_nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_nestedCollectionResource, pagination, parentGroupId);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.site.apio.internal.architect.router.WebSiteNestedCollectionRouter"
	)
	private NestedCollectionRouter _nestedCollectionResource;

	@DeleteAfterTestRun
	private Group _parentGroup;

}