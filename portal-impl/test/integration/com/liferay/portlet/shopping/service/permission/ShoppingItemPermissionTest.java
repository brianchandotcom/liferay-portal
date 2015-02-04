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

package com.liferay.portlet.shopping.service.permission;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.test.BasePermissionTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.util.test.ShoppingTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
public class ShoppingItemPermissionTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			ShoppingItemPermission.contains(
				permissionChecker, _item, ActionKeys.VIEW));
		Assert.assertTrue(
			ShoppingItemPermission.contains(
				permissionChecker, _subitem, ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			ShoppingItemPermission.contains(
				permissionChecker, _item, ActionKeys.VIEW));
		Assert.assertFalse(
			ShoppingItemPermission.contains(
				permissionChecker, _subitem, ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		_item = ShoppingTestUtil.addItem(group.getGroupId());

		ShoppingCategory category = ShoppingTestUtil.addCategory(
			group.getGroupId());

		_subitem = ShoppingTestUtil.addItem(
			group.getGroupId(), category.getCategoryId());
	}

	@Override
	protected String getResourceName() {
		return ShoppingPermission.RESOURCE_NAME;
	}

	private ShoppingItem _item;
	private ShoppingItem _subitem;

}