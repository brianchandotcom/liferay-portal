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

package com.liferay.portal.configuration.settings.internal.scoped.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Drew Brokke
 */
public class ScopeKey {

	public ScopeKey(Class<?> objectClass, Scope scope, String scopePrimKey) {
		Objects.requireNonNull(
			objectClass,
			"The object class parameter must not be null. A scope key must " +
				"correspond to an existing configuration bean class.");

		Objects.requireNonNull(
			scope,
			StringBundler.concat(
				"The scope parameter must not be null. A scope key must ",
				"correspond to an existing scope from ", Scope.class.getName(),
				"."));

		if (scope.equals(Scope.SYSTEM)) {
			Stream<Scope> scopeStream = Arrays.stream(Scope.values());

			String scopeNames = scopeStream.filter(
				scope1 -> !scope1.equals(Scope.SYSTEM)
			).map(
				scope1 -> scope1.name()
			).collect(
				Collectors.joining(", ")
			);

			throw new IllegalArgumentException(
				StringBundler.concat(
					"A scope key can only be used for the following scopes ",
					"from ", Scope.class.getName(), ": ", scopeNames, "."));
		}

		if (Validator.isNull(scopePrimKey)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"The scope primary key parameter must not be null. A ",
					"scope key must correspond to a primary key for the given ",
					"scope."));
		}

		_objectClass = objectClass;
		_scope = scope;
		_scopePrimKey = scopePrimKey;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ScopeKey)) {
			return false;
		}

		ScopeKey scopeKey = (ScopeKey)obj;

		if (Objects.equals(_objectClass, scopeKey.getObjectClass()) &&
			Objects.equals(_scope, scopeKey.getScope()) &&
			Objects.equals(_scopePrimKey, scopeKey.getScopePrimKey())) {

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
		String s = StringBundler.concat(
			_objectClass.getName(), _scope.getValue(), _scopePrimKey);

		return s.hashCode();
	}

	private final Class<?> _objectClass;
	private final Scope _scope;
	private final String _scopePrimKey;

}