/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.chunker;

import com.liferay.semantic.search.cli.util.Chunk;

import java.util.List;

/**
 * Dispatches to a language-specific chunker by file extension.
 *
 * @author JR Houn
 */
public class Chunkers {

	public static List<Chunk> parse(String text, String relPath) {
		if (relPath.endsWith(".java")) {
			return _JAVA_CHUNKER.parse(text, relPath);
		}

		return _MARKDOWN_CHUNKER.parse(text, relPath);
	}

	private static final JavaChunker _JAVA_CHUNKER = new JavaChunker();

	private static final MarkdownChunker _MARKDOWN_CHUNKER =
		new MarkdownChunker();

}