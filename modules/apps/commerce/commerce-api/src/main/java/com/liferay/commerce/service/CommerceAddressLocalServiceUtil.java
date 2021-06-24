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

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the local service utility for CommerceAddress. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceAddressLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressLocalService
 * @generated
 */
public class CommerceAddressLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceAddressLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	public static CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	public static CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	public static CommerceAddress addCommerceAddress(
			String externalReferenceCode, String className, long classPK,
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, String phoneNumber, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceAddress(
			externalReferenceCode, className, classPK, name, description,
			street1, street2, street3, city, zip, regionId, countryId,
			phoneNumber, type, serviceContext);
	}

	public static CommerceAddress copyCommerceAddress(
			long commerceAddressId, String className, long classPK,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyCommerceAddress(
			commerceAddressId, className, classPK, serviceContext);
	}

	public static CommerceAddress createCommerceAddress(
		long commerceAddressId) {

		return getService().createCommerceAddress(commerceAddressId);
	}

	public static CommerceAddress deleteCommerceAddress(
			CommerceAddress commerceAddress)
		throws PortalException {

		return getService().deleteCommerceAddress(commerceAddress);
	}

	public static CommerceAddress deleteCommerceAddress(long commerceAddressId)
		throws PortalException {

		return getService().deleteCommerceAddress(commerceAddressId);
	}

	public static void deleteCommerceAddresses(String className, long classPK)
		throws PortalException {

		getService().deleteCommerceAddresses(className, classPK);
	}

	public static void deleteCountryCommerceAddresses(long countryId)
		throws PortalException {

		getService().deleteCountryCommerceAddresses(countryId);
	}

	public static void deleteRegionCommerceAddresses(long regionId)
		throws PortalException {

		getService().deleteRegionCommerceAddresses(regionId);
	}

	public static CommerceAddress fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceAddress fetchCommerceAddress(long commerceAddressId) {
		return getService().fetchCommerceAddress(commerceAddressId);
	}

	public static CommerceAddress geolocateCommerceAddress(
			long commerceAddressId)
		throws PortalException {

		return getService().geolocateCommerceAddress(commerceAddressId);
	}

	public static List<CommerceAddress> getBillingAndShippingCommerceAddresses(
		long companyId, String className, long classPK) {

		return getService().getBillingAndShippingCommerceAddresses(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		return getService().getBillingCommerceAddresses(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getBillingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	public static int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		return getService().getBillingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	public static CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		return getService().getCommerceAddress(commerceAddressId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static List<CommerceAddress> getCommerceAddresses(
		long groupId, String className, long classPK) {

		return getService().getCommerceAddresses(groupId, className, classPK);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static List<CommerceAddress> getCommerceAddresses(
		long groupId, String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return getService().getCommerceAddresses(
			groupId, className, classPK, start, end, orderByComparator);
	}

	public static List<CommerceAddress> getCommerceAddresses(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return getService().getCommerceAddresses(
			className, classPK, start, end, orderByComparator);
	}

	public static List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK) {

		return getService().getCommerceAddressesByCompanyId(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getCommerceAddressesByCompanyId(
		long companyId, String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {

		return getService().getCommerceAddressesByCompanyId(
			companyId, className, classPK, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static int getCommerceAddressesCount(
		long groupId, String className, long classPK) {

		return getService().getCommerceAddressesCount(
			groupId, className, classPK);
	}

	public static int getCommerceAddressesCount(
		String className, long classPK) {

		return getService().getCommerceAddressesCount(className, classPK);
	}

	public static int getCommerceAddressesCountByCompanyId(
		long companyId, String className, long classPK) {

		return getService().getCommerceAddressesCountByCompanyId(
			companyId, className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		return getService().getShippingCommerceAddresses(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getShippingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	public static int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		return getService().getShippingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company. Don't need to pass groupId
	 */
	@Deprecated
	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceAddress> searchCommerceAddresses(
				long companyId, long groupId, String className, long classPK,
				String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceAddresses(
			companyId, groupId, className, classPK, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceAddress> searchCommerceAddresses(
				long companyId, String className, long classPK, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	public static CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	public static CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceAddress(
			commerceAddressId, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, type, serviceContext);
	}

	public static CommerceAddressLocalService getService() {
		return _service;
	}

	private static volatile CommerceAddressLocalService _service;

}