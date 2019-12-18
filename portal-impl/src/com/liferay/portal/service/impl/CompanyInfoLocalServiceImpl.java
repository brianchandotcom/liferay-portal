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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.model.CompanyInfo;
import com.liferay.portal.service.base.CompanyInfoLocalServiceBaseImpl;

/**
 * Provides the local service for adding, checking, and updating company infos.
 * Each company info refers to a separate portal instance (company).
 *
 * @author Brian Wing Shun Chan
 * @author Alberto Chaparo
 */
public class CompanyInfoLocalServiceImpl
	extends CompanyInfoLocalServiceBaseImpl {

	/**
	 * Returns the company info with the companyId
	 *
	 * @param companyId the company ID
	 * @return the matching company info, or <code>null</code> if a matching
	 * 			company info could not be found
	 */
	public CompanyInfo fetchByCompanyId(long companyId) {
		return companyInfoPersistence.fetchByCompanyId(companyId);
	}

}