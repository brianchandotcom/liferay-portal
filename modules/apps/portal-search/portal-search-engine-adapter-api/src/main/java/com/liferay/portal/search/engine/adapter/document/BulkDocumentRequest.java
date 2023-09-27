/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class BulkDocumentRequest
	extends CrossClusterRequest
	implements DocumentRequest<BulkDocumentResponse> {

	@Override
	public BulkDocumentResponse accept(
		DocumentRequestExecutor documentRequestExecutor) {

		return documentRequestExecutor.executeBulkDocumentRequest(this);
	}

	public void addBulkableDocumentRequest(
		BulkableDocumentRequest<?> bulkableDocumentRequest) {

		_bulkableDocumentRequests.add(bulkableDocumentRequest);
	}

	public List<BulkableDocumentRequest<?>> getBulkableDocumentRequests() {
		return _bulkableDocumentRequests;
	}

	public boolean isRefresh() {
		return _refresh;
	}

	public void setRefresh(boolean refresh) {
		_refresh = refresh;
	}

	private final List<BulkableDocumentRequest<?>> _bulkableDocumentRequests =
		new ArrayList<>();
	private boolean _refresh;

}