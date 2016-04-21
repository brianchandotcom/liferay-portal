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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.util.Encryptor;

import java.security.Key;

/**
 * @author Mika Koivisto
 */
public class VerifyCompany extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyCompanyKey();
	}

	protected void verifyCompanyKey() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (Company company : CompanyLocalServiceUtil.getCompanies()) {
				Key key = (Key)Base64.stringToObjectSilent(company.getKey());

				if (key != null) {
					String keyString = Encryptor.serializeKey(key);
					long companyId = company.getCompanyId();

					runSQL(
						"update Company set key_ = '" + keyString +
							"' where companyId = " + companyId);
				}
			}
		}
	}

}