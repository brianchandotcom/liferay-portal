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

package com.liferay.portal.vulcan.internal.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.context.VulcanContextProvider;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = VulcanContextProvider.class)
public class CompanyVulcanContextProvider
	implements VulcanContextProvider<Company> {

	@Override
	public Class<Company> getType() {
		return Company.class;
	}

	@Override
	public Company getValue() throws PortalException {
		return LazyProxyUtil.createProxy(
			Company.class,
			() -> _portal.getCompany(
				AccessControlThreadLocal.getHttpServletRequest()));
	}

	@Reference
	private Portal _portal;

}