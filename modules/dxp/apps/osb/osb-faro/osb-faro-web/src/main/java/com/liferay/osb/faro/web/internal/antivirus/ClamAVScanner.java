/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.antivirus;

import com.liferay.osb.faro.util.FaroPropsValues;

import fi.solita.clamav.ClamAVClient;
import fi.solita.clamav.ClamAVSizeLimitException;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(service = ClamAVScanner.class)
public class ClamAVScanner {

	public void scan(InputStream inputStream) {
		if (!FaroPropsValues.OSB_FARO_ANTIVIRUS_ENABLED) {
			return;
		}

		try {
			byte[] reply = _clamAVClient.scan(inputStream);

			if (!ClamAVClient.isCleanReply(reply)) {
				throw new RuntimeException(
					String.format(
						"Virus %s was detected ", _getVirusName(reply)));
			}
		}
		catch (ClamAVSizeLimitException | IOException exception) {
			throw new RuntimeException("Unable to scan for Viruses", exception);
		}
	}

	@Activate
	protected void activate() {
		if (FaroPropsValues.OSB_FARO_ANTIVIRUS_ENABLED) {
			_clamAVClient = new ClamAVClient(
				FaroPropsValues.OSB_FARO_CLAMAV_HOSTNAME,
				FaroPropsValues.OSB_FARO_CLAMAV_PORT,
				FaroPropsValues.OSB_FARO_CLAMAV_TIMEOUT);
		}
	}

	private String _getVirusName(byte[] reply) {
		String virusName = new String(reply, StandardCharsets.US_ASCII);

		return virusName.substring(
			"stream: ".length(), virusName.length() - (" FOUND".length() + 1));
	}

	private ClamAVClient _clamAVClient;

}