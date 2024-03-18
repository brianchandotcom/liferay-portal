/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.data.set.filter;

import com.liferay.commerce.order.web.internal.constants.CommerceReturnFDSNames;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "frontend.data.set.name=" + CommerceReturnFDSNames.RETURNS,
	service = FDSFilter.class
)
public class CommerceReturnStatusSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "returnStatus";
	}

	@Override
	public String getLabel() {
		return "return-status";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"L_COMMERCE_RETURN_STATUSES",
					CompanyThreadLocal.getCompanyId());

		if (listTypeDefinition == null) {
			return selectionFDSFilterItems;
		}

		for (ListTypeEntry listTypeEntry :
				_listTypeEntryLocalService.getListTypeEntries(
					listTypeDefinition.getListTypeDefinitionId())) {

			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					listTypeEntry.getName(locale), listTypeEntry.getKey()));
		}

		return selectionFDSFilterItems;
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

}