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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.base.CommerceAddressServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressServiceImpl extends CommerceAddressServiceBaseImpl {

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	@Override
	public CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			ServiceContext serviceContext)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.addCommerceAddress(
			className, classPK, name, description, street1, street2, street3,
			city, zip, regionId, countryId, phoneNumber, defaultBilling,
			defaultShipping, serviceContext);
	}

	@Override
	public CommerceAddress addCommerceAddress(
			String className, long classPK, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type, ServiceContext serviceContext)
		throws PortalException {

		return commerceAddressService.addCommerceAddress(
			null, className, classPK, name, description, street1, street2,
			street3, city, zip, regionId, countryId, phoneNumber, type,
			serviceContext);
	}

	@Override
	public CommerceAddress addCommerceAddress(
			String externalReferenceCode, String className, long classPK,
			String name, String description, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, String phoneNumber, int type,
			ServiceContext serviceContext)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.addCommerceAddress(
			externalReferenceCode, className, classPK, name, description,
			street1, street2, street3, city, zip, regionId, countryId,
			phoneNumber, type, serviceContext);
	}

	@Override
	public void deleteCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		commerceAddressLocalService.deleteCommerceAddress(commerceAddress);
	}

	@Override
	public CommerceAddress fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commerceAddress != null) {
			checkPermission(commerceAddress);
		}

		return commerceAddress;
	}

	@Override
	public CommerceAddress fetchCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.fetchCommerceAddress(commerceAddressId);

		if (commerceAddress != null) {
			checkPermission(commerceAddress);
		}

		return commerceAddress;
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddresses(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	@Override
	public CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		return commerceAddress;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public List<CommerceAddress> getCommerceAddresses(
			long groupId, String className, long classPK)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddresses(
			groupId, className, classPK);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public List<CommerceAddress> getCommerceAddresses(
			long groupId, String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddresses(
			groupId, className, classPK, start, end, orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddresses(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesByCompanyId(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesByCompanyId(
			companyId, className, classPK, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company use *ByCompanyId
	 */
	@Deprecated
	@Override
	public int getCommerceAddressesCount(
			long groupId, String className, long classPK)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesCount(
			groupId, className, classPK);
	}

	@Override
	public int getCommerceAddressesCount(String className, long classPK)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesCount(
			className, classPK);
	}

	@Override
	public int getCommerceAddressesCountByCompanyId(
			long companyId, String className, long classPK)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesCountByCompanyId(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getShippingCommerceAddresses(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getShippingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.getShippingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), commerceAddress is scoped to Company. Don't need to pass groupId
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
			long companyId, long groupId, String className, long classPK,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.searchCommerceAddresses(
			companyId, groupId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		checkPermission(className, classPK);

		return commerceAddressLocalService.searchCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), defaultBilling/Shipping exist on Account Entity. Pass type.
	 */
	@Deprecated
	@Override
	public CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			boolean defaultBilling, boolean defaultShipping,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		return commerceAddressLocalService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(), name, description, street1,
			street2, street3, city, zip, regionId, countryId, phoneNumber,
			defaultBilling, defaultShipping, serviceContext);
	}

	@Override
	public CommerceAddress updateCommerceAddress(
			long commerceAddressId, String name, String description,
			String street1, String street2, String street3, String city,
			String zip, long regionId, long countryId, String phoneNumber,
			int type, ServiceContext serviceContext)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		checkPermission(commerceAddress);

		return commerceAddressLocalService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(), name, description, street1,
			street2, street3, city, zip, regionId, countryId, phoneNumber, type,
			serviceContext);
	}

	protected void checkPermission(CommerceAddress commerceAddress)
		throws PortalException {

		checkPermission(
			commerceAddress.getClassName(), commerceAddress.getClassPK());
	}

	protected void checkPermission(String className, long classPK)
		throws PortalException {

		if (className.equals(CommerceOrder.class.getName())) {
			commerceOrderService.getCommerceOrder(classPK);
		}
		else if (className.equals(CommerceAccount.class.getName())) {
			_commerceAccountService.getCommerceAccount(classPK);
		}
	}

	@ServiceReference(type = CommerceAccountService.class)
	private CommerceAccountService _commerceAccountService;

}