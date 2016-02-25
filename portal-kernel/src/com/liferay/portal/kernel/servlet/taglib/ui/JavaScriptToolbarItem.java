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
public class JavaScriptToolbarItem
	extends ToolbarItem implements JavaScriptUIItem {

	public String getJavaScript() {
		return _javaScript;
	}

	@Override
	public String getOnClick() {
		return _onClick;
	}

	public void setJavaScript(String javaScript) {
		_javaScript = javaScript;
	}

	@Override
	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	private String _javaScript;
	private String _onClick;

}