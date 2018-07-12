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

import com.liferay.rss.model.SyndContent;

/**
 * @author Shuyang Zhou
 */
public class SyndContentImpl implements SyndContent {

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	@Override
	public void setValue(String value) {
		_value = value;
	}

	private String _type;
	private String _value;

}