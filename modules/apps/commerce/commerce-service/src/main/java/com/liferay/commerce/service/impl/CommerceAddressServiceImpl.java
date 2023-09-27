/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.service.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.base.CommerceAddressServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceAddress"
	},
	service = AopService.class
)
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

		_checkPermission(className, classPK);

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

		_checkPermission(className, classPK);

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

		_checkPermission(commerceAddress);

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
			_checkPermission(commerceAddress);
		}

		return commerceAddress;
	}

	@Override
	public CommerceAddress fetchCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.fetchCommerceAddress(commerceAddressId);

		if (commerceAddress != null) {
			_checkPermission(commerceAddress);
		}

		return commerceAddress;
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddresses(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddresses(
			channelId, className, classPK, start, end);
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public List<CommerceAddress> getBillingCommerceAddressesCount(
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddresses(
			channelId, className, classPK, start, end);
	}

	@Override
	public int getBillingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getBillingCommerceAddressesCount(
			companyId, className, classPK, keywords);
	}

	@Override
	public CommerceAddress getCommerceAddress(long commerceAddressId)
		throws PortalException {

		CommerceAddress commerceAddress =
			commerceAddressLocalService.getCommerceAddress(commerceAddressId);

		_checkPermission(commerceAddress);

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

		_checkPermission(className, classPK);

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

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddresses(
			groupId, className, classPK, start, end, orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddresses(
			String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddresses(
			className, classPK, start, end, orderByComparator);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesByCompanyId(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getCommerceAddressesByCompanyId(
			long companyId, String className, long classPK, int start, int end,
			OrderByComparator<CommerceAddress> orderByComparator)
		throws PortalException {

		_checkPermission(className, classPK);

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

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesCount(
			groupId, className, classPK);
	}

	@Override
	public int getCommerceAddressesCount(String className, long classPK)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesCount(
			className, classPK);
	}

	@Override
	public int getCommerceAddressesCountByCompanyId(
			long companyId, String className, long classPK)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getCommerceAddressesCountByCompanyId(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getShippingCommerceAddresses(
			companyId, className, classPK);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getShippingCommerceAddresses(
			channelId, className, classPK, start, end);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getShippingCommerceAddresses(
			companyId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public List<CommerceAddress> getShippingCommerceAddressesCount(
			long channelId, String className, long classPK, int start, int end)
		throws PortalException {

		_checkPermission(className, classPK);

		return commerceAddressLocalService.getShippingCommerceAddresses(
			channelId, className, classPK, start, end);
	}

	@Override
	public int getShippingCommerceAddressesCount(
			long companyId, String className, long classPK, String keywords)
		throws PortalException {

		_checkPermission(className, classPK);

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

		_checkPermission(className, classPK);

		return commerceAddressLocalService.searchCommerceAddresses(
			companyId, groupId, className, classPK, keywords, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<CommerceAddress> searchCommerceAddresses(
			long companyId, String className, long classPK, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		_checkPermission(className, classPK);

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

		_checkPermission(commerceAddress);

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

		_checkPermission(commerceAddress);

		return commerceAddressLocalService.updateCommerceAddress(
			commerceAddress.getCommerceAddressId(), name, description, street1,
			street2, street3, city, zip, regionId, countryId, phoneNumber, type,
			serviceContext);
	}

	private void _checkPermission(CommerceAddress commerceAddress)
		throws PortalException {

		_checkPermission(
			commerceAddress.getClassName(), commerceAddress.getClassPK());
	}

	private void _checkPermission(String className, long classPK)
		throws PortalException {

		if (className.equals(CommerceOrder.class.getName())) {
			_commerceOrderService.getCommerceOrder(classPK);
		}
		else if (className.equals(AccountEntry.class.getName())) {
			if (classPK == AccountConstants.ACCOUNT_ENTRY_ID_GUEST) {
				_accountEntryLocalService.fetchAccountEntry(classPK);
			}
			else {
				_accountEntryLocalService.getAccountEntry(classPK);
			}
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

}