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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.util.Arrays;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Victor Silvestre
 * @author Rafael Praxedes
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AIHubCellPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				company.getCompanyId(), "LPD-62272")) {

			return;
		}

		_addOAuth2Application(company);
		_addSAPEntry(company);
	}

	private void _addOAuth2Application(Company company) {
		try {
			OAuth2Application oAuth2Application =
				_oAuth2ApplicationLocalService.
					fetchOAuth2ApplicationByExternalReferenceCode(
						AIHubCellConstants.AI_HUB_CELL_OAUTH2_APPLICATION_ERC,
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
				AIHubCellConstants.AI_HUB_CELL_OAUTH2_APPLICATION_ERC,
				user.getUserId(), user.getScreenName(),
				Arrays.asList(GrantType.CLIENT_CREDENTIALS),
				"client_secret_post", user.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.HEADLESS_SERVER.id(),
				OAuth2SecureRandomGenerator.generateClientSecret(), null, null,
				company.getPortalURL(0), 0, null, "AI Hub Cell", null,
				Arrays.asList(), false,
				Arrays.asList("Liferay.Portal.Search.REST.everything.read"),
				false, new ServiceContext());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add OAuth2 application for company " +
					company.getCompanyId(),
				portalException);
		}
	}

	private void _addSAPEntry(Company company) {
		try {
			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				company.getCompanyId(), _SAP_ENTRY_NAME);

			if (sapEntry != null) {
				return;
			}

			_sapEntryLocalService.addSAPEntry(
				_userLocalService.getGuestUserId(company.getCompanyId()),
				StringBundler.concat(
					"com.liferay.ai.hub.cell.rest.internal.resource.v1_0.",
					"AuthorizationTokenResourceImpl#postAuthorizationToken\n",
					"com.liferay.portal.search.rest.internal.resource.v1_0.",
					"SearchResultResourceImpl#getSearchPage"),
				true, true, _SAP_ENTRY_NAME,
				Collections.singletonMap(
					LocaleUtil.getDefault(), _SAP_ENTRY_NAME),
				new ServiceContext());
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add service access policy entry for company " +
					company.getCompanyId(),
				portalException);
		}
	}

	private static final String _SAP_ENTRY_NAME = "AI_HUB_CELL_TOKEN";

	private static final Log _log = LogFactoryUtil.getLog(
		AIHubCellPortalInstanceLifecycleListener.class);

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}