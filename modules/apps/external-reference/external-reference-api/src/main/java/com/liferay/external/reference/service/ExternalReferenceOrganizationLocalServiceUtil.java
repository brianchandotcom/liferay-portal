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

package com.liferay.external.reference.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ExternalReferenceOrganization. This utility wraps
 * {@link com.liferay.external.reference.service.impl.ExternalReferenceOrganizationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceOrganizationLocalService
 * @see com.liferay.external.reference.service.base.ExternalReferenceOrganizationLocalServiceBaseImpl
 * @see com.liferay.external.reference.service.impl.ExternalReferenceOrganizationLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExternalReferenceOrganizationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.external.reference.service.impl.ExternalReferenceOrganizationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Add or update an organization.
	*
	* @param userId the primary key of the user
	* @param parentOrganizationId the primary key of organization's parent
	organization
	* @param name the organization's name
	* @param type the organization's type
	* @param regionId the primary key of the organization's region
	* @param countryId the primary key of the organization's country
	* @param statusId the organization's workflow status
	* @param comments the comments about the organization
	* @param site whether the organization is to be associated with a main
	site
	* @param logo whether to update the ogranization's logo
	* @param logoBytes the new logo image data
	* @param externalReferenceCode the organization's external reference
	code
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set asset category IDs and asset tag
	names for the organization, and merge expando bridge
	attributes for the organization.
	* @review
	* @return the organization
	*/
	public static com.liferay.portal.kernel.model.Organization upsertOrganization(
		long userId, long parentOrganizationId, String name, String type,
		long regionId, long countryId, long statusId, String comments,
		boolean site, boolean logo, byte[] logoBytes,
		String externalReferenceCode,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .upsertOrganization(userId, parentOrganizationId, name,
			type, regionId, countryId, statusId, comments, site, logo,
			logoBytes, externalReferenceCode, serviceContext);
	}

	public static ExternalReferenceOrganizationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ExternalReferenceOrganizationLocalService, ExternalReferenceOrganizationLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ExternalReferenceOrganizationLocalService.class);

		ServiceTracker<ExternalReferenceOrganizationLocalService, ExternalReferenceOrganizationLocalService> serviceTracker =
			new ServiceTracker<ExternalReferenceOrganizationLocalService, ExternalReferenceOrganizationLocalService>(bundle.getBundleContext(),
				ExternalReferenceOrganizationLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}