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

package com.liferay.oauth2.provider.shortcut.internal.instance.lifecycle;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.BigEndianCodec;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.security.SecureRandomUtil;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.ContactService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PortalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class OAuth2ProviderShortcutPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstancePreunregistered(Company company)
		throws Exception {

		_oAuth2ApplicationLocalService.deleteOAuth2Applications(
			company.getCompanyId());
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (_hasOAuth2Application(company.getCompanyId())) {
			return;
		}

		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		_addSAPEntries(company.getCompanyId(), user.getUserId());

		Stream<String[]> stream = Arrays.stream(_SAP_ENTRY_OBJECT_ARRAYS);

		List<String> featuresList = stream.map(
			sapEntryObjectArray -> StringUtil.replaceFirst(
				sapEntryObjectArray[0], "OAUTH2_", StringPool.BLANK)
		).collect(
			Collectors.toList()
		);

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				company.getCompanyId(), user.getUserId(), user.getScreenName(),
				new ArrayList<GrantType>() {
					{
						add(GrantType.AUTHORIZATION_CODE);
						add(GrantType.REFRESH_TOKEN);
					}
				},
				_generateRandomId(), ClientProfile.WEB_APPLICATION.id(),
				_generateRandomSecret(), null, null,
				"https://analytics.liferay.com", 0, _APPLICATION_NAME, null,
				Collections.singletonList(
					"https://analytics.liferay.com/oauth/receive"),
				featuresList, new ServiceContext());

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/logo.png");

		_oAuth2ApplicationLocalService.updateIcon(
			oAuth2Application.getOAuth2ApplicationId(), inputStream);
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

	private void _addSAPEntries(long companyId, long userId)
		throws PortalException {

		Class<?> clazz =
			OAuth2ProviderShortcutPortalInstanceLifecycleListener.class;

		ResourceBundleLoader resourceBundleLoader =
			new AggregateResourceBundleLoader(
				ResourceBundleUtil.getResourceBundleLoader(
					"content.Language", clazz.getClassLoader()),
				LanguageResources.RESOURCE_BUNDLE_LOADER);

		for (String[] sapEntryObjectArray : _SAP_ENTRY_OBJECT_ARRAYS) {
			String sapEntryName = sapEntryObjectArray[0];

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				companyId, sapEntryName);

			if (sapEntry != null) {
				continue;
			}

			Map<Locale, String> titleMap =
				ResourceBundleUtil.getLocalizationMap(
					resourceBundleLoader, sapEntryName);

			_sapEntryLocalService.addSAPEntry(
				userId, sapEntryObjectArray[1], false, true, sapEntryName,
				titleMap, new ServiceContext());
		}
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

	private static final String[][] _SAP_ENTRY_OBJECT_ARRAYS = {
		{
			"OAUTH2_analytics.read",
			StringBundler.concat(
				"com.liferay.portal.security.audit.storage.service.",
				"AuditEventService#getAuditEvents\n",
				ContactService.class.getName(), "#getContact\n",
				GroupService.class.getName(), "#getGroup\n",
				GroupService.class.getName(), "#getGroups\n",
				GroupService.class.getName(), "#getGroupsCount\n",
				GroupService.class.getName(), "#getGtGroups\n",
				OrganizationService.class.getName(), "#fetchOrganization\n",
				OrganizationService.class.getName(), "#getGtOrganizations\n",
				OrganizationService.class.getName(), "#getOrganization\n",
				OrganizationService.class.getName(), "#getOrganizations\n",
				OrganizationService.class.getName(), "#getOrganizationsCount\n",
				OrganizationService.class.getName(), "#getUserOrganizations\n",
				PortalService.class.getName(), "#getBuildNumber\n",
				UserService.class.getName(), "#getCompanyUsers\n",
				UserService.class.getName(), "#getCompanyUsersCount\n",
				UserService.class.getName(), "#getCurrentUser\n",
				UserService.class.getName(), "#getGtCompanyUsers\n",
				UserService.class.getName(), "#getGtOrganizationUsers\n",
				UserService.class.getName(), "#getGtUserGroupUsers\n",
				UserService.class.getName(), "#getOrganizationUsers\n",
				UserService.class.getName(), "#getOrganizationUsersCount\n",
				UserService.class.getName(),
				"#getOrganizationsAndUserGroupsUsersCount\n",
				UserService.class.getName(), "#getUserById\n",
				UserService.class.getName(), "#getUserGroupUsers\n",
				UserGroupService.class.getName(), "#fetchUserGroup\n",
				UserGroupService.class.getName(), "#getGtUserGroups\n",
				UserGroupService.class.getName(), "#getUserGroup\n",
				UserGroupService.class.getName(), "#getUserGroups\n",
				UserGroupService.class.getName(), "#getUserGroupsCount\n",
				UserGroupService.class.getName(), "#getUserUserGroups")
		},
		{
			"OAUTH2_analytics.write",
			CompanyService.class.getName() + "#updatePreferences"
		}
	};

	private static final Pattern _baseIdPattern = Pattern.compile(
		"(.{8})(.{4})(.{4})(.{4})(.*)");

	@Reference(
		target = "(indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	private Indexer<DLFileEntry> _indexer;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}