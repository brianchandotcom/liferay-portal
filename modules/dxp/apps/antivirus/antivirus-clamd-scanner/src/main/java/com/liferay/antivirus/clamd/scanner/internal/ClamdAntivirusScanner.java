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

package com.liferay.antivirus.clamd.scanner.internal;

import com.liferay.antivirus.clamd.scanner.internal.configuration.ClamdAntivirusScannerConfiguration;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.AntivirusVirusFoundException;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import fi.solita.clamav.ClamAVClient;
import fi.solita.clamav.ClamAVSizeLimitException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.antivirus.clamd.scanner.internal.configuration.ClamdAntivirusScannerConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = AntivirusScanner.class
)
public class ClamdAntivirusScanner implements AntivirusScanner {

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void scan(byte[] bytes) throws AntivirusScannerException {
		try {
			byte[] reply = _clamdClient.scan(bytes);

			if (!ClamAVClient.isCleanReply(reply)) {
				throw new AntivirusVirusFoundException(
					"Virus detected in byte array", _extractVirusName(reply));
			}
		}
		catch (ClamAVSizeLimitException clamAVSizeLimitException) {
			throw new AntivirusScannerException(
				AntivirusScannerException.SIZE_LIMIT_EXCEEDED,
				clamAVSizeLimitException);
		}
		catch (IOException ioException) {
			throw new AntivirusScannerException(
				AntivirusScannerException.PROCESS_FAILURE, ioException);
		}
	}

	@Override
	public void scan(File file) throws AntivirusScannerException {
		try (InputStream inputStream = new FileInputStream(file)) {
			byte[] reply = _clamdClient.scan(inputStream);

			if (!ClamAVClient.isCleanReply(reply)) {
				throw new AntivirusVirusFoundException(
					"Virus detected in " + file.getAbsolutePath(),
					_extractVirusName(reply));
			}
		}
		catch (ClamAVSizeLimitException clamAVSizeLimitException) {
			throw new AntivirusScannerException(
				AntivirusScannerException.SIZE_LIMIT_EXCEEDED,
				clamAVSizeLimitException);
		}
		catch (IOException ioException) {
			throw new AntivirusScannerException(
				AntivirusScannerException.PROCESS_FAILURE, ioException);
		}
	}

	@Override
	public void scan(InputStream inputStream) throws AntivirusScannerException {
		try {
			byte[] reply = _clamdClient.scan(inputStream);

			if (!ClamAVClient.isCleanReply(reply)) {
				throw new AntivirusVirusFoundException(
					"Virus detected in stream", _extractVirusName(reply));
			}
		}
		catch (ClamAVSizeLimitException clamAVSizeLimitException) {
			throw new AntivirusScannerException(
				AntivirusScannerException.SIZE_LIMIT_EXCEEDED,
				clamAVSizeLimitException);
		}
		catch (IOException ioException) {
			throw new AntivirusScannerException(
				AntivirusScannerException.PROCESS_FAILURE, ioException);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		ClamdAntivirusScannerConfiguration clamdAntivirusScannerConfiguration =
			ConfigurableUtil.createConfigurable(
				ClamdAntivirusScannerConfiguration.class, properties);

		_clamdClient = new ClamAVClient(
			clamdAntivirusScannerConfiguration.hostname(),
			clamdAntivirusScannerConfiguration.port(),
			clamdAntivirusScannerConfiguration.timeout());
	}

	private String _extractVirusName(byte[] result) {
		String reply = new String(result, StandardCharsets.US_ASCII);

		return reply.substring(
			"stream: ".length(), reply.length() - (" FOUND".length() + 1));
	}

	private ClamAVClient _clamdClient;

}