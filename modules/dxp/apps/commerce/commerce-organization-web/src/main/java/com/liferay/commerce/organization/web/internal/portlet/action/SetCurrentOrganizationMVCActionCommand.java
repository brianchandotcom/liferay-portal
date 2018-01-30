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

package com.liferay.commerce.organization.web.internal.portlet.action;

import com.liferay.commerce.organization.web.internal.constants.CommerceOrganizationPortletKeys;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceOrganizationPortletKeys.COMMERCE_SEARCH_ORGANIZATION_ADMIN,
		"mvc.command.name=setCurrentOrganization"
	},
	service = MVCActionCommand.class
)
public class SetCurrentOrganizationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		httpServletRequest = _portal.getOriginalServletRequest(
			httpServletRequest);

		HttpSession httpSession = httpServletRequest.getSession();

		long currentOrganizationId = ParamUtil.getLong(
			actionRequest, "currentOrganizationId");

		httpSession.setAttribute(
			"LIFERAY_SHARED_currentOrganizationId", currentOrganizationId);
	}

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private Portal _portal;

}