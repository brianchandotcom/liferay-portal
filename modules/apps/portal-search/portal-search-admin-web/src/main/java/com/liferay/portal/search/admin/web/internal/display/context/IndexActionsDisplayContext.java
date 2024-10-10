/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import java.util.Map;

/**
 * @author Olivia Yu
 */
public class IndexActionsDisplayContext {

	public Map<String, Object> getData() {
		return _data;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	private Map<String, Object> _data;

}