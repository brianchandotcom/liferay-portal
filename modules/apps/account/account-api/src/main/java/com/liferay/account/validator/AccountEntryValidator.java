/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Tancredi Covioli
 */
public interface AccountEntryValidator {

	public String getKey(
		AccountEntry accountEntry, Map<String, Object> additionalProps);

	public AccountEntryValidatorResult validate(
			AccountEntry accountEntry, Map<String, Object> additionalProps)
		throws PortalException;

}