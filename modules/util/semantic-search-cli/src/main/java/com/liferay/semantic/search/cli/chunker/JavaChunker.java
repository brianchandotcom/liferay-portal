/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.chunker;

import com.liferay.petra.string.StringBundler;
import com.liferay.semantic.search.cli.util.Chunk;

import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Splits a Java source file into method-scoped chunks.
 *
 *   1. Strip the license header (everything before the {@code package}
 *      line).
 *   2. Strip the {@code package} declaration and the entire
 *      {@code import} block — both are pure boilerplate that
 *      otherwise produces near-identical first chunks across every
 *      file in the same package and ruins code-to-code similarity.
 *   3. Capture the top-level class/interface name as the H1 analog.
 *   4. Each top-level method signature begins a chunk; the chunk runs
 *      to just before the next method signature.
 *   5. The class-level content before the first method (class javadoc,
 *      fields, constants) is the intro chunk with heading
 *      {@code [className]}.
 *   6. Methods longer than CHUNK_WINDOW_WORDS get a sliding-window
 *      split sharing the same heading_path.
 *
 * @author JR Houn
 */
public class JavaChunker {

	public List<Chunk> parse(String text, String relPath) {
		String className = _classNameFromFile(relPath);

		Matcher classMatcher = _classPattern.matcher(text);

		if (classMatcher.find()) {
			className = classMatcher.group(2);
		}

		Matcher packageMatcher = _packagePattern.matcher(text);

		String body = text;

		if (packageMatcher.find()) {
			body = text.substring(packageMatcher.end());
		}

		Matcher importsMatcher = _importsPattern.matcher(body);

		if (importsMatcher.find() && (importsMatcher.start() < 100)) {
			body = body.substring(importsMatcher.end());
		}

		body = body.replaceFirst("\\A\\s+", "");

		List<int[]> methodSigs = new ArrayList<>();

		Matcher sigMatcher = _methodSigPattern.matcher(body);

		while (sigMatcher.find()) {
			methodSigs.add(new int[] {sigMatcher.start(), sigMatcher.end()});
		}

		List<Chunk> chunks = new ArrayList<>();

		if (methodSigs.isEmpty()) {
			_emit(chunks, relPath, Arrays.asList(className), body);

			return chunks;
		}

		String intro = body.substring(0, methodSigs.get(0)[0]);

		_emit(chunks, relPath, Arrays.asList(className), intro);

		for (int i = 0; i < methodSigs.size(); i++) {
			int[] sig = methodSigs.get(i);

			int end = body.length();

			if ((i + 1) < methodSigs.size()) {
				end = methodSigs.get(i + 1)[0];
			}

			String signatureLine = body.substring(sig[0], sig[1]);

			Matcher nameMatcher = _methodNamePattern.matcher(signatureLine);

			String methodName = "method";

			if (nameMatcher.find()) {
				methodName = nameMatcher.group(1);
			}

			String methodBlock = body.substring(sig[0], end);

			_emit(
				chunks, relPath, Arrays.asList(className, methodName),
				methodBlock);
		}

		return chunks;
	}

	private String _classNameFromFile(String relPath) {
		String name = String.valueOf(
			Paths.get(
				relPath
			).getFileName());

		return name.replaceFirst("\\.java$", "");
	}

	private void _emit(
		List<Chunk> chunks, String relPath, List<String> headingPath,
		String sectionText) {

		String stripped = sectionText.strip();

		if (stripped.isEmpty()) {
			return;
		}

		List<String> parts = _splitByWords(
			stripped, MarkdownChunker.CHUNK_WINDOW_WORDS,
			MarkdownChunker.CHUNK_OVERLAP_WORDS);

		String headingSlug = String.join(" > ", headingPath);

		for (int idx = 0; idx < parts.size(); idx++) {
			String chunkId = StringBundler.concat(
				relPath, "#", headingSlug, "#", idx);

			chunks.add(
				new Chunk(chunkId, relPath, headingPath, parts.get(idx)));
		}
	}

	private List<String> _splitByWords(String text, int window, int overlap) {
		String[] words = text.split("\\s+");

		List<String> result = new ArrayList<>();

		if (words.length <= window) {
			result.add(text);

			return result;
		}

		int step = Math.max(window - overlap, 1);

		List<String> wordList = Arrays.asList(words);

		for (int start = 0; start < words.length; start += step) {
			int end = Math.min(start + window, words.length);

			result.add(String.join(" ", wordList.subList(start, end)));

			if ((start + window) >= words.length) {
				break;
			}
		}

		return result;
	}

	private static final Pattern _classPattern = Pattern.compile(
		"\\b(class|interface|enum|@interface)\\s+(\\w+)");
	private static final Pattern _importsPattern = Pattern.compile(
		"(?m)(^import\\s+[^\\n]+;\\s*\\n)" +
			"(?:\\s*(?:import\\s+[^\\n]+;\\s*\\n)?\\n?)*");
	private static final Pattern _methodNamePattern = Pattern.compile(
		"\\s(\\w+)\\s*\\(");
	private static final Pattern _methodSigPattern = Pattern.compile(
		"(?m)^\\t(?:public|protected|private)\\b[^=\\n]*\\([^)]*\\)[^\\n]*$");
	private static final Pattern _packagePattern = Pattern.compile(
		"(?m)^package\\s+[\\w.]+;\\s*\\n");

}