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

package com.liferay.portal.kernel.editor.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergio González
 */
public class EditorOptions {

	public Map<String, Object> getDynamicAttributes() {
		return _dynamicAttributes;
	}

	public String getUploadURL() {
		return _uploadURL;
	}

	public boolean isTextMode() {
		return _textMode;
	}

	public void setTextMode(boolean textMode) {
		_textMode = textMode;
	}

	public void setUploadURL(String uploadURL) {
		_uploadURL = uploadURL;
	}

	private Map<String, Object> _dynamicAttributes = new HashMap<>();
	private boolean _textMode;
	private String _uploadURL;

}