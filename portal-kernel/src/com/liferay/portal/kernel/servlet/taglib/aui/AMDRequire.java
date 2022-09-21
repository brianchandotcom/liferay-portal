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

package com.liferay.portal.kernel.servlet.taglib.aui;

import java.util.Objects;

/**
 * @author Iván Zaera Avellón
 */
public final class AMDRequire {

	/**
	 * Construct an AMD require for a given module. Alias is computed using
	 * {@link VariableUtil}
	 *
	 * @review
	 */
	public AMDRequire(String module) {
		this(VariableUtil.generateVariable(module), module);
	}

	/**
	 * Construct an AMD require for a given module.
	 *
	 * JavaScript equivalent would be:
	 *
	 * Liferay.Loader.require('module', function(alias) {});
	 *
	 * @review
	 */
	public AMDRequire(String alias, String module) {
		_alias = alias;
		_module = module;
	}

	/**
	 * The semantics for the equality of two {@link AMDRequire} objects are that
	 * they are the same as long as the module is the same, not taking the alias
	 * into account.
	 *
	 * @review
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		AMDRequire amdRequire = (AMDRequire)object;

		return _module.equals(amdRequire._module);
	}

	public String getAlias() {
		return _alias;
	}

	public String getModule() {
		return _module;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_module);
	}

	private final String _alias;
	private final String _module;

}