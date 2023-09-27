/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v7_0_0;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.PhoneLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class CommerceAddressUpgradeProcess extends UpgradeProcess {

	public CommerceAddressUpgradeProcess(
		AddressLocalService addressLocalService,
		AccountEntryLocalService accountEntryLocalService,
		ListTypeLocalService listTypeLocalService,
		PhoneLocalService phoneLocalService) {

		_addressLocalService = addressLocalService;
		_accountEntryLocalService = accountEntryLocalService;
		_listTypeLocalService = listTypeLocalService;
		_phoneLocalService = phoneLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement selectStatement = connection.createStatement()) {
			ResultSet resultSet = selectStatement.executeQuery(
				"select * from CommerceAddress order by commerceAddressId");

			while (resultSet.next()) {
				Address address = _addressLocalService.createAddress(
					resultSet.getLong("commerceAddressId"));

				address.setExternalReferenceCode(
					resultSet.getString("externalReferenceCode"));
				address.setCompanyId(resultSet.getLong("companyId"));
				address.setUserId(resultSet.getLong("userId"));
				address.setUserName(resultSet.getString("userName"));
				address.setCreateDate(resultSet.getTimestamp("createDate"));
				address.setModifiedDate(resultSet.getTimestamp("modifiedDate"));
				address.setClassNameId(resultSet.getLong("classNameId"));
				address.setClassPK(resultSet.getLong("classPK"));
				address.setCountryId(resultSet.getLong("countryId"));
				address.setListTypeId(
					_getListTypeId(resultSet.getInt("type_")));
				address.setRegionId(resultSet.getLong("regionId"));
				address.setCity(resultSet.getString("city"));
				address.setDescription(resultSet.getString("description"));
				address.setLatitude(resultSet.getDouble("latitude"));
				address.setLongitude(resultSet.getDouble("longitude"));
				address.setName(resultSet.getString("name"));
				address.setStreet1(resultSet.getString("street1"));
				address.setStreet2(resultSet.getString("street2"));
				address.setStreet3(resultSet.getString("street3"));
				address.setZip(resultSet.getString("zip"));

				address = _addressLocalService.addAddress(address);

				_setPhoneNumber(address, resultSet.getString("phoneNumber"));
				_setDefaultBilling(
					address, resultSet.getBoolean("defaultBilling"));
				_setDefaultShipping(
					address, resultSet.getBoolean("defaultShipping"));
			}
		}
	}

	@Override
	protected UpgradeStep[] getPostUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.dropTables("CommerceAddress")
		};
	}

	private long _getListTypeId(int commerceAddressType) {
		String name = null;

		if (CommerceAddressConstants.ADDRESS_TYPE_BILLING ==
				commerceAddressType) {

			name = AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING;
		}
		else if (CommerceAddressConstants.ADDRESS_TYPE_SHIPPING ==
					commerceAddressType) {

			name = AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING;
		}
		else {
			name =
				AccountListTypeConstants.
					ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING;
		}

		ListType listType = _listTypeLocalService.getListType(
			name, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);

		if (listType == null) {
			listType = _listTypeLocalService.addListType(
				name, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
		}

		return listType.getListTypeId();
	}

	private void _setDefaultBilling(Address address, boolean defaultBilling) {
		String className = address.getClassName();

		if (defaultBilling &&
			(Objects.equals(AccountEntry.class.getName(), className) ||
			 Objects.equals(
				 className,
				 "com.liferay.commerce.account.model.CommerceAccount"))) {

			try {
				_accountEntryLocalService.updateDefaultBillingAddressId(
					address.getClassPK(), address.getAddressId());
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}
	}

	private void _setDefaultShipping(Address address, boolean defaultShipping) {
		String className = address.getClassName();

		if (defaultShipping &&
			(Objects.equals(AccountEntry.class.getName(), className) ||
			 Objects.equals(
				 className,
				 "com.liferay.commerce.account.model.CommerceAccount"))) {

			try {
				_accountEntryLocalService.updateDefaultShippingAddressId(
					address.getClassPK(), address.getAddressId());
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}
	}

	private void _setPhoneNumber(Address address, String phoneNumber) {
		if (phoneNumber == null) {
			return;
		}

		ListType listType = _listTypeLocalService.getListType(
			"phone-number", ListTypeConstants.ADDRESS_PHONE);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(address.getUserId());

		try {
			_phoneLocalService.addPhone(
				serviceContext.getUserId(), Address.class.getName(),
				address.getAddressId(), phoneNumber, null,
				listType.getListTypeId(), false, serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAddressUpgradeProcess.class);

	private final AccountEntryLocalService _accountEntryLocalService;
	private final AddressLocalService _addressLocalService;
	private final ListTypeLocalService _listTypeLocalService;
	private final PhoneLocalService _phoneLocalService;

}