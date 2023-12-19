/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.name=" + CommercePaymentsFDSNames.PAYMENTS,
	service = FDSFilter.class
)
public class CommercePaymentStatusSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.COLLECTION;
	}

	@Override
	public String getId() {
		return "type";
	}

	@Override
	public String getLabel() {
		return "type";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return TransformUtil.transformToList(
			CommercePaymentEntryConstants.TYPES,
			type -> new SelectionFDSFilterItem(
				CommercePaymentEntryConstants.getTypeLabel(type), type));
	}

}