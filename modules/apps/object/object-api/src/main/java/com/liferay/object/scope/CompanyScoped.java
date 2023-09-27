/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.scope;

/**
 * @author Feliphe Marinho
 */
public interface CompanyScoped {

	public long getAllowedCompanyId();

	public default boolean isAllowedCompany(long companyId) {
		if (getAllowedCompanyId() == companyId) {
			return true;
		}

		return false;
	}

}