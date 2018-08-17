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

package com.liferay.oauth2.provider.scope.internal.osgi.commands;

import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=listScopes", "osgi.command.scope=oauth2"
	},
	service = OAuth2OSGICommands.class
)
public class OAuth2OSGICommands {

	public void listScopes() {
		listScopes(_portal.getDefaultCompanyId());
	}

	public void listScopes(long companyId) {
		Collection<String> scopeAliases = ListUtil.sort(
			new ArrayList<>(_scopeLocator.getScopeAliases(companyId)));

		for (String alias : scopeAliases) {
			System.out.println();

			System.out.println(alias);

			Collection<LiferayOAuth2Scope> liferayOAuth2Scopes = ListUtil.sort(
				new ArrayList<>(
					_scopeLocator.getLiferayOAuth2Scopes(companyId, alias)),
				Comparator.comparing(LiferayOAuth2Scope::getScope));

			for (LiferayOAuth2Scope liferayOAuth2Scope : liferayOAuth2Scopes) {
				System.out.println(
					StringBundler.concat(
						"    ", liferayOAuth2Scope.getScope(), " (",
						liferayOAuth2Scope.getApplicationName(), ")"));
			}
		}

		System.out.println();
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ScopeLocator _scopeLocator;

}