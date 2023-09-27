/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

/**
 * @author Dylan Rebelak
 */
public class IndexDocumentResponse implements DocumentResponse {

	public IndexDocumentResponse(int status, String uid) {
		_status = status;
		_uid = uid;
	}

	public int getStatus() {
		return _status;
	}

	public String getUid() {
		return _uid;
	}

	private final int _status;
	private final String _uid;

}