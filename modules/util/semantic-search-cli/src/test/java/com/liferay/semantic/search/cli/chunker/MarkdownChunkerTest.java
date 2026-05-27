/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.semantic.search.cli.chunker;

import com.liferay.semantic.search.cli.util.Chunk;

import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author JR Houn
 */
public class MarkdownChunkerTest {

	@Test
	public void testFrontMatterIsStripped() {
		String text = _read("simple.md");

		MarkdownChunker chunker = new MarkdownChunker();

		List<Chunk> chunks = chunker.parse(text, "simple.md");

		Chunk firstChunk = chunks.get(0);

		String firstChunkText = firstChunk.text();

		Assert.assertFalse(
			"chunk text should not contain front matter",
			firstChunkText.contains("uuid:"));
	}

	@Test
	public void testH2SectionsGetH1H2HeadingPath() {
		String text = _read("simple.md");

		MarkdownChunker chunker = new MarkdownChunker();

		List<Chunk> chunks = chunker.parse(text, "simple.md");

		List<List<String>> headingPaths = new ArrayList<>();

		for (Chunk chunk : chunks) {
			headingPaths.add(chunk.headingPath());
		}

		Assert.assertEquals(List.of("Simple Document"), headingPaths.get(0));

		Assert.assertEquals(
			List.of("Simple Document", "First Section"), headingPaths.get(1));

		Assert.assertEquals(
			List.of("Simple Document", "Second Section"), headingPaths.get(2));
	}

	@Test
	public void testIntroBecomesH1Chunk() {
		String text = _read("simple.md");

		MarkdownChunker chunker = new MarkdownChunker();

		List<Chunk> chunks = chunker.parse(text, "simple.md");

		Chunk intro = chunks.get(0);

		List<String> headingPath = intro.headingPath();

		Assert.assertEquals(headingPath.toString(), 1, headingPath.size());

		Assert.assertEquals(
			headingPath.toString(), "Simple Document", headingPath.get(0));

		String introText = intro.text();

		Assert.assertTrue(introText.startsWith("This is the intro"));
	}

	@Test
	public void testNoH2_singleChunkUnderH1() {
		String text = _read("no-h2.md");

		MarkdownChunker chunker = new MarkdownChunker();

		List<Chunk> chunks = chunker.parse(text, "no-h2.md");

		Assert.assertEquals(chunks.toString(), 1, chunks.size());

		Chunk only = chunks.get(0);

		Assert.assertEquals(List.of("Just an H1"), only.headingPath());
	}

	@Test
	public void testSimpleDocumentChunkCount() {
		String text = _read("simple.md");

		MarkdownChunker chunker = new MarkdownChunker();

		List<Chunk> chunks = chunker.parse(text, "simple.md");

		Assert.assertEquals(chunks.toString(), 3, chunks.size());
	}

	@Test
	public void testSlidingWindowSplitsLongSections() {
		StringBuilder stringBuilder = new StringBuilder("# Long Doc\n\n");

		for (int i = 0; i < 500; i++) {
			stringBuilder.append("word ");
		}

		MarkdownChunker chunker = new MarkdownChunker();

		List<Chunk> chunks = chunker.parse(stringBuilder.toString(), "long.md");

		Assert.assertTrue(
			"long intro should produce more than one chunk", chunks.size() > 1);

		for (Chunk chunk : chunks) {
			Assert.assertEquals(List.of("Long Doc"), chunk.headingPath());
		}
	}

	private String _read(String fixtureName) {
		String resourcePath =
			"com/liferay/semantic/search/cli/chunker/dependencies/" +
				fixtureName;

		ClassLoader classLoader = MarkdownChunkerTest.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				resourcePath)) {

			if (inputStream == null) {
				throw new RuntimeException("missing fixture: " + resourcePath);
			}

			return new String(
				inputStream.readAllBytes(), StandardCharsets.UTF_8);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}