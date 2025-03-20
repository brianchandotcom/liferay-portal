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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.util.PortalInstances;

import java.util.Date;

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
				String companyWebId;

				if (GetterUtil.getBoolean(
						TestPropsUtil.get("test.extract.and.insert.company")) ||
					GetterUtil.getBoolean(
						TestPropsUtil.get("test.copy.company"))) {

					companyWebId = RandomTestUtil.randomString() + ".com";
				}
				else {
					companyWebId = TestPropsValues.COMPANY_WEB_ID;
				}

				PortalInstances.addCompany(
					"",
					() -> CompanyLocalServiceUtil.addCompany(
						null, companyWebId, companyWebId, companyWebId, 0, true,
						true, null, null, null, null, null, null));

				if (GetterUtil.getBoolean(
						TestPropsUtil.get("test.extract.and.insert.company"))) {

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
							TestPropsUtil.get("test.copy.company"))) {

					company = CompanyLocalServiceUtil.fetchCompanyByVirtualHost(
						companyWebId);

					CompanyLocalServiceUtil.copyDBPartitionCompany(
						company.getCompanyId(),
						RandomTestUtil.randomLong(
							(long)Math.pow(10, 13), (long)Math.pow(10, 14)),
						TestPropsValues.COMPANY_WEB_ID,
						TestPropsValues.COMPANY_WEB_ID,
						TestPropsValues.COMPANY_WEB_ID);

					CompanyLocalServiceUtil.deleteCompany(
						company.getCompanyId());
				}
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return statement;
	}

}