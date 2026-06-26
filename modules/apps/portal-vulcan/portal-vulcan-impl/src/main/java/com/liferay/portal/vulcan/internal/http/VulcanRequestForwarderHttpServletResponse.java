/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.http;

import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public class VulcanRequestForwarderHttpServletResponse
	extends DummyHttpServletResponse {

	@Override
	public String getContentType() {
		return _contentType;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void sendError(int status) {
		_status = status;
	}

	@Override
	public void sendError(int status, String message) {
		_status = status;
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

	@Override
	public void setStatus(int status) {
		_status = status;
	}

	private String _contentType;
	private int _status = HttpServletResponse.SC_OK;

}