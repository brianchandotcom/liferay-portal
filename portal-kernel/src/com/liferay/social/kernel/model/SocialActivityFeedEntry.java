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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialActivityFeedEntry {

	public SocialActivityFeedEntry(String title, String body) {
		this(null, title, body);
	}

	public SocialActivityFeedEntry(String link, String title, String body) {
		setLink(link);
		setTitle(title);
		setBody(body);
	}

	public String getBody() {
		return _body;
	}

	public String getLink() {
		return _link;
	}

	public String getPortletId() {
		return _portletId;
	}

	public String getTitle() {
		return _title;
	}

	public void setBody(String body) {
		_body = GetterUtil.getString(body);
	}

	public void setLink(String link) {
		_link = GetterUtil.getString(link);
	}

	public void setPortletId(String portletId) {
		_portletId = GetterUtil.getString(portletId);
	}

	public void setTitle(String title) {
		_title = GetterUtil.getString(title);
	}

	private String _body;
	private String _link;
	private String _portletId = StringPool.BLANK;
	private String _title;

}