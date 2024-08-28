/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.model;

import com.liferay.portal.kernel.model.Company;

/**
 * @author Daniel Sanz
 */
public interface DataSetModelManager {

	public void checkCompany(Company company);

	public void checkCompany(long companyId);

}