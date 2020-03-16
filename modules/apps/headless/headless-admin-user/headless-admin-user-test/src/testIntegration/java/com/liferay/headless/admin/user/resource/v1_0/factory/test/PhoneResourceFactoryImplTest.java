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

package com.liferay.headless.admin.user.resource.v1_0.factory.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.resource.v1_0.PhoneResource;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class PhoneResourceFactoryImplTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_organization = OrganizationTestUtil.addOrganization();

		_user = UserTestUtil.addCompanyAdminUser(
			_companyLocalService.getCompany(_organization.getCompanyId()));
	}

	@Test
	public void testBuildWithAdminUser() throws Exception {
		com.liferay.portal.kernel.model.Phone serviceBuiderPhone =
			OrganizationTestUtil.addPhone(_organization);

		PhoneResource phoneResource = PhoneResource.builder(
		).user(
			_user
		).build();

		Page<Phone> page = phoneResource.getOrganizationPhonesPage(
			String.valueOf(_organization.getOrganizationId()));

		Assert.assertEquals(1, page.getTotalCount());

		Collection<Phone> phones = page.getItems();

		Iterator<Phone> iterator = phones.iterator();

		Phone phone = iterator.next();

		Assert.assertEquals(
			Long.valueOf(serviceBuiderPhone.getPhoneId()), phone.getId());
	}

	@Ignore
	@Test
	public void testBuildWithRegularUser() throws Exception {
		com.liferay.portal.kernel.model.Phone serviceBuiderPhone =
			OrganizationTestUtil.addPhone(_organization);

		PhoneResource phoneResource = PhoneResource.builder(
		).checkPermissions(
			false
		).user(
			_user
		).build();

		Page<Phone> page = phoneResource.getOrganizationPhonesPage(
			String.valueOf(_organization.getOrganizationId()));

		Assert.assertEquals(1, page.getTotalCount());

		Collection<Phone> phones = page.getItems();

		Iterator<Phone> iterator = phones.iterator();

		Phone phone = iterator.next();

		Assert.assertEquals(
			Long.valueOf(serviceBuiderPhone.getPhoneId()), phone.getId());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@DeleteAfterTestRun
	private User _user;

}