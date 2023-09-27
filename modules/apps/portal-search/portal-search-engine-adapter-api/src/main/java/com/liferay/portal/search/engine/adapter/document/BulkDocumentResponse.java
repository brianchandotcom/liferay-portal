/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class BulkDocumentResponse implements DocumentResponse {

	public BulkDocumentResponse(long took) {
		_took = took;
	}

	public void addBulkDocumentItemResponse(
		BulkDocumentItemResponse bulkDocumentItemResponse) {

		_bulkDocumentItemResponses.add(bulkDocumentItemResponse);
	}

	public List<BulkDocumentItemResponse> getBulkDocumentItemResponses() {
		return _bulkDocumentItemResponses;
	}

	public long getTook() {
		return _took;
	}

	public boolean hasErrors() {
		return _errors;
	}

	public void setErrors(boolean errors) {
		_errors = errors;
	}

	private final List<BulkDocumentItemResponse> _bulkDocumentItemResponses =
		new ArrayList<>();
	private boolean _errors;
	private final long _took;

}