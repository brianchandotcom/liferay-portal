/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.system;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class SystemObjectEntry {

	public SystemObjectEntry(long classPK, Map<String, Object> values) {
		_classPK = classPK;
		_values = values;
	}

	public long getClassPK() {
		return _classPK;
	}

	public Map<String, Object> getValues() {
		return _values;
	}

	private final long _classPK;
	private final Map<String, Object> _values;

}