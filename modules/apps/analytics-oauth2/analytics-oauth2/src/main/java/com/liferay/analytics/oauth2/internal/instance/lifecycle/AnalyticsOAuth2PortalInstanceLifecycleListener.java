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

package com.liferay.analytics.oauth2.internal.instance.lifecycle;

import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AnalyticsOAuth2PortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (_hasOAuth2Application(company.getCompanyId())) {
			return;
		}

		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		_addSAPEntry(company.getCompanyId(), user.getUserId());

		List<GrantType> grantTypes = new ArrayList<>();

		grantTypes.add(GrantType.AUTHORIZATION_CODE);
		grantTypes.add(GrantType.REFRESH_TOKEN);

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				company.getCompanyId(), user.getUserId(), user.getScreenName(),
				grantTypes, _generateRandomId(),
				ClientProfile.WEB_APPLICATION.id(), _generateRandomSecret(),
				null, null, "https://analytics.liferay.com", 0,
				_APPLICATION_NAME, null,
				Collections.singletonList(
					"https://analytics.liferay.com/oauth/receive"),
				Arrays.asList("everything.read", "preferences.write"),
				new ServiceContext());

		if (false) {
			Class<?> clazz = getClass();

			InputStream inputStream = clazz.getResourceAsStream(
				"dependencies/logo.png");

			_oAuth2ApplicationLocalService.updateIcon(
				oAuth2Application.getOAuth2ApplicationId(), inputStream);
		}
	}

	private static String _generateRandomId() {
		String randomSecret = _generateRandomSecret();

		return StringUtil.replace(randomSecret, "secret-", "id-");
	}

	private static String _generateRandomSecret() {
		int size = 16;

		int count = (int)Math.ceil((double)size / 8);

		byte[] buffer = new byte[count * 8];

		for (int i = 0; i < count; i++) {
			BigEndianCodec.putLong(buffer, i * 8, SecureRandomUtil.nextLong());
		}

		StringBundler sb = new StringBundler(size);

		for (int i = 0; i < size; i++) {
			sb.append(Integer.toHexString(0xFF & buffer[i]));
		}

		Matcher matcher = _baseIdPattern.matcher(sb.toString());

		return matcher.replaceFirst("secret-$1-$2-$3-$4-$5");
	}

	private void _addSAPEntry(long companyId, long userId)
		throws PortalException {

		SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
			companyId, _SAP_ENTRY_NAME);

		if (sapEntry != null) {
			return;
		}

		String allowedServiceSignatures =
			CompanyService.class.getName() + "#updatePreferences";

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(
			LocaleUtil.getDefault(),
			"create/update/delete preferences on your behalf");

		_sapEntryLocalService.addSAPEntry(
			userId, allowedServiceSignatures, false, true, _SAP_ENTRY_NAME,
			titleMap, new ServiceContext());
	}

	private boolean _hasOAuth2Application(long companyId) {
		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(_APPLICATION_NAME));

		if (_oAuth2ApplicationLocalService.dynamicQueryCount(dynamicQuery) >
				0) {

			return true;
		}

		return false;
	}

	private static final String _APPLICATION_NAME = "Analytics Cloud";

	private static final String _SAP_ENTRY_NAME = "OAUTH2_preferences.write";

	private static final Pattern _baseIdPattern = Pattern.compile(
		"(.{8})(.{4})(.{4})(.{4})(.*)");

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}