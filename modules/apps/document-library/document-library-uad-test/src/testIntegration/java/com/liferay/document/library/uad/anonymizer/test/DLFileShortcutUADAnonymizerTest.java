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

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.uad.test.DLFileShortcutUADTestHelper;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;
import com.liferay.user.associated.data.test.util.WhenHasStatusByUserIdField;

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
public class DLFileShortcutUADAnonymizerTest extends BaseUADAnonymizerTestCase<DLFileShortcut>
	implements WhenHasStatusByUserIdField {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public DLFileShortcut addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		DLFileShortcut dlFileShortcut = _dlFileShortcutUADTestHelper.addDLFileShortcutWithStatusByUserId(userId,
				statusByUserId);

		_dlFileShortcuts.add(dlFileShortcut);

		return dlFileShortcut;
	}

	@After
	public void tearDown() throws Exception {
		_dlFileShortcutUADTestHelper.cleanUpDependencies(_dlFileShortcuts);
	}

	@Override
	protected DLFileShortcut addBaseModel(long userId)
		throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected DLFileShortcut addBaseModel(long userId,
		boolean deleteAfterTestRun) throws Exception {
		DLFileShortcut dlFileShortcut = _dlFileShortcutUADTestHelper.addDLFileShortcut(userId);

		if (deleteAfterTestRun) {
			_dlFileShortcuts.add(dlFileShortcut);
		}

		return dlFileShortcut;
	}

	@Override
	protected void deleteBaseModels(List<DLFileShortcut> baseModels)
		throws Exception {
		_dlFileShortcutUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		DLFileShortcut dlFileShortcut = _dlFileShortcutLocalService.getDLFileShortcut(baseModelPK);

		String userName = dlFileShortcut.getUserName();
		String statusByUserName = dlFileShortcut.getStatusByUserName();

		if ((dlFileShortcut.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName()) &&
				(dlFileShortcut.getStatusByUserId() != user.getUserId()) &&
				!statusByUserName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_dlFileShortcutLocalService.fetchDLFileShortcut(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<DLFileShortcut> _dlFileShortcuts = new ArrayList<DLFileShortcut>();
	@Inject
	private DLFileShortcutLocalService _dlFileShortcutLocalService;
	@Inject
	private DLFileShortcutUADTestHelper _dlFileShortcutUADTestHelper;
	@Inject(filter = "component.name=*.DLFileShortcutUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}