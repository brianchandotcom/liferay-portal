/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

/**
 * @author Dylan Rebelak
 */
public class UpdateByQueryDocumentResponse implements DocumentResponse {

	public UpdateByQueryDocumentResponse(long updated, long took) {
		_updated = updated;
		_took = took;
	}

	public long getTook() {
		return _took;
	}

	public long getUpdated() {
		return _updated;
	}

	private final long _took;
	private final long _updated;

}