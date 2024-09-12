/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.web.internal.frontend.data.set.filter;

import com.liferay.commerce.constants.CommercePaymentEntryConstants;
import com.liferay.commerce.payment.web.internal.constants.CommercePaymentsFDSNames;
import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "frontend.data.set.name=" + CommercePaymentsFDSNames.REFUNDS,
	service = FDSFilter.class
)
public class CommerceRefundStatusSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.COLLECTION;
	}

	@Override
	public String getId() {
		return "paymentStatus";
	}

	@Override
	public String getLabel() {
		return "refund-status";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return TransformUtil.transformToList(
			CommercePaymentEntryConstants.STATUSES_REFUND,
			paymentStatus -> new SelectionFDSFilterItem(
				CommercePaymentEntryConstants.getPaymentStatusLabel(
					paymentStatus),
				paymentStatus));
	}

}