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

package com.liferay.configuration.admin.web.internal.display.context;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.Serializable;

/**
 * @author Drew Brokke
 */
public class ConfigurationScopeDisplayContext {

	public ConfigurationScopeDisplayContext(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		_scope = scope;
		_scopePK = scopePK;
	}

	public ExtendedObjectClassDefinition.Scope getScope() {
		return _scope;
	}

	public Serializable getScopePK() {
		return _scopePK;
	}

	private final ExtendedObjectClassDefinition.Scope _scope;
	private final Serializable _scopePK;

}