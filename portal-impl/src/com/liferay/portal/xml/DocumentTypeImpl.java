/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.xml.DocumentType;

import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class DocumentTypeImpl implements DocumentType {

	public DocumentTypeImpl(org.dom4j.DocumentType documentType) {
		_documentType = documentType;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentTypeImpl)) {
			return false;
		}

		DocumentTypeImpl documentTypeImpl = (DocumentTypeImpl)object;

		if (Objects.equals(_documentType, documentTypeImpl._documentType)) {
			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return _documentType.getName();
	}

	@Override
	public String getPublicId() {
		if (_documentType == null) {
			return null;
		}

		return _documentType.getPublicID();
	}

	@Override
	public String getSystemId() {
		if (_documentType == null) {
			return null;
		}

		return _documentType.getSystemID();
	}

	public org.dom4j.DocumentType getWrappedDocumentType() {
		return _documentType;
	}

	@Override
	public int hashCode() {
		if (_documentType == null) {
			return super.hashCode();
		}

		return _documentType.hashCode();
	}

	@Override
	public String toString() {
		if (_documentType == null) {
			return StringPool.BLANK;
		}

		return _documentType.toString();
	}

	private org.dom4j.DocumentType _documentType;

}