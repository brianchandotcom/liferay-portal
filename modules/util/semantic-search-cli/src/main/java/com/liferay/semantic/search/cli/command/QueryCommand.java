/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.command;

import com.liferay.petra.string.StringBundler;
import com.liferay.semantic.search.cli.client.QdrantClientWrapper;
import com.liferay.semantic.search.cli.client.TextEmbeddingsClient;
import com.liferay.semantic.search.cli.util.Args;
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
		Args parsed = new Args(args);

		List<String> positional = parsed.positional();

		if (positional.isEmpty()) {
			System.err.println("search: query requires a \"<text>\" argument");

			return 2;
		}

		QdrantClientWrapper qdrantClient = new QdrantClientWrapper();

		if (!qdrantClient.isReachable()) {
			System.err.println(
				"search: cannot reach Qdrant at " +
					qdrantClient.getQdrantUrl());

			return 5;
		}

		if (!qdrantClient.collectionExists(QdrantClientWrapper.COLLECTION)) {
			System.err.println(
				"search: index does not exist. Run ingest first.");

			return 3;
		}

		String text = positional.get(0);

		TextEmbeddingsClient teiClient = new TextEmbeddingsClient();

		float[] vector = teiClient.embedQuery(text);

		String path = parsed.path();

		List<QdrantClientWrapper.Hit> rawHits = qdrantClient.search(
			vector, parsed.top(), path);

		List<Hit> hits = new ArrayList<>();

		for (QdrantClientWrapper.Hit rawHit : rawHits) {
			hits.add(
				new Hit(
					rawHit.relPath(), rawHit.score(), rawHit.chunkId(),
					Output.snippet(rawHit.text()), rawHit.headingPath()));
		}

		if (hits.isEmpty() && !path.isEmpty()) {
			System.err.println(
				StringBundler.concat(
					"search: no results under \"", path,
					"\". The --path prefix is relative to the index's ingest ",
					"root; confirm the prefix and that the index was built ",
					"with --path support."));
		}

		System.out.println(Output.formatHits(hits, parsed.format()));

		return 0;
	}

}