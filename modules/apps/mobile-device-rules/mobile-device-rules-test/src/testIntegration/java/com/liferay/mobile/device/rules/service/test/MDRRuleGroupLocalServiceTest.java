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

package com.liferay.mobile.device.rules.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.mobile.device.rules.model.MDRRuleGroup;
import com.liferay.mobile.device.rules.service.MDRRuleGroupLocalService;
import com.liferay.mobile.device.rules.util.test.MDRTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class MDRRuleGroupLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		_ruleGroup = MDRTestUtil.addRuleGroup(companyGroup.getGroupId());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCopyRuleGroup() throws Exception {
		Role ownerRole = _roleLocalService.getRole(
			_ruleGroup.getCompanyId(), RoleConstants.OWNER);

		ResourcePermission oldResourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				_ruleGroup.getCompanyId(), MDRRuleGroup.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_ruleGroup.getPrimaryKey()),
				ownerRole.getRoleId());

		Assert.assertTrue(oldResourcePermission.isViewActionId());

		RoleTestUtil.removeResourcePermission(
			RoleConstants.OWNER, MDRRuleGroup.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_ruleGroup.getPrimaryKey()), ActionKeys.VIEW);

		oldResourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				_ruleGroup.getCompanyId(), MDRRuleGroup.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(_ruleGroup.getPrimaryKey()),
				ownerRole.getRoleId());

		Assert.assertFalse(oldResourcePermission.isViewActionId());

		MDRRuleGroup newRuleGroup = _mdrRuleGroupLocalService.copyRuleGroup(
			_ruleGroup, _ruleGroup.getGroupId(),
			ServiceContextTestUtil.getServiceContext());

		ResourcePermission newResourcePermission =
			_resourcePermissionLocalService.getResourcePermission(
				_ruleGroup.getCompanyId(), MDRRuleGroup.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(newRuleGroup.getPrimaryKey()),
				ownerRole.getRoleId());

		Assert.assertFalse(newResourcePermission.isViewActionId());

		Assert.assertNotEquals(oldResourcePermission, newResourcePermission);

		Assert.assertEquals(
			oldResourcePermission.getRoleId(),
			newResourcePermission.getRoleId());
		Assert.assertEquals(
			oldResourcePermission.getOwnerId(),
			newResourcePermission.getOwnerId());
		Assert.assertEquals(
			oldResourcePermission.getActionIds(),
			newResourcePermission.getActionIds());
		Assert.assertEquals(
			oldResourcePermission.isViewActionId(),
			newResourcePermission.isViewActionId());
	}

	@Test
	public void testSelectGlobalRulesNotPresent() throws Exception {
		testSelectableRuleGroups(false);
	}

	@Test
	public void testSelectGlobalRulesPresent() throws Exception {
		testSelectableRuleGroups(true);
	}

	protected void testSelectableRuleGroups(boolean includeGlobalGroup)
		throws Exception {

		Layout layout = LayoutTestUtil.addLayout(_group);

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (includeGlobalGroup) {
			params.put("includeGlobalScope", Boolean.TRUE);
		}

		List<MDRRuleGroup> ruleGroups =
			_mdrRuleGroupLocalService.searchByKeywords(
				layout.getGroupId(), StringPool.BLANK, params, false,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (includeGlobalGroup) {
			Assert.assertTrue(
				ruleGroups.toString(), ruleGroups.contains(_ruleGroup));
		}
		else {
			Assert.assertFalse(
				ruleGroups.toString(), ruleGroups.contains(_ruleGroup));
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private MDRRuleGroupLocalService _mdrRuleGroupLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	private MDRRuleGroup _ruleGroup;

}