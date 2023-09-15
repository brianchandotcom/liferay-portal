/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.reference.service.impl;

import com.liferay.external.reference.service.base.EROrganizationLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.users.admin.kernel.file.uploads.UserFileUploadsSettings;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.Organization",
	service = AopService.class
)
public class EROrganizationLocalServiceImpl
	extends EROrganizationLocalServiceBaseImpl {

	@Override
	public Organization addOrUpdateOrganization(
			String externalReferenceCode, long userId,
			long parentOrganizationId, String name, String type, long regionId,
			long countryId, long statusId, String comments, boolean site,
			boolean hasLogo, byte[] logoBytes, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		Organization organization =
			_organizationLocalService.fetchOrganizationByExternalReferenceCode(
				externalReferenceCode, user.getCompanyId());

		if (organization == null) {
			organization = _organizationLocalService.addOrganization(
				null, userId, parentOrganizationId, name, type, regionId,
				countryId, statusId, comments, site, serviceContext);

			organization.setExternalReferenceCode(externalReferenceCode);

			_portal.updateImageId(
				organization, hasLogo, logoBytes, "logoId",
				_userFileUploadsSettings.getImageMaxSize(),
				_userFileUploadsSettings.getImageMaxHeight(),
				_userFileUploadsSettings.getImageMaxWidth());

			organization = _organizationLocalService.updateOrganization(
				organization);
		}
		else {
			_organizationLocalService.updateOrganization(
				organization.getExternalReferenceCode(), user.getCompanyId(),
				organization.getOrganizationId(), parentOrganizationId, name,
				type, regionId, countryId, statusId, comments, hasLogo,
				logoBytes, site, serviceContext);
		}

		return organization;
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserFileUploadsSettings _userFileUploadsSettings;

	@Reference
	private UserLocalService _userLocalService;

}