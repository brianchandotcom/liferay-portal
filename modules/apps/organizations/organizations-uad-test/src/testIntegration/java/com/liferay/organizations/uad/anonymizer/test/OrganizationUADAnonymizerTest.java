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

package com.liferay.organizations.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.organizations.uad.test.OrganizationUADTestHelper;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
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
public class OrganizationUADAnonymizerTest extends BaseUADAnonymizerTestCase<Organization> {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		_organizationUADTestHelper.cleanUpDependencies(_organizations);
	}

	@Override
	protected Organization addBaseModel(long userId) throws Exception {
		return addBaseModel(userId, true);
	}

	@Override
	protected Organization addBaseModel(long userId, boolean deleteAfterTestRun)
		throws Exception {
		Organization organization = _organizationUADTestHelper.addOrganization(userId);

		if (deleteAfterTestRun) {
			_organizations.add(organization);
		}

		return organization;
	}

	@Override
	protected void deleteBaseModels(List<Organization> baseModels)
		throws Exception {
		_organizationUADTestHelper.cleanUpDependencies(baseModels);
	}

	@Override
	protected UADAnonymizer getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {
		Organization organization = _organizationLocalService.getOrganization(baseModelPK);

		String userName = organization.getUserName();

		if ((organization.getUserId() != user.getUserId()) &&
				!userName.equals(user.getFullName())) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		if (_organizationLocalService.fetchOrganization(baseModelPK) == null) {
			return true;
		}

		return false;
	}

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<Organization>();
	@Inject
	private OrganizationLocalService _organizationLocalService;
	@Inject
	private OrganizationUADTestHelper _organizationUADTestHelper;
	@Inject(filter = "component.name=*.OrganizationUADAnonymizer")
	private UADAnonymizer _uadAnonymizer;
}