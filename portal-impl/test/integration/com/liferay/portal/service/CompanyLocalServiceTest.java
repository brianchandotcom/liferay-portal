/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.RequiredCompanyException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.File;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Mika Koivisto
 * @author Dale Shan
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class CompanyLocalServiceTest {

	@Before
	public void setUp() {
		File file = new File("portal-web/docroot");

		_mockServletContext = new MockServletContext(
			"file:" + file.getAbsolutePath(), new FileSystemResourceLoader());
	}

	@Test
	public void testAddAndDeleteCompany() throws Exception {
		Company company = addCompany();

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

		for (String webId : PortalInstances.getWebIds()) {
			Assert.assertNotEquals("test.com", webId);
		}
	}

	@Test
	public void testAddAndDeleteCompanyWithCompanyGroupStaging()
		throws Exception {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group companyGroup = company.getGroup();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute("staging", Boolean.TRUE);

		Group companyStagingGroup = GroupLocalServiceUtil.addGroup(
			userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			companyGroup.getClassName(), companyGroup.getClassPK(),
			companyGroup.getGroupId(), companyGroup.getDescriptiveName(),
			companyGroup.getDescription(), companyGroup.getType(),
			companyGroup.isManualMembership(),
			companyGroup.getMembershipRestriction(),
			companyGroup.getFriendlyURL(), false, companyGroup.isActive(),
			serviceContext);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

		companyGroup = GroupLocalServiceUtil.fetchGroup(
			companyGroup.getGroupId());

		Assert.assertNull(companyGroup);

		companyStagingGroup = GroupLocalServiceUtil.fetchGroup(
			companyStagingGroup.getGroupId());

		Assert.assertNull(companyStagingGroup);
	}

	@Test
	public void testAddandDeleteCompanyWithLayoutSetPrototype()
		throws Exception {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group group = GroupTestUtil.addGroup(
			companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString());

		LayoutSetPrototype layoutSetPrototype = addLayoutSetPrototype(
			companyId, userId, ServiceTestUtil.randomString());

		SitesUtil.updateLayoutSetPrototypesLinks(
			group, layoutSetPrototype.getLayoutSetPrototypeId(), 0, true,
			false);

		addUser(
			companyId, userId, group.getGroupId(),
			getServiceContext(companyId));

		CompanyLocalServiceUtil.deleteCompany(companyId);

		layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(
				layoutSetPrototype.getLayoutSetPrototypeId());

		Assert.assertNull(layoutSetPrototype);
	}

	@Test
	public void testAddandDeleteCompanyWithParentGroup() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group parentGroup = GroupTestUtil.addGroup(
			companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString());

		Group group = GroupTestUtil.addGroup(
			companyId, userId, parentGroup.getGroupId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString());

		addUser(
			companyId, userId, group.getGroupId(),
			getServiceContext(companyId));

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

		parentGroup = GroupLocalServiceUtil.fetchGroup(
			parentGroup.getGroupId());

		Assert.assertNull(parentGroup);

		group = GroupLocalServiceUtil.fetchGroup(group.getGroupId());

		Assert.assertNull(group);
	}

	@Test(expected = RequiredCompanyException.class)
	public void testDeleteDefaultCompany() throws Exception {
		long companyId = PortalInstances.getDefaultCompanyId();

		CompanyLocalServiceUtil.deleteCompany(companyId);
	}

	@Test
	public void testInValidAccountName() throws Exception {
		Company company = addCompany();

		Group group = GroupTestUtil.addGroup();

		group.setCompanyId(company.getCompanyId());

		GroupLocalServiceUtil.updateGroup(group);

		String[] InvalidAccountNames = {
			StringPool.BLANK, group.getName()
		};

		testAccountName(company, InvalidAccountNames, true);

		GroupLocalServiceUtil.deleteGroup(group);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	@Test
	public void testInValidVirtualHost() throws Exception {
		Company company = addCompany();

		String[] inValidVirtualHostNames = {
			StringPool.BLANK, "localhost", ".abc",
		};

		testVirtualHost(company, inValidVirtualHostNames, true);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	@Test
	public void testMailMxUpdatePropertyIsFalseAndMxIsInvalid()
		throws Exception {

		Company company = addCompany();

		String originalMx = company.getMx();

		String invalidMx = StringPool.BLANK;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), invalidMx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		finally {
			CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

			field.set(null, value);
		}
	}

	@Test
	public void testMailMxUpdatePropertyIsFalseAndMxIsValid() throws Exception {
		Company company = addCompany();

		String originalMx = company.getMx();

		String validMx = "abc.com";

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.FALSE);

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), validMx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			Assert.assertEquals(originalMx, updatedMx);
		}
		finally {
			CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

			field.set(null, value);
		}
	}

	@Test(expected = CompanyMxException.class)
	public void testMailMxUpdatePropertyIsTrueAndMxIsInvalid()
		throws Exception {

		Company company = addCompany();

		String invalidMx = StringPool.BLANK;

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			company = CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), invalidMx,
				company.getMaxUsers(), company.getActive());
		}
		finally {
			CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

			field.set(null, value);
		}
	}

	@Test
	public void testMailMxUpdatePropertyIsTrueAndMxIsValid() throws Exception {
		Company company = addCompany();

		String originalMx = company.getMx();

		String validMx = "abc.com";

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			field.set(null, Boolean.TRUE);

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), validMx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			Assert.assertNotEquals(originalMx, updatedMx);
		}
		finally {
			CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

			field.set(null, value);
		}
	}

	@Test
	public void testValidAccountName() throws Exception {
		Company company = addCompany();

		String[] validAccountName = {ServiceTestUtil.randomString()};

		testAccountName(company, validAccountName, false);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	@Test
	public void testValidVirtualHost() throws Exception {
		Company company = addCompany();

		String[] validVirtualHostNames = {"abc.com"};

		testVirtualHost(company, validVirtualHostNames, false);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	protected Company addCompany() throws Exception {
		Company company = CompanyLocalServiceUtil.addCompany(
			"test.com", "test.com", "test.com", PropsValues.SHARD_DEFAULT_NAME,
			false, 0, true);

		PortalInstances.initCompany(_mockServletContext, "test.com");

		return company;
	}

	protected LayoutSetPrototype addLayoutSetPrototype(
			long companyId, long userId, String name)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				userId, companyId, nameMap, StringPool.BLANK, true, true,
				getServiceContext(companyId));

		return layoutSetPrototype;
	}

	protected User addUser(
			long companyId, long userId, long groupId,
			ServiceContext serviceContext)
		throws Exception {

		return UserTestUtil.addUser(
			companyId, userId, ServiceTestUtil.randomString(), false,
			LocaleUtil.getDefault(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), new long[] {groupId},
			serviceContext);
	}

	protected ServiceContext getServiceContext(long companyId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);

		return serviceContext;
	}

	protected void testAccountName(
			Company company, String[] accountNames, boolean fail)
		throws Exception {

		Account account = AccountLocalServiceUtil.getAccount(
			company.getAccountId());

		for (String accountName : accountNames) {
			try {
				company = CompanyLocalServiceUtil.updateCompany(
					company.getCompanyId(), company.getVirtualHostname(),
					company.getMx(), company.getHomeURL(), accountName,
					account.getLegalName(), account.getLegalId(),
					account.getLegalType(), account.getSicCode(),
					account.getTickerSymbol(), account.getIndustry(),
					account.getType(), account.getSize());

				if (fail) {
					Assert.fail();
				}
			}
			catch (AccountNameException ane) {
				if (!fail) {
					Assert.fail();
				}
			}
		}
	}

	protected void testVirtualHost(
			Company company, String[] virtualHostNames, boolean fail)
		throws Exception {

		for (String virtualHostName : virtualHostNames) {
			try {
				CompanyLocalServiceUtil.updateCompany(
					company.getCompanyId(), virtualHostName, company.getMx(),
					company.getMaxUsers(), company.getActive());

				if (fail) {
					Assert.fail();
				}
			}
			catch (CompanyVirtualHostException cvhe) {
				if (!fail) {
					Assert.fail();
				}
			}
		}
	}

	private MockServletContext _mockServletContext;

}