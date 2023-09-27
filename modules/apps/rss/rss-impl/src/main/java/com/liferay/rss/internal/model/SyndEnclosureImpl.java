/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.internal.model;

import com.liferay.rss.model.SyndEnclosure;

/**
 * @author Shuyang Zhou
 */
public class SyndEnclosureImpl implements SyndEnclosure {

	@Override
	public long getLength() {
		return _length;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getUrl() {
		return _url;
	}

	@Override
	public void setLength(long length) {
		_length = length;
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	@Override
	public void setUrl(String url) {
		_url = url;
	}

	private long _length;
	private String _type;
	private String _url;

}