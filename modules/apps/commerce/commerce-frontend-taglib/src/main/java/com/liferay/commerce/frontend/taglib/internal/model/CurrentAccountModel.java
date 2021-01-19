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

	public CurrentAccountModel(long id, String logoURL, String name) {
		_id = id;
		_logoURL = logoURL;
		_name = name;
	}

	public long getId() {
		return _id;
	}

	public String getLogoURL() {
		return _logoURL;
	}

	public String getName() {
		return _name;
	}

	private final long _id;
	private final String _logoURL;
	private final String _name;

}