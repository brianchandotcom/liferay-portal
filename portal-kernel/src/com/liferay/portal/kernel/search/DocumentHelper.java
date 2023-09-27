/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

/**
 * @author André de Oliveira
 */
public class DocumentHelper {

	public DocumentHelper(Document document) {
		_document = document;
	}

	public void setAttachmentOwnerKey(long classNameId, long classPK) {
		_document.addKeyword(Field.CLASS_NAME_ID, String.valueOf(classNameId));
		_document.addKeyword(Field.CLASS_PK, String.valueOf(classPK));
	}

	public void setEntryKey(String className, long classPK) {
		_document.addKeyword(Field.ENTRY_CLASS_NAME, className);
		_document.addKeyword(Field.ENTRY_CLASS_PK, String.valueOf(classPK));
	}

	private final Document _document;

}