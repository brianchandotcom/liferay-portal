/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.internal.event.AntivirusAsyncEventListenerManager;
import com.liferay.antivirus.async.store.util.AntivirusAsyncUtil;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.AntivirusVirusFoundException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = AntivirusScannerHelper.class
)
public class AntivirusScannerHelper {

	public void processMessage(Message message) {
		try {
			long companyId = message.getLong("companyId");
			long repositoryId = message.getLong("repositoryId");
			String fileName = message.getString("fileName");
			String versionLabel = message.getString("versionLabel");

			boolean fileExists = _store.hasFile(
				companyId, repositoryId, fileName, versionLabel);

			if (!fileExists) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							AntivirusAsyncUtil.getFileIdentifier(message),
							" is no longer present: ", message.getValues()));
				}

				_antivirusAsyncEventListenerManager.onMissing(message);

				return;
			}

			try {
				InputStream inputStream = _store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel);

				_antivirusScanner.scan(inputStream);

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							AntivirusAsyncUtil.getFileIdentifier(message),
							" was scanned successfully: ",
							message.getValues()));
				}

				_antivirusAsyncEventListenerManager.onSuccess(message);
			}
			catch (AntivirusScannerException antivirusScannerException) {
				int type = antivirusScannerException.getType();

				if (antivirusScannerException instanceof
						AntivirusVirusFoundException) {

					AntivirusVirusFoundException antivirusVirusFoundException =
						(AntivirusVirusFoundException)antivirusScannerException;

					// Quarantine original file

					_store.addFile(
						companyId,
						AntivirusAsyncConstants.REPOSITORY_ID_QUARANTINE,
						fileName, versionLabel,
						_store.getFileAsStream(
							companyId, repositoryId, fileName, versionLabel));

					// Delete original file

					_store.deleteFile(
						companyId, repositoryId, fileName, versionLabel);

					_antivirusAsyncEventListenerManager.onVirusFound(
						message, antivirusVirusFoundException,
						antivirusVirusFoundException.getVirusName());
				}
				else if (type ==
							AntivirusScannerException.SIZE_LIMIT_EXCEEDED) {

					_antivirusAsyncEventListenerManager.onSizeExceeded(
						message, antivirusScannerException);
				}
				else {
					throw antivirusScannerException;
				}
			}
		}
		catch (Exception exception) {
			_antivirusAsyncEventListenerManager.onProcessingError(
				message, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntivirusScannerHelper.class);

	@Reference
	private AntivirusAsyncEventListenerManager
		_antivirusAsyncEventListenerManager;

	@Reference
	private AntivirusScanner _antivirusScanner;

	@Reference(target = "(default=true)")
	private Store _store;

}