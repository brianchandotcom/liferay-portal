/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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