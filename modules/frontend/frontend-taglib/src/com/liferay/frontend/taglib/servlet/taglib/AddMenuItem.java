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

import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Ambrín Chaudhary
 */
public class AddMenuItem extends MenuItem {

	public AddMenuItem(
		java.lang.Object anchorData, String id, String label, String url) {

		setLabel(label);

		_anchorData = anchorData;
		_id = id;
		_url = url;
	}

	public AddMenuItem(String label, String url) {
		setLabel(label);

		_anchorData = null;
		_id = StringPool.BLANK;
		_url = url;
	}

	public AddMenuItem(String id, String label, String url) {
		setLabel(label);

		_anchorData = null;
		_id = id;
		_url = url;
	}

	public java.lang.Object getAnchorData() {
		return _anchorData;
	}

	public String getId() {
		return _id;
	}

	public String getUrl() {
		return _url;
	}

	private final java.lang.Object _anchorData;
	private final String _id;
	private final String _url;

}