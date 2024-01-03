/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.service.impl.CompanyLocalServiceImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsValues;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;

/**
 * @author Raymond Augé
 */
public class CompanyLocalServiceImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		BundleContext bundleContext = Mockito.mock(BundleContext.class);

		_systemBundleUtilMockedStatic = Mockito.mockStatic(
			SystemBundleUtil.class);

		_systemBundleUtilMockedStatic.when(
			SystemBundleUtil::getBundleContext
		).thenReturn(
			bundleContext
		);
	}

	@After
	public void tearDown() {
		_systemBundleUtilMockedStatic.close();
	}

	@Test
	public void testSyncDefaultCompanyVirtualHost() throws Exception {
		String testDomain = "a.test.domain";

		Company testCompany = new CompanyImpl();

		testCompany.setWebId("liferay.com");

		CompanyLocalServiceImpl companyLocalServiceImpl =
			new CompanyLocalServiceImpl() {

				@Override
				public Company checkCompany(String webId)
					throws PortalException {

					return syncDefaultCompanyVirtualHost(
						getCompanyByWebId(webId));
				}

				@Override
				public Company getCompanyByWebId(String webId)
					throws PortalException {

					return testCompany;
				}

				@Override
				public Company updateCompany(
						long companyId, String virtualHostname, String mx,
						int maxUsers, boolean active)
					throws PortalException {

					testCompany.setCompanyId(companyId);
					testCompany.setVirtualHostname(virtualHostname);
					testCompany.setMx(mx);
					testCompany.setMaxUsers(maxUsers);
					testCompany.setActive(active);

					return testCompany;
				}

			};

		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"COMPANY_DEFAULT_VIRTUAL_HOST_SYNC_ON_STARTUP", true);
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"COMPANY_DEFAULT_VIRTUAL_HOST_NAME", testDomain);
			SafeCloseable safeCloseable3 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"COMPANY_DEFAULT_VIRTUAL_HOST_MAIL_DOMAIN", testDomain)) {

			Company company = companyLocalServiceImpl.checkCompany(
				PropsValues.COMPANY_DEFAULT_WEB_ID);

			Assert.assertEquals(testDomain, company.getMx());
			Assert.assertEquals(testDomain, company.getVirtualHostname());
		}
	}

	private MockedStatic<SystemBundleUtil> _systemBundleUtilMockedStatic;

}