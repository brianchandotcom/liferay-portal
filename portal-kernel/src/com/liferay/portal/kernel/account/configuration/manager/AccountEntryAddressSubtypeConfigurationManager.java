/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.account.configuration.manager;

/**
 * @author Matyas Wollner
 */
public interface AccountEntryAddressSubtypeConfigurationManager {

	public String
		getBillingAddressSubtypeListTypeDefinitionExternalReferenceCode(
			long companyId);

	public String
		getBillingAndShippingAddressSubtypeListTypeDefinitionExternalReferenceCode(
			long companyId);

	public String
		getShippingAddressSubtypeListTypeDefinitionExternalReferenceCode(
			long companyId);

}