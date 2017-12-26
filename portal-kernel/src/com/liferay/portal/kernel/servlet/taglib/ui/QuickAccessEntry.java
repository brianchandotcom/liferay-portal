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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Eudaldo Alonso
 */
public class QuickAccessEntry {

	public String getContent() {
		if (Validator.isNotNull(_label)) {
			return _label;
		}

		return _bodySB.toString();
	}

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
		if (Validator.isNull(_url)) {
			return "javascript:;";
		}

		return _url;
	}

	public void setBody(StringBundler bodySB) {
		_bodySB = bodySB;
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

	public void setURL(String url) {
		_url = url;
	}

	private StringBundler _bodySB;
	private String _data;
	private String _id;
	private String _label;
	private String _onClick;
	private String _url;

}