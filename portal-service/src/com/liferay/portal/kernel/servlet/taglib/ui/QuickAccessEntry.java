/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * @author Eudaldo Alonso
 */
public class QuickAccessEntry {

	public static final String ATTRIBUTE_NAME = "QUICK_ACCESS";

	public String getData() {
		return _data;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public String getOnClick() {
		return _onClick;
	}

	public String getURL() {
		return _url;
	}

	public void setData(String data) {
		_data = data;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	private String _id;

	public void setURL(String url) {
		_url = url;
	}

	private String _data;
	private String _label;
	private String _onClick;
	private String _url;

}