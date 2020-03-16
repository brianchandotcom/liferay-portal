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
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.OrganizationTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

	@Test
	public void testBuildWithTransaction() throws Throwable {
		PhoneResource phoneResource = PhoneResource.builder(
		).user(
			_user
		).build();

		Phone expectedPhone = _randomPhone();

		Exception exception1 = new Exception();

		try {
			TransactionInvokerUtil.invoke(
				_REQUIRES_NEW_TRANSACTION_CONFIG,
				() -> {
					List<ListType> listTypes =
						_listTypeLocalService.getListTypes(
							ListTypeConstants.ORGANIZATION_PHONE);

					ListType listType = listTypes.get(0);

					_phoneLocalService.addPhone(
						_user.getUserId(), _organization.getModelClassName(),
						_organization.getOrganizationId(),
						expectedPhone.getPhoneNumber(),
						expectedPhone.getExtension(), listType.getListTypeId(),
						expectedPhone.getPrimary(), new ServiceContext());

					Page<Phone> page = phoneResource.getOrganizationPhonesPage(
						String.valueOf(_organization.getOrganizationId()));

					Collection<Phone> phones = page.getItems();

					Iterator<Phone> iterator = phones.iterator();

					Phone phone = iterator.next();

					Assert.assertEquals(
						expectedPhone.getPhoneNumber(), phone.getPhoneNumber());

					throw exception1;
				});
		}
		catch (Exception exception2) {
			Assert.assertSame(exception1, exception2);
		}

		TransactionInvokerUtil.invoke(
			_SUPPORTS_TRANSACTION_CONFIG,
			() -> {
				Page<Phone> page = phoneResource.getOrganizationPhonesPage(
					String.valueOf(_organization.getOrganizationId()));

				Collection<Phone> phones = page.getItems();

				Assert.assertTrue(phones.isEmpty());

				return null;
			});
	}

	private Phone _randomPhone() {
		return new Phone() {
			{
				extension = String.valueOf(RandomTestUtil.randomInt());
				phoneNumber = RandomTestUtil.randomString();
				primary = false;
			}
		};
	}

	private static final TransactionConfig _REQUIRES_NEW_TRANSACTION_CONFIG =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private static final TransactionConfig _SUPPORTS_TRANSACTION_CONFIG =
		TransactionConfig.Factory.create(
			Propagation.SUPPORTS, new Class<?>[] {Exception.class});

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	@DeleteAfterTestRun
	private Organization _organization;

	@Inject
	private PhoneLocalService _phoneLocalService;

	@DeleteAfterTestRun
	private User _user;

}