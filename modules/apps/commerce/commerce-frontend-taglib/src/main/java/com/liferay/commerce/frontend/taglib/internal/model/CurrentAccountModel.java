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

package com.liferay.commerce.frontend.taglib.internal.model;

/**
 * @author Marco Leo
 */
public class CurrentAccountModel {

	public CurrentAccountModel(long id, String name, String thumbnail) {
		_id = id;
		_name = name;
		_thumbnail = thumbnail;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getThumbnail() {
		return _thumbnail;
	}

	private final long _id;
	private final String _name;
	private final String _thumbnail;

}