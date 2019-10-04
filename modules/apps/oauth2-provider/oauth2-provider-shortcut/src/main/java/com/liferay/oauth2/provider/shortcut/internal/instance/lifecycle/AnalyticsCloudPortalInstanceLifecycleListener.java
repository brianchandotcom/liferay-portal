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
import com.liferay.oauth2.provider.constants.OAuth2ProviderActionKeys;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.oauth2.provider.scope.spi.prefix.handler.PrefixHandlerFactory;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.oauth2.provider.scope.spi.scope.mapper.ScopeMapper;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.oauth2.provider.service.OAuth2ApplicationScopeAliasesLocalService;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.oauth2.provider.shortcut.internal.constants.OAuth2ProviderShortcutConstants;
import com.liferay.oauth2.provider.shortcut.internal.spi.scope.finder.OAuth2ProviderShortcutScopeFinder;
import com.liferay.oauth2.provider.util.OAuth2SecureRandomGenerator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.service.ContactService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.PortalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.security.service.access.policy.configuration.SAPSystemEntry;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(
	immediate = true,
	property = {
		"osgi.jaxrs.name=liferay-json-web-services-analytics",
		"sap.scope.finder=true", "sap.system.entry=OAUTH2_analytics.read",
		"sap.system.entry=OAUTH2_analytics.write"
	},
	service = {PortalInstanceLifecycleListener.class, SAPSystemEntry.class}
)
public class AnalyticsCloudPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener implements SAPSystemEntry {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		OAuth2Application oAuth2Application = _addOAuth2Application(company);

		_addResourcePermissions(oAuth2Application);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scopeAliasesList = new ArrayList<>(_SAP_ENTRY_OBJECT_ARRAYS.length);

		for (String[] sapEntryObjectArray : _SAP_ENTRY_OBJECT_ARRAYS) {
			_scopeAliasesList.add(
				StringUtil.replaceFirst(
					sapEntryObjectArray[0], "OAUTH2_", StringPool.BLANK));
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"osgi.jaxrs.name",
			OAuth2ProviderShortcutConstants.APPLICATION_NAME);
		properties.put("sap.scope.finder", true);

		_serviceRegistration = bundleContext.registerService(
			new String[] {
				ApplicationDescriptor.class.getName(),
				PrefixHandlerFactory.class.getName(),
				ScopeFinder.class.getName(), ScopeMapper.class.getName()
			},
			new OAuth2ProviderShortcutScopeFinder(_sapEntryLocalService),
			properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private OAuth2Application _addOAuth2Application(Company company)
		throws Exception {

		DynamicQuery dynamicQuery =
			_oAuth2ApplicationLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(company.getCompanyId()));

		Property nameProperty = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(nameProperty.eq(_APPLICATION_NAME));

		List<OAuth2Application> oAuth2Applications =
			_oAuth2ApplicationLocalService.dynamicQuery(dynamicQuery);

		if (!oAuth2Applications.isEmpty()) {
			return oAuth2Applications.get(0);
		}

		User user = _userLocalService.getDefaultUser(company.getCompanyId());

		_addSAPEntries(company.getCompanyId(), user.getUserId());

		OAuth2Application oAuth2Application =
			_oAuth2ApplicationLocalService.addOAuth2Application(
				company.getCompanyId(), user.getUserId(), user.getScreenName(),
				new ArrayList<GrantType>() {
					{
						add(GrantType.AUTHORIZATION_CODE);
						add(GrantType.REFRESH_TOKEN);
					}
				},
				user.getUserId(),
				OAuth2SecureRandomGenerator.generateClientId(),
				ClientProfile.WEB_APPLICATION.id(),
				OAuth2SecureRandomGenerator.generateClientSecret(), null, null,
				"https://analytics.liferay.com", 0, _APPLICATION_NAME, null,
				Collections.singletonList(
					"https://analytics.liferay.com/oauth/receive"),
				_scopeAliasesList, new ServiceContext());

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/logo.png");

		_oAuth2ApplicationLocalService.updateIcon(
			oAuth2Application.getOAuth2ApplicationId(), inputStream);

		_createOAuth2ScopeGrants(oAuth2Application);

		return oAuth2Application;
	}

	private void _addResourcePermissions(OAuth2Application oAuth2Application)
		throws Exception {

		Role role = _roleLocalService.fetchRole(
			oAuth2Application.getCompanyId(),
			RoleConstants.ANALYTICS_ADMINISTRATOR);

		if (role == null) {
			return;
		}

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				oAuth2Application.getCompanyId(),
				OAuth2Application.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(oAuth2Application.getPrimaryKey()),
				role.getRoleId());

		if (resourcePermission != null) {
			return;
		}

		_resourcePermissionLocalService.setResourcePermissions(
			oAuth2Application.getCompanyId(), OAuth2Application.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(oAuth2Application.getPrimaryKey()), role.getRoleId(),
			new String[] {
				ActionKeys.VIEW, OAuth2ProviderActionKeys.ACTION_CREATE_TOKEN
			});
	}

	private void _addSAPEntries(long companyId, long userId)
		throws PortalException {

		Class<?> clazz = getClass();

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

	private void _createOAuth2ScopeGrants(OAuth2Application oAuth2Application)
		throws PortalException {

		for (String scope : _SEGMENTS_ASAH_DEFAULT_OAUTH2_SCOPE_GRANTS) {
			_oAuth2ScopeGrantLocalService.createOAuth2ScopeGrant(
				oAuth2Application.getCompanyId(),
				oAuth2Application.getOAuth2ApplicationScopeAliasesId(),
				"Liferay.Segments.Asah.REST",
				"com.liferay.segments.asah.rest.impl", scope,
				Collections.singletonList(
					"Liferay.Segments.Asah.REST.everything"));
		}

		_oAuth2ApplicationLocalService.updateOAuth2Application(
			oAuth2Application);
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

	private static final String[] _SEGMENTS_ASAH_DEFAULT_OAUTH2_SCOPE_GRANTS = {
		"DELETE", "GET", "POST"
	};

	@Reference(
		target = "(indexer.class.name=com.liferay.document.library.kernel.model.DLFileEntry)"
	)
	private Indexer<DLFileEntry> _indexer;

	@Reference
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Reference
	private OAuth2ApplicationScopeAliasesLocalService
		_oAuth2ApplicationScopeAliasesLocalService;

	@Reference
	private OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private List<String> _scopeAliasesList;
	private ServiceRegistration<?> _serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

}