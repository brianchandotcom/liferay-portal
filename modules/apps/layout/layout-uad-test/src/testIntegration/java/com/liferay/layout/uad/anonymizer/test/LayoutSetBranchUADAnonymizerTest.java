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

package com.liferay.layout.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.LayoutSetBranchConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class LayoutSetBranchUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<LayoutSetBranch> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected LayoutSetBranch addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected LayoutSetBranch addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.addLayoutSetBranch(
				userId, TestPropsValues.getGroupId(), false,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				false, LayoutSetBranchConstants.ALL_BRANCHES,
				ServiceContextTestUtil.getServiceContext());

		if (deleteAfterTestRun) {
			_layoutSetBranchs.add(layoutSetBranch);
		}

		return layoutSetBranch;
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.getLayoutSetBranch(baseModelPK);

		String userName = layoutSetBranch.getUserName();

		if ((layoutSetBranch.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_layoutSetBranchLocalService.fetchLayoutSetBranch(baseModelPK) ==
				null) {

			return true;
		}

		return false;
	}

	@Inject
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@DeleteAfterTestRun
	private final List<LayoutSetBranch> _layoutSetBranchs = new ArrayList<>();

	@Inject(filter = "component.name=*.LayoutSetBranchUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

}