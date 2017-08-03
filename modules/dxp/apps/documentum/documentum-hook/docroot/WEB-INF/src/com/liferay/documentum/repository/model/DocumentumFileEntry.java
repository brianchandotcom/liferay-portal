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

package com.liferay.documentum.repository.model;

import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.common.DfException;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Iván Zaera
 */
public class DocumentumFileEntry
	extends DocumentumObject implements ExtRepositoryFileEntry {

	public DocumentumFileEntry(
		IDfDocument idfDocument, IDfDocument idfDocumentLastVersion) {

		super(idfDocument);

		_idfDocument = idfDocument;
		_idfDocumentLastVersion = idfDocumentLastVersion;
	}

	@Override
	public String getCheckedOutBy() {
		try {
			return _idfDocumentLastVersion.getLockOwner();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	public IDfDocument getIDfDocument() {
		return _idfDocument;
	}

	@Override
	public String getMimeType() {
		return null;
	}

	@Override
	public String getTitle() {
		try {
			return _idfDocumentLastVersion.getObjectName();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	private final IDfDocument _idfDocument;
	private final IDfDocument _idfDocumentLastVersion;

}