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

		public String getReasonPhrase() {
			return toString();
		}

		public int getStatusCode() {
			return _statusCode;
		}

		public String toString() {
			return _reasonPhrase;
		}

		private Status(int statusCode, String reasonPhrase) {
			_statusCode = statusCode;
			_reasonPhrase = reasonPhrase;
		}

		private final String _reasonPhrase;
		private final int _statusCode;

	}

}