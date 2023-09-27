/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

/**
 * @author Neil Griffin
 */
public class RenderData {

	public RenderData(String contentType, String content) {
		_contentType = contentType;
		_content = content;
	}

	public String getContent() {
		return _content;
	}

	public String getContentType() {
		return _contentType;
	}

	private final String _content;
	private final String _contentType;

}