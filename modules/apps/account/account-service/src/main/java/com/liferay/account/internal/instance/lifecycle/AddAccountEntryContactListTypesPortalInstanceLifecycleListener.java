/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.instance.lifecycle;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddAccountEntryContactListTypesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		for (Map.Entry<String, String[]> entry :
				_accountEntryContactListTypes.entrySet()) {

			for (String value : entry.getValue()) {
				if (!_hasListType(
						company.getCompanyId(), value, entry.getKey())) {

					_listTypeLocalService.addListType(
						company.getCompanyId(), value, entry.getKey());
				}
			}
		}
	}

	private boolean _hasListType(long companyId, String name, String type) {
		ListType listType = _listTypeLocalService.getListType(
			companyId, name, type);

		if (listType != null) {
			return true;
		}

		return false;
	}

	private final Map<String, String[]> _accountEntryContactListTypes =
		HashMapBuilder.put(
			AccountListTypeConstants.ACCOUNT_ENTRY_CONTACT_ADDRESS,
			new String[] {
				AccountListTypeConstants.
					ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_BILLING,
				AccountListTypeConstants.
					ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_OTHER,
				AccountListTypeConstants.
					ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_P_O_BOX,
				AccountListTypeConstants.
					ACCOUNT_ENTRY_CONTACT_ADDRESS_TYPE_SHIPPING
			}
		).put(
			AccountListTypeConstants.ACCOUNT_ENTRY_EMAIL_ADDRESS,
			new String[] {
				AccountListTypeConstants.
					ACCOUNT_ENTRY_EMAIL_ADDRESS_TYPE_EMAIL_ADDRESS,
				AccountListTypeConstants.
					ACCOUNT_ENTRY_EMAIL_ADDRESS_TYPE_EMAIL_ADDRESS_2,
				AccountListTypeConstants.
					ACCOUNT_ENTRY_EMAIL_ADDRESS_TYPE_EMAIL_ADDRESS_3
			}
		).put(
			AccountListTypeConstants.ACCOUNT_ENTRY_PHONE,
			new String[] {
				AccountListTypeConstants.ACCOUNT_ENTRY_PHONE_TYPE_FAX,
				AccountListTypeConstants.ACCOUNT_ENTRY_PHONE_TYPE_LOCAL,
				AccountListTypeConstants.ACCOUNT_ENTRY_PHONE_TYPE_OTHER,
				AccountListTypeConstants.ACCOUNT_ENTRY_PHONE_TYPE_TOOL_FREE,
				AccountListTypeConstants.ACCOUNT_ENTRY_PHONE_TYPE_TTY
			}
		).put(
			AccountListTypeConstants.ACCOUNT_ENTRY_WEBSITE,
			new String[] {
				AccountListTypeConstants.ACCOUNT_ENTRY_WEBSITE_TYPE_INTRANET,
				AccountListTypeConstants.ACCOUNT_ENTRY_WEBSITE_TYPE_PUBLIC
			}
		).build();

	@Reference
	private ListTypeLocalService _listTypeLocalService;

}