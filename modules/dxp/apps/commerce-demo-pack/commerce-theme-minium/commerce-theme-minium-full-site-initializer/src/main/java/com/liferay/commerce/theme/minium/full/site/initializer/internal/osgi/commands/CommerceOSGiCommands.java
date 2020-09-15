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

package com.liferay.commerce.theme.minium.full.site.initializer.internal.osgi.commands;

import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolver;
import com.liferay.commerce.theme.minium.full.site.initializer.internal.importer.CommerceMLRecommendationImporter;
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
	enabled = false, immediate = true,
	property = {
		"osgi.command.function=importRecommendations",
		"osgi.command.scope=commerce"
	},
	service = CommerceOSGiCommands.class
)
public class CommerceOSGiCommands {

	public void importRecommendations(
			long siteId, String externalReferenceCodePrefix)
		throws Exception {

		Group group = _groupLocalService.getGroup(siteId);

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

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			_fullSiteInitializerDependencyResolver.getJSON(
				"recommendations.json"));

		_commerceMLRecommendationImporter.importCommerceMLRecommendations(
			jsonArray, externalReferenceCodePrefix, siteId, user.getUserId());
	}

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