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

package com.liferay.portal.search.web.internal.low.level.search.options.portlet.action;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Bryan Engler
 */
public class ConfigurationDisplayContext {

	public List<String> getConnectionIds() {
		return _connectionIds;
	}

	public void setConnectionIds(List<String> connectionIds) {
		_connectionIds = connectionIds;
	}

	private List<String> _connectionIds = new LinkedList<>();

}