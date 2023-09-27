/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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