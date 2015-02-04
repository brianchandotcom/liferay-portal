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

package com.liferay.portlet.mobiledevicerules.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.util.test.MDRTestUtil;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class MDRRuleGroupLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		Group companyGroup = company.getGroup();

		_ruleGroup = MDRTestUtil.addRuleGroup(companyGroup.getGroupId());

		_group = GroupTestUtil.addGroup();
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
			MDRRuleGroupLocalServiceUtil.searchByKeywords(
				layout.getGroupId(), StringPool.BLANK, params, false,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (includeGlobalGroup) {
			Assert.assertTrue(ruleGroups.contains(_ruleGroup));
		}
		else {
			Assert.assertFalse(ruleGroups.contains(_ruleGroup));
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private MDRRuleGroup _ruleGroup;

}