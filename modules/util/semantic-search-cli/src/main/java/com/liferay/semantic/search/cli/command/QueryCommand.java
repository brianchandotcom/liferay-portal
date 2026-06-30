/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.command;

import com.liferay.petra.string.StringBundler;
import com.liferay.semantic.search.cli.client.QdrantClientWrapper;
import com.liferay.semantic.search.cli.client.TextEmbeddingsClient;
import com.liferay.semantic.search.cli.util.Arguments;
import com.liferay.semantic.search.cli.util.Hit;
import com.liferay.semantic.search.cli.util.Output;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JR Houn
 */
public class QueryCommand implements Command {

	@Override
	public String description() {
		return "Search by string: query \"<text>\" [--format text|json] " +
			"[--path <dir-prefix>] [--top N]";
	}

	@Override
	public int run(String[] args) throws Exception {
		Arguments arguments = new Arguments(args);

		List<String> positional = arguments.getPositional();

		if (positional.isEmpty()) {
			System.err.println("search: query requires a \"<text>\" argument");

			return 2;
		}

		QdrantClientWrapper qdrantClientWrapper = new QdrantClientWrapper();

		if (!qdrantClientWrapper.isReachable()) {
			System.err.println(
				"search: cannot reach Qdrant at " +
					qdrantClientWrapper.getQdrantURL());

			return 5;
		}

		if (!qdrantClientWrapper.hasCollection(
				QdrantClientWrapper.COLLECTION)) {

			System.err.println(
				"search: index does not exist; run ingest first");

			return 3;
		}

		String text = positional.get(0);

		TextEmbeddingsClient textEmbeddingsClient = new TextEmbeddingsClient();

		float[] vector = textEmbeddingsClient.embedQuery(text);

		String path = arguments.getPath();

		List<QdrantClientWrapper.SearchResult> searchResults =
			qdrantClientWrapper.search(vector, arguments.getTop(), path);

		List<Hit> hits = new ArrayList<>();

		for (QdrantClientWrapper.SearchResult searchResult : searchResults) {
			hits.add(
				new Hit(
					searchResult.getRelPath(), searchResult.getScore(),
					searchResult.getChunkId(),
					Output.snippet(searchResult.getText()),
					searchResult.getHeadingPath()));
		}

		if (hits.isEmpty() && !path.isEmpty()) {
			System.err.println(
				StringBundler.concat(
					"search: no results under \"", path,
					"\". The --path prefix is relative to the index's ingest ",
					"root; confirm the prefix and that the index was built ",
					"with --path support."));
		}

		System.out.println(Output.formatHits(hits, arguments.getFormat()));

		return 0;
	}

}