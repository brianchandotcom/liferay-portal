/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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