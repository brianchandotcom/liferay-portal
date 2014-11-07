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
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.util.test.MDRTestUtil;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MDRRuleGroupLocalServiceTest {

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

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

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