/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.util;

import java.util.List;

/**
 * One element of the JSON output array emitted by `query` and
 * `similar`. Field names are part of the stable contract.
 *
 * @author JR Houn
 */
public class Hit {

	public Hit(
		String path, double score, String chunkId, String snippet,
		List<String> headingPath) {

		_path = path;
		_score = score;
		_chunkId = chunkId;
		_snippet = snippet;
		_headingPath = headingPath;
	}

	public String getChunkId() {
		return _chunkId;
	}

	public List<String> getHeadingPath() {
		return _headingPath;
	}

	public String getPath() {
		return _path;
	}

	public double getScore() {
		return _score;
	}

	public String getSnippet() {
		return _snippet;
	}

	private final String _chunkId;
	private final List<String> _headingPath;
	private final String _path;
	private final double _score;
	private final String _snippet;

}