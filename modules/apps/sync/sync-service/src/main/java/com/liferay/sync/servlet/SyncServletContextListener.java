/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sync.servlet;

import com.liferay.document.library.kernel.model.DLSyncEvent;
import com.liferay.document.library.kernel.service.DLSyncEventLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.SerialDestination;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.sync.configuration.SyncServiceConfigurationKeys;
import com.liferay.sync.configuration.SyncServiceConfigurationValues;
import com.liferay.sync.messaging.DLSyncEventMessageListener;
import com.liferay.sync.messaging.SyncDLFileVersionDiffMessageListener;
import com.liferay.sync.service.SyncDLObjectLocalServiceUtil;
import com.liferay.sync.service.SyncPreferencesLocalServiceUtil;
import com.liferay.sync.util.VerifyUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Brian Wing Shun Chan
 * @author Dennis Ju
 */
public class SyncServletContextListener
	extends BasePortalLifecycle implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		portalDestroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		registerPortalLifecycle();
	}

	@Override
	protected void doPortalInit() {
		try {
			if (SyncServiceConfigurationValues.SYNC_VERIFY) {
				VerifyUtil.verify();
			}

			List<Company> companies = CompanyLocalServiceUtil.getCompanies();

			for (Company company : companies) {
				boolean oAuthEnabled = PrefsPropsUtil.getBoolean(
					company.getCompanyId(),
					SyncServiceConfigurationKeys.SYNC_OAUTH_ENABLED,
					SyncServiceConfigurationValues.SYNC_OAUTH_ENABLED);

				if (!oAuthEnabled) {
					continue;
				}

				ServiceContext serviceContext = new ServiceContext();

				User user = UserLocalServiceUtil.getDefaultUser(
					company.getCompanyId());

				serviceContext.setUserId(user.getUserId());

				SyncPreferencesLocalServiceUtil.enableOAuth(
					company.getCompanyId(), serviceContext);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}


	private static final Log _log = LogFactoryUtil.getLog(
		SyncServletContextListener.class);

}