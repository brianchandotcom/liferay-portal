/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.partition.internal.configuration.persistence.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.test.rule.Inject;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mariano Álvaro Sáiz
 */
@RunWith(Arquillian.class)
public class DBPartitionVirtualInstanceExtractionConfigurationModelListenerTest
	extends BaseConfigurationModelListenerTestCase {

	@Override
	public String getListenerName() {
		return "DBPartitionVirtualInstanceExtractionConfigurationModelListener";
	}

	@Test
	public void testCompanyExtraction() throws Exception {
		String toBeExtractedCompanyWebId = "Test" + COMPANY_IDS[0];

		try (AutoCloseable autoCloseable = swapCompanyLocalService(
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "extractCompany")) {
						Assert.assertEquals(
							COMPANY_IDS[0], GetterUtil.getLong(args[0]));

						_calledDoExportPartitionCompanyMethod = true;
					}
					else if (Objects.equals(
								method.getName(), "getCompanyByWebId")) {

						Assert.assertEquals(toBeExtractedCompanyWebId, args[0]);

						return _companyLocalService.createCompany(
							COMPANY_IDS[0]);
					}

					return null;
				})) {

			deployConfiguration(
				_PID, "webId=T\"" + toBeExtractedCompanyWebId + "\"\n");

			Assert.assertTrue(_calledDoExportPartitionCompanyMethod);
		}
	}

	@Test
	public void testConfigurationIsDeletedAfterDeploy() throws Exception {
		try (AutoCloseable autoCloseable = swapCompanyLocalService(
				(proxy, method, args) -> {
					if (Objects.equals(method.getName(), "getCompanyByWebId")) {
						return _companyLocalService.createCompany(
							COMPANY_IDS[0]);
					}

					return null;
				})) {

			testConfigurationIsDeletedAfterDeploy(
				_PID, "webId=T\"testWebId\"\n");
		}
	}

	private static final String _PID =
		"com.liferay.portal.db.partition.internal.configuration." +
			"DBPartitionVirtualInstanceExtractionConfiguration";

	private boolean _calledDoExportPartitionCompanyMethod;

	@Inject
	private CompanyLocalService _companyLocalService;

}