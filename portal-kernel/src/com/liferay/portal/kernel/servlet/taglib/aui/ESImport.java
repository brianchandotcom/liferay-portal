/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.aui;

import java.util.Objects;

/**
 * @author Iván Zaera Avellón
 */
public final class ESImport {

	/**
	 * Construct an ESM import for a given symbol and module.
	 *
	 * JavaScript equivalent would be:
	 *
	 * import {symbol} from module;
	 *
	 * @review
	 */
	public ESImport(String module, String symbol) {
		this(symbol, module, symbol);
	}

	/**
	 * Construct an ESM import for a given symbol and module, where the symbol
	 * is aliased.
	 *
	 * JavaScript equivalent would be:
	 *
	 * import {symbol as alias} from module;
	 *
	 * @review
	 */
	public ESImport(String alias, String module, String symbol) {
		_alias = alias;
		_module = module;
		_symbol = symbol;
	}

	/**
	 * The semantics for the equality of two {@link ESImport} objects are that
	 * they are the same as long as the symbol and module match, not taking the
	 * alias into account.
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

		ESImport esImport = (ESImport)object;

		if (_module.equals(esImport._module) &&
			_symbol.equals(esImport._symbol)) {

			return true;
		}

		return false;
	}

	public String getAlias() {
		return _alias;
	}

	public String getModule() {
		return _module;
	}

	public String getSymbol() {
		return _symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_module, _symbol);
	}

	private String _alias;
	private String _module;
	private String _symbol;

}