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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.TitleFieldQueryBuilder;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
public abstract class BaseTitleFieldQueryBuilderTestCase
	extends BaseFieldQueryBuilderTestCase {

	@Test
	public void testBasicWordMatches() throws Exception {
		addDocument("name tag end");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("end", Arrays.asList("name tag end"));
		assertSearch("g", Arrays.asList("NA-META-G"));
		assertSearch("META G", Arrays.asList("NA-META-G"));
		assertSearch("meta", Arrays.asList("NA-META-G"));
		assertSearch("META-G", Arrays.asList("NA-META-G"));
		assertSearch("nA mEtA g", Arrays.asList("NA-META-G"));
		assertSearch("NA-META-G", Arrays.asList("NA-META-G"));
		assertSearch("na-meta-g", Arrays.asList("NA-META-G"));
		assertSearch("name tag", Arrays.asList("name tag end", "Tag Name"));
		assertSearch("name", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("NaMe*", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("name-tag", Arrays.asList("name tag end", "Tag Name"));
		assertSearch("tag 1", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("tag name", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("tag", Arrays.asList("Tag Name", "name tag end", "TAG1"));
		assertSearch("tag(142857)", Arrays.asList("Tag Name", "name tag end"));
		assertSearch("tag1", Arrays.asList("TAG1"));

		assertSearchNoHits("1");
		assertSearchNoHits("ame");
		assertSearchNoHits("METAG");
		assertSearchNoHits("nameTAG");
		assertSearchNoHits("tag2");
	}

	@Test
	public void testDot() throws Exception {
		addDocument("1.0");
		addDocument("1.1");

		assertSearch("1.0", Arrays.asList("1.0"));
		assertSearch("1.1", Arrays.asList("1.1"));

		assertSearch("1", Arrays.asList("1.0", "1.1"));

		assertSearchNoHits("0");
	}

	@Test
	public void testExactMatchBoost() throws Exception {
		addDocument("one two three four five six seven eight");
		addDocument("one two three four five six");
		addDocument("three four five six seven");
		addDocument("one two four five seven eight");

		assertSearch(
			"one two four five seven eight",
			Arrays.asList(
				"one two four five seven eight",
				"one two three four five six seven eight",
				"one two three four five six", "three four five six seven"));
		assertSearch(
			"three four five six seven",
			Arrays.asList(
				"three four five six seven",
				"one two three four five six seven eight",
				"one two three four five six",
				"one two four five seven eight"));

		assertSearch(
			"\"one two\" \"three four\" \"five six\"",
			Arrays.asList(
				"one two three four five six",
				"one two three four five six seven eight"));
		assertSearch(
			"\"three four\" five \"six seven\"",
			Arrays.asList(
				"three four five six seven",
				"one two three four five six seven eight"));
	}

	@Test
	public void testLuceneUnfriendlyTerms() throws Exception {
		assertSearchNoHits(StringPool.STAR);

		assertSearchNoHits(StringPool.AMPERSAND);
		assertSearchNoHits(StringPool.DASH);
		assertSearchNoHits(StringPool.EXCLAMATION);

		assertSearchNoHits(StringPool.CLOSE_PARENTHESIS);
		assertSearchNoHits(StringPool.OPEN_PARENTHESIS);

		assertSearchNoHits(StringPool.CLOSE_BRACKET);
		assertSearchNoHits(StringPool.OPEN_BRACKET);

		assertSearchNoHits(StringPool.CLOSE_CURLY_BRACE);
		assertSearchNoHits(StringPool.OPEN_CURLY_BRACE);

		assertSearchNoHits(StringPool.BACK_SLASH);

		assertSearchNoHits(
			StringBundler.concat(
				StringPool.STAR, StringPool.SPACE, StringPool.AMPERSAND,
				StringPool.DASH, StringPool.SPACE, StringPool.EXCLAMATION));

		assertSearchNoHits("AND");
		assertSearchNoHits("NOT");
		assertSearchNoHits("OR");

		assertSearchNoHits("ONE AND TWO OR THREE NOT FOUR");

		assertSearchNoHits("\"ONE\" NOT \"TWO\"");
	}

	@Test
	public void testMultiwordPrefixes() throws Exception {
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
		assertSearch("tabs names tags", 4);
		assertSearch("tabs names", 3);
		assertSearch("tag na", 1);
		assertSearch("tag name", 2);
		assertSearch("tag names", 3);
		assertSearch("tags na ta", 2);
		assertSearch("tags names tabs", 4);
		assertSearch("tags names", 4);
		assertSearch("zz name", 1);
		assertSearch("zz names", 3);
		assertSearch("zz tab", 1);
		assertSearch("zz tabs", 1);
		assertSearch("zz tag", 1);
		assertSearch("zz tags", 2);

		assertSearchNoHits("zz na");
		assertSearchNoHits("zz ta");
	}

	@Test
	public void testNull() throws Exception {
		addDocument("null");
		addDocument("anulled");
		addDocument("The word null is in this sentence");
		addDocument("Ultimate Nullifier");
		addDocument("llun");

		assertSearch(
			"null",
			Arrays.asList(
				"null", "The word null is in this sentence",
				"Ultimate Nullifier"));
	}

	@Test
	public void testNumbers() throws Exception {
		addDocument("Nametag5");
		addDocument("2Tagname");
		addDocument("LETTERS ONLY");

		assertSearch("2", 1);
		assertSearch("2Tag", 1);
		assertSearch("2Tagname", 1);
		assertSearch("Name", 1);
		assertSearch("Nametag", 1);
		assertSearch("Nametag5", 1);

		assertSearchNoHits("5");
		assertSearchNoHits("5Nametag");
		assertSearchNoHits("5Tagname");
		assertSearchNoHits("Nametag2");
		assertSearchNoHits("Nametag9");
		assertSearchNoHits("Tagname");
		assertSearchNoHits("Tagname5");
		assertSearchNoHits("Tagname2");
		assertSearchNoHits("Tagname9");
	}

	@Test
	public void testPhrasePrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("\"me*\"", 1);
		assertSearch("\"meta\"", 1);
		assertSearch("\"na, meta, g\"", 1);
		assertSearch("\"namet*\"", 1);
		assertSearch("\"Ta*\"", 2);
		assertSearch("\"Tag (Name)\"", 1);
		assertSearch("\"tag1\"", 1);
		assertSearch("\"tag\"", 1);

		assertSearch("\"*me*\"", 1);
		assertSearch("\"*met*\"", 1);
		assertSearch("\"*namet*\"", 1);
		assertSearch("\"*Ta*\"", 2);

		assertSearchNoHits("\"met\"");
		assertSearchNoHits("\"NA G\"");
		assertSearchNoHits("\"Namet\"");
		assertSearchNoHits("\"Ta\"");
		assertSearchNoHits("\"tag 1\"");

		assertSearchNoHits("\"*me\"");
		assertSearchNoHits("\"*namet\"");
		assertSearchNoHits("\"*Ta\"");
	}

	@Test
	public void testPhrases() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags here");

		assertSearch("\"HERE\"", 1);
		assertSearch("\"more\"   names   \"here\"", 1);
		assertSearch("\"More\"", 1);
		assertSearch("\"more\"", 1);
		assertSearch("\"names of tags\"", 2);
		assertSearch("\"NAmes\" \"TAGS\"", 2);
		assertSearch("\"names\" MORE \"tags\"", 1);
		assertSearch("\"names\" of \"tAgs\"", 2);
		assertSearch("\"Tags here\"", 1);
		assertSearch("\"Tags\" here", 1);
		assertSearch("\"TAGS\"", 2);

		assertSearch("\"   more   \"   tags   \"   here   \"", 1);

		assertSearchNoHits("\"more\" other \"here\"");
		assertSearchNoHits("\"name\" of \"tags\"");
	}

	@Test
	public void testStopwords() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags");

		assertSearch("of", 2);

		assertSearch("Names of tags", 2);
		assertSearch("tags names", 2);
	}

	@Test
	public void testWhitespace() throws Exception {
		String ideographicSpace = "\u3000";

		assertSearchNoHits(ideographicSpace);
		assertSearchNoHits("ONE" + ideographicSpace + "TWO");
		assertSearchNoHits("\"ONE\"" + ideographicSpace + "\"TWO\"");
	}

	@Test
	public void testWildcardCharacters() throws Exception {
		addDocument("AAA+BBB-CCC{DDD]");
		addDocument("AAA BBB CCC DDD");
		addDocument("M*A*S*H");
		addDocument("M... A... S... H");
		addDocument("Who? When? Where?");
		addDocument("Who. When. Where.");

		assertSearch("AA?+BB?-CC?{DD?]", Arrays.asList());
		assertSearch("AA*+BB*-CC*{DD*]", Arrays.asList());

		assertSearch("M*A*S*H", Arrays.asList("M*A*S*H", "M... A... S... H"));
		assertSearch("M A S H", Arrays.asList("M*A*S*H", "M... A... S... H"));
		assertSearch(
			"M* A* *S *H", Arrays.asList("M*A*S*H", "M... A... S... H"));

		assertSearch("Wh?? W?en? Wher??", Arrays.asList());
		assertSearch("Wh* W*en* Wher*", Arrays.asList());

		assertSearchIgnoreRelevance(
			"AAA+???-CCC?DDD]",
			Arrays.asList("AAA+BBB-CCC{DDD]", "AAA BBB CCC DDD"));
		assertSearchIgnoreRelevance(
			"AAA+*{DDD*", Arrays.asList("AAA+BBB-CCC{DDD]", "AAA BBB CCC DDD"));

		assertSearchIgnoreRelevance(
			"When?", Arrays.asList("Who? When? Where?", "Who. When. Where."));
		assertSearchIgnoreRelevance(
			"Who? When?",
			Arrays.asList("Who? When? Where?", "Who. When. Where."));
		assertSearchIgnoreRelevance(
			"Who? *en? Where?",
			Arrays.asList("Who? When? Where?", "Who. When. Where."));
		assertSearchIgnoreRelevance(
			"Who? * Where?",
			Arrays.asList("Who? When? Where?", "Who. When. Where."));
		assertSearchIgnoreRelevance(
			"Who?   When?   Where?",
			Arrays.asList("Who? When? Where?", "Who. When. Where."));
	}

	@Test
	public void testWordPrefixes() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("me", 1);
		assertSearch("me*", 1);
		assertSearch("met", 1);
		assertSearch("Na", 3);
		assertSearch("NA*", 3);
		assertSearch("Namet", 1);
		assertSearch("namet*", 1);
		assertSearch("Ta", 2);
		assertSearch("Ta*", 2);
		assertSearch("tag", 2);

		assertSearch("*me", 1);
		assertSearch("*me*", 1);
		assertSearch("*met*", 1);
		assertSearch("*NA", 3);
		assertSearch("*NA*", 3);
		assertSearch("*namet", 1);
		assertSearch("*namet*", 1);
		assertSearch("*Ta", 2);
		assertSearch("*Ta*", 2);

		assertSearchNoHits("ag");
		assertSearchNoHits("amet");
	}

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

}