/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.db.partition.DBPartition;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
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
		try {
			if (DBPartition.isPartitionEnabled()) {
				try {
					CompanyLocalServiceUtil.getCompanyByWebId(
						_PARTITION_WEB_ID);
				}
				catch (Exception exception) {
					if (exception instanceof NoSuchCompanyException) {
						PortalInstances.addCompany(
							"",
							() -> CompanyLocalServiceUtil.addCompany(
								null, _PARTITION_WEB_ID, _PARTITION_WEB_ID,
								_PARTITION_WEB_ID, 0, true, true, null, null,
								null, null, null, null));
					}
					else {
						throw exception;
					}
				}
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		return statement;
	}

	private static final String _PARTITION_WEB_ID = "db-partition.com";

}