/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.frontend.data.set.filter;

import com.liferay.commerce.payment.entry.CommercePaymentEntryRefundType;
import com.liferay.commerce.payment.entry.CommercePaymentEntryRefundTypeRegistry;
import com.liferay.commerce.payment.web.internal.constants.CommercePaymentsFDSNames;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "frontend.data.set.name=" + CommercePaymentsFDSNames.REFUNDS,
	service = FDSFilter.class
)
public class CommerceRefundReasonSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.COLLECTION;
	}

	@Override
	public String getId() {
		return "reasonKey";
	}

	@Override
	public String getLabel() {
		return "refund-reason";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		for (CommercePaymentEntryRefundType commercePaymentEntryRefundType :
				_commercePaymentEntryRefundTypeRegistry.
					getCommercePaymentEntryRefundTypes(
						CompanyThreadLocal.getCompanyId())) {

			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					commercePaymentEntryRefundType.getName(locale),
					commercePaymentEntryRefundType.getKey()));
		}

		return selectionFDSFilterItems;
	}

	@Reference
	private CommercePaymentEntryRefundTypeRegistry
		_commercePaymentEntryRefundTypeRegistry;

}