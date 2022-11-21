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

package com.liferay.oauth2.provider.scope.internal;

import com.liferay.oauth2.provider.model.OAuth2ScopeGrant;
import com.liferay.oauth2.provider.scope.ScopeChecker;
import com.liferay.oauth2.provider.scope.liferay.ScopeContext;
import com.liferay.oauth2.provider.service.OAuth2ScopeGrantLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Carlos Sierra Andrés
 */
@Component(service = {ScopeChecker.class, ScopeContext.class})
public class ThreadLocalScopeContextScopeChecker
	implements ScopeChecker, ScopeContext {

	@Override
	public boolean checkAllScopes(String... scopes) {
		_checkOAuth2ScopeGrantLocalService();

		if (Validator.isNull(scopes)) {
			throw new IllegalArgumentException("Scopes are null");
		}

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants = new ArrayList<>(
			_oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
				_companyIdThreadLocal.get(), _applicationNameThreadLocal.get(),
				_bundleSymbolicNameThreadLocal.get(),
				_accessTokenThreadLocal.get()));

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
			_oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
				_companyIdThreadLocal.get(), _applicationNameThreadLocal.get(),
				_bundleSymbolicNameThreadLocal.get(),
				_accessTokenThreadLocal.get());

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

		Collection<OAuth2ScopeGrant> oAuth2ScopeGrants =
			_oAuth2ScopeGrantLocalService.getOAuth2ScopeGrants(
				_companyIdThreadLocal.get(), _applicationNameThreadLocal.get(),
				_bundleSymbolicNameThreadLocal.get(),
				_accessTokenThreadLocal.get());

		for (OAuth2ScopeGrant oAuth2ScopeGrant : oAuth2ScopeGrants) {
			if (scope.equals(oAuth2ScopeGrant.getScope())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void clear() {
		_accessTokenThreadLocal.remove();
		_applicationNameThreadLocal.remove();
		_bundleSymbolicNameThreadLocal.remove();
		_companyIdThreadLocal.remove();
	}

	@Override
	public void setAccessToken(String accessToken) {
		_accessTokenThreadLocal.set(accessToken);
	}

	@Override
	public void setApplicationName(String applicationName) {
		_applicationNameThreadLocal.set(applicationName);
	}

	@Override
	public void setBundle(Bundle bundle) {
		String symbolicName = null;

		if (bundle != null) {
			symbolicName = bundle.getSymbolicName();
		}

		_bundleSymbolicNameThreadLocal.set(symbolicName);
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyIdThreadLocal.set(companyId);
	}

	private void _checkOAuth2ScopeGrantLocalService() {
		if (_oAuth2ScopeGrantLocalService == null) {
			throw new IllegalStateException(
				"ScopeChecker dependency upon OAuth2ScopeGrantLocalService " +
					"is not satisfied");
		}
	}

	private final ThreadLocal<String> _accessTokenThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	private final ThreadLocal<String> _applicationNameThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	private final ThreadLocal<String> _bundleSymbolicNameThreadLocal =
		ThreadLocal.withInitial(() -> StringPool.BLANK);
	private final ThreadLocal<Long> _companyIdThreadLocal =
		ThreadLocal.withInitial(() -> 0L);

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile OAuth2ScopeGrantLocalService _oAuth2ScopeGrantLocalService;

}