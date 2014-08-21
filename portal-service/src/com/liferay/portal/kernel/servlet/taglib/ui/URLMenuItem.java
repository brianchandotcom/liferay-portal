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
public class URLMenuItem extends MenuItem {

	public URLMenuItem(
		String id, String iconCssClass, String message, String target,
		String url) {

		super(id, iconCssClass, message);

		_target = target;
		_url = url;
	}

	public String getMethod() {
		return _method;
	}

	public String getTarget() {
		return _target;
	}

	public String getURL() {
		return _url;
	}

	public boolean isUseDialog() {
		return _useDialog;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setUseDialog(boolean useDialog) {
		_useDialog = useDialog;
	}

	private String _method;
	private String _target;
	private String _url;
	private boolean _useDialog;

}