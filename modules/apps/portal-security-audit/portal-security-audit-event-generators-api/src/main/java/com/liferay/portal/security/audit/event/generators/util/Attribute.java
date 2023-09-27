/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.event.generators.util;

import com.liferay.petra.string.StringPool;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
public class Attribute {

	public Attribute(String name) {
		this(name, StringPool.BLANK, StringPool.BLANK);
	}

	public Attribute(String name, Object newValue, Object oldValue) {
		_name = name;
		_newValue = newValue;
		_oldValue = oldValue;
	}

	public String getName() {
		return _name;
	}

	public Object getNewValue() {
		return _newValue;
	}

	public Object getOldValue() {
		return _oldValue;
	}

	private final String _name;
	private final Object _newValue;
	private final Object _oldValue;

}