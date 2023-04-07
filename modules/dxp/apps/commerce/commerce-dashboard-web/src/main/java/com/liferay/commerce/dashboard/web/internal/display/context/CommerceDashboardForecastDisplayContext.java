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

package com.liferay.commerce.dashboard.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.dashboard.web.internal.configuration.CommerceDashboardForecastPortletInstanceConfiguration;
import com.liferay.commerce.dashboard.web.internal.display.context.helper.CommerceDashboardForecastRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.PortletDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Ferrari
 */
public class CommerceDashboardForecastDisplayContext {

	public CommerceDashboardForecastDisplayContext(
			ModelResourcePermission<AccountEntry>
				accountEntryModelResourcePermission,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		_accountEntryModelResourcePermission =
			accountEntryModelResourcePermission;

		_commerceDashboardForecastRequestHelper =
			new CommerceDashboardForecastRequestHelper(httpServletRequest);

		PortletDisplay portletDisplay =
			_commerceDashboardForecastRequestHelper.getPortletDisplay();

		_commerceDashboardForecastPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceDashboardForecastPortletInstanceConfiguration.class);
	}

	public String getAssetCategoryIds() {
		return _commerceDashboardForecastPortletInstanceConfiguration.
			assetCategoryIds();
	}

	public boolean hasViewPermission() {
		PermissionChecker permissionChecker =
			_commerceDashboardForecastRequestHelper.getPermissionChecker();

		try {
			return _accountEntryModelResourcePermission.contains(
				permissionChecker,
				_commerceDashboardForecastRequestHelper.getAccountEntryId(),
				ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDashboardForecastDisplayContext.class);

	private final ModelResourcePermission<AccountEntry>
		_accountEntryModelResourcePermission;
	private final CommerceDashboardForecastPortletInstanceConfiguration
		_commerceDashboardForecastPortletInstanceConfiguration;
	private final CommerceDashboardForecastRequestHelper
		_commerceDashboardForecastRequestHelper;

}