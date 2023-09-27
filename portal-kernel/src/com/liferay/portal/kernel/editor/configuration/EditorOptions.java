/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public String getUploadItemReturnType() {
		return _uploadItemReturnType;
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

	public void setUploadItemReturnType(String uploadItemReturnType) {
		_uploadItemReturnType = uploadItemReturnType;
	}

	public void setUploadURL(String uploadURL) {
		_uploadURL = uploadURL;
	}

	private final Map<String, Object> _dynamicAttributes = new HashMap<>();
	private boolean _textMode;
	private String _uploadItemReturnType;
	private String _uploadURL;

}