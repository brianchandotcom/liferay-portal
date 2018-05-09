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

package com.liferay.document.library.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.uad.test.DLFileEntryTypeUADTestHelper;

import com.liferay.portal.kernel.model.User;
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
public class DLFileEntryTypeUADAnonymizerTest extends BaseUADAnonymizerTestCase<DLFileEntryType> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_dlFileEntryTypeUADTestHelper.cleanUpDependencies(_dlFileEntryTypes);
	}

	@Override
	protected DLFileEntryType addBaseModel(long userId)
		throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected DLFileEntryType addBaseModel(long userId,
		boolean deleteAfterTestRun) throws Exception {
		DLFileEntryType dlFileEntryType = _dlFileEntryTypeUADTestHelper.addDLFileEntryType(userId);

		if (deleteAfterTestRun) {
			_dlFileEntryTypes.add(dlFileEntryType);
		}

		return dlFileEntryType;
	}

	@Override
	protected void deleteBaseModels(List<DLFileEntryType> baseModels)
		throws Exception {
		_dlFileEntryTypeUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		DLFileEntryType dlFileEntryType = _dlFileEntryTypeLocalService.getDLFileEntryType(baseModelPK);

		String userName = dlFileEntryType.getUserName();

		if ((dlFileEntryType.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_dlFileEntryTypeLocalService.fetchDLFileEntryType(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<DLFileEntryType> _dlFileEntryTypes = new ArrayList<DLFileEntryType>();
	@Inject
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	@Inject
	private DLFileEntryTypeUADTestHelper _dlFileEntryTypeUADTestHelper;
	@Inject(filter = "component.name=*.DLFileEntryTypeUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}