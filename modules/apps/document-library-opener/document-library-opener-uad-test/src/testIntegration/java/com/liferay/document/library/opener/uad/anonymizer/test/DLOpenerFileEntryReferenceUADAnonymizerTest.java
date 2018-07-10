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

package com.liferay.document.library.opener.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.document.library.opener.uad.test.DLOpenerFileEntryReferenceUADTestHelper;
import com.liferay.portal.kernel.model.User;
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
public class DLOpenerFileEntryReferenceUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<DLOpenerFileEntryReference> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_dlOpenerFileEntryReferenceUADTestHelper.cleanUpDependencies(
			_dlOpenerFileEntryReferences);
	}

	@Override
	protected DLOpenerFileEntryReference addBaseModel(long userId)
		throws Exception {

		return addBaseModel(userId, true);
	}

	@Override
	protected DLOpenerFileEntryReference addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceUADTestHelper.
				addDLOpenerFileEntryReference(userId);

		if (deleteAfterTestRun) {
			_dlOpenerFileEntryReferences.add(dlOpenerFileEntryReference);
		}

		return dlOpenerFileEntryReference;
	}

	@Override
	protected void deleteBaseModels(List<DLOpenerFileEntryReference> baseModels)
		throws Exception {

		_dlOpenerFileEntryReferenceUADTestHelper.cleanUpDependencies(
			baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(baseModelPK);

		String userName = dlOpenerFileEntryReference.getUserName();

		if ((dlOpenerFileEntryReference.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(baseModelPK) == null) {

			return true;
		}

		return false;
	}

	@Inject
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@DeleteAfterTestRun
	private final List<DLOpenerFileEntryReference>
		_dlOpenerFileEntryReferences = new ArrayList<>();

	@Inject
	private DLOpenerFileEntryReferenceUADTestHelper
		_dlOpenerFileEntryReferenceUADTestHelper;

	@Inject(filter = "component.name=*.DLOpenerFileEntryReferenceUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;

}