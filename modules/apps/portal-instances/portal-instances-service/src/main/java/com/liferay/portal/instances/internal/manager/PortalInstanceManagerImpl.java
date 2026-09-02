/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.manager;

import com.liferay.portal.instances.backgroundtask.constants.PortalInstanceBackgroundTaskConstants;
import com.liferay.portal.instances.exception.PortalInstanceAlreadyBeingAddedException;
import com.liferay.portal.instances.internal.backgroundtask.AddPortalInstanceBackgroundTaskExecutor;
import com.liferay.portal.instances.manager.PortalInstanceManager;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.encryptor.Encryptor;
import com.liferay.portal.kernel.encryptor.EncryptorException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(service = PortalInstanceManager.class)
public class PortalInstanceManagerImpl implements PortalInstanceManager {

	@Override
	public long addPortalInstance(
			long userId, String webId, String virtualHostname, String mx,
			int maxUsers, boolean active, String defaultAdminPassword,
			String defaultAdminScreenName, String defaultAdminEmailAddress,
			String defaultAdminFirstName, String defaultAdminMiddleName,
			String defaultAdminLastName, String siteInitializerKey)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
		}

		_companyLocalService.validateCompany(
			webId, virtualHostname, mx, maxUsers);

		String name = "AddPortalInstance#" + webId;
		String taskExecutorClassName =
			AddPortalInstanceBackgroundTaskExecutor.class.getName();

		int count = _backgroundTaskManager.getBackgroundTasksCount(
			BackgroundTaskConstants.GROUP_ID_DEFAULT, name,
			taskExecutorClassName, false);

		if (count > 0) {
			throw new PortalInstanceAlreadyBeingAddedException(
				"Portal instance " + webId + " is already being added");
		}

		BackgroundTask backgroundTask =
			_backgroundTaskManager.addBackgroundTask(
				userId, BackgroundTaskConstants.GROUP_ID_DEFAULT, name,
				taskExecutorClassName,
				HashMapBuilder.<String, Serializable>put(
					PortalInstanceBackgroundTaskConstants.ACTIVE, active
				).put(
					PortalInstanceBackgroundTaskConstants.
						DEFAULT_ADMIN_EMAIL_ADDRESS,
					defaultAdminEmailAddress
				).put(
					PortalInstanceBackgroundTaskConstants.
						DEFAULT_ADMIN_FIRST_NAME,
					defaultAdminFirstName
				).put(
					PortalInstanceBackgroundTaskConstants.
						DEFAULT_ADMIN_LAST_NAME,
					defaultAdminLastName
				).put(
					PortalInstanceBackgroundTaskConstants.
						DEFAULT_ADMIN_MIDDLE_NAME,
					defaultAdminMiddleName
				).put(
					PortalInstanceBackgroundTaskConstants.
						DEFAULT_ADMIN_PASSWORD,
					_encryptDefaultAdminPassword(defaultAdminPassword)
				).put(
					PortalInstanceBackgroundTaskConstants.
						DEFAULT_ADMIN_SCREEN_NAME,
					defaultAdminScreenName
				).put(
					PortalInstanceBackgroundTaskConstants.MAX_USERS, maxUsers
				).put(
					PortalInstanceBackgroundTaskConstants.MX, mx
				).put(
					PortalInstanceBackgroundTaskConstants.SITE_INITIALIZER_KEY,
					siteInitializerKey
				).put(
					PortalInstanceBackgroundTaskConstants.VIRTUAL_HOSTNAME,
					virtualHostname
				).put(
					PortalInstanceBackgroundTaskConstants.WEB_ID, webId
				).build(),
				new ServiceContext());

		return backgroundTask.getBackgroundTaskId();
	}

	private String _encryptDefaultAdminPassword(String defaultAdminPassword)
		throws PortalException {

		if (Validator.isNull(defaultAdminPassword)) {
			return null;
		}

		Company company = _companyLocalService.getCompanyById(
			PortalInstances.getDefaultCompanyId());

		try {
			return _encryptor.encrypt(
				company.getKeyObj(), defaultAdminPassword);
		}
		catch (EncryptorException encryptorException) {
			throw new PortalException(encryptorException);
		}
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Encryptor _encryptor;

}