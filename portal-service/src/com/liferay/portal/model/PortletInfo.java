/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class PortletInfo implements Serializable {

	public PortletInfo() {
	}

	public PortletInfo(
		String title, String shortTitle, String keywords, String description) {

		_title = title;
		_shortTitle = shortTitle;
		_keywords = keywords;
		_description = description;
	}

	public String getTitle() {
		return _title;
	}

	public String getShortTitle() {
		return _shortTitle;
	}

	public String getKeywords() {
		return _keywords;
	}

	public String getDescription() {
		return _description;
	}

	private String _description;
	private String _keywords;
	private String _shortTitle;
	private String _title;

}