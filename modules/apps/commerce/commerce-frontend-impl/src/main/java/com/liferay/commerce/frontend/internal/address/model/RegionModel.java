/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.frontend.internal.address.model;

/**
 * @author Marco Leo
 */
public class RegionModel {

	public RegionModel(long id, String name) {
		_id = id;
		_name = name;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	private final long _id;
	private final String _name;

}