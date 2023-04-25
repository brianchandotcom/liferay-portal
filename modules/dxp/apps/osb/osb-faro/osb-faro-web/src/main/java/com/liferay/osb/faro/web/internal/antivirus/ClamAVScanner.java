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

package com.liferay.osb.faro.web.internal.antivirus;

import com.liferay.portal.kernel.util.GetterUtil;

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
		if (!_OSB_FARO_ANTIVIRUS_ENABLED) {
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
		if (_OSB_FARO_ANTIVIRUS_ENABLED) {
			_clamAVClient = new ClamAVClient(
				_CLAMAV_HOSTNAME, _CLAMAV_PORT, _CLAMAV_TIMEOUT);
		}
	}

	private String _getVirusName(byte[] reply) {
		String virusName = new String(reply, StandardCharsets.US_ASCII);

		return virusName.substring(
			"stream: ".length(), virusName.length() - (" FOUND".length() + 1));
	}

	private static final String _CLAMAV_HOSTNAME = GetterUtil.getString(
		System.getenv("OSB_FARO_CLAMAV_HOSTNAME"), "antivirus");

	private static final int _CLAMAV_PORT = GetterUtil.getInteger(
		System.getenv("OSB_FARO_CLAMAV_PORT"), 3310);

	private static final int _CLAMAV_TIMEOUT = GetterUtil.getInteger(
		System.getenv("OSB_FARO_CLAMAV_TIMEOUT"), 10000);

	private static final boolean _OSB_FARO_ANTIVIRUS_ENABLED =
		GetterUtil.getBoolean(System.getenv("OSB_FARO_ANTIVIRUS_ENABLED"));

	private ClamAVClient _clamAVClient;

}