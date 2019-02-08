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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LiferayOAuth2ScopeImpl)) {
			return false;
		}

		LiferayOAuth2ScopeImpl liferayOAuth2ScopeImpl =
			(LiferayOAuth2ScopeImpl)obj;

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