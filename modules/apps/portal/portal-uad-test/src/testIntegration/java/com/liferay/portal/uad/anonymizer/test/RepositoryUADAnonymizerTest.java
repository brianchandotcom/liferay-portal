/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.uad.test.RepositoryUADTestHelper;

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
public class RepositoryUADAnonymizerTest extends BaseUADAnonymizerTestCase<Repository> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_repositoryUADTestHelper.cleanUpDependencies(_repositories);
	}

	@Override
	protected Repository addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected Repository addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {
		Repository repository = _repositoryUADTestHelper.addRepository(userId);

		if (deleteAfterTestRun) {
			_repositories.add(repository);
		}

		return repository;
	}

	@Override
	protected void deleteBaseModels(List<Repository> baseModels)
		throws Exception {
		_repositoryUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		Repository repository = _repositoryLocalService.getRepository(baseModelPK);

		String userName = repository.getUserName();

		if ((repository.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_repositoryLocalService.fetchRepository(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<Repository> _repositories = new ArrayList<Repository>();
	@Inject
	private RepositoryLocalService _repositoryLocalService;
	@Inject
	private RepositoryUADTestHelper _repositoryUADTestHelper;
	@Inject(filter = "component.name=*.RepositoryUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}