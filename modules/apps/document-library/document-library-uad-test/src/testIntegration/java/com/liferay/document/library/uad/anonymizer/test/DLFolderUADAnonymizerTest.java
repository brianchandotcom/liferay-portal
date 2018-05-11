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

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.uad.test.DLFolderUADTestHelper;

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
public class DLFolderUADAnonymizerTest extends BaseUADAnonymizerTestCase<DLFolder>
	implements WhenHasStatusByUserIdField {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@Override
	public DLFolder addBaseModelWithStatusByUserId(long userId,
		long statusByUserId) throws Exception {
		DLFolder dlFolder = _dlFolderUADTestHelper.addDLFolderWithStatusByUserId(userId,
				statusByUserId);

		_dlFolders.add(dlFolder);

		return dlFolder;
	}

	@After
	public void tearDown() throws Exception {
		_dlFolderUADTestHelper.cleanUpDependencies(_dlFolders);
	}

	@Override
	protected DLFolder addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected DLFolder addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {
		DLFolder dlFolder = _dlFolderUADTestHelper.addDLFolder(userId);

		if (deleteAfterTestRun) {
			_dlFolders.add(dlFolder);
		}

		return dlFolder;
	}

	@Override
	protected void deleteBaseModels(List<DLFolder> baseModels)
		throws Exception {
		_dlFolderUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		DLFolder dlFolder = _dlFolderLocalService.getDLFolder(baseModelPK);

		String userName = dlFolder.getUserName();
		String statusByUserName = dlFolder.getStatusByUserName();

		if ((dlFolder.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName()) &&
				(dlFolder.getStatusByUserId() != user.getUserId()) &&
				!statusByUserName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_dlFolderLocalService.fetchDLFolder(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<DLFolder> _dlFolders = new ArrayList<DLFolder>();
	@Inject
	private DLFolderLocalService _dlFolderLocalService;
	@Inject
	private DLFolderUADTestHelper _dlFolderUADTestHelper;
	@Inject(filter = "component.name=*.DLFolderUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}