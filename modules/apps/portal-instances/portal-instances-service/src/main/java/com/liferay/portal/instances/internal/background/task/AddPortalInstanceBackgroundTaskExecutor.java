/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.background.task;

import com.liferay.portal.instances.background.task.PortalInstanceOperationType;
import com.liferay.portal.instances.background.task.constants.PortalInstanceBackgroundTaskConstants;
import com.liferay.portal.instances.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.exception.CompanyMaxUsersException;
import com.liferay.portal.kernel.exception.CompanyMxException;
import com.liferay.portal.kernel.exception.CompanyVirtualHostException;
import com.liferay.portal.kernel.exception.CompanyWebIdException;
import com.liferay.portal.kernel.exception.ContactNameException;
import com.liferay.portal.kernel.exception.UserEmailAddressException;
import com.liferay.portal.kernel.exception.UserPasswordException;
import com.liferay.portal.kernel.exception.UserScreenNameException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.portal.instances.internal.background.task.AddPortalInstanceBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class AddPortalInstanceBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public AddPortalInstanceBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String webId = GetterUtil.getString(
			taskContextMap.get(PortalInstanceBackgroundTaskConstants.WEB_ID));
		String virtualHostname = GetterUtil.getString(
			taskContextMap.get(
				PortalInstanceBackgroundTaskConstants.VIRTUAL_HOSTNAME));
		String mx = GetterUtil.getString(
			taskContextMap.get(PortalInstanceBackgroundTaskConstants.MX));
		int maxUsers = GetterUtil.getInteger(
			taskContextMap.get(
				PortalInstanceBackgroundTaskConstants.MAX_USERS));
		boolean active = GetterUtil.getBoolean(
			taskContextMap.get(PortalInstanceBackgroundTaskConstants.ACTIVE));
		String defaultAdminPassword = _decryptDefaultAdminPassword(
			(String)taskContextMap.get(
				PortalInstanceBackgroundTaskConstants.DEFAULT_ADMIN_PASSWORD));
		String defaultAdminScreenName = (String)taskContextMap.get(
			PortalInstanceBackgroundTaskConstants.DEFAULT_ADMIN_SCREEN_NAME);
		String defaultAdminEmailAddress = (String)taskContextMap.get(
			PortalInstanceBackgroundTaskConstants.DEFAULT_ADMIN_EMAIL_ADDRESS);
		String defaultAdminFirstName = (String)taskContextMap.get(
			PortalInstanceBackgroundTaskConstants.DEFAULT_ADMIN_FIRST_NAME);
		String defaultAdminMiddleName = (String)taskContextMap.get(
			PortalInstanceBackgroundTaskConstants.DEFAULT_ADMIN_MIDDLE_NAME);
		String defaultAdminLastName = (String)taskContextMap.get(
			PortalInstanceBackgroundTaskConstants.DEFAULT_ADMIN_LAST_NAME);

		Company company = PortalInstances.addCompany(
			GetterUtil.getString(
				taskContextMap.get(
					PortalInstanceBackgroundTaskConstants.
						SITE_INITIALIZER_KEY)),
			() -> _companyService.addCompany(
				null, webId, virtualHostname, mx, maxUsers, active,
				defaultAdminPassword, defaultAdminScreenName,
				defaultAdminEmailAddress, defaultAdminFirstName,
				defaultAdminMiddleName, defaultAdminLastName));

		try {
			_sendUserNotificationEvent(
				backgroundTask.getUserId(),
				_getPayloadJSONObject(
					company.getCompanyId(), null,
					BackgroundTaskConstants.STATUS_SUCCESSFUL, webId));
		}
		catch (Exception exception) {
			_log.error("Unable to send success user notification", exception);
		}

		JSONObject statusMessageJSONObject = JSONUtil.put(
			PortalInstanceBackgroundTaskConstants.COMPANY_ID,
			company.getCompanyId());

		return new BackgroundTaskResult(
			BackgroundTaskConstants.STATUS_SUCCESSFUL,
			statusMessageJSONObject.toString());
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	@Override
	public String handleException(
		BackgroundTask backgroundTask, Exception exception1) {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		try {
			_sendUserNotificationEvent(
				backgroundTask.getUserId(),
				_getPayloadJSONObject(
					0, _getErrorMessageKey(exception1),
					BackgroundTaskConstants.STATUS_FAILED,
					GetterUtil.getString(
						taskContextMap.get(
							PortalInstanceBackgroundTaskConstants.WEB_ID))));
		}
		catch (Exception exception2) {
			_log.error("Unable to send failure user notification", exception2);
		}

		return super.handleException(backgroundTask, exception1);
	}

	private String _decryptDefaultAdminPassword(String defaultAdminPassword)
		throws Exception {

		if (Validator.isNull(defaultAdminPassword)) {
			return null;
		}

		Company company = _companyLocalService.getCompanyById(
			PortalInstances.getDefaultCompanyId());

		return _encryptor.decrypt(company.getKeyObj(), defaultAdminPassword);
	}

	private String _getErrorMessageKey(Exception exception) {
		Throwable throwable = exception;

		while (throwable != null) {
			if (throwable instanceof CompanyMaxUsersException) {
				return "please-enter-a-valid-max-users";
			}
			else if (throwable instanceof CompanyMxException) {
				return "please-enter-a-valid-mail-domain";
			}
			else if (throwable instanceof CompanyVirtualHostException) {
				return "please-enter-a-valid-virtual-host";
			}
			else if (throwable instanceof CompanyWebIdException) {
				return "please-enter-a-valid-web-id";
			}
			else if (throwable instanceof
						ContactNameException.MustHaveFirstName) {

				return "please-enter-a-valid-first-name";
			}
			else if (throwable instanceof
						ContactNameException.MustHaveLastName) {

				return "please-enter-a-valid-last-name";
			}
			else if (throwable instanceof
						ContactNameException.MustHaveMiddleName) {

				return "please-enter-a-valid-middle-name";
			}
			else if (throwable instanceof
						ContactNameException.MustHaveValidFullName) {

				return "please-enter-a-valid-first-middle-and-last-name";
			}
			else if (throwable instanceof UserEmailAddressException) {
				return "please-enter-a-valid-email-address";
			}
			else if (throwable instanceof UserPasswordException) {
				return "please-enter-a-valid-password";
			}
			else if (throwable instanceof UserScreenNameException) {
				return "please-enter-a-valid-screen-name";
			}

			throwable = throwable.getCause();
		}

		return "an-unexpected-error-occurred";
	}

	private JSONObject _getPayloadJSONObject(
		long companyId, String errorMessageKey, int status, String webId) {

		return JSONUtil.put(
			PortalInstanceBackgroundTaskConstants.COMPANY_ID, companyId
		).put(
			PortalInstanceBackgroundTaskConstants.ERROR_MESSAGE_KEY,
			errorMessageKey
		).put(
			PortalInstanceBackgroundTaskConstants.OPERATION_TYPE,
			PortalInstanceOperationType.ADD.getValue()
		).put(
			PortalInstanceBackgroundTaskConstants.STATUS,
			BackgroundTaskConstants.getStatusLabel(status)
		).put(
			PortalInstanceBackgroundTaskConstants.WEB_ID, webId
		);
	}

	private void _sendUserNotificationEvent(
			long userId, JSONObject payloadJSONObject)
		throws Exception {

		_userNotificationEventLocalService.sendUserNotificationEvents(
			userId, PortalInstancesPortletKeys.PORTAL_INSTANCES,
			UserNotificationDeliveryConstants.TYPE_WEBSITE, payloadJSONObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddPortalInstanceBackgroundTaskExecutor.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyService _companyService;

	@Reference
	private Encryptor _encryptor;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}