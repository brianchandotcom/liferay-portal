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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class JavaScriptMenuItem extends MenuItem implements JavaScriptUIItem {

	public Map<String, Object> getData() {
		if (_data == null) {
			_data = new HashMap<>();
		}

		return _data;
	}

	public String getJavaScript() {
		return _javaScript;
	}

	@Override
	public String getOnClick() {
		return _onClick;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setJavaScript(String javaScript) {
		_javaScript = javaScript;
	}

	@Override
	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	private Map<String, Object> _data;
	private String _javaScript;
	private String _onClick;

}