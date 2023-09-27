/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.osgi.commands;

import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {
		"osgi.command.function=listScopes", "osgi.command.scope=oauth2"
	},
	service = OAuth2OSGiCommands.class
)
public class OAuth2OSGiCommands {

	public void listScopes() {
		listScopes(_portal.getDefaultCompanyId());
	}

	public void listScopes(long companyId) {
		Collection<String> scopeAliases = ListUtil.sort(
			new ArrayList<>(_scopeLocator.getScopeAliases(companyId)));

		for (String scopeAlias : scopeAliases) {
			System.out.println();
			System.out.println(scopeAlias);

			Collection<LiferayOAuth2Scope> liferayOAuth2Scopes = ListUtil.sort(
				new ArrayList<>(
					_scopeLocator.getLiferayOAuth2Scopes(
						companyId, scopeAlias)),
				Comparator.comparing(LiferayOAuth2Scope::getScope));

			for (LiferayOAuth2Scope liferayOAuth2Scope : liferayOAuth2Scopes) {
				Bundle bundle = liferayOAuth2Scope.getBundle();

				System.out.println(
					StringBundler.concat(
						"    ", liferayOAuth2Scope.getScope(), " (",
						liferayOAuth2Scope.getApplicationName(), " [",
						bundle.getBundleId(), "])"));
			}
		}

		System.out.println();
	}

	@Reference
	private Portal _portal;

	@Reference
	private ScopeLocator _scopeLocator;

}