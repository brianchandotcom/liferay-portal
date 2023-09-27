/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.avalara.connector;

import java.util.List;
import java.util.Map;

import net.avalara.avatax.rest.client.models.TaxCodeModel;

/**
 * @author Riccardo Alberti
 */
public interface CommerceAvalaraConnector {

	public Map<String, String> getCompanyCodes() throws Exception;

	public List<TaxCodeModel> getTaxCodeModels() throws Exception;

	public String getTaxRateByZipCode() throws Exception;

	public void verifyConnection(
			String accountNumber, String licenseKey, String serviceURL)
		throws Exception;

}