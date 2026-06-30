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
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author JR Houn
 */
public class JavaChunkerTest {

	@Test
	public void testParseClassNameDrivesHeadingPath() {
		String text = _read("SampleResourceImpl.java.txt");

		JavaChunker javaChunker = new JavaChunker();

		List<Chunk> chunks = javaChunker.parse(text, "SampleResourceImpl.java");

		for (Chunk chunk : chunks) {
			List<String> headingPath = chunk.getHeadingPath();

			Assert.assertEquals("SampleResourceImpl", headingPath.get(0));
		}
	}

	@Test
	public void testParseImportBlockIsStripped() {
		String text = _read("SampleResourceImpl.java.txt");

		JavaChunker javaChunker = new JavaChunker();

		List<Chunk> chunks = javaChunker.parse(text, "SampleResourceImpl.java");

		for (Chunk chunk : chunks) {
			Assert.assertFalse(
				"chunks must not contain raw import lines",
				chunk.getText(
				).contains(
					"import com.example.other.SampleService"
				));
		}
	}

	@Test
	public void testParseLicenseHeaderIsStripped() {
		String text = _read("SampleResourceImpl.java.txt");

		JavaChunker javaChunker = new JavaChunker();

		List<Chunk> chunks = javaChunker.parse(text, "SampleResourceImpl.java");

		for (Chunk chunk : chunks) {
			Assert.assertFalse(
				"chunks must not contain license-header text",
				chunk.getText(
				).contains(
					"SPDX-FileCopyrightText"
				));
		}
	}

	@Test
	public void testParseMethodNamesBecomeSecondHeadingElement() {
		String text = _read("SampleResourceImpl.java.txt");

		JavaChunker javaChunker = new JavaChunker();

		List<Chunk> chunks = javaChunker.parse(text, "SampleResourceImpl.java");

		List<String> methodNames = new ArrayList<>();

		for (Chunk chunk : chunks) {
			List<String> headingPath = chunk.getHeadingPath();

			if (headingPath.size() == 2) {
				methodNames.add(headingPath.get(1));
			}
		}

		Assert.assertEquals(
			"three top-level methods",
			Arrays.asList("getItems", "postItem", "_validate"), methodNames);
	}

	@Test
	public void testParseOneChunkPerMethodPlusIntro() {
		String text = _read("SampleResourceImpl.java.txt");

		JavaChunker javaChunker = new JavaChunker();

		List<Chunk> chunks = javaChunker.parse(text, "SampleResourceImpl.java");

		Assert.assertEquals("intro + 3 methods", 4, chunks.size());
	}

	private String _read(String fixtureName) {
		String resourcePath =
			"com/liferay/semantic/search/cli/chunker/dependencies/" +
				fixtureName;

		ClassLoader classLoader = JavaChunkerTest.class.getClassLoader();

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