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

package com.liferay.social.kernel.model;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialRequestFeedEntry {

	public SocialRequestFeedEntry(String title, String body) {
		_title = title;
		_body = body;
	}

	public String getBody() {
		return _body;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getTitle() {
		return _title;
	}

	public void setBody(String body) {
		_body = body;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _body;
	private String _portletId;
	private String _title;

}