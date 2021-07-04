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

package com.liferay.portal.vulcan.jaxrs.exception.mapper;

import javax.ws.rs.core.Response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 */
@XmlRootElement(name = "Problem")
public class Problem {

	public Problem() {
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

	private String _detail;
	private Response.Status _status;
	private String _title;
	private String _type;

}