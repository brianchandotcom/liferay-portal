/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.command;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.semantic.search.cli.chunker.Chunkers;
import com.liferay.semantic.search.cli.client.QdrantClientWrapper;
import com.liferay.semantic.search.cli.client.TextEmbeddingsClient;
import com.liferay.semantic.search.cli.util.Args;
import com.liferay.semantic.search.cli.util.Chunk;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author JR Houn
 */
public class IngestCommand implements Command {

	@Override
	public String description() {
		return "Index every file under <dir> matching SEARCH_FILE_EXTS " +
			"[--force]";
	}

	@Override
	public int run(String[] args) throws Exception {
		Args parsed = new Args(args);

		List<String> positional = parsed.positional();

		if (positional.isEmpty()) {
			System.err.println("search: ingest requires a <dir> argument");

			return 2;
		}

		Path path = Paths.get(positional.get(0));

		Path root = path.toAbsolutePath();

		if (!Files.exists(root)) {
			System.err.println("search: ingest target does not exist: " + root);

			return 1;
		}

		List<Path> files = _indexableFiles(root);

		if (files.isEmpty()) {
			System.err.println(
				"search: no indexable files found under " + root);

			return 1;
		}

		QdrantClientWrapper qdrantClient = new QdrantClientWrapper();

		if (!qdrantClient.isReachable()) {
			System.err.println(
				"search: cannot reach Qdrant at " +
					qdrantClient.getQdrantURL());

			return 5;
		}

		Path base = Files.isDirectory(root) ? root : root.getParent();

		TextEmbeddingsClient textEmbeddingsClient = new TextEmbeddingsClient();

		int vectorDim = textEmbeddingsClient.embedQuery("warmup").length;

		qdrantClient.dropCollectionsIfVectorSizeChanged(vectorDim);

		qdrantClient.ensureCollection(
			QdrantClientWrapper.COLLECTION, vectorDim);

		qdrantClient.ensureCollection(QdrantClientWrapper.META_COLLECTION, 1);

		Map<String, String> existingHashes = qdrantClient.loadFileHashes();

		Set<String> seenRelPaths = new HashSet<>();

		List<Chunk> pendingChunks = new ArrayList<>();

		List<String[]> pendingFiles = new ArrayList<>();

		int skipped = 0;

		System.err.println(
			StringBundler.concat(
				"Scanning ", files.size(), " file(s) under ", root));

		for (Path file : files) {
			String text = new String(
				Files.readAllBytes(file), StandardCharsets.UTF_8);

			String sha = _sha256(text);

			String relPath = String.valueOf(base.relativize(file));

			seenRelPaths.add(relPath);

			if (!parsed.isForce() && sha.equals(existingHashes.get(relPath))) {
				skipped++;

				continue;
			}

			List<Chunk> chunks = Chunkers.parse(text, relPath);

			pendingFiles.add(new String[] {relPath, sha});

			pendingChunks.addAll(chunks);
		}

		int indexed = pendingFiles.size();

		if (!pendingChunks.isEmpty()) {
			Set<String> changedRelPaths = new TreeSet<>();

			for (Chunk chunk : pendingChunks) {
				changedRelPaths.add(chunk.relPath());
			}

			qdrantClient.deleteByRelPaths(new ArrayList<>(changedRelPaths));

			// A smaller default than the embedding server's client-batch cap:
			// a heavier code model embedding large method chunks needs fewer
			// per request to stay within the request timeout. Override with
			// SEARCH_EMBED_BATCH.

			int batchSize = GetterUtil.getInteger(
				System.getenv("SEARCH_EMBED_BATCH"), 16);

			int upserted = 0;

			int pendingChunksSize = pendingChunks.size();

			for (int from = 0; from < pendingChunksSize; from += batchSize) {
				int to = Math.min(from + batchSize, pendingChunksSize);

				List<Chunk> batch = pendingChunks.subList(from, to);

				List<String> texts = new ArrayList<>();

				for (Chunk chunk : batch) {
					texts.add(chunk.embeddingText());
				}

				float[][] vectors = textEmbeddingsClient.embedDocuments(texts);

				qdrantClient.upsertChunks(batch, vectors);

				upserted += batch.size();

				System.err.println(
					StringBundler.concat(
						"  embedded ", upserted, "/", pendingChunksSize,
						" chunks"));
			}
		}

		for (String[] fileEntry : pendingFiles) {
			qdrantClient.writeMetaFile(fileEntry[0], fileEntry[1]);
		}

		List<String> removed = new ArrayList<>();

		if (Files.isDirectory(root)) {
			Set<String> stale = new HashSet<>(existingHashes.keySet());

			stale.removeAll(seenRelPaths);

			for (String relPath : stale) {
				removed.add(relPath);
			}

			if (!removed.isEmpty()) {
				qdrantClient.deleteByRelPaths(removed);

				qdrantClient.deleteMetaFiles(removed);
			}
		}

		qdrantClient.writeMetaState(base.toString(), seenRelPaths.size());

		System.err.println(
			StringBundler.concat(
				"Done. indexed=", indexed, " skipped=", skipped, " removed=",
				removed.size(), " total_files=", seenRelPaths.size()));

		return 0;
	}

	private String[] _fileExtensions() {
		String env = System.getenv("SEARCH_FILE_EXTS");

		if ((env == null) || env.isEmpty()) {
			return new String[] {".md"};
		}

		String[] split = env.split(",");

		List<String> exts = new ArrayList<>();

		for (String ext : split) {
			String trimmed = ext.trim();

			if (!trimmed.isEmpty()) {
				exts.add(trimmed);
			}
		}

		return exts.toArray(new String[0]);
	}

	private List<Path> _indexableFiles(Path root) throws Exception {
		String[] exts = _fileExtensions();

		List<Path> files = new ArrayList<>();

		if (Files.isRegularFile(root)) {
			String name = String.valueOf(root.getFileName());

			if (_isMatchingExtension(name, exts) && !name.startsWith(".")) {
				files.add(root);
			}

			return files;
		}

		Files.walkFileTree(
			root,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dir, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path dirFileName = dir.getFileName();

					if (dirFileName == null) {
						return FileVisitResult.CONTINUE;
					}

					String dirName = dirFileName.toString();

					if (dirName.startsWith(".")) {
						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path file, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = file.getFileName();

					if (fileNamePath == null) {
						return FileVisitResult.CONTINUE;
					}

					String fileName = fileNamePath.toString();

					if (_isMatchingExtension(fileName, exts) &&
						!fileName.startsWith(".")) {

						files.add(file);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		Collections.sort(files);

		return files;
	}

	private boolean _isMatchingExtension(String name, String[] exts) {
		for (String ext : exts) {
			if (name.endsWith(ext)) {
				return true;
			}
		}

		return false;
	}

	private String _sha256(String text) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");

		byte[] bytes = digest.digest(text.getBytes(StandardCharsets.UTF_8));

		StringBundler sb = new StringBundler(bytes.length);

		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

}