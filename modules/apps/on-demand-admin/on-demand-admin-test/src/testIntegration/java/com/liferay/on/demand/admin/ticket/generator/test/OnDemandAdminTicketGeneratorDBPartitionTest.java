/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.on.demand.admin.ticket.generator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.on.demand.admin.ticket.generator.OnDemandAdminTicketGenerator;
import com.liferay.portal.db.partition.DBPartitionUtil;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.IndexStatusManagerThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.security.DefaultAdminUtil;
import com.liferay.portal.test.rule.Inject;

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
		enableDBPartition();

		addDBPartitions();

		DBPartitionUtil.forEachCompanyId(
			company -> ResourceActionLocalServiceUtil.checkResourceActions());

		insertPartitionRequiredData();

		insertPartitionData();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		deletePartitionRequiredData();

		removeDBPartitions();

		disableDBPartition();
	}

	@Test
	public void testOnDemandAdmin() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		User user = DefaultAdminUtil.fetchDefaultAdmin(companyId);

		IndexStatusManagerThreadLocal.setIndexReadOnly(true);

		for (long targetCompanyId : COMPANY_IDS) {
			if (targetCompanyId == companyId) {
				return;
			}

			Ticket ticket = _onDemandAdminTicketGenerator.generate(
				_companyLocalService.getCompany(targetCompanyId), null,
				user.getUserId());

			Assert.assertNotNull(ticket);
			Assert.assertNotEquals(user.getCompanyId(), ticket.getCompanyId());
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private OnDemandAdminTicketGenerator _onDemandAdminTicketGenerator;

}