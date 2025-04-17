/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class PostalAddressUtil {

	public static long[] getAccountEntryContactAddressListTypeIds(
		long companyId, ListTypeLocalService listTypeLocalService) {

		return TransformUtil.transformToLongArray(
			_names,
			name -> {
				ListType listType = listTypeLocalService.getListType(
					companyId, name,
					AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS);

				return listType.getListTypeId();
			});
	}

	private static final List<String> _names = ListUtil.fromArray(
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_BILLING,
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_OTHER,
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_P_O_BOX,
		AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_SHIPPING);

}