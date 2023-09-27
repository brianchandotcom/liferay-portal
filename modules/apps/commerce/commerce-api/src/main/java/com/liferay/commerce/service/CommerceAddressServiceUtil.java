/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceAddress;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CommerceAddress. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceAddressServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressService
 * @generated
 */
public class CommerceAddressServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceAddressServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
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

	public static void deleteCommerceAddress(long commerceAddressId)
		throws PortalException {

		getService().deleteCommerceAddress(commerceAddressId);
	}

	public static CommerceAddress fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceAddress fetchCommerceAddress(long commerceAddressId)
		throws PortalException {

		return getService().fetchCommerceAddress(commerceAddressId);
	}

	public static List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		return getService().getBillingCommerceAddresses(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getBillingCommerceAddresses(
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		return getService().getBillingCommerceAddresses(
			channelId, className, classPK, start, end);
	}

	public static List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getBillingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	public static List<CommerceAddress> getBillingCommerceAddressesCount(
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		return getService().getBillingCommerceAddressesCount(
			channelId, className, classPK, start, end);
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
			long groupId, String className, long classPK)
		throws PortalException {

		return getService().getCommerceAddresses(groupId, className, classPK);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static List<CommerceAddress> getCommerceAddresses(
			long groupId, String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		return getService().getCommerceAddresses(
			groupId, className, classPK, start, end, orderByComparator);
	}

	public static List<CommerceAddress> getCommerceAddresses(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		return getService().getCommerceAddresses(
			className, classPK, start, end, orderByComparator);
	}

	public static List<CommerceAddress> getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK)
		throws PortalException {

		return getService().getCommerceAddressesByCompanyId(
			companyId, className, classPK);
	}

	public static List<CommerceAddress> getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		return getService().getCommerceAddressesByCompanyId(
			companyId, className, classPK, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	public static int getCommerceAddressesCount(
			long groupId, String className, long classPK)
		throws PortalException {

		return getService().getCommerceAddressesCount(
			groupId, className, classPK);
	}

	public static int getCommerceAddressesCount(String className, long classPK)
		throws PortalException {

		return getService().getCommerceAddressesCount(className, classPK);
	}

	public static int getCommerceAddressesCountByCompanyId(
			long companyId, String className, long classPK)
		throws PortalException {

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
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		return getService().getShippingCommerceAddresses(
			channelId, className, classPK, start, end);
	}

	public static List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().getShippingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	public static List<CommerceAddress> getShippingCommerceAddressesCount(
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		return getService().getShippingCommerceAddressesCount(
			channelId, className, classPK, start, end);
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

	public static CommerceAddressService getService() {
		return _service;
	}

	public static void setService(CommerceAddressService service) {
		_service = service;
	}

	private static volatile CommerceAddressService _service;

}