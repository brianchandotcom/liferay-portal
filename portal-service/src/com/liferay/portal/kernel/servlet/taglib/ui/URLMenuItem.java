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
public class URLMenuItem extends MenuItem implements URLUIItem {

	public Map<String, Object> getData() {
		if (_data == null) {
			_data = new HashMap<>();
		}

		return _data;
	}

	public String getMethod() {
		return _method;
	}

	@Override
	public String getTarget() {
		return _target;
	}

	@Override
	public String getURL() {
		return _url;
	}

	public boolean isUseDialog() {
		return _useDialog;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setMethod(String method) {
		_method = method;
	}

	@Override
	public void setTarget(String target) {
		_target = target;
	}

	@Override
	public void setURL(String url) {
		_url = url;
	}

	public void setUseDialog(boolean useDialog) {
		_useDialog = useDialog;
	}

	private static final String _TARGET_DEFAULT = "_self";

	private Map<String, Object> _data;
	private String _method;
	private String _target = _TARGET_DEFAULT;
	private String _url;
	private boolean _useDialog;

}