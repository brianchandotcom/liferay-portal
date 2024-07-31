/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;

import java.util.List;

/**
 * Provides the remote service utility for Address. This utility wraps
 * <code>com.liferay.portal.service.impl.AddressServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AddressService
 * @generated
 */
public class AddressServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.AddressServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Address addAddress(
			String externalReferenceCode, String className, long classPK,
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, long listTypeId, boolean mailing, boolean primary,
			String phoneNumber, ServiceContext serviceContext)
		throws PortalException {

		return getService().addAddress(
			externalReferenceCode, className, classPK, name, description,
			street1, street2, street3, city, zip, regionId, countryId,
			listTypeId, mailing, primary, phoneNumber, serviceContext);
	}

	public static void deleteAddress(long addressId) throws PortalException {
		getService().deleteAddress(addressId);
	}

	public static Address getAddress(long addressId) throws PortalException {
		return getService().getAddress(addressId);
	}

	public static List<Address> getAddresses(String className, long classPK)
		throws PortalException {

		return getService().getAddresses(className, classPK);
	}

	public static List<Address> getListTypeAddresses(
			String className, long classPK, long[] listTypeIds)
		throws PortalException {

		return getService().getListTypeAddresses(
			className, classPK, listTypeIds);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static Address updateAddress(
			long addressId, String name, String description, String street1,
			String street2, String street3, String city, String zip,
			long regionId, long countryId, long listTypeId, boolean mailing,
			boolean primary, String phoneNumber)
		throws PortalException {

		return getService().updateAddress(
			addressId, name, description, street1, street2, street3, city, zip,
			regionId, countryId, listTypeId, mailing, primary, phoneNumber);
	}

	public static Address updateExternalReferenceCode(
			Address address, String externalReferenceCode)
		throws PortalException {

		return getService().updateExternalReferenceCode(
			address, externalReferenceCode);
	}

	public static Address updateExternalReferenceCode(
			long addressId, String externalReferenceCode)
		throws PortalException {

		return getService().updateExternalReferenceCode(
			addressId, externalReferenceCode);
	}

	public static AddressService getService() {
		return _service;
	}

	public static void setService(AddressService service) {
		_service = service;
	}

	private static volatile AddressService _service;

}