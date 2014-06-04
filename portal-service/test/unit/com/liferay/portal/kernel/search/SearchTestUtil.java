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

import com.liferay.portal.util.test.RandomTestUtil;

/**
 * @author Manuel de la Peña
 */
public class SearchTestUtil {

	public static final String DOCUMENT_CLASS_NAME =
		"com.liferay.ClassInDocument";

	public static final long DOCUMENT_CLASS_NAME_ID =
		RandomTestUtil.randomLong();

	public static final long DOCUMENT_CLASS_PK = RandomTestUtil.randomLong();

	public static final long ENTRY_CLASS_PK = RandomTestUtil.randomLong();

	public static final String SUMMARY_CONTENT =
		"A long time ago, in a galaxy far, far away...";

	public static final String SUMMARY_TITLE = "S.R. Wars";

	public static Document createDocument(String entryClassName) {
		return createDocument(entryClassName, ENTRY_CLASS_PK);
	}

	public static Document createDocument(
		String entryClassName, long entryClassPk) {

		Document document = new DocumentImpl();

		document.add(new Field(Field.ENTRY_CLASS_NAME, entryClassName));
		document.add(
			new Field(Field.ENTRY_CLASS_PK, String.valueOf(entryClassPk)));

		return document;
	}

	public static Document createDocumentWithAlternateKey(String entryClassName) {
		return createDocumentWithAlternateKey(entryClassName, ENTRY_CLASS_PK);
	}

	public static Document createDocumentWithAlternateKey(
		String entryClassName, long entryClassPK) {

		Document document = createDocument(entryClassName, entryClassPK);

		document.add(
			new Field(
				Field.CLASS_NAME_ID, String.valueOf(DOCUMENT_CLASS_NAME_ID)));
		document.add(
			new Field(Field.CLASS_PK, String.valueOf(DOCUMENT_CLASS_PK)));

		return document;
	}

}