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

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class MenuItem
	extends com.liferay.portal.kernel.servlet.taglib.ui.MenuItem {

	public MenuItem(
		Map<String, Object> anchorData, String id, String label, String url) {

		_anchorData = anchorData;
		_id = id;
		_label = label;
		_url = url;

		_cssClass = StringPool.BLANK;
	}

	public MenuItem(
		Map<String, Object> anchorData, String cssClass, String id,
		String label, String url) {

		_anchorData = anchorData;
		_cssClass = cssClass;
		_id = id;
		_label = label;
		_url = url;
	}

	public MenuItem(String label, String url) {
		_id = StringPool.BLANK;
		_label = label;
		_url = url;

		_anchorData = null;
		_cssClass = StringPool.BLANK;
	}

	public MenuItem(String id, String label, String url) {
		_id = id;
		_label = label;
		_url = url;

		_anchorData = null;
		_cssClass = StringPool.BLANK;
	}

	public MenuItem(String cssClass, String id, String label, String url) {
		_cssClass = cssClass;
		_id = id;
		_label = label;
		_url = url;

		_anchorData = null;
	}

	public Map<String, Object> getAnchorData() {
		return _anchorData;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getId() {
		return _id;
	}

	@Override
	public String getLabel() {
		return _label;
	}

	public String getUrl() {
		return _url;
	}

	public void setAnchorData(Map<String, Object> anchorData) {
		_anchorData = anchorData;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setLabel(String label) {
		_label = label;
	}

	public void setUrl(String url) {
		_url = url;
	}

	private Map<String, Object> _anchorData;
	private final String _cssClass;
	private String _id;
	private String _label;
	private String _url;

}