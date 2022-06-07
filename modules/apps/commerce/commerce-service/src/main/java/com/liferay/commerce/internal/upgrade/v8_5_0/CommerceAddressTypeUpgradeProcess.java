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

package com.liferay.commerce.internal.upgrade.v8_5_0;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Pei-Jung Lan
 */
public class CommerceAddressTypeUpgradeProcess extends UpgradeProcess {

	public CommerceAddressTypeUpgradeProcess(
		ListTypeLocalService listTypeLocalService) {

		_listTypeLocalService = listTypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_setAddressType(
			_getListTypeId(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING),
			14000);
		_setAddressType(
			_getListTypeId(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING),
			14002);
		_setAddressType(
			_getListTypeId(
				AccountListTypeConstants.
					ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING),
			14001);
	}

	private long _getListTypeId(String name) {
		ListType listType = _listTypeLocalService.getListType(
			name, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);

		if (listType == null) {
			listType = _listTypeLocalService.addListType(
				name, AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
		}

		return listType.getListTypeId();
	}

	private void _setAddressType(long newTypeId, long oldTypeId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update Address set typeId = ", newTypeId, " where typeId = ",
				oldTypeId));
	}

	private final ListTypeLocalService _listTypeLocalService;

}