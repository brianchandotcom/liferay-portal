/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.liferay.petra.string.StringBundler;

/**
 * @author Matthew Kong
 * @author David Arques
 */
public class Author {

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

	@Override
	public String toString() {
		return StringBundler.concat("{id=", _id, ", name=", _name, "}");
	}

	private String _id;
	private String _name;

}