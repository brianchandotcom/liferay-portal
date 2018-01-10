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

package com.liferay.commerce.user.web.internal.display.context;

import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationDetailDisplayContext extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationDetailDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceUserService commerceUserService,
			OrganizationService organizationService,
			UserFileUploadsConfiguration userFileUploadsConfiguration) {

		super(httpServletRequest, organizationService);

		this.commerceUserService = commerceUserService;
		this.userFileUploadsConfiguration = userFileUploadsConfiguration;
	}

	public Address getOrganizationPrimaryAddress() throws PortalException{
		Organization organization = getSiteOrganization();

		return commerceUserService.getOrganizationPrimaryAddress(
			organization.getOrganizationId());
	}

	public EmailAddress getOrganizationPrimaryEmailAddress() throws PortalException{
		Organization organization = getSiteOrganization();

		return commerceUserService.getOrganizationPrimaryEmailAddress(
				organization.getOrganizationId());
	}

	public UserFileUploadsConfiguration getUserFileUploadsConfiguration(){
		return userFileUploadsConfiguration;
	}

	protected final UserFileUploadsConfiguration userFileUploadsConfiguration;
	protected final CommerceUserService commerceUserService;
}