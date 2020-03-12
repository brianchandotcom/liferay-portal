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

package com.liferay.headless.admin.user.resource.v1_0.builder.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.user.dto.v1_0.Phone;
import com.liferay.headless.admin.user.resource.v1_0.PhoneResource;
import com.liferay.headless.admin.user.resource.v1_0.builder.PhoneResourceBuilder;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class PhoneResourceBuilderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testWithAdminUser() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		com.liferay.portal.kernel.model.Phone serviceBuiderPhone =
			OrganizationTestUtil.addPhone(organization);

		User user = UserTestUtil.addCompanyAdminUser(
			_companyLocalService.getCompany(organization.getCompanyId()));

		PhoneResource phoneResource = _phoneResourceBuilder.initialize(
		).user(
			user
		).build();

		Page<Phone> page = phoneResource.getOrganizationPhonesPage(
			String.valueOf(organization.getOrganizationId()));

		Assert.assertEquals(1, page.getTotalCount());

		Collection<Phone> phones = page.getItems();

		Iterator<Phone> iterator = phones.iterator();

		Phone phone = iterator.next();

		Assert.assertEquals(
			Long.valueOf(serviceBuiderPhone.getPhoneId()), phone.getId());

		_organizationLocalService.deleteOrganization(organization);
		_userLocalService.deleteUser(user);
	}

	@Ignore
	@Test
	public void testWithRegularUser() throws Exception {
		Organization organization = OrganizationTestUtil.addOrganization();

		com.liferay.portal.kernel.model.Phone serviceBuiderPhone =
			OrganizationTestUtil.addPhone(organization);

		User user = UserTestUtil.addUser();

		PhoneResource phoneResource = _phoneResourceBuilder.initialize(
		).usePermissionChecker(
			false
		).user(
			user
		).build();

		Page<Phone> page = phoneResource.getOrganizationPhonesPage(
			String.valueOf(organization.getOrganizationId()));

		Assert.assertEquals(1, page.getTotalCount());

		Collection<Phone> phones = page.getItems();

		Iterator<Phone> iterator = phones.iterator();

		Phone phone = iterator.next();

		Assert.assertEquals(
			Long.valueOf(serviceBuiderPhone.getPhoneId()), phone.getId());

		_organizationLocalService.deleteOrganization(organization);
		_userLocalService.deleteUser(user);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	@Inject
	private PhoneResourceBuilder _phoneResourceBuilder;

	@Inject
	private UserLocalService _userLocalService;

}