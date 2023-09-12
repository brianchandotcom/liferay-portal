/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet;

import java.util.List;

/**
 * @author Neil Griffin
 */
public class Preference {

	public Preference(boolean readOnly, List<String> values) {
		_readOnly = readOnly;
		_values = values;
	}

	public List<String> getValues() {
		return _values;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	private final boolean _readOnly;
	private final List<String> _values;

}