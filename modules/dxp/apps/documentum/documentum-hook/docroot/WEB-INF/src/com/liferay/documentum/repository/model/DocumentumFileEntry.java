/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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