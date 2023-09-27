/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.avalara.connector.engine;

import com.liferay.commerce.tax.model.CommerceTaxMethod;

/**
 * @author Riccardo Alberti
 */
public interface CommerceAvalaraConnectorEngine {

	public void addTaxCategories(long userId) throws Exception;

	public void deleteByAddressEntries(CommerceTaxMethod commerceTaxMethod);

	public void updateByAddressEntries(long groupId) throws Exception;

}