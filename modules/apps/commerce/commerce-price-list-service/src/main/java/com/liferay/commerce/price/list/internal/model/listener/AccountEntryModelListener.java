/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.model.listener;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRelTable;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lianne Louie
 */
@Component(service = ModelListener.class)
public class AccountEntryModelListener extends BaseModelListener<AccountEntry> {

	@Override
	public void onBeforeRemove(AccountEntry accountEntry) {
		List<Long> commercePriceListAccountRelIds =
			_commercePriceListAccountRelLocalService.dslQuery(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceListAccountRelTable.INSTANCE.
						commercePriceListAccountRelId
				).from(
					CommercePriceListAccountRelTable.INSTANCE
				).where(
					CommercePriceListAccountRelTable.INSTANCE.commerceAccountId.
						eq(accountEntry.getAccountEntryId())
				));

		for (Long commercePriceListAccountRelId :
				commercePriceListAccountRelIds) {

			try {
				_commercePriceListAccountRelLocalService.
					deleteCommercePriceListAccountRel(
						commercePriceListAccountRelId);
			}
			catch (PortalException portalException) {
				throw new ModelListenerException(portalException);
			}
		}
	}

	@Reference
	private CommercePriceListAccountRelLocalService
		_commercePriceListAccountRelLocalService;

}