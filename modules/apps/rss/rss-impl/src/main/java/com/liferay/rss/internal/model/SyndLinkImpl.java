/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.internal.model;

import com.liferay.rss.model.SyndLink;

/**
 * @author Shuyang Zhou
 */
public class SyndLinkImpl implements SyndLink {

	@Override
	public String getHref() {
		return _href;
	}

	@Override
	public long getLength() {
		return _length;
	}

	@Override
	public String getRel() {
		return _rel;
	}

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public void setHref(String href) {
		_href = href;
	}

	@Override
	public void setLength(long length) {
		_length = length;
	}

	@Override
	public void setRel(String rel) {
		_rel = rel;
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	private String _href;
	private long _length;
	private String _rel;
	private String _type;

}