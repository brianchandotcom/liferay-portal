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
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.site.apio.route.base.test.BaseUserAccountWebSiteNestedCollectionRouterTestCase;

import java.util.List;

import org.junit.After;
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
public class PersonWebSiteNestedCollectionRouterTest
	extends BaseUserAccountWebSiteNestedCollectionRouterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetPageItems() throws Throwable {
		User user = getUser();

		PageItems<Group> pageItems = getPageItems(
			PaginationRequest.of(10, 1), user.getUserId());

		List<Group> groups = (List<Group>)pageItems.getItems();

		Assert.assertEquals(groups.toString(), 2, groups.size());

		List<Group> userGroups = user.getGroups();

		Assert.assertTrue(groups.contains(userGroups.get(0)));
		Assert.assertTrue(groups.contains(userGroups.get(1)));
	}

	@Override
	protected NestedCollectionRouter getNestedCollectionResource() {
		return _nestedCollectionResource;
	}

	@Inject(
		filter = "component.name=com.liferay.site.apio.internal.architect.router.PersonWebSiteNestedCollectionRouter"
	)
	private NestedCollectionRouter _nestedCollectionResource;

}