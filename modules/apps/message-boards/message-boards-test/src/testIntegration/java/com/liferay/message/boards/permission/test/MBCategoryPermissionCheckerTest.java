/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.permission.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.security.permission.test.util.BasePermissionTestCase;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
@RunWith(Arquillian.class)
public class MBCategoryPermissionCheckerTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			_categoryModelResourcePermission.contains(
				permissionChecker, _category, ActionKeys.VIEW));
		Assert.assertTrue(
			_categoryModelResourcePermission.contains(
				permissionChecker, _subcategory, ActionKeys.VIEW));

		removePortletModelViewPermission();

		Assert.assertFalse(
			_categoryModelResourcePermission.contains(
				permissionChecker, _category, ActionKeys.VIEW));
		Assert.assertFalse(
			_categoryModelResourcePermission.contains(
				permissionChecker, _subcategory, ActionKeys.VIEW));
	}

	@Override
	protected void doSetUp() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		_category = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);

		_subcategory = MBCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), _category.getCategoryId(),
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
	}

	@Override
	protected String getResourceName() {
		return MBConstants.RESOURCE_NAME;
	}

	@Inject(
		filter = "model.class.name=com.liferay.message.boards.model.MBCategory"
	)
	private static ModelResourcePermission<MBCategory>
		_categoryModelResourcePermission;

	private MBCategory _category;
	private MBCategory _subcategory;

}