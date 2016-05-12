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

package com.liferay.sync.util;

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthApplicationConstants;
import com.liferay.oauth.service.OAuthApplicationLocalService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sync.configuration.SyncServiceConfigurationKeys;
import com.liferay.sync.configuration.SyncServiceConfigurationValues;
import com.liferay.sync.exception.OAuthPortletUndeployedException;

import java.io.InputStream;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = SyncOAuthUtil.class)
public class SyncOAuthUtil {

	public static void enableOAuth(
			long companyId, ServiceContext serviceContext)
		throws Exception {

		if (!isDeployed()) {
			throw new OAuthPortletUndeployedException();
		}

		boolean oAuthEnabled = PrefsPropsUtil.getBoolean(
			companyId, SyncServiceConfigurationKeys.SYNC_OAUTH_ENABLED,
			SyncServiceConfigurationValues.SYNC_OAUTH_ENABLED);

		if (!oAuthEnabled) {
			return;
		}

		User user = _userLocalService.getDefaultUser(companyId);

		serviceContext.setUserId(user.getUserId());

		long oAuthApplicationId = PrefsPropsUtil.getLong(
			companyId, SyncServiceConfigurationKeys.SYNC_OAUTH_APPLICATION_ID,
			0);

		OAuthApplication oAuthApplication =
			_oAuthApplicationLocalService.fetchOAuthApplication(
				oAuthApplicationId);

		if (oAuthApplication != null) {
			return;
		}

		oAuthApplication = _oAuthApplicationLocalService.addOAuthApplication(
			serviceContext.getUserId(), "Liferay Sync", StringPool.BLANK,
			OAuthApplicationConstants.ACCESS_WRITE, true, "http://liferay-sync",
			"http://liferay-sync", serviceContext);

		Class<?> clazz = SyncOAuthUtil.class;

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"images/logo.png");

		_oAuthApplicationLocalService.updateLogo(
			oAuthApplication.getOAuthApplicationId(), inputStream);

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			portletPreferences.setValue(
				SyncServiceConfigurationKeys.SYNC_OAUTH_APPLICATION_ID,
				String.valueOf(oAuthApplication.getOAuthApplicationId()));
			portletPreferences.setValue(
				SyncServiceConfigurationKeys.SYNC_OAUTH_CONSUMER_KEY,
				oAuthApplication.getConsumerKey());
			portletPreferences.setValue(
				SyncServiceConfigurationKeys.SYNC_OAUTH_CONSUMER_SECRET,
				oAuthApplication.getConsumerSecret());

			portletPreferences.store();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public static boolean isDeployed() {
		if (_oAuthApplicationLocalService == null) {
			return false;
		}

		return true;
	}

	public static boolean isOAuthApplicationAvailable(long oAuthApplicationId) {
		if (!isDeployed()) {
			return false;
		}

		OAuthApplication oAuthApplication =
			_oAuthApplicationLocalService.fetchOAuthApplication(
				oAuthApplicationId);

		if (oAuthApplication == null) {
			return false;
		}

		return true;
	}

	protected static void enableOAuth(long companyId) throws Exception {
		enableOAuth(companyId, new ServiceContext());
	}

	@Activate
	protected void activate() {
		for (Company company : _companyLocalService.getCompanies()) {
			try {
				enableOAuth(company.getCompanyId());
			}
			catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "unsetAuthApplicationLocalService")
	protected void setOAuthApplicationLocalService(
		OAuthApplicationLocalService oAuthApplicationLocalService) {

		_oAuthApplicationLocalService = oAuthApplicationLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void unsetAuthApplicationLocalService(
		OAuthApplicationLocalService oAuthApplicationLocalService) {

		_oAuthApplicationLocalService = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(SyncOAuthUtil.class);

	private static CompanyLocalService _companyLocalService;
	private static OAuthApplicationLocalService _oAuthApplicationLocalService;
	private static UserLocalService _userLocalService;

}