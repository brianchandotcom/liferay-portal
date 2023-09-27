/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

/**
 * @author Dylan Rebelak
 */
public class DeleteByQueryDocumentResponse implements DocumentResponse {

	public DeleteByQueryDocumentResponse(long deleted, long took) {
		_deleted = deleted;
		_took = took;
	}

	public long getDeleted() {
		return _deleted;
	}

	public long getTook() {
		return _took;
	}

	private final long _deleted;
	private final long _took;

}