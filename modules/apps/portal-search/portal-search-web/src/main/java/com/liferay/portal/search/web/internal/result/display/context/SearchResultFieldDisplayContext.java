/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.result.display.context;

import java.io.Serializable;

/**
 * @author André de Oliveira
 */
public class SearchResultFieldDisplayContext implements Serializable {

	public String getName() {
		return _name;
	}

	public String getValuesToString() {
		return _valuesToString;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValuesToString(String valuesToString) {
		_valuesToString = valuesToString;
	}

	private String _name;
	private String _valuesToString;

}