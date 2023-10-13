/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.rule.web.internal.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class Product {

	public Product(long cProductId, String name) {
		_cProductId = cProductId;
		_name = name;
	}

	public long getCProductId() {
		return _cProductId;
	}

	public String getName() {
		return _name;
	}

	private final long _cProductId;
	private final String _name;

}