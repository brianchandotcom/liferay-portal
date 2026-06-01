/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.chunker;

import com.liferay.semantic.search.cli.util.Chunk;

import java.util.List;

/**
 * Contract for a language-specific chunker: splits one file's text into
 * embeddable {@link Chunk}s. {@link Chunkers} selects an implementation by
 * file extension.
 *
 * @author JR Houn
 */
public interface Chunker {

	public List<Chunk> parse(String text, String relPath);

}