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

package com.liferay.commerce.customer.portal.internal.util;

import com.liferay.commerce.customer.portal.util.CommerceCustomerPortalHelper;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceCustomerPortalHelperImpl
	implements CommerceCustomerPortalHelper {

	@Override
	public Organization getCurrentOrganization(
		HttpServletRequest httpServletRequest, String organizationType) {

		HttpSession httpSession = httpServletRequest.getSession();

		long currentOrganizationId = GetterUtil.getLong(
			httpSession.getAttribute("LIFERAY_SHARED_currentOrganizationId"));

		return _organizationLocalService.fetchOrganization(
			currentOrganizationId);
	}

	@Override
	public Organization setCurrentOrganization(
		HttpServletRequest httpServletRequest, String organizationType) {

		return null;
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

}