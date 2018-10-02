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
import com.liferay.layout.uad.test.LayoutBranchUADTestUtil;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutBranchLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class LayoutBranchUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<LayoutBranch> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		LayoutBranchUADTestUtil.cleanUpDependencies(
			_layoutSetBranchLocalService, _layoutBranchs);
	}

	@Override
	protected LayoutBranch addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected LayoutBranch addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {

		LayoutBranch layoutBranch = LayoutBranchUADTestUtil.addLayoutBranch(
			_layoutBranchLocalService, _layoutSetBranchLocalService, userId);

		if (deleteAfterTestRun) {
			_layoutBranchs.add(layoutBranch);
		}

		return layoutBranch;
	}

	@Override
	protected void deleteBaseModels(List<LayoutBranch> baseModels)
		throws Exception {

		LayoutBranchUADTestUtil.cleanUpDependencies(
			_layoutSetBranchLocalService, baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		LayoutBranch layoutBranch = _layoutBranchLocalService.getLayoutBranch(
			baseModelPK);

		String userName = layoutBranch.getUserName();

		if ((layoutBranch.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_layoutBranchLocalService.fetchLayoutBranch(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@Inject
	private LayoutBranchLocalService _layoutBranchLocalService;

	@DeleteAfterTestRun
	private final List<LayoutBranch> _layoutBranchs = new ArrayList<>();

	@Inject
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Inject(filter = "component.name=*.LayoutBranchUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

}