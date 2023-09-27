/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.jaxrs.exception.mapper;

import com.liferay.petra.string.StringBundler;

import javax.ws.rs.core.Response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 */
@XmlRootElement(name = "Problem")
public class Problem {

	public Problem() {
	}

	public Problem(Exception exception) {
		_status = Response.Status.BAD_REQUEST;
		_title = exception.getMessage();

		Class<?> clazz = exception.getClass();

		_type = clazz.getName();
	}

	public Problem(Response.Status status, String title) {
		_status = status;
		_title = title;
	}

	public Problem(
		String detail, Response.Status status, String title, String type) {

		_detail = detail;
		_status = status;
		_title = title;
		_type = type;
	}

	public String getDetail() {
		return _detail;
	}

	public Response.Status getStatus() {
		return _status;
	}

	public String getTitle() {
		return _title;
	}

	public String getType() {
		return _type;
	}

	public void setDetail(String detail) {
		_detail = detail;
	}

	public void setStatus(Response.Status status) {
		_status = status;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{detail=", _detail, ", status=", _status, ", title=", _title,
			", type=", _type, "}");
	}

	private String _detail;
	private Response.Status _status;
	private String _title;
	private String _type;

}