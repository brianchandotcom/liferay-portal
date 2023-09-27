/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.model.BaseModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 */
public class BreadcrumbEntry {

	public BaseModel<?> getBaseModel() {
		return _baseModel;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public Object getData(String key) {
		return _data.get(key);
	}

	public String getTitle() {
		return _title;
	}

	public String getURL() {
		return _url;
	}

	public boolean isBrowsable() {
		return _browsable;
	}

	public void putData(String key, Object value) {
		if (_data == null) {
			_data = new HashMap<>();
		}

		_data.put(key, value);
	}

	public void setBaseModel(BaseModel<?> baseModel) {
		_baseModel = baseModel;
	}

	public void setBrowsable(boolean browsable) {
		_browsable = browsable;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setURL(String url) {
		_url = url;
	}

	private BaseModel<?> _baseModel;
	private boolean _browsable = true;
	private Map<String, Object> _data;
	private String _title;
	private String _url;

}