/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortalInstances;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Jorge Avalos
 */
public class DBPartitionTestRule implements TestRule {

	public static final DBPartitionTestRule INSTANCE =
		new DBPartitionTestRule();

	@Override
	public Statement apply(Statement statement, Description description) {
		if (!DBPartition.isPartitionEnabled()) {
			return statement;
		}

		try {
			Company company = CompanyLocalServiceUtil.fetchCompanyByVirtualHost(
				TestPropsValues.COMPANY_WEB_ID);

			if (company == null) {
				String companyWebId = TestPropsValues.COMPANY_WEB_ID;

				if (GetterUtil.getBoolean(
						TestPropsUtil.get(_PROPERTY_TEST_COPY_COMPANY))) {

					companyWebId = RandomTestUtil.randomString() + ".com";
				}

				PortalInstances.addCompany(
					"",
					() -> CompanyLocalServiceUtil.addCompany(
						null, companyWebId, companyWebId, companyWebId, 0, true,
						true, null, null, null, null, null, null));

				if (GetterUtil.getBoolean(
						TestPropsUtil.get(
							_PROPERTY_TEST_EXTRACT_AND_INSERT_COMPANY))) {

					company = CompanyLocalServiceUtil.fetchCompanyByVirtualHost(
						companyWebId);

					CompanyLocalServiceUtil.extractCompany(
						company.getCompanyId());

					CompanyLocalServiceUtil.deleteCompany(
						company.getCompanyId());

					CompanyLocalServiceUtil.addDBPartitionCompany(
						company.getCompanyId(), TestPropsValues.COMPANY_WEB_ID,
						TestPropsValues.COMPANY_WEB_ID,
						TestPropsValues.COMPANY_WEB_ID);
				}
				else if (GetterUtil.getBoolean(
							TestPropsUtil.get(_PROPERTY_TEST_COPY_COMPANY))) {

					company = CompanyLocalServiceUtil.fetchCompanyByVirtualHost(
						companyWebId);

					CompanyLocalServiceUtil.copyDBPartitionCompany(
						company.getCompanyId(), null,
						TestPropsValues.COMPANY_WEB_ID,
						TestPropsValues.COMPANY_WEB_ID,
						TestPropsValues.COMPANY_WEB_ID);
				}
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return statement;
	}

	private static final String _PROPERTY_TEST_COPY_COMPANY =
		"test.copy.company";

	private static final String _PROPERTY_TEST_EXTRACT_AND_INSERT_COMPANY =
		"test.extract.and.insert.company";

}