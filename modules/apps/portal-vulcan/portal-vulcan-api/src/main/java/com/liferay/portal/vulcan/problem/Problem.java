/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.problem;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public interface Problem {

	public String getDetail(Locale locale);

	public Status getStatus(Locale locale);

	public String getTitle(Locale locale);

	public String getType();

	public static enum Status {

		BAD_REQUEST(400, "Bad Request");

		public String getReason() {
			return toString();
		}

		public int getStatusCode() {
			return _code;
		}

		public String toString() {
			return _reason;
		}

		private Status(int statusCode, String reason) {
			_reason = reason;

			_code = statusCode;
		}

		private final int _code;
		private final String _reason;

	}

}