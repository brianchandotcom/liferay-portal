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

package com.liferay.account.internal.instance.lifecycle;

import com.liferay.account.constants.AccountListTypeConstants;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.service.ListTypeLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddAccountEntryAddressListTypesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_hasListType(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS)) {

			_listTypeLocalService.addListType(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
		}

		if (!_hasListType(
				AccountListTypeConstants.
					ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS)) {

			_listTypeLocalService.addListType(
				AccountListTypeConstants.
					ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING_AND_SHIPPING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
		}

		if (!_hasListType(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS)) {

			_listTypeLocalService.addListType(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_SHIPPING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
		}

		if (!_hasListType(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS)) {

			_listTypeLocalService.addListType(
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS_TYPE_BILLING,
				AccountListTypeConstants.ACCOUNT_ENTRY_ADDRESS);
		}
	}

	private boolean _hasListType(String name, String type) {
		ListType listType = _listTypeLocalService.getListType(name, type);

		if (listType != null) {
			return true;
		}

		return false;
	}

	@Reference
	private ListTypeLocalService _listTypeLocalService;

}