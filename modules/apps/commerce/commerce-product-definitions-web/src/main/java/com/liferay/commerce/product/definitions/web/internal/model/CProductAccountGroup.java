/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.model;

/**
 * @author Alessio Antonio Rendina
 */
public class CProductAccountGroup {

	public CProductAccountGroup(long accountGroupRelId, String name) {
		_accountGroupRelId = accountGroupRelId;
		_name = name;
	}

	public long getAccountGroupRelId() {
		return _accountGroupRelId;
	}

	public String getName() {
		return _name;
	}

	private final long _accountGroupRelId;
	private final String _name;

}