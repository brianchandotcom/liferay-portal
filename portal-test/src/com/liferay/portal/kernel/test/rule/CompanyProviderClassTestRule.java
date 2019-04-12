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

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import org.junit.runner.Description;

/**
 * @author Cristina González
 */
public class CompanyProviderClassTestRule extends ClassTestRule<Long> {

	public static final CompanyProviderClassTestRule INSTANCE =
		new CompanyProviderClassTestRule();

	@Override
	protected void afterClass(Description description, Long previousCompanyId) {
		CompanyThreadLocal.setCompanyId(previousCompanyId);
	}

	@Override
	protected Long beforeClass(Description description) throws PortalException {
		Long companyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		return companyId;
	}

	private CompanyProviderClassTestRule() {
	}

}