/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.rest.internal.resource.v1_0;

import com.liferay.cookies.rest.dto.v1_0.CookiesConsentPreference;
import com.liferay.cookies.rest.internal.dto.v1_0.util.CookiesConsentPreferenceUtil;
import com.liferay.cookies.rest.resource.v1_0.CookiesConsentPreferenceResource;
import com.liferay.cookies.service.CookiesConsentPreferenceLocalService;
import com.liferay.cookies.service.persistence.CookiesConsentPreferencePersistence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.net.URI;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Christopher Kian
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/cookies-consent-preference.properties",
	scope = ServiceScope.PROTOTYPE,
	service = CookiesConsentPreferenceResource.class
)
public class CookiesConsentPreferenceResourceImpl
	extends BaseCookiesConsentPreferenceResourceImpl {

	@Override
	public void deleteCookiesConsentPreferenceByName(String name)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-75032")) {

			throw new UnsupportedOperationException();
		}

		_cookiesConsentPreferenceLocalService.deleteCookiesConsentPreference(
			contextUser.getUserId(), _getDomain(), name);
	}

	@Override
	public void deleteCookiesConsentPreferences() throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-75032")) {

			throw new UnsupportedOperationException();
		}

		_cookiesConsentPreferenceLocalService.deleteCookiesConsentPreferences(
			contextUser.getUserId(), _getDomain());
	}

	@Override
	public CookiesConsentPreference getCookiesConsentPreferenceByName(
		String name) {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-75032")) {

			throw new UnsupportedOperationException();
		}

		com.liferay.cookies.model.CookiesConsentPreference
			serviceBuilderCookiesConsentPreference =
				_cookiesConsentPreferenceLocalService.
					getCookiesConsentPreference(
						contextUser.getUserId(), _getDomain(), name);

		if (serviceBuilderCookiesConsentPreference == null) {
			return null;
		}

		return CookiesConsentPreferenceUtil.toCookiesConsentPreference(
			serviceBuilderCookiesConsentPreference);
	}

	@Override
	public CookiesConsentPreference putCookiesConsentPreference(
			CookiesConsentPreference cookiesConsentPreference)
		throws PortalException {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-75032")) {

			throw new UnsupportedOperationException();
		}

		com.liferay.cookies.model.CookiesConsentPreference
			serviceBuilderCookiesConsentPreference =
				_cookiesConsentPreferencePersistence.fetchByU_D_N(
					contextUser.getUserId(),
					cookiesConsentPreference.getDomain(),
					cookiesConsentPreference.getName());

		if (serviceBuilderCookiesConsentPreference != null) {
			serviceBuilderCookiesConsentPreference.setExpirationDate(
				cookiesConsentPreference.getExpirationDate());
			serviceBuilderCookiesConsentPreference.setValue(
				cookiesConsentPreference.getValue());

			serviceBuilderCookiesConsentPreference =
				_cookiesConsentPreferencePersistence.update(
					serviceBuilderCookiesConsentPreference);
		}
		else {
			serviceBuilderCookiesConsentPreference =
				_cookiesConsentPreferenceLocalService.
					addCookiesConsentPreference(
						contextUser.getUserId(),
						cookiesConsentPreference.getDomain(),
						cookiesConsentPreference.getExpirationDate(),
						cookiesConsentPreference.getName(),
						cookiesConsentPreference.getValue());
		}

		cookiesConsentPreference.setId(
			serviceBuilderCookiesConsentPreference::getPrimaryKey);

		return cookiesConsentPreference;
	}

	private String _getDomain() {
		URI uri = contextUriInfo.getRequestUri();

		StringBuilder sb = new StringBuilder(3);

		sb.append(uri.getScheme());
		sb.append("://");
		sb.append(uri.getAuthority());

		return sb.toString();
	}

	@Reference
	private CookiesConsentPreferenceLocalService
		_cookiesConsentPreferenceLocalService;

	@Reference
	private CookiesConsentPreferencePersistence
		_cookiesConsentPreferencePersistence;

}