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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.CompanyProviderFactory;

/**
 * @author Miguel Pastor
 * @author Cristina González
 */
public class CompanyProviderFactoryImpl implements CompanyProviderFactory {

	@Override
	public long getCompanyId() {
		return _companyProvider.getCompanyId();
	}

	@Override
	public String getCompanyIdName() {
		return _companyProvider.getCompanyIdName();
	}

	@Override
	public CompanyProvider getCompanyProvider() {
		return _companyProvider;
	}

	@Override
	public void setCompanyProvider(CompanyProvider companyProvider) {
		_companyProvider = companyProvider;
	}

	private CompanyProvider _companyProvider;

}