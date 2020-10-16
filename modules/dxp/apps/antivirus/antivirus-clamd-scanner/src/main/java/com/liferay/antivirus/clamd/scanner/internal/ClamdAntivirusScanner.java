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

import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import fi.solita.clamav.ClamAVClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;

/**
 * @author Shuyang Zhou
 */
@Component(
	configurationPid = "com.liferay.antivirus.clamd.scanner.internal.ClamdAntivirusScannerConfiguration",
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
			if (!ClamAVClient.isCleanReply(_clamdClient.scan(bytes))) {
				throw new AntivirusScannerException(
					"Virus detected in byte array",
					AntivirusScannerException.VIRUS_DETECTED);
			}
		}
		catch (IOException ioException) {
			throw new AntivirusScannerException(
				AntivirusScannerException.PROCESS_FAILURE, ioException);
		}
	}

	@Override
	public void scan(File file) throws AntivirusScannerException {
		try (InputStream inputStream = new FileInputStream(file)) {
			if (!ClamAVClient.isCleanReply(_clamdClient.scan(inputStream))) {
				throw new AntivirusScannerException(
					"Virus detected in " + file.getAbsolutePath(),
					AntivirusScannerException.VIRUS_DETECTED);
			}
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

	private ClamAVClient _clamdClient;

}