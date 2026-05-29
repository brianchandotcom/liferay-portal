/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.account.configuration.AccountEntryValidatorConfiguration;
import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;

/**
 * @author Tancredi Covioli
 */
public interface AccountEntryValidator {

	public AccountEntryValidatorConfiguration getConfiguration(long companyId)
		throws ConfigurationException;

	public String getKey(AccountEntry accountEntry, JSONObject jsonObject);

	public AccountEntryValidatorResult validate(
			AccountEntry accountEntry, JSONObject jsonObject)
		throws PortalException;

}