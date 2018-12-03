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

package com.liferay.portal.search.web.internal.custom.filter.portlet.action;

/**
 * @author André de Oliveira
 */
public class OccurEntry {

	public String getName() {
		return _name;
	}

	public String getOccur() {
		return _occur;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOccur(String occur) {
		_occur = occur;
	}

	private String _name;
	private String _occur;

}