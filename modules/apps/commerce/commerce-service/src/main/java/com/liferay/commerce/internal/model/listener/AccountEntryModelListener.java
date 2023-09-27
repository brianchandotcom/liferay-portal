/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.exception.CommerceAccountOrdersException;
import com.liferay.commerce.internal.search.CommerceOrderBatchReindexer;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(service = ModelListener.class)
public class AccountEntryModelListener extends BaseModelListener<AccountEntry> {

	@Override
	public void onAfterUpdate(
			AccountEntry originalAccountEntry, AccountEntry accountEntry)
		throws ModelListenerException {

		if (StringUtil.equals(
				originalAccountEntry.getName(), accountEntry.getName())) {

			return;
		}

		commerceOrderBatchReindexer.reindex(
			accountEntry.getAccountEntryId(), accountEntry.getCompanyId());
	}

	@Override
	public void onBeforeRemove(AccountEntry accountEntry) {
		int accountOrders =
			_commerceOrderLocalService.
				getCommerceOrdersCountByCommerceAccountId(
					accountEntry.getAccountEntryId());

		if (accountOrders > 0) {
			throw new CommerceAccountOrdersException();
		}

		_commerceShippingOptionAccountEntryRelLocalService.
			deleteCommerceShippingOptionAccountEntryRelsByAccountEntryId(
				accountEntry.getAccountEntryId());
	}

	@Reference
	protected CommerceOrderBatchReindexer commerceOrderBatchReindexer;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceShippingOptionAccountEntryRelLocalService
		_commerceShippingOptionAccountEntryRelLocalService;

}