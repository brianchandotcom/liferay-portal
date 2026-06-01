/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.chunker;

import com.liferay.petra.string.StringBundler;
import com.liferay.semantic.search.cli.util.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Splits a Markdown document into chunks for embedding:
 *
 *   1. Strip YAML front matter (`---` block at the start).
 *   2. Capture the H1 as the document title. If absent, use the file
 *      path as the title.
 *   3. The intro between H1 and the first H2 is one chunk with
 *      heading_path = [h1].
 *   4. Each ## section is one chunk with heading_path = [h1, h2].
 *   5. Sections longer than CHUNK_WINDOW_WORDS are split with a
 *      sliding window of CHUNK_WINDOW_WORDS / CHUNK_OVERLAP_WORDS,
 *      sharing the same heading_path.
 *   6. H3+ headings stay inside the chunk body.
 *
 * @author JR Houn
 */
public class MarkdownChunker implements Chunker {

	public static final int CHUNK_OVERLAP_WORDS = 60;

	public static final int CHUNK_WINDOW_WORDS = 350;

	@Override
	public List<Chunk> parse(String text, String relPath) {
		String body = _stripFrontMatter(text);

		Matcher h1Matcher = _h1Pattern.matcher(body);

		String h1 = relPath;

		int introStart = 0;

		if (h1Matcher.find()) {
			String h1Group = h1Matcher.group(1);

			h1 = h1Group.trim();

			introStart = h1Matcher.end();
		}

		List<int[]> h2Matches = new ArrayList<>();

		Matcher h2Matcher = _h2Pattern.matcher(body);

		while (h2Matcher.find()) {
			h2Matches.add(
				new int[] {
					h2Matcher.start(), h2Matcher.end(),
					_findEndOfLine(body, h2Matcher.end())
				});
		}

		List<RawSection> rawSections = new ArrayList<>();

		if (h2Matches.isEmpty()) {
			String content = body.substring(introStart);

			content = content.trim();

			if (!content.isEmpty()) {
				rawSections.add(new RawSection(Arrays.asList(h1), content));
			}
		}
		else {
			int firstH2Start = h2Matches.get(0)[0];

			String intro = body.substring(introStart, firstH2Start);

			intro = intro.trim();

			if (!intro.isEmpty()) {
				rawSections.add(new RawSection(Arrays.asList(h1), intro));
			}

			for (int i = 0; i < h2Matches.size(); i++) {
				int[] match = h2Matches.get(i);

				String h2Heading = _h2Text(body, match);

				int sectionStart = match[1];

				int sectionEnd =
					((i + 1) < h2Matches.size()) ? h2Matches.get(i + 1)[0] :
						body.length();

				String sectionBody = body.substring(sectionStart, sectionEnd);

				sectionBody = sectionBody.trim();

				if (!sectionBody.isEmpty()) {
					rawSections.add(
						new RawSection(
							Arrays.asList(h1, h2Heading), sectionBody));
				}
			}
		}

		List<Chunk> chunks = new ArrayList<>();

		for (RawSection rawSection : rawSections) {
			List<String> parts = _splitByWords(
				rawSection._text, CHUNK_WINDOW_WORDS, CHUNK_OVERLAP_WORDS);

			String headingSlug = String.join(" > ", rawSection._headingPath);

			for (int idx = 0; idx < parts.size(); idx++) {
				String chunkId = StringBundler.concat(
					relPath, "#", headingSlug, "#", idx);

				chunks.add(
					new Chunk(
						chunkId, relPath, rawSection._headingPath,
						parts.get(idx)));
			}
		}

		return chunks;
	}

	private int _findEndOfLine(String body, int from) {
		int newline = body.indexOf('\n', from);

		if (newline < 0) {
			return body.length();
		}

		return newline;
	}

	private String _h2Text(String body, int[] match) {
		String fullMatch = body.substring(match[0], match[1]);

		String head = fullMatch.substring(3);

		return head.trim();
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

	private String _stripFrontMatter(String text) {
		if (!text.startsWith("---\n") && !text.startsWith("---\r\n")) {
			return text;
		}

		Matcher matcher = _frontMatterEndPattern.matcher(text);

		if (matcher.find()) {
			return text.substring(matcher.end());
		}

		return text;
	}

	private static final Pattern _frontMatterEndPattern = Pattern.compile(
		"\\n---\\s*\\n");
	private static final Pattern _h1Pattern = Pattern.compile(
		"^#\\s+(.+?)\\s*$", Pattern.MULTILINE);
	private static final Pattern _h2Pattern = Pattern.compile(
		"^##\\s+.+?\\s*$", Pattern.MULTILINE);

	private static class RawSection {

		private RawSection(List<String> headingPath, String text) {
			_headingPath = headingPath;
			_text = text;
		}

		private final List<String> _headingPath;
		private final String _text;

	}

}