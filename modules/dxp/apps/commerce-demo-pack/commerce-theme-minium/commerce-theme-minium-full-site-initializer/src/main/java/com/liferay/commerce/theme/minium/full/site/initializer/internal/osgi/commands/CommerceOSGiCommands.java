/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.minium.full.site.initializer.internal.osgi.commands;

import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolver;
import com.liferay.commerce.theme.minium.full.site.initializer.internal.importer.CommerceMLForecastImporter;
import com.liferay.commerce.theme.minium.full.site.initializer.internal.importer.CommerceMLRecommendationImporter;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(
	property = {
		"osgi.command.function=importForecasts",
		"osgi.command.function=importRecommendations",
		"osgi.command.scope=commerce"
	},
	service = OSGiCommands.class
)
public class CommerceOSGiCommands implements OSGiCommands {

	public void importForecasts(long siteId) throws Exception {
		User user = _getImportUserByGroupId(siteId);

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			_fullSiteInitializerDependencyResolver.getJSON("forecasts.json"));

		_commerceMLForecastImporter.importCommerceMLForecasts(
			jsonArray, siteId, user.getUserId());
	}

	public void importRecommendations(
			long siteId, String externalReferenceCodePrefix)
		throws Exception {

		User user = _getImportUserByGroupId(siteId);

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			_fullSiteInitializerDependencyResolver.getJSON(
				"recommendations.json"));

		_commerceMLRecommendationImporter.importCommerceMLRecommendations(
			jsonArray, externalReferenceCodePrefix, siteId, user.getUserId());
	}

	private User _getImportUserByGroupId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		Company company = _companyLocalService.getCompanyById(
			group.getCompanyId());

		Role role = _roleLocalService.fetchRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		List<User> roleUsers = _userLocalService.getRoleUsers(role.getRoleId());

		User user = roleUsers.get(0);

		PermissionChecker permissionChecker = _permissionCheckerFactory.create(
			user);

		PrincipalThreadLocal.setName(user.getUserId());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		return user;
	}

	@Reference
	private CommerceMLForecastImporter _commerceMLForecastImporter;

	@Reference
	private CommerceMLRecommendationImporter _commerceMLRecommendationImporter;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = "(site.initializer.key=minium-full-initializer)")
	private SiteInitializerDependencyResolver
		_fullSiteInitializerDependencyResolver;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}