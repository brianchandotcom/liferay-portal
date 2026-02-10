/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncPortletKeys;
import com.liferay.antivirus.async.store.internal.event.AntivirusAsyncEventListenerManager;
import com.liferay.antivirus.async.store.util.AntivirusAsyncUtil;
import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.AntivirusVirusFoundException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;

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
			long classPK = message.getLong("classPK");
			long companyId = message.getLong("companyId");
			long repositoryId = message.getLong("repositoryId");
			String fileName = message.getString("fileName");
			String versionLabel = message.getString("versionLabel");

			if (classPK > 0) {
				repositoryId = _getRepositoryId(
					companyId, fileName, repositoryId, versionLabel);
			}

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

			try (InputStream inputStream = _store.getFileAsStream(
					companyId, repositoryId, fileName, versionLabel)) {

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

					if (classPK <= 0) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								StringBundler.concat(
									"Antivirus scanner detected a virus in ",
									"the file ", fileName),
								antivirusScannerException);
						}

						// Quarantine original file

						_store.addFile(
							companyId,
							AntivirusAsyncConstants.REPOSITORY_ID_QUARANTINE,
							fileName, versionLabel,
							_store.getFileAsStream(
								companyId, repositoryId, fileName,
								versionLabel));

						// Delete original file

						_store.deleteFile(
							companyId, repositoryId, fileName, versionLabel);
					}
					else {
						String sourceFileName = message.getString(
							"sourceFileName");

						if (_log.isDebugEnabled()) {
							_log.debug(
								StringBundler.concat(
									"Antivirus scanner detected a virus in ",
									"the file ", fileName),
								antivirusScannerException);
						}

						int fileVersionsCount =
							_dlFileVersionLocalService.getFileVersionsCount(
								classPK, WorkflowConstants.STATUS_ANY);

						DLFileEntry dlFileEntry =
							_dlFileEntryLocalService.getDLFileEntry(classPK);

						String version = _getVersion(versionLabel);

						if (fileVersionsCount <= 1) {
							_dlAppService.deleteFileEntry(classPK);
						}
						else {
							_dlAppService.deleteFileVersion(classPK, version);
						}

						_store.deleteFile(
							companyId, repositoryId, fileName, versionLabel);

						String userEmailAddress = StringPool.BLANK;
						long userId = message.getLong("userId");
						String userName = StringPool.BLANK;

						if (userId != 0) {
							User user = _userLocalService.getUser(userId);

							userEmailAddress = user.getEmailAddress();
							userName = user.getFullName();
						}

						_auditRouter.route(
							new AuditMessage(
								EventTypes.DELETE, companyId, 0,
								StringPool.BLANK, DLFileEntry.class.getName(),
								String.valueOf(classPK), null,
								JSONUtil.put(
									"fileEntryId", classPK
								).put(
									"fileName", sourceFileName
								).put(
									"userEmailAddress", userEmailAddress
								).put(
									"userId", userId
								).put(
									"userName", userName
								).put(
									"virusName",
									antivirusVirusFoundException.getVirusName()
								)));

						ServiceContext serviceContext = new ServiceContext();

						serviceContext.setCompanyId(companyId);

						serviceContext.setUuid(dlFileEntry.getUuid());

						_userNotificationEventLocalService.
							addUserNotificationEvent(
								userId,
								AntivirusAsyncPortletKeys.
									ANTIVIRUS_ASYNC_NOTIFICATION,
								System.currentTimeMillis(),
								UserNotificationDeliveryConstants.TYPE_WEBSITE,
								0,
								JSONUtil.put(
									"companyId", companyId
								).put(
									"fileName", sourceFileName
								).put(
									"repositoryId", repositoryId
								).put(
									"version", version
								).put(
									"versionLabel", versionLabel
								).put(
									"virusName",
									antivirusVirusFoundException.getVirusName()
								).toString(),
								false, serviceContext);
					}

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

	private long _getRepositoryId(
		long companyId, String fileName, long repositoryId,
		String versionLabel) {

		if (_store.hasFile(companyId, repositoryId, fileName, versionLabel)) {
			return repositoryId;
		}

		if (_store.hasFile(
				companyId, AntivirusAsyncConstants.REPOSITORY_ID_QUARANTINE,
				fileName, versionLabel)) {

			return AntivirusAsyncConstants.REPOSITORY_ID_QUARANTINE;
		}

		return repositoryId;
	}

	private String _getVersion(String versionLabel) {
		String[] versionParts = StringUtil.split(versionLabel, "~");

		String version = "";

		if (versionParts.length > 0) {
			version = versionParts[0];
		}

		return version;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntivirusScannerHelper.class);

	@Reference
	private AntivirusAsyncEventListenerManager
		_antivirusAsyncEventListenerManager;

	@Reference
	private AntivirusScanner _antivirusScanner;

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference(target = "(default=true)")
	private Store _store;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}