/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.security.Key;

import java.util.List;

/**
 * @author Tomas Polesovsky
 */
public class UpgradeCompany extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (Encryptor.KEY_ALGORITHM.equals("DES")) {
			_log.warn(
				"Unable to upgrade company key, DES is chosen as default");

			return;
		}

		List<Company> companies = CompanyLocalServiceUtil.getCompanies();

		for (Company company : companies) {
			upgradeKey(company);
		}
	}

	private void upgradeKey(Company company)
		throws EncryptorException, SystemException {

		Key key = company.getKeyObj();
		String algorithm = key.getAlgorithm();

		if (!algorithm.equals("DES")) {
			return;
		}

		Key newKey = Encryptor.generateKey();

		company.setKey(Base64.objectToString(newKey));

		CompanyLocalServiceUtil.updateCompany(company);
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeCompany.class);

}