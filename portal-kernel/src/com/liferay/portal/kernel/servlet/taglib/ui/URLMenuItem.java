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