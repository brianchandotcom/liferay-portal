/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.liferay.ScopeContext;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = ScopeChecker.class)
public class ThreadLocalScopeChecker implements ScopeChecker {

	@Override
	public boolean checkAllScopes(String... scopes) {
		_checkOAuth2ScopeGrantLocalService();

		if (Validator.isNull(scopes)) {
			throw new IllegalArgumentException("Scopes are null");
		}

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants = new ArrayList<>(
			_getOAuth2ScopeGrants());

		if (scopes.length > oAuth2ScopeGrants.size()) {
			return false;
		}

		for (String scope : scopes) {
			if (Validator.isNull(scope)) {
				throw new IllegalArgumentException("Scope is null");
			}

			if (!oAuth2ScopeGrants.removeIf(o -> scope.equals(o.getScope()))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkAnyScope(String... scopes) {
		_checkOAuth2ScopeGrantLocalService();

		if (Validator.isNull(scopes)) {
			throw new IllegalArgumentException("Scopes are null");
		}

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants =
			_getOAuth2ScopeGrants();

		for (String scope : scopes) {
			if (Validator.isNull(scope)) {
				throw new IllegalArgumentException("Scope is null");
			}

			for (OAuth2ScopeGrant oAuth2ScopeGrant : oAuth2ScopeGrants) {
				if (scope.equals(oAuth2ScopeGrant.getScope())) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean checkScope(String scope) {
		_checkOAuth2ScopeGrantLocalService();

		if (Validator.isNull(scope)) {
			throw new IllegalArgumentException("Scope is null");
		}

		for (OAuth2ScopeGrant oAuth2ScopeGrant : _getOAuth2ScopeGrants()) {
			if (scope.equals(oAuth2ScopeGrant.getScope())) {
				return true;
			}
		}

		return false;
	}

	private void _checkOAuth2ScopeGrantLocalService() {
		if (_oAuth2ScopeGrantLocalServiceSnapshot.get() == null) {
			throw new IllegalStateException(
				"ScopeChecker dependency upon OAuth2ScopeGrantLocalService " +
					"is not satisfied");
		}
	}

	private Collection<OAuth2ScopeGrant> _getOAuth2ScopeGrants() {
		OAuth2ScopeGrantLocalService oAuth2ScopeGrantLocalService =
			_oAuth2ScopeGrantLocalServiceSnapshot.get();

		return oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
			_threadLocalScopeContext.getCompanyId(),
			_threadLocalScopeContext.getApplicationName(),
			_threadLocalScopeContext.getBundleSymbolicName(),
			_threadLocalScopeContext.getAccessToken());
	}

	private static final Snapshot<OAuth2ScopeGrantLocalService>
		_oAuth2ScopeGrantLocalServiceSnapshot = new Snapshot<>(
			ThreadLocalScopeChecker.class, OAuth2ScopeGrantLocalService.class,
			null, true);

	@Reference(
		target = "(component.name=com.liferay.oauth2.provider.scope.internal.liferay.ThreadLocalScopeContext)"
	)
	private ScopeContext _threadLocalScopeContext;

}