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