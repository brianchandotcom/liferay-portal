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

package com.liferay.sync.oauth.internal.helper;

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthApplicationConstants;
import com.liferay.oauth.service.OAuthApplicationLocalService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sync.constants.SyncConstants;
import com.liferay.sync.exception.OAuthPortletUndeployedException;
import com.liferay.sync.oauth.helper.SyncOAuthHelper;

import java.io.InputStream;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = SyncOAuthHelper.class)
public class SyncOAuthHelperImpl implements SyncOAuthHelper {

	@Override
	public void enableOAuth(long companyId, ServiceContext serviceContext)
		throws Exception {

		if (!isDeployed()) {
			throw new OAuthPortletUndeployedException();
		}

		User user = _userLocalService.getDefaultUser(companyId);

		serviceContext.setUserId(user.getUserId());

		long oAuthApplicationId = PrefsPropsUtil.getLong(
			companyId, SyncConstants.SYNC_OAUTH_APPLICATION_ID);

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

		Class<?> clazz = SyncOAuthHelperImpl.class;

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"images/logo.png");

		_oAuthApplicationLocalService.updateLogo(
			oAuthApplication.getOAuthApplicationId(), inputStream);

		PortletPreferences portletPreferences = PrefsPropsUtil.getPreferences(
			companyId);

		try {
			portletPreferences.setValue(
				SyncConstants.SYNC_OAUTH_APPLICATION_ID,
				String.valueOf(oAuthApplication.getOAuthApplicationId()));
			portletPreferences.setValue(
				SyncConstants.SYNC_OAUTH_CONSUMER_KEY,
				oAuthApplication.getConsumerKey());
			portletPreferences.setValue(
				SyncConstants.SYNC_OAUTH_CONSUMER_SECRET,
				oAuthApplication.getConsumerSecret());

			portletPreferences.store();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public boolean isDeployed() {
		if (_oAuthApplicationLocalService == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isOAuthApplicationAvailable(long oAuthApplicationId) {
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

	@Reference(unbind = "unsetOAuthApplicationLocalService")
	protected void setOAuthApplicationLocalService(
		OAuthApplicationLocalService oAuthApplicationLocalService) {

		_oAuthApplicationLocalService = oAuthApplicationLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void unsetOAuthApplicationLocalService(
		OAuthApplicationLocalService oAuthApplicationLocalService) {

		_oAuthApplicationLocalService = null;
	}

	private static OAuthApplicationLocalService _oAuthApplicationLocalService;
	private static UserLocalService _userLocalService;

}