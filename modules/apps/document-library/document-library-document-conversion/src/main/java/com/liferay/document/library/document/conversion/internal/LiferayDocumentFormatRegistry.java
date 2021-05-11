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

package com.liferay.document.library.document.conversion.internal;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;

import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class LiferayDocumentFormatRegistry implements DocumentFormatRegistry {

	@Override
	public DocumentFormat getFormatByFileExtension(String extension) {
		if (_documentFormatsByExtension.containsKey(extension)) {
			return _documentFormatsByExtension.get(extension);
		}

		return _documentFormatRegistry.getFormatByFileExtension(extension);
	}

	@Override
	public DocumentFormat getFormatByMimeType(String mimeType) {
		if (_documentFormatsByMimeType.containsKey(mimeType)) {
			return _documentFormatsByMimeType.get(mimeType);
		}

		return _documentFormatRegistry.getFormatByMimeType(mimeType);
	}

	private static final Map<String, DocumentFormat>
		_documentFormatsByExtension =
			HashMapBuilder.<String, DocumentFormat>put(
				"css",
				new DocumentFormat(
					"CSS", DocumentFamily.TEXT, ContentTypes.TEXT_CSS, "css")
			).put(
				"java",
				new DocumentFormat(
					"Java", DocumentFamily.TEXT,
					ContentTypes.TEXT_X_JAVA_SOURCE, "java")
			).put(
				"js",
				new DocumentFormat(
					"Javascript", DocumentFamily.TEXT,
					ContentTypes.APPLICATION_JAVASCRIPT, "js")
			).put(
				"jsp",
				new DocumentFormat(
					"JSP", DocumentFamily.TEXT, ContentTypes.TEXT_X_JSP, "jsp")
			).put(
				"jspf",
				new DocumentFormat(
					"JSPF", DocumentFamily.TEXT, ContentTypes.TEXT_X_JSP,
					"jspf")
			).put(
				"sh",
				new DocumentFormat(
					"Shell Script", DocumentFamily.TEXT,
					ContentTypes.APPLICATION_X_SH, "sh")
			).build();
	private static final Map<String, DocumentFormat>
		_documentFormatsByMimeType = HashMapBuilder.<String, DocumentFormat>put(
			ContentTypes.APPLICATION_JAVASCRIPT,
			new DocumentFormat(
				"Javascript", DocumentFamily.TEXT,
				ContentTypes.APPLICATION_JAVASCRIPT, "js")
		).put(
			ContentTypes.APPLICATION_X_SH,
			new DocumentFormat(
				"Shell Script", DocumentFamily.TEXT,
				ContentTypes.APPLICATION_X_SH, "sh")
		).put(
			ContentTypes.TEXT_CSS,
			new DocumentFormat(
				"CSS", DocumentFamily.TEXT, ContentTypes.TEXT_CSS, "css")
		).put(
			ContentTypes.TEXT_X_JAVA_SOURCE,
			new DocumentFormat(
				"Java", DocumentFamily.TEXT, ContentTypes.TEXT_X_JAVA_SOURCE,
				"java")
		).put(
			ContentTypes.TEXT_X_JSP,
			new DocumentFormat(
				"JSP", DocumentFamily.TEXT, ContentTypes.TEXT_X_JSP, "jsp")
		).build();

	private final DocumentFormatRegistry _documentFormatRegistry =
		new DefaultDocumentFormatRegistry();

}