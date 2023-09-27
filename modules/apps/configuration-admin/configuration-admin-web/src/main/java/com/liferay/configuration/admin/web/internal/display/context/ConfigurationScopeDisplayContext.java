/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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