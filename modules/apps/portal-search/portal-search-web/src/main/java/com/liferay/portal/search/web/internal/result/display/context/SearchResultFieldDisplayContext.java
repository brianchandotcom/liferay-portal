/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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