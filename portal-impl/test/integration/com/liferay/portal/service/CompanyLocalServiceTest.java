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

package com.liferay.portal.service;

import com.liferay.portal.AccountNameException;
import com.liferay.portal.CompanyMxException;
import com.liferay.portal.CompanyVirtualHostException;
import com.liferay.portal.RequiredCompanyException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.File;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
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
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class CompanyLocalServiceTest {

	@Before
	public void setUp() {
		_companyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(PortalInstances.getDefaultCompanyId());

		File file = new File("portal-web/docroot");

		_mockServletContext = new MockServletContext(
			"file:" + file.getAbsolutePath(), new FileSystemResourceLoader());
	}

	@After
	public void tearDown() {
		CompanyThreadLocal.setCompanyId(_companyId);
	}

	@Test
	public void testAddAndDeleteCompany() throws Exception {
		Company company = addCompany();

		String companyWebId = company.getWebId();

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

		for (String webId : PortalInstances.getWebIds()) {
			Assert.assertNotEquals(companyWebId, webId);
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
	public void testAddAndDeleteCompanyWithDLFileEntryTypes() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		Group companyGroup = company.getGroup();

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				companyGroup.getGroupId(),
				DLFileEntryTypeConstants.NAME_CONTRACT);

		ServiceContext serviceContext = getServiceContext(companyId);

		serviceContext.setAttribute(
			"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());
		serviceContext.setScopeGroupId(guestGroup.getGroupId());
		serviceContext.setUserId(userId);

		DLAppLocalServiceUtil.addFileEntry(
			userId, guestGroup.getGroupId(), 0, "test.xml", "text/xml",
			"test.xml", "", "", "test".getBytes(), serviceContext);

		CompanyLocalServiceUtil.deleteCompany(companyId);
	}

	@Test
	public void testAddAndDeleteCompanyWithLayoutSetPrototype()
		throws Exception {

		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group group = GroupTestUtil.addGroup(
			companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		LayoutSetPrototype layoutSetPrototype = addLayoutSetPrototype(
			companyId, userId, RandomTestUtil.randomString());

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
	public void testAddAndDeleteCompanyWithParentGroup() throws Exception {
		Company company = addCompany();

		long companyId = company.getCompanyId();

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group parentGroup = GroupTestUtil.addGroup(
			companyId, userId, GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		Group group = GroupTestUtil.addGroup(
			companyId, userId, parentGroup.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

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
	public void testUpdateDisplay() throws Exception {
		Company company = addCompany();

		User user = UserLocalServiceUtil.getDefaultUser(company.getCompanyId());

		UserLocalServiceUtil.updateUser(user);

		CompanyLocalServiceUtil.updateDisplay(
			company.getCompanyId(), "hu", "CET");

		user = UserLocalServiceUtil.getDefaultUser(company.getCompanyId());

		Assert.assertEquals("hu", user.getLanguageId());
		Assert.assertEquals("CET", user.getTimeZoneId());
	}

	@Test
	public void testUpdateInvalidAccountNames() throws Exception {
		Company company = addCompany();

		Group group = GroupTestUtil.addGroup();

		group.setCompanyId(company.getCompanyId());

		GroupLocalServiceUtil.updateGroup(group);

		testUpdateAccountNames(
			company, new String[] {StringPool.BLANK, group.getName()}, true);

		GroupLocalServiceUtil.deleteGroup(group);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	@Test
	public void testUpdateInvalidVirtualHostNames() throws Exception {
		testUpdateVirtualHostNames(
			new String[] {StringPool.BLANK, "localhost", ".abc",}, true);
	}

	@Test
	public void testUpdateMx() throws Exception {
		testUpdateMx("abc.com", true, true);
		testUpdateMx("abc.com", true, false);
		testUpdateMx(StringPool.BLANK, false, true);
		testUpdateMx(StringPool.BLANK, false, false);
	}

	@Test
	public void testUpdateValidAccountNames() throws Exception {
		Company company = addCompany();

		testUpdateAccountNames(
			company, new String[] {RandomTestUtil.randomString()}, false);

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	@Test
	public void testUpdateValidVirtualHostNames() throws Exception {
		testUpdateVirtualHostNames(new String[] {"abc.com"}, false);
	}

	protected Company addCompany() throws Exception {
		String webId = RandomTestUtil.randomString() + "test.com";

		Company company = CompanyLocalServiceUtil.addCompany(
			webId, webId, "test.com", PropsValues.SHARD_DEFAULT_NAME, false, 0,
			true);

		PortalInstances.initCompany(_mockServletContext, webId);

		return company;
	}

	protected LayoutSetPrototype addLayoutSetPrototype(
			long companyId, long userId, String name)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				userId, companyId, nameMap, new HashMap<Locale, String>(), true,
				true, getServiceContext(companyId));

		return layoutSetPrototype;
	}

	protected User addUser(
			long companyId, long userId, long groupId,
			ServiceContext serviceContext)
		throws Exception {

		return UserTestUtil.addUser(
			companyId, userId, RandomTestUtil.randomString(), false,
			LocaleUtil.getDefault(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {groupId},
			serviceContext);
	}

	protected ServiceContext getServiceContext(long companyId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(companyId);

		return serviceContext;
	}

	protected void testUpdateAccountNames(
			Company company, String[] accountNames, boolean expectFailure)
		throws Exception {

		Account account = AccountLocalServiceUtil.getAccount(
			company.getAccountId());

		for (String accountName : accountNames) {
			try {
				company = CompanyLocalServiceUtil.updateCompany(
					company.getCompanyId(), company.getVirtualHostname(),
					company.getMx(), company.getHomeURL(), true, null,
					accountName, account.getLegalName(), account.getLegalId(),
					account.getLegalType(), account.getSicCode(),
					account.getTickerSymbol(), account.getIndustry(),
					account.getType(), account.getSize());

				if (expectFailure) {
					Assert.fail();
				}
			}
			catch (AccountNameException ane) {
				if (!expectFailure) {
					Assert.fail();
				}
			}
		}
	}

	protected void testUpdateMx(String mx, boolean valid, boolean mailMxUpdate)
		throws Exception {

		Company company = addCompany();

		String originalMx = company.getMx();

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, "MAIL_MX_UPDATE");

		Object value = field.get(null);

		try {
			if (mailMxUpdate) {
				field.set(null, Boolean.TRUE);
			}
			else {
				field.set(null, Boolean.FALSE);
			}

			CompanyLocalServiceUtil.updateCompany(
				company.getCompanyId(), company.getVirtualHostname(), mx,
				company.getMaxUsers(), company.getActive());

			company = CompanyLocalServiceUtil.getCompany(
				company.getCompanyId());

			String updatedMx = company.getMx();

			if (valid && mailMxUpdate) {
				Assert.assertNotEquals(originalMx, updatedMx);
			}
			else {
				Assert.assertEquals(originalMx, updatedMx);
			}
		}
		catch (CompanyMxException cme) {
			if (valid || !mailMxUpdate) {
				Assert.fail();
			}
		}
		finally {
			CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());

			field.set(null, value);
		}
	}

	protected void testUpdateVirtualHostNames(
			String[] virtualHostNames, boolean expectFailure)
		throws Exception {

		Company company = addCompany();

		for (String virtualHostName : virtualHostNames) {
			try {
				CompanyLocalServiceUtil.updateCompany(
					company.getCompanyId(), virtualHostName, company.getMx(),
					company.getMaxUsers(), company.getActive());

				if (expectFailure) {
					Assert.fail();
				}
			}
			catch (CompanyVirtualHostException cvhe) {
				if (!expectFailure) {
					Assert.fail();
				}
			}
		}

		CompanyLocalServiceUtil.deleteCompany(company.getCompanyId());
	}

	private long _companyId;
	private MockServletContext _mockServletContext;

}