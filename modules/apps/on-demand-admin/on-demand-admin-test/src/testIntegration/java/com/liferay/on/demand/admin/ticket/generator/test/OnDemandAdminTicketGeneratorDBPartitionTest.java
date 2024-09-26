/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.on.demand.admin.ticket.generator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.on.demand.admin.ticket.generator.OnDemandAdminTicketGenerator;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserWrapper;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.DefaultAdminUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Locale;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stian Sigvartsen
 */
@RunWith(Arquillian.class)
public class OnDemandAdminTicketGeneratorDBPartitionTest
	extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();

		BaseDBPartitionTestCase.setUpDBPartitions();

		_originalCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(portal.getDefaultCompanyId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		CompanyThreadLocal.setCompanyId(_originalCompanyId);

		BaseDBPartitionTestCase.tearDownDBPartitions();
	}

	@Test
	public void testOnDemandAdmin() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		User user = new UserWrapper(
			DefaultAdminUtil.fetchDefaultAdmin(companyId)) {

			@Override
			public String getFirstName() {
				_assertCompanyId(companyId);

				return super.getFirstName();
			}

			@Override
			public String getLastName() {
				_assertCompanyId(companyId);

				return super.getLastName();
			}

			@Override
			public Locale getLocale() {
				_assertCompanyId(companyId);

				return super.getLocale();
			}

			@Override
			public String getMiddleName() {
				_assertCompanyId(companyId);

				return super.getMiddleName();
			}

			@Override
			public boolean isMale() throws PortalException {
				_assertCompanyId(companyId);

				return super.isMale();
			}

		};

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);

		for (long targetCompanyId : COMPANY_IDS) {
			if (targetCompanyId == companyId) {
				return;
			}

			try (SafeCloseable safeCloseable =
					CompanyThreadLocal.setCompanyIdWithSafeCloseable(
						companyId)) {

				Ticket ticket = _onDemandAdminTicketGenerator.generate(
					companyLocalService.getCompany(targetCompanyId), null,
					user);

				Assert.assertNotNull(ticket);
				Assert.assertNotEquals(
					user.getCompanyId(), ticket.getCompanyId());
			}
		}
	}

	private void _assertCompanyId(long companyId) {
		Assert.assertEquals(companyId, (long)CompanyThreadLocal.getCompanyId());
	}

	private static long _originalCompanyId;

	@Inject
	private OnDemandAdminTicketGenerator _onDemandAdminTicketGenerator;

}