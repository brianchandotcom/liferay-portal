/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.instance.lifecycle;

import com.liferay.ai.hub.cell.constants.AIHubCellConstants;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Arrays;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AIHubCellOAuth2ApplicationPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.
					fetchOAuth2ApplicationByExternalReferenceCode(
						AIHubCellConstants.OAUTH2_APPLICATION_ERC_AI_HUB_CELL,
						company.getCompanyId());

			if (oAuth2Application != null) {
				return;
			}

			User user = _userLocalService.fetchUserByScreenName(
				company.getCompanyId(), "default-service-account");

			if (user == null) {
				return;
			}

			_oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
				AIHubCellConstants.OAUTH2_APPLICATION_ERC_AI_HUB_CELL,
				user.getUserId(), user.getScreenName(),
				Arrays.asList(GrantType.CLIENT_CREDENTIALS),
				"client_secret_post", user.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.HEADLESS_SERVER.id(),
				OAuth2SecureRandomGenerator.generateClientSecret(), null, null,
				company.getPortalURL(0), 0, null, "AI Hub Cell", null,
				Arrays.asList(), false,
				Arrays.asList("Liferay.Portal.Search.REST.everything"), false,
				new ServiceContext());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add OAuth2 application for company " +
					company.getCompanyId(),
				portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AIHubCellOAuth2ApplicationPortalInstanceLifecycleListener.class);

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private UserLocalService _userLocalService;

}