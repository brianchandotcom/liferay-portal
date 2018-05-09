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

import com.liferay.layout.uad.test.LayoutFriendlyURLUADTestHelper;

import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@RunWith(Arquillian.class)
public class LayoutFriendlyURLUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<LayoutFriendlyURL> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_layoutFriendlyURLUADTestHelper.cleanUpDependencies(_layoutFriendlyURLs);
	}

	@Override
	protected LayoutFriendlyURL addBaseModel(long userId)
		throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected LayoutFriendlyURL addBaseModel(long userId,
		boolean deleteAfterTestRun) throws Exception {
		LayoutFriendlyURL layoutFriendlyURL = _layoutFriendlyURLUADTestHelper.addLayoutFriendlyURL(userId);

		if (deleteAfterTestRun) {
			_layoutFriendlyURLs.add(layoutFriendlyURL);
		}

		return layoutFriendlyURL;
	}

	@Override
	protected void deleteBaseModels(List<LayoutFriendlyURL> baseModels)
		throws Exception {
		_layoutFriendlyURLUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		LayoutFriendlyURL layoutFriendlyURL = _layoutFriendlyURLLocalService.getLayoutFriendlyURL(baseModelPK);

		String userName = layoutFriendlyURL.getUserName();

		if ((layoutFriendlyURL.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_layoutFriendlyURLLocalService.fetchLayoutFriendlyURL(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<LayoutFriendlyURL> _layoutFriendlyURLs = new ArrayList<LayoutFriendlyURL>();
	@Inject
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;
	@Inject
	private LayoutFriendlyURLUADTestHelper _layoutFriendlyURLUADTestHelper;
	@Inject(filter = "component.name=*.LayoutFriendlyURLUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}