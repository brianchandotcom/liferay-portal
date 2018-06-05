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

package com.liferay.external.reference.service.impl;

import com.liferay.external.reference.service.base.ExternalReferenceOrganizationLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

/**
 * @author Dylan Rebelak
 */
public class ExternalReferenceOrganizationLocalServiceImpl
	extends ExternalReferenceOrganizationLocalServiceBaseImpl {

	/**
	 * Add or update an organization.
	 *
	 * @param      userId the primary key of the user
	 * @param      parentOrganizationId the primary key of organization's parent
	 *             organization
	 * @param      name the organization's name
	 * @param      type the organization's type
	 * @param      regionId the primary key of the organization's region
	 * @param      countryId the primary key of the organization's country
	 * @param      statusId the organization's workflow status
	 * @param      comments the comments about the organization
	 * @param      site whether the organization is to be associated with a main
	 *             site
	 * @param      logo whether to update the ogranization's logo
	 * @param      logoBytes the new logo image data
	 * @param      externalReferenceCode the organization's external reference
	 *             code
	 * @param      serviceContext the service context to be applied (optionally
	 *             <code>null</code>). Can set asset category IDs and asset tag
	 *             names for the organization, and merge expando bridge
	 *             attributes for the organization.
	 *
	 * @review
	 * @return     the organization
	 */
	@Override
	public Organization upsertOrganization(
			long userId, long parentOrganizationId, String name, String type,
			long regionId, long countryId, long statusId, String comments,
			boolean site, boolean logo, byte[] logoBytes,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		Organization organization =
			organizationLocalService.fetchOrganizationByReferenceCode(
				user.getCompanyId(), externalReferenceCode);

		if (Validator.isNull(organization)) {
			organization = organizationLocalService.addOrganization(
				userId, parentOrganizationId, name, type, regionId, countryId,
				statusId, comments, site, serviceContext);

			organization.setExternalReferenceCode(externalReferenceCode);

			PortalUtil.updateImageId(
				organization, logo, logoBytes, "logoId",
				_userFileUploadsSettings.getImageMaxSize(),
				_userFileUploadsSettings.getImageMaxHeight(),
				_userFileUploadsSettings.getImageMaxWidth());

			organization = organizationLocalService.updateOrganization(
				organization);
		}
		else {
			organizationLocalService.updateOrganization(
				user.getCompanyId(), organization.getOrganizationId(),
				parentOrganizationId, name, type, regionId, countryId, statusId,
				comments, logo, logoBytes, site, serviceContext);
		}

		return organization;
	}

	@ServiceReference
	private UserFileUploadsSettings _userFileUploadsSettings;

}