/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.command;

import com.liferay.semantic.search.cli.chunker.Chunkers;
import com.liferay.semantic.search.cli.client.QdrantClientWrapper;
import com.liferay.semantic.search.cli.client.TextEmbeddingsClient;
import com.liferay.semantic.search.cli.util.Args;
import com.liferay.semantic.search.cli.util.Chunk;
import com.liferay.semantic.search.cli.util.Hit;
import com.liferay.semantic.search.cli.util.Output;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JR Houn
 */
public class SimilarCommand implements Command {

	@Override
	public String description() {
		return "Search by file: similar <path> [--top N] [--format text|json]";
	}

	@Override
	public int run(String[] args) throws Exception {
		Args parsed = new Args(args);

		List<String> positional = parsed.positional();

		if (positional.isEmpty()) {
			System.err.println("search: similar requires a <path> argument");

			return 2;
		}

		Path targetPath = Paths.get(positional.get(0));

		if (!Files.exists(targetPath)) {
			System.err.println("search: file not found: " + targetPath);

			return 1;
		}

		String text = new String(
			Files.readAllBytes(targetPath), StandardCharsets.UTF_8);

		String targetBaseName = String.valueOf(targetPath.getFileName());

		List<Chunk> chunks = Chunkers.parse(text, targetBaseName);

		if (chunks.isEmpty()) {
			System.err.println(
				"search: target file has no extractable content.");

			return 4;
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

		Chunk seed = chunks.get(0);

		String queryText =
			String.join(" > ", seed.headingPath()) + "\n\n" + seed.text();

		TextEmbeddingsClient teiClient = new TextEmbeddingsClient();

		float[] vector = teiClient.embedQuery(queryText);

		List<QdrantClientWrapper.Hit> rawHits = qdrantClient.search(
			vector, (parsed.top() * 3) + 10);

		List<Hit> hits = new ArrayList<>();

		for (QdrantClientWrapper.Hit rawHit : rawHits) {
			String relPath = rawHit.relPath();

			if (relPath.equals(targetBaseName) ||
				relPath.endsWith("/" + targetBaseName)) {

				continue;
			}

			hits.add(
				new Hit(
					relPath, rawHit.score(), rawHit.chunkId(),
					Output.snippet(rawHit.text()), rawHit.headingPath()));

			if (hits.size() >= parsed.top()) {
				break;
			}
		}

		System.out.println(Output.formatHits(hits, parsed.format()));

		return 0;
	}

}