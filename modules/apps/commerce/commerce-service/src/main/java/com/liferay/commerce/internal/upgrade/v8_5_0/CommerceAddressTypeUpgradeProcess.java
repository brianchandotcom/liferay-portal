/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		_setAddressListType(
			_getListTypeId(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING),
			14000);
		_setAddressListType(
			_getListTypeId(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING),
			14002);
		_setAddressListType(
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

	private void _setAddressListType(long newListTypeId, long oldListTypeId)
		throws Exception {

		runSQL(
			StringBundler.concat(
				"update Address set listTypeId = ", newListTypeId,
				" where listTypeId = ", oldListTypeId));
	}

	private final ListTypeLocalService _listTypeLocalService;

}