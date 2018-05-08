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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.petra.string.StringPool;

/**
 * @author Eudaldo Alonso
 */
public class ManagementBarFilterItem {

	public ManagementBarFilterItem(boolean active, String label, String url) {
		_active = active;
		_label = label;
		_url = url;

		_id = null;
	}

	public ManagementBarFilterItem(
		boolean active, String id, String label, String url) {

		_active = active;
		_id = id;
		_label = label;
		_url = url;
	}

	public ManagementBarFilterItem(String label, String url) {
		_label = label;
		_url = url;

		_active = false;
		_id = StringPool.BLANK;
	}

	public ManagementBarFilterItem(String id, String label, String url) {
		_id = id;
		_label = label;
		_url = url;

		_active = false;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getUrl() {
		return _url;
	}

	public boolean isActive() {
		return _active;
	}

	private final boolean _active;
	private final String _id;
	private final String _label;
	private final String _url;

}