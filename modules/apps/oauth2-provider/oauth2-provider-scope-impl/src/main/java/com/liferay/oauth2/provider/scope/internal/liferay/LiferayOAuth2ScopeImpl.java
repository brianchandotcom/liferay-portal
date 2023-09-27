/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.liferay;

import com.liferay.oauth2.provider.scope.liferay.LiferayOAuth2Scope;

import java.util.Objects;

import org.osgi.framework.Bundle;

/**
 * @author Carlos Sierra Andrés
 */
public class LiferayOAuth2ScopeImpl implements LiferayOAuth2Scope {

	public LiferayOAuth2ScopeImpl(
		String applicationName, Bundle bundle, String scope) {

		_applicationName = applicationName;
		_bundle = bundle;
		_scope = scope;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LiferayOAuth2ScopeImpl)) {
			return false;
		}

		LiferayOAuth2ScopeImpl liferayOAuth2ScopeImpl =
			(LiferayOAuth2ScopeImpl)object;

		if (Objects.equals(
				_applicationName, liferayOAuth2ScopeImpl._applicationName) &&
			Objects.equals(_bundle, liferayOAuth2ScopeImpl._bundle) &&
			Objects.equals(_scope, liferayOAuth2ScopeImpl._scope)) {

			return true;
		}

		return false;
	}

	@Override
	public String getApplicationName() {
		return _applicationName;
	}

	@Override
	public Bundle getBundle() {
		return _bundle;
	}

	@Override
	public String getScope() {
		return _scope;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_applicationName, _bundle, _scope);
	}

	@Override
	public String toString() {
		return getScope() + "@" + getApplicationName();
	}

	private final String _applicationName;
	private final Bundle _bundle;
	private final String _scope;

}