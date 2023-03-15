/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.main;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author Marcellus Tavares
 */
public class TokenDisplay {

	public TokenDisplay(
		Date createDate, Date expirationDate, Date lastAccessDate,
		String token) {

		_createDate = new Date(createDate.getTime());
		_expirationDate = new Date(expirationDate.getTime());
		_lastAccessDate = new Date(lastAccessDate.getTime());
		_token = token;
	}

	@JsonFormat(
		pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		shape = JsonFormat.Shape.STRING, timezone = "UTC"
	)
	public Date getCreateDate() {
		return new Date(_createDate.getTime());
	}

	@JsonFormat(
		pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		shape = JsonFormat.Shape.STRING, timezone = "UTC"
	)
	public Date getExpirationDate() {
		return new Date(_expirationDate.getTime());
	}

	@JsonFormat(
		pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
		shape = JsonFormat.Shape.STRING, timezone = "UTC"
	)
	public Date getLastAccessDate() {
		return new Date(_lastAccessDate.getTime());
	}

	public String getToken() {
		return _token;
	}

	private final Date _createDate;
	private final Date _expirationDate;
	private final Date _lastAccessDate;
	private final String _token;

}