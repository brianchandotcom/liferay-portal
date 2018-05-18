/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.display.context;

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.configuration.UserFileUploadsConfiguration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationDetailDisplayContext
	extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationDetailDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceOrganizationService commerceOrganizationService,
		HttpServletRequest httpServletRequest,
		OrganizationLocalService organizationLocalService, Portal portal,
		UserFileUploadsConfiguration userFileUploadsConfiguration) {

		super(
			commerceOrganizationHelper, commerceOrganizationService,
			httpServletRequest, portal);

		_organizationLocalService = organizationLocalService;
		_userFileUploadsConfiguration = userFileUploadsConfiguration;
	}

	public Address getOrganizationPrimaryAddress() throws PortalException {
		Organization organization = getCurrentOrganization();

		return commerceOrganizationService.getOrganizationPrimaryAddress(
			organization.getOrganizationId());
	}

	public EmailAddress getOrganizationPrimaryEmailAddress()
		throws PortalException {

		Organization organization = getCurrentOrganization();

		return commerceOrganizationService.getOrganizationPrimaryEmailAddress(
			organization.getOrganizationId());
	}

	public String[] getOrganizationTypes() {
		return _organizationLocalService.getTypes();
	}

	public UserFileUploadsConfiguration getUserFileUploadsConfiguration() {
		return _userFileUploadsConfiguration;
	}

	private final OrganizationLocalService _organizationLocalService;
	private final UserFileUploadsConfiguration _userFileUploadsConfiguration;

}