/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class PortletInfo implements Serializable {

	public PortletInfo() {
		this(null, null, null, null);
	}

	public PortletInfo(
		String title, String shortTitle, String keywords, String description) {

		_title = title;
		_shortTitle = shortTitle;
		_keywords = keywords;
		_description = description;
	}

	public String getDescription() {
		return _description;
	}

	public String getKeywords() {
		return _keywords;
	}

	public String getShortTitle() {
		return _shortTitle;
	}

	public String getTitle() {
		return _title;
	}

	private final String _description;
	private final String _keywords;
	private final String _shortTitle;
	private final String _title;

}