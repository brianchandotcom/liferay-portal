/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.service.base.CompanyInfoLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Alberto Chaparo
 */
public class CompanyInfoLocalServiceImpl
	extends CompanyInfoLocalServiceBaseImpl {

	@Override
	public CompanyInfo fetchCompany(long companyId) {
		return companyInfoPersistence.fetchByCompanyId(companyId);
	}

}