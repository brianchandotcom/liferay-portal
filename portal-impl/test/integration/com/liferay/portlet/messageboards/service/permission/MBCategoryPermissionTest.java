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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.BasePermissionTestCase;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBCategoryPermissionTest extends BasePermissionTestCase {

	@ClassRule
	public static final MainServletTestRule mainServletTestRule =
		MainServletTestRule.INSTANCE;

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			MBCategoryPermission.contains(
				permissionChecker, _category, ActionKeys.VIEW));
		Assert.assertTrue(
			MBCategoryPermission.contains(
				permissionChecker, _subcategory, ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			MBCategoryPermission.contains(
				permissionChecker, _category, ActionKeys.VIEW));
		Assert.assertFalse(
			MBCategoryPermission.contains(
				permissionChecker, _subcategory, ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		_category = MBTestUtil.addCategory(group.getGroupId());

		_subcategory = MBTestUtil.addCategory(
			group.getGroupId(), _category.getCategoryId());
	}

	@Override
	protected String getResourceName() {
		return MBPermission.RESOURCE_NAME;
	}

	private MBCategory _category;
	private MBCategory _subcategory;

}