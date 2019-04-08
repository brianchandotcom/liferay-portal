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

package com.liferay.headless.foundation.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.foundation.dto.v1_0.Organization;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class OrganizationResourceTest extends BaseOrganizationResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addGroupAdminUser(testGroup);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		OrganizationLocalServiceUtil.clearGroupOrganizations(
			testGroup.getGroupId());
		OrganizationLocalServiceUtil.clearUserOrganizations(_user.getUserId());
		UserLocalServiceUtil.deleteUser(_user);

		for (com.liferay.portal.kernel.model.Organization parentOrganization :
				_parentOrganizations) {

			_deleteOrganizations(parentOrganization.getOrganizationId());
		}

		_deleteOrganizations(0);

		super.tearDown();
	}

	@Override
	protected void assertValid(Organization organization) {
		boolean valid = false;

		if ((organization.getId() != null) &&
			(organization.getName() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		Organization organization1, Organization organization2) {

		if (Objects.equals(organization1.getName(), organization2.getName())) {
			return true;
		}

		return false;
	}

	@Override
	protected Organization
			testGetMyUserAccountOrganizationsPage_addOrganization(
				Long userAccountId, Organization organization)
		throws Exception {

		return _addOrganization(userAccountId, organization);
	}

	@Override
	protected Long testGetMyUserAccountOrganizationsPage_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	@Override
	protected Organization testGetOrganization_addOrganization()
		throws Exception {

		return _addOrganization(_user.getUserId(), randomOrganization());
	}

	@Override
	protected Organization testGetOrganizationOrganizationsPage_addOrganization(
			Long parentOrganizationId, Organization organization)
		throws Exception {

		return _toOrganization(
			OrganizationLocalServiceUtil.addOrganization(
				_user.getUserId(), parentOrganizationId, organization.getName(),
				true));
	}

	@Override
	protected Long
			testGetOrganizationOrganizationsPage_getParentOrganizationId()
		throws Exception {

		com.liferay.portal.kernel.model.Organization organization =
			OrganizationLocalServiceUtil.addOrganization(
				_user.getUserId(), 0, RandomTestUtil.randomString(), true);

		_parentOrganizations.add(organization);

		return organization.getOrganizationId();
	}

	@Override
	protected Organization testGetOrganizationsPage_addOrganization(
			Organization organization)
		throws Exception {

		return _addOrganization(_user.getUserId(), organization);
	}

	@Override
	protected Organization testGetUserAccountOrganizationsPage_addOrganization(
			Long userAccountId, Organization organization)
		throws Exception {

		return _addOrganization(userAccountId, organization);
	}

	@Override
	protected Long testGetUserAccountOrganizationsPage_getUserAccountId()
		throws Exception {

		return _user.getUserId();
	}

	private Organization _addOrganization(
			Long userAccountId, Organization organization)
		throws Exception {

		organization = _toOrganization(
			OrganizationLocalServiceUtil.addOrganization(
				_user.getUserId(), 0, organization.getName(), true));

		if (userAccountId != null) {
			UserLocalServiceUtil.addOrganizationUser(
				organization.getId(), userAccountId);
		}

		return organization;
	}

	private void _deleteOrganizations(long parentOrganizationId)
		throws PortalException {

		List<com.liferay.portal.kernel.model.Organization> organizations =
			OrganizationLocalServiceUtil.getOrganizations(
				testGroup.getCompanyId(), parentOrganizationId);

		for (com.liferay.portal.kernel.model.Organization organization :
				organizations) {

			OrganizationLocalServiceUtil.deleteOrganization(organization);
		}
	}

	private Organization _toOrganization(
		com.liferay.portal.kernel.model.Organization organization) {

		return new Organization() {
			{
				id = organization.getOrganizationId();
				dateCreated = organization.getCreateDate();
				dateModified = organization.getModifiedDate();
				name = organization.getName();
			}
		};
	}

	private final List<com.liferay.portal.kernel.model.Organization>
		_parentOrganizations = new ArrayList<>();
	private User _user;

}