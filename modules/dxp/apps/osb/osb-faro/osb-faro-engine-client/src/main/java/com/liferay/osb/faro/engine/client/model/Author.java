/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class Author {

	public static Author getSystem() {
		Author author = new Author();

		author.setId(_FARO_SYSTEM);
		author.setName(_FARO_SYSTEM);

		return author;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	private static final String _FARO_SYSTEM = "FARO_SYSTEM";

	private String _id;
	private String _name;

}