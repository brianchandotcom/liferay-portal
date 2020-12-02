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

import com.liferay.commerce.avalara.connector.constants.CommerceAvalaraConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;

import java.text.SimpleDateFormat;

import java.util.Date;

import net.avalara.avatax.rest.client.AvaTaxClient;
import net.avalara.avatax.rest.client.models.PingResultModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Riccardo Alberti
 */
public class CommerceAvalaraConnectorImplTest {

	@Before
	public void setUp() {
		_avaTaxClient = new AvaTaxClient(
			CommerceAvalaraConstants.APP_MACHINE,
			CommerceAvalaraConstants.APP_VERSION,
			CommerceAvalaraConstants.MACHINE_NAME,
			"https://sandbox-rest.avatax.com");

		StringBundler sb = new StringBundler(3);

		sb.append("1100068273");
		sb.append(StringPool.COLON);
		sb.append("13F38C128580E9A1");

		String securityHeader = sb.toString();

		byte[] securityHeaderBytes = securityHeader.getBytes();

		String encodedSecurityHeader = Base64.encode(securityHeaderBytes);

		_avaTaxClient = _avaTaxClient.withSecurity(encodedSecurityHeader);
	}

	@Test
	public void testDownloadTaxRatesByZipCode() throws Exception {
		Date date = new Date() {

			@Override
			public String toString() {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");

				return simpleDateFormat.format(this);
			}

		};

		String taxRatesByZipCode = _avaTaxClient.downloadTaxRatesByZipCode(
			date, null);

		Assert.assertNotEquals(taxRatesByZipCode, StringPool.BLANK);
	}

	@Test
	public void testVerifyConnectionToAvalara() throws Exception {
		PingResultModel ping = _avaTaxClient.ping();

		Assert.assertTrue(ping.getAuthenticated());
	}

	private AvaTaxClient _avaTaxClient;

}