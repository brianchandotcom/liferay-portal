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

package com.liferay.portal.configuration.settings.internal;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Drew Brokke
 */
public class ScopeKey {

	public ScopeKey(Class<?> objectClass, Scope scope, String scopePrimKey) {
		Objects.requireNonNull(
			objectClass,
			"The objectClass parameter must not be null. A ScopeKey must " +
				"correspond to an existing configuration bean class.");

		Objects.requireNonNull(
			scope,
			"The scope parameter must not be null. A ScopeKey must " +
				"correspond to an existing scope from " +
					"ExtendedObjectClassDefinition.Scope.");

		if (scope.equals(Scope.SYSTEM)) {
			throw new IllegalArgumentException(
				"A ScopeKey can only be used for the following scopes from " +
					"ExtendedObjectClassDefinition.Scope: COMPANY, GROUP, " +
						"PORTLET_INSTANCE.");
		}

		if (Validator.isNull(scopePrimKey)) {
			throw new IllegalArgumentException(
				"The scopePrimKey parameter must not be null. A ScopeKey " +
					"must correspond to a primary key for the given scope.");
		}

		_objectClass = objectClass;
		_scope = scope;
		_scopePrimKey = scopePrimKey;
	}

	@Override
	public boolean equals(Object obj) {
		ScopeKey otherScopeKey = (ScopeKey)obj;

		if (_objectClass.equals(otherScopeKey.getObjectClass()) &&
			_scope.equals(otherScopeKey.getScope()) &&
			_scopePrimKey.equals(otherScopeKey.getScopePrimKey())) {

			return true;
		}

		return false;
	}

	public Class<?> getObjectClass() {
		return _objectClass;
	}

	public Scope getScope() {
		return _scope;
	}

	public String getScopePrimKey() {
		return _scopePrimKey;
	}

	@Override
	public int hashCode() {
		StringBundler sb = new StringBundler(3);

		sb.append(_objectClass.getName());
		sb.append(_scope.getValue());
		sb.append(_scopePrimKey);

		String s = sb.toString();

		return s.hashCode();
	}

	private final Class<?> _objectClass;
	private final Scope _scope;
	private final String _scopePrimKey;

}