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

import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SessionParamUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

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
	public String getCommerceUserPortletURL(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		long plid = _portal.getPlidFromPortletId(
			groupId, _COMMERCE_USER_PORTLET_ID);

		if (plid > 0) {
			PortletURL portletURL = _portletURLFactory.create(
				httpServletRequest, _COMMERCE_USER_PORTLET_ID, plid,
				PortletRequest.RENDER_PHASE);

			return portletURL.toString();
		}

		return StringPool.BLANK;
	}

	@Override
	public Organization getCurrentOrganization(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		long currentOrganizationId = SessionParamUtil.getLong(
			httpServletRequest, _CURRENT_ORGANIZATION_ID_KEY);

		if (currentOrganizationId <= 0) {
			return null;
		}

		if (!_commerceOrganizationLocalService.hasGroupOrganization(
				groupId, currentOrganizationId)) {

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

	private static final String _COMMERCE_USER_PORTLET_ID =
		"com_liferay_commerce_user_web_internal_portlet_CommerceUserPortlet";

	private static final String _CURRENT_ORGANIZATION_ID_KEY =
		"LIFERAY_SHARED_CURRENT_ORGANIZATION_ID";

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

}