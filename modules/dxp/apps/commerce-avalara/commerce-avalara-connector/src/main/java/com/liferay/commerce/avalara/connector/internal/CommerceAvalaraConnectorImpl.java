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

package com.liferay.commerce.avalara.connector.internal;

import com.liferay.commerce.avalara.connector.CommerceAvalaraConnector;
import com.liferay.commerce.avalara.connector.constants.CommerceAvalaraConstants;
import com.liferay.commerce.avalara.connector.exception.CommerceAvalaraConnectionException;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;

import net.avalara.avatax.rest.client.AvaTaxClient;
import net.avalara.avatax.rest.client.models.PingResultModel;

import org.osgi.service.component.annotations.Component;

/**
 * @author Calvin Keum
 */
@Component(
	enabled = false, immediate = true, service = CommerceAvalaraConnector.class
)
public class CommerceAvalaraConnectorImpl implements CommerceAvalaraConnector {

	@Override
	public void verifyConnection(
			String accountNumber, String licenseKey, String serviceUrl)
		throws Exception {

		try {
			AvaTaxClient avaTaxClient = _getAvaTaxClient(
				accountNumber, licenseKey, serviceUrl);

			PingResultModel pingResultModel = avaTaxClient.ping();

			if (!pingResultModel.getAuthenticated()) {
				throw new CommerceAvalaraConnectionException();
			}
		}
		catch (Exception exception) {
			throw new CommerceAvalaraConnectionException(exception.getCause());
		}
	}

	private AvaTaxClient _getAvaTaxClient(
		String accountNumber, String licenseKey, String serviceUrl) {

		AvaTaxClient avaTaxClient = new AvaTaxClient(
			CommerceAvalaraConstants.APP_MACHINE,
			CommerceAvalaraConstants.APP_VERSION,
			CommerceAvalaraConstants.MACHINE_NAME, serviceUrl);

		StringBundler sb = new StringBundler(3);

		sb.append(accountNumber);
		sb.append(StringPool.COLON);
		sb.append(licenseKey);

		String securityHeader = sb.toString();

		byte[] securityHeaderBytes = securityHeader.getBytes();

		String encodedSecurityHeader = Base64.encode(securityHeaderBytes);

		return avaTaxClient.withSecurity(encodedSecurityHeader);
	}

}