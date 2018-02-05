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

package com.liferay.commerce.organization.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CommerceOrganization. This utility wraps
 * {@link com.liferay.commerce.organization.service.impl.CommerceOrganizationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marco Leo
 * @see CommerceOrganizationLocalService
 * @see com.liferay.commerce.organization.service.base.CommerceOrganizationLocalServiceBaseImpl
 * @see com.liferay.commerce.organization.service.impl.CommerceOrganizationLocalServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrganizationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.organization.service.impl.CommerceOrganizationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Organization addOrganization(
		long parentOrganizationId, java.lang.String name,
		java.lang.String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrganization(parentOrganizationId, name, type,
			serviceContext);
	}

	public static void addOrganizationUsers(long organizationId,
		java.lang.String[] emailAddresses,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addOrganizationUsers(organizationId, emailAddresses, serviceContext);
	}

	public static com.liferay.portal.kernel.model.Organization getAccountOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAccountOrganization(organizationId);
	}

	public static com.liferay.portal.kernel.model.Address getOrganizationPrimaryAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganizationPrimaryAddress(organizationId);
	}

	public static com.liferay.portal.kernel.model.EmailAddress getOrganizationPrimaryEmailAddress(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganizationPrimaryEmailAddress(organizationId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static boolean isB2BOrganization(long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().isB2BOrganization(organizationId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		long userId, long parentOrganizationId, java.lang.String type,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchOrganizations(userId, parentOrganizationId, type,
			keywords, start, end, sorts);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizationsByGroup(
		long groupId, long userId, java.lang.String type,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchOrganizationsByGroup(groupId, userId, type, keywords,
			start, end, sorts);
	}

	public static void unsetOrganizationUsers(long organizationId,
		long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().unsetOrganizationUsers(organizationId, userIds);
	}

	public static com.liferay.portal.kernel.model.Organization updateOrganization(
		long organizationId, java.lang.String name, long emailAddressId,
		java.lang.String address, long addressId, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long regionId,
		long countryId, boolean logo, byte[] logoBytes,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOrganization(organizationId, name, emailAddressId,
			address, addressId, street1, street2, street3, city, zip, regionId,
			countryId, logo, logoBytes, serviceContext);
	}

	public static CommerceOrganizationLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrganizationLocalService, CommerceOrganizationLocalService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceOrganizationLocalService.class);
}