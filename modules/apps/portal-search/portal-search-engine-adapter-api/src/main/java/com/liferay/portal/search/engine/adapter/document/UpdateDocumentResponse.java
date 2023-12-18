/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

/**
 * @author Dylan Rebelak
 */
public class UpdateDocumentResponse implements DocumentResponse {

	public UpdateDocumentResponse(int status) {
		_status = status;

		_statusString = null;
	}

	public UpdateDocumentResponse(int status, String statusString) {
		_status = status;
		_statusString = statusString;
	}

	public int getStatus() {
		return _status;
	}

	public String getStatusString() {
		return _statusString;
	}

	private final int _status;
	private final String _statusString;

}