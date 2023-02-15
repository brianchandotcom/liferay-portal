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

package com.liferay.headless.builder.internal.operation;

/**
 * @author Carlos Correa
 */
public class HeadlessBuilderEntry {

	public Object getContent() {
		return _content;
	}

	public String getName() {
		return _name;
	}

	public long getPrimaryKey() {
		return _primaryKey;
	}

	public void setContent(Object content) {
		_content = content;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPrimaryKey(long primaryKey) {
		_primaryKey = primaryKey;
	}

	private Object _content;
	private String _name;
	private long _primaryKey;

}