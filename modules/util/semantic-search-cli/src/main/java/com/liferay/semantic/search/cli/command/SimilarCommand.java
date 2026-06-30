/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.command;

import com.liferay.semantic.search.cli.chunker.Chunkers;
import com.liferay.semantic.search.cli.client.QdrantClientWrapper;
import com.liferay.semantic.search.cli.client.TextEmbeddingsClient;
import com.liferay.semantic.search.cli.util.Arguments;
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
		return "Search by file: similar <file> [--format text|json] [--path " +
			"<dir-prefix>] [--top N]";
	}

	@Override
	public int run(String[] args) throws Exception {
		Arguments arguments = new Arguments(args);

		List<String> positional = arguments.getPositional();

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
				"search: target file has no extractable content");

			return 4;
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

		Chunk seed = chunks.get(0);

		String queryText =
			String.join(" > ", seed.getHeadingPath()) + "\n\n" + seed.getText();

		TextEmbeddingsClient textEmbeddingsClient = new TextEmbeddingsClient();

		// Similar is code-to-code: embed the seed as a document (no query
		// instruction prefix), matching the document side of the model.

		float[] vector =
			textEmbeddingsClient.embedDocuments(List.of(queryText))[0];

		List<QdrantClientWrapper.SearchResult> searchResults =
			qdrantClientWrapper.search(
				vector, (arguments.getTop() * 3) + 10, arguments.getPath());

		List<Hit> hits = new ArrayList<>();

		for (QdrantClientWrapper.SearchResult searchResult : searchResults) {
			String relPath = searchResult.getRelPath();

			if (relPath.equals(targetBaseName) ||
				relPath.endsWith("/" + targetBaseName)) {

				continue;
			}

			hits.add(
				new Hit(
					relPath, searchResult.getScore(), searchResult.getChunkId(),
					Output.snippet(searchResult.getText()),
					searchResult.getHeadingPath()));

			if (hits.size() >= arguments.getTop()) {
				break;
			}
		}

		System.out.println(Output.formatHits(hits, arguments.getFormat()));

		return 0;
	}

}