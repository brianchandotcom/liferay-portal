/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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