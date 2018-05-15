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

package com.liferay.portal.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.uad.test.RepositoryEntryUADTestHelper;

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
public class RepositoryEntryUADAnonymizerTest extends BaseUADAnonymizerTestCase<RepositoryEntry> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_repositoryEntryUADTestHelper.cleanUpDependencies(_repositoryEntries);
	}

	@Override
	protected RepositoryEntry addBaseModel(long userId)
		throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected RepositoryEntry addBaseModel(long userId,
		boolean deleteAfterTestRun) throws Exception {
		RepositoryEntry repositoryEntry = _repositoryEntryUADTestHelper.addRepositoryEntry(userId);

		if (deleteAfterTestRun) {
			_repositoryEntries.add(repositoryEntry);
		}

		return repositoryEntry;
	}

	@Override
	protected void deleteBaseModels(List<RepositoryEntry> baseModels)
		throws Exception {
		_repositoryEntryUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		RepositoryEntry repositoryEntry = _repositoryEntryLocalService.getRepositoryEntry(baseModelPK);

		String userName = repositoryEntry.getUserName();

		if ((repositoryEntry.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_repositoryEntryLocalService.fetchRepositoryEntry(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<RepositoryEntry> _repositoryEntries = new ArrayList<RepositoryEntry>();
	@Inject
	private RepositoryEntryLocalService _repositoryEntryLocalService;
	@Inject
	private RepositoryEntryUADTestHelper _repositoryEntryUADTestHelper;
	@Inject(filter = "component.name=*.RepositoryEntryUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}