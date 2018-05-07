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