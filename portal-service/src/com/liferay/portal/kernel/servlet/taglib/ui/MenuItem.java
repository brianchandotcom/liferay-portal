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

package com.liferay.portal.kernel.servlet.taglib.ui;

/**
 * @author Iván Zaera
 */
public abstract class MenuItem {

	public MenuItem(String id, String iconCssClass, String message) {
		_id = id;
		_iconCssClass = iconCssClass;
		_message = message;
	}

	public String getIconCssClass() {
		return _iconCssClass;
	}

	public String getId() {
		return _id;
	}

	public String getMessage() {
		return _message;
	}

	private String _iconCssClass;
	private String _id;
	private String _message;

}