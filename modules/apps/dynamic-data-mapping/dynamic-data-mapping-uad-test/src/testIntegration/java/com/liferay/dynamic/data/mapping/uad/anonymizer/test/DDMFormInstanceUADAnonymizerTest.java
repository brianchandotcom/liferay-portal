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

package com.liferay.dynamic.data.mapping.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.uad.util.DDMFormInstanceUADTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseHasAssetEntryUADAnonymizerTestCase;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Ibson
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceUADAnonymizerTest
	extends BaseHasAssetEntryUADAnonymizerTestCase<DDMFormInstance> {

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

	@Override
	protected DDMFormInstance addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected DDMFormInstance addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		return DDMFormInstanceUADTestUtil.addDDMFormInstance(_group, userId);
	}

	@Override
	protected UADAnonymizer<DDMFormInstance> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.getDDMFormInstance(baseModelPK);

		String userName = ddmFormInstance.getUserName();

		if ((ddmFormInstance.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.fetchDDMFormInstance(baseModelPK);

		if (ddmFormInstance == null) {
			return true;
		}

		return false;
	}

	@Inject
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(filter = "component.name=*.DDMFormInstanceUADAnonymizer")
	private UADAnonymizer<DDMFormInstance> _uadAnonymizer;

}