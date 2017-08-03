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
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfTime;

import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Date;

/**
 * @author Iván Zaera
 */
public class DocumentumFileVersion implements ExtRepositoryFileVersion {

	public DocumentumFileVersion(
		IDfDocument idfDocument, IDfDocument idfDocumentFirstVersion) {

		_idfDocument = idfDocument;
		_idfDocumentFirstVersion = idfDocumentFirstVersion;
	}

	@Override
	public String getChangeLog() {
		try {
			return _idfDocument.getLogEntry();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public Date getCreateDate() {
		try {
			IDfTime creationDate = _idfDocument.getCreationDate();

			return creationDate.getDate();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public String getExtRepositoryModelKey() {
		try {
			IDfId idfId = _idfDocumentFirstVersion.getObjectId();

			return idfId.getId() + StringPool.AT + getVersion();
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
	public String getOwner() {
		try {
			return _idfDocument.getOwnerName();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public long getSize() {
		try {
			return _idfDocument.getContentSize();
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	@Override
	public String getVersion() {
		try {
			return _idfDocument.getVersionLabel(0);
		}
		catch (DfException de) {
			throw new SystemException(de);
		}
	}

	private final IDfDocument _idfDocument;
	private final IDfDocument _idfDocumentFirstVersion;

}