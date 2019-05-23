/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.suggest;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
public class DictionaryReaderTest {

	@Test
	public void testBlanks() throws Exception {
		assertEntries(
			Arrays.asList("one=1.0", "two=2.0", "three=3.0"),
			createDictionaryReader(
				StringPool.BLANK, "one 1", StringPool.BLANK, "two 2",
				StringPool.BLANK, StringPool.BLANK, "three 3", StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK));
	}

	@Test
	public void testDuplicates() throws Exception {
		assertEntries(
			Arrays.asList("one", "two", "one", "two"),
			createDictionaryReader("one", "two", "one", "two"));
	}

	@Test
	public void testNull() throws Exception {
		assertEntries(
			Arrays.asList(StringPool.NULL),
			createDictionaryReader(StringPool.NULL));
	}

	@Test
	public void testNullUppercase() throws Exception {
		assertEntries(
			Arrays.asList(StringUtil.toUpperCase(StringPool.NULL)),
			createDictionaryReader(StringUtil.toUpperCase(StringPool.NULL)));
	}

	@Test
	public void testNullWithWeight() throws Exception {
		assertEntries(
			Arrays.asList("null=0.5"), createDictionaryReader("null 0.5"));
	}

	@Test
	public void testNumbers() throws Exception {
		assertEntries(
			Arrays.asList("1=1.0", "2", "3", "4=4.0"),
			createDictionaryReader("1 1", "2", " 3", " 4 4"));
	}

	@Test
	public void testPoint() throws Exception {
		assertEntries(
			Arrays.asList("after=5.0", "before=0.5"),
			createDictionaryReader("after 5.", "before .5"));
	}

	@Test
	public void testSpaces() throws Exception {
		assertEntries(
			Arrays.asList("leading=1.0", "trailing", "wide=5.0"),
			createDictionaryReader(" leading 1", "trailing ", "  wide   5"));
	}

	@Test
	public void testSpacesInBetween() throws Exception {
		assertEntries(
			Arrays.asList("too"),
			createDictionaryReader("too many spaces 3.14"));
	}

	@Test
	public void testSpacesOnly() throws Exception {
		assertEntries(
			Arrays.asList(),
			createDictionaryReader(
				StringPool.SPACE, StringPool.DOUBLE_SPACE,
				StringPool.THREE_SPACES));
	}

	@Test
	public void testUppercase() throws Exception {
		assertEntries(Arrays.asList("SDK"), createDictionaryReader("SDK"));
	}

	@Test
	public void testWeightMissing() throws Exception {
		assertEntries(
			Arrays.asList("weightless"), createDictionaryReader("weightless"));
	}

	protected static Map<String, Float> toMap(String key, double value) {
		return Collections.singletonMap(key, (float)value);
	}

	protected void assertEntries(
		List<String> expectedList, DictionaryReader dictionaryReader) {

		List<String> actualList = new LinkedList<>();

		dictionaryReader.accept(
			dictionaryEntry -> actualList.add(toString(dictionaryEntry)));

		Assert.assertEquals(expectedList, actualList);
	}

	protected DictionaryReader createDictionaryReader(String... lines)
		throws Exception {

		String content = StringUtil.merge(lines, StringPool.NEW_LINE);

		return new DictionaryReader(
			new ByteArrayInputStream(content.getBytes()));
	}

	protected String toString(DictionaryEntry dictionaryEntry) {
		float weight = dictionaryEntry.getWeight();
		String word = dictionaryEntry.getWord();

		if (weight == 0) {
			return word;
		}

		return word + "=" + weight;
	}

}