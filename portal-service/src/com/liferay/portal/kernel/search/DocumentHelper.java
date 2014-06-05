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

	private Document _document;

}