/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.web.internal.frontend.data.set.filter;

import com.liferay.commerce.order.web.internal.constants.CommerceReturnFDSNames;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Stefano Motta
 */
@Component(
	property = "frontend.data.set.name=" + CommerceReturnFDSNames.RETURNS,
	service = FDSFilter.class
)
public class CommerceReturnAccountSelectionFDSFilter
	extends BaseSelectionFDSFilter {

	@Override
	public String getAPIURL() {
		return "/o/headless-commerce-admin-account/v1.0/accounts?sort=name:asc";
	}

	@Override
	public String getId() {
		return "r_accountToCommerceReturns_accountEntryERC";
	}

	@Override
	public String getItemKey() {
		return "externalReferenceCode";
	}

	@Override
	public String getItemLabel() {
		return "name";
	}

	@Override
	public String getLabel() {
		return "account";
	}

	@Override
	public boolean isAutocompleteEnabled() {
		return true;
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

}