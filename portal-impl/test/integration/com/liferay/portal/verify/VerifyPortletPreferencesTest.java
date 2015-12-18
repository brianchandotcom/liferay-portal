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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.service.LayoutBranchLocalServiceUtil;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Andrew Betts
 */
public class VerifyPortletPreferencesTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testCleanUpLayoutRevisionPortletPreferences() throws Exception {
		LayoutRevision layoutRevision = getLayoutRevision();

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.addPortletPreferences(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0,
				layoutRevision.getLayoutRevisionId(),
				RandomTestUtil.randomString(), new PortletImpl(),
				StringPool.BLANK);

		Assert.assertNotNull(portletPreferences);

		doVerify();

		portletPreferences =
			PortletPreferencesLocalServiceUtil.fetchPortletPreferences(
				portletPreferences.getPortletPreferencesId());

		Assert.assertNull(portletPreferences);
	}

	protected LayoutRevision getLayoutRevision() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(_group, false);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.addLayoutSetBranch(
				TestPropsValues.getUserId(), _group.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				true, 0, serviceContext);

		LayoutBranch layoutBranch =
			LayoutBranchLocalServiceUtil.getMasterLayoutBranch(
				layoutSetBranch.getLayoutSetBranchId(), layout.getPlid());

		return LayoutRevisionLocalServiceUtil.getLayoutRevision(
			layoutSetBranch.getLayoutSetBranchId(),
			layoutBranch.getLayoutBranchId(), layout.getPlid());
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyPortletPreferences();
	}

	@DeleteAfterTestRun
	private Group _group;

}