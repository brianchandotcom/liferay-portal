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

package com.liferay.portal.search.test.util.mappings;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.TitleFieldQueryBuilder;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
public abstract class BaseTitleFieldQueryBuilderTestCase
	extends BaseFieldQueryBuilderTestCase {

	@Override
	protected FieldQueryBuilder createFieldQueryBuilder() {
		return new TitleFieldQueryBuilder() {
			{
				keywordTokenizer = new SimpleKeywordTokenizer();
			}
		};
	}

	@Override
	protected String getField() {
		return Field.TITLE;
	}

	protected void testBasicWordMatches() throws Exception {
		addDocument("name tag end");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("end", 1);
		assertSearch("name", 2);
		assertSearch("tag", 3);
		assertSearch("na-meta-g", 1);
		assertSearch("\"NAME\"", 2);
	}

	protected void testExactMatchBoost() throws Exception {
		addDocument("one two three four five six seven eight");
		addDocument("one two three four five six");
		addDocument("three four five six seven");
		addDocument("one two four five seven eight");

		assertSearch(
			"three four five six seven", "three four five six seven",
			"one two three four five six seven eight",
			"one two three four five six", "one two four five seven eight");
		assertSearch(
			"one two four five seven eight", "one two four five seven eight",
			"one two three four five six seven eight",
			"one two three four five six", "three four five six seven");
		assertSearch(
			"\"three four\" five \"six seven\"", "three four five six seven",
			"one two three four five six seven eight");
		assertSearch(
			"\"one two\" \"three four\" \"five six\"",
			"one two three four five six",
			"one two three four five six seven eight");
	}

	protected void testMultiwordPhrasePrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("\"name ta*\"", 1);
		assertSearch("\"name tab*\"", 0);
		assertSearch("\"name tabs*\"", 0);
		assertSearch("\"name tag*\"", 1);
		assertSearch("\"name tags*\"", 1);

		assertSearch("\"names ta*\"", 2);
		assertSearch("\"names tab*\"", 1);
		assertSearch("\"names tabs*\"", 0);
		assertSearch("\"names tag*\"", 1);
		assertSearch("\"names tags*\"", 1);

		assertSearch("\"tab na*\"", 0);
		assertSearch("\"tab names*\"", 0);

		assertSearch("\"tabs na ta*\"", 0);
		assertSearch("\"tabs name*\"", 1);
		assertSearch("\"tabs name ta*\"", 0);
		assertSearch("\"tabs names*\"", 1);
		assertSearch("\"tabs names ta*\"", 1);
		assertSearch("\"tabs names tag*\"", 1);
		assertSearch("\"tabs names tags*\"", 1);

		assertSearch("\"tag na*\"", 1);
		assertSearch("\"tag name*\"", 1);
		assertSearch("\"tag names*\"", 1);

		assertSearch("\"tags na ta*\"", 0);
		assertSearch("\"tags names*\"", 0);
		assertSearch("\"tags names tabs*\"", 0);

		assertSearch("\"zz na*\"", 0);
		assertSearch("\"zz name*\"", 0);
		assertSearch("\"zz names*\"", 0);
		assertSearch("\"zz ta*\"", 0);
		assertSearch("\"zz tab*\"", 0);
		assertSearch("\"zz tabs*\"", 0);
		assertSearch("\"zz tag*\"", 0);
		assertSearch("\"zz tags*\"", 0);
	}

	protected void testMultiwordPrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("name ta", 1);
		assertSearch("name tab", 2);
		assertSearch("name tabs", 2);
		assertSearch("name tag", 2);
		assertSearch("name tags", 2);

		assertSearch("names ta", 3);
		assertSearch("names tab", 3);
		assertSearch("names tabs", 3);
		assertSearch("names tag", 3);
		assertSearch("names tags", 4);

		assertSearch("tab na", 1);
		assertSearch("tab names", 3);

		assertSearch("tabs na ta", 1);
		assertSearch("tabs names", 3);
		assertSearch("tabs names tags", 4);

		assertSearch("tag na", 1);
		assertSearch("tag name", 2);
		assertSearch("tag names", 3);

		assertSearch("tags na ta", 2);
		assertSearch("tags names", 4);
		assertSearch("tags names tabs", 4);

		assertSearch("zz na", 0);
		assertSearch("zz name", 1);
		assertSearch("zz names", 3);
		assertSearch("zz ta", 0);
		assertSearch("zz tab", 1);
		assertSearch("zz tabs", 1);
		assertSearch("zz tag", 1);
		assertSearch("zz tags", 2);
	}

	protected void testNull() throws Exception {
		addDocument("null");
		addDocument("anulled");
		addDocument("The word null is in this sentence");
		addDocument("Ultimate Nullifier");
		addDocument("llun");

		assertSearch(
			StringPool.NULL, "null", "The word null is in this sentence",
			"Ultimate Nullifier");
	}

	protected void testNumbers() throws Exception {
		addDocument("Nametag5");
		addDocument("2Tagname");
		addDocument("LETTERS ONLY");

		assertSearch("2", 1);
		assertSearch("2Tag", 1);
		assertSearch("2Tagname", 1);
		assertSearch("Name", 1);
		assertSearch("Nametag", 1);
		assertSearch("Nametag5", 1);

		assertSearch("5", 0);
		assertSearch("5Nametag", 0);
		assertSearch("5Tagname", 0);
		assertSearch("Nametag2", 0);
		assertSearch("Nametag9", 0);
		assertSearch("Tagname", 0);
		assertSearch("Tagname5", 0);
		assertSearch("Tagname2", 0);
		assertSearch("Tagname9", 0);
	}

	protected void testPhrasePrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("\"meta\"", 1);
		assertSearch("\"me*\"", 1);
		assertSearch("\"*me\"", 0);
		assertSearch("\"*me*\"", 1);
		assertSearch("\"met\"", 0);
		assertSearch("\"*met*\"", 1);
		assertSearch("\"Namet\"", 0);
		assertSearch("\"namet*\"", 1);
		assertSearch("\"*namet\"", 0);
		assertSearch("\"*namet*\"", 1);
		assertSearch("\"Ta\"", 0);
		assertSearch("\"Ta*\"", 2);
		assertSearch("\"*Ta\"", 0);
		assertSearch("\"*Ta*\"", 2);
		assertSearch("\"tag\"", 1);
	}

	protected void testPhrases() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags here");

		assertSearch("\"names of tags\"", 2);
		assertSearch("\"Tags here\"", 1);
		assertSearch("\"Tags\" here", 1);
		assertSearch("\"NAmes\" \"TAGS\"", 2);
		assertSearch("\"names\" of \"tAgs\"", 2);
		assertSearch("\"names\" MORE \"tags\"", 1);
		assertSearch("\"name\" of \"tags\"", 0);
		assertSearch("\"more\" other \"here\"", 0);
		assertSearch("\"   more   \"   tags   \"   here   \"", 1);
		assertSearch("\"more\"   names   \"here\"", 1);
		assertSearch("\"TAGS\"", 2);
		assertSearch("\"more\"", 1);
		assertSearch("\"More\"", 1);
		assertSearch("\"HERE\"", 1);
	}

	protected void testStopwords() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags");

		assertSearch("of", 2);
		assertSearch("Names of tags", 2);
		assertSearch("tags names", 2);
	}

	protected void testWordPrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("me", 1);
		assertSearch("me*", 1);
		assertSearch("*me", 1);
		assertSearch("*me*", 1);
		assertSearch("met", 1);
		assertSearch("*met*", 1);
		assertSearch("Na", 3);
		assertSearch("NA*", 3);
		assertSearch("*NA", 3);
		assertSearch("*NA*", 3);
		assertSearch("Namet", 1);
		assertSearch("namet*", 1);
		assertSearch("*namet", 1);
		assertSearch("*namet*", 1);
		assertSearch("Ta", 2);
		assertSearch("Ta*", 2);
		assertSearch("*Ta", 2);
		assertSearch("*Ta*", 2);
		assertSearch("tag", 2);

		assertSearch("ag", 0);
		assertSearch("amet", 0);
	}

}