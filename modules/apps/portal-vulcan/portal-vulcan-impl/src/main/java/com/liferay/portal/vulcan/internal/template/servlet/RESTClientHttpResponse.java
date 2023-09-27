/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.template.servlet;

import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public class RESTClientHttpResponse extends DummyHttpServletResponse {

	@Override
	public String getContentType() {
		return _contentType;
	}

	@Override
	public void setContentType(String contentType) {
		_contentType = contentType;
	}

	@Override
	public void setHeader(String name, String value) {
		if (Objects.equals(name, HttpHeaders.CONTENT_TYPE)) {
			_contentType = value;
		}

		super.setHeader(name, value);
	}

	private String _contentType;

}