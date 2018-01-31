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

package com.liferay.commerce.organization.internal.util;

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionParamUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CommerceOrganizationHelperImpl
	implements CommerceOrganizationHelper {

	@Override
	public Organization getCurrentOrganization(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		long currentOrganizationId = SessionParamUtil.getLong(
			httpServletRequest, _CURRENT_ORGANIZATION_ID_KEY);

		if (currentOrganizationId <= 0) {
			return null;
		}

		return _commerceOrganizationService.getOrganization(
			currentOrganizationId);
	}

	@Override
	public void setCurrentOrganization(
		HttpServletRequest httpServletRequest, long organizationId) {

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		httpSession.setAttribute(_CURRENT_ORGANIZATION_ID_KEY, organizationId);
	}

	private static final String _CURRENT_ORGANIZATION_ID_KEY =
		"LIFERAY_SHARED_CURRENT_ORGANIZATION_ID";

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private Portal _portal;

}