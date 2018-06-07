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

package com.liferay.commerce.organization.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceOrganization. This utility wraps
 * {@link com.liferay.commerce.organization.service.impl.CommerceOrganizationServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceOrganizationService
 * @see com.liferay.commerce.organization.service.base.CommerceOrganizationServiceBaseImpl
 * @see com.liferay.commerce.organization.service.impl.CommerceOrganizationServiceImpl
 * @generated
 */
@ProviderType
public class CommerceOrganizationServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.organization.service.impl.CommerceOrganizationServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.Organization addOrganization(
		long parentOrganizationId, String name, String type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addOrganization(parentOrganizationId, name, type,
			serviceContext);
	}

	public static void addOrganizationUsers(long organizationId,
		String[] emailAddresses,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.addOrganizationUsers(organizationId, emailAddresses, serviceContext);
	}

	public static com.liferay.portal.kernel.model.Organization fetchOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchOrganization(organizationId);
	}

	public static com.liferay.portal.kernel.model.Organization getOrganization(
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getOrganization(organizationId);
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
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizations(
		long userId, long organizationId, String type, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort[] sorts)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchOrganizations(userId, organizationId, type, keywords,
			start, end, sorts);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.kernel.model.Organization> searchOrganizationsByGroup(
		long groupId, long userId, String type, String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort[] sorts)
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
		long organizationId, String name, long emailAddressId, String address,
		long addressId, String street1, String street2, String street3,
		String city, String zip, long regionId, long countryId, boolean logo,
		byte[] logoBytes,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateOrganization(organizationId, name, emailAddressId,
			address, addressId, street1, street2, street3, city, zip, regionId,
			countryId, logo, logoBytes, serviceContext);
	}

	public static CommerceOrganizationService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceOrganizationService, CommerceOrganizationService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceOrganizationService.class);

		ServiceTracker<CommerceOrganizationService, CommerceOrganizationService> serviceTracker =
			new ServiceTracker<CommerceOrganizationService, CommerceOrganizationService>(bundle.getBundleContext(),
				CommerceOrganizationService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}