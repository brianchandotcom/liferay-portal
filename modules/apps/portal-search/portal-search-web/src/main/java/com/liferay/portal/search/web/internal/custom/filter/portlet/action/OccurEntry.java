/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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