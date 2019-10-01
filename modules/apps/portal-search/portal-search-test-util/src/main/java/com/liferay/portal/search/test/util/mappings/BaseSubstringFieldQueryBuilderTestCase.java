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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.SubstringFieldQueryBuilder;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
public abstract class BaseSubstringFieldQueryBuilderTestCase
	extends BaseFieldQueryBuilderTestCase {

	@Test
	public void testBasicWordMatches() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("ame", 2);
		assertSearch("g", 4);
		assertSearch("META G", 4);
		assertSearch("meta", 2);
		assertSearch("META-G", 1);
		assertSearch("METAG", 1);
		assertSearch("METAG*", 1);
		assertSearch("nA mEtA g", 4);
		assertSearch("NA-META-G", 1);
		assertSearch("name tag", 3);
		assertSearch("name", 2);
		assertSearch("NaMe*", 2);
		assertSearch("nameTAG", 1);
		assertSearch("tag 1", 3);
		assertSearch("tag name", 3);
		assertSearch("tag1", 1);

		assertSearch("1", 1);

		assertSearch("*METAG", 1);

		assertSearch("\"meta\"", 2);
		assertSearch("\"tag1\"", 1);

		assertSearchNoHits("name-tag");
		assertSearchNoHits("tag2");

		assertSearchNoHits("\"NA G\"");
		assertSearchNoHits("\"na, meta, g\"");
		assertSearchNoHits("\"Name Tag\"");
		assertSearchNoHits("\"Tag (Name)\"");
		assertSearchNoHits("\"tag 1\"");
	}

	@Test
	public void testLuceneUnfriendlyTerms() throws Exception {
		assertSearchNoHits(StringPool.STAR);

		assertSearchNoHits(StringPool.AMPERSAND);
		assertSearchNoHits(StringPool.CARET);
		assertSearchNoHits(StringPool.COLON);
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
	public void testMultiwordPhrasePrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tag Names");
		addDocument("Tabs Names Tags");

		assertSearch("\"name ta*\"", 1);
		assertSearch("\"name tag*\"", 1);
		assertSearch("\"name tags*\"", 1);
		assertSearch("\"names ta*\"", 2);
		assertSearch("\"names tab*\"", 1);
		assertSearch("\"names tag*\"", 1);
		assertSearch("\"names tags*\"", 1);
		assertSearch("\"tabs name*\"", 1);
		assertSearch("\"tabs names ta*\"", 1);
		assertSearch("\"tabs names tag*\"", 1);
		assertSearch("\"tabs names tags*\"", 1);
		assertSearch("\"tabs names*\"", 1);
		assertSearch("\"tag na*\"", 1);
		assertSearch("\"tag name*\"", 1);
		assertSearch("\"tag names*\"", 1);

		assertSearchNoHits("\"name tab*\"");
		assertSearchNoHits("\"name tabs*\"");
		assertSearchNoHits("\"names tabs*\"");
		assertSearchNoHits("\"tab na*\"");
		assertSearchNoHits("\"tab names*\"");
		assertSearchNoHits("\"tabs na ta*\"");
		assertSearchNoHits("\"tabs name ta*\"");
		assertSearchNoHits("\"tags na ta*\"");
		assertSearchNoHits("\"tags names tabs*\"");
		assertSearchNoHits("\"tags names*\"");
		assertSearchNoHits("\"zz na*\"");
		assertSearchNoHits("\"zz name*\"");
		assertSearchNoHits("\"zz names*\"");
		assertSearchNoHits("\"zz ta*\"");
		assertSearchNoHits("\"zz tab*\"");
		assertSearchNoHits("\"zz tabs*\"");
		assertSearchNoHits("\"zz tag*\"");
		assertSearchNoHits("\"zz tags*\"");
	}

	@Test
	public void testNull() throws Exception {
		addDocument("null");
		addDocument("anulled");
		addDocument("The word null is in this sentence");
		addDocument("Ultimate Nullifier");
		addDocument("llun");

		assertSearchIgnoreRelevance(
			"null",
			Arrays.asList(
				"null", "anulled", "the word null is in this sentence",
				"ultimate nullifier"));
	}

	@Test
	public void testNumbers() throws Exception {
		addDocument("Nametag5");
		addDocument("2Tagname");
		addDocument("LETTERS ONLY");

		assertSearch("2", 1);
		assertSearch("2Tag", 1);
		assertSearch("2Tagname", 1);
		assertSearch("5", 1);

		assertSearch("Name", 2);
		assertSearch("Nametag", 1);
		assertSearch("Nametag5", 1);
		assertSearch("Tagname", 1);

		assertSearchNoHits("5Nametag");
		assertSearchNoHits("5Tagname");

		assertSearchNoHits("Nametag2");
		assertSearchNoHits("Nametag9");
		assertSearchNoHits("Tagname2");
		assertSearchNoHits("Tagname5");
		assertSearchNoHits("Tagname9");
	}

	@Test
	public void testParentheses() throws Exception {
		addDocument("401 k");
		addDocument("401(k)");

		assertSearch("(", 1);
		assertSearch("(k", 1);
		assertSearch("(k)", 1);
		assertSearch(")", 1);

		assertSearch("1", 2);
		assertSearch("1(", 1);
		assertSearch("1(k", 1);
		assertSearch("1(k)", 1);
		assertSearch("4", 2);
		assertSearch("401 k", 2);
		assertSearch("401", 2);
		assertSearch("401(k)", 1);
		assertSearch("k", 2);
		assertSearch("k)", 1);

		assertSearchNoHits("()");

		assertSearchNoHits("1k");
		assertSearchNoHits("401k");
	}

	@Test
	public void testPhrases() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags here");

		assertSearch("\"ags her\"  ame  \"mor\"", 2);
		assertSearch("\"ags here\"", 1);
		assertSearch("\"ames of tag\" \"here tags\"", 2);
		assertSearch("\"ames of tag\"", 2);
		assertSearch("\"here tags\"  ame  \"ore\"", 2);
		assertSearch("\"HERE\"", 1);
		assertSearch("\"More\"", 1);
		assertSearch("\"more\"", 1);
		assertSearch("\"ore name\" \"ags her\"", 1);
		assertSearch("\"ore name\" \"here tags\"", 1);
		assertSearch("\"TAGS\"", 2);

		assertSearchNoHits("\"  tags  \"");
	}

	@Test
	public void testStopwords() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags");

		assertSearch("of", 2);

		assertSearch("ags ames", 2);
		assertSearch("ames of tags", 2);
	}

	@Test
	public void testSubstrings() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("*", 4);
		assertSearch("**", 4);
		assertSearch("***", 4);
		assertSearch("****", 4);

		assertSearch("me", 3);
		assertSearch("me*", 3);
		assertSearch("met", 2);
		assertSearch("Na", 3);
		assertSearch("NA*", 3);
		assertSearch("Namet", 1);
		assertSearch("namet*", 1);
		assertSearch("Ta", 4);
		assertSearch("Ta*", 4);
		assertSearch("tag", 3);

		assertSearch("*me", 3);
		assertSearch("*me*", 3);
		assertSearch("*met*", 2);
		assertSearch("*NA", 3);
		assertSearch("*NA*", 3);
		assertSearch("*namet", 1);
		assertSearch("*namet*", 1);
		assertSearch("*Ta", 4);
		assertSearch("*Ta*", 4);
	}

	@Test
	public void testWildcardCharacters() throws Exception {
		addDocument("AAA+BBB-CCC{DDD]");
		addDocument("AAA BBB CCC DDD");
		addDocument("M*A*S*H");
		addDocument("M... A... S... H");
		addDocument("Who? When? Where?");
		addDocument("Who. When. Where.");

		assertSearch("AAA+???-CCC?DDD]", Arrays.asList("aaa+bbb-ccc{ddd]"));
		assertSearch("AAA+*{DDD*", Arrays.asList("aaa+bbb-ccc{ddd]"));
		assertSearch("AA?+BB?-CC?{DD?]", Arrays.asList("aaa+bbb-ccc{ddd]"));
		assertSearch("AA*+BB*-CC*{DD*]", Arrays.asList("aaa+bbb-ccc{ddd]"));

		assertSearchIgnoreRelevance(
			"M*A*S*H", Arrays.asList("m*a*s*h", "m... a... s... h"));
		assertSearchIgnoreRelevance(
			"M A S H",
			Arrays.asList(
				"m*a*s*h", "m... a... s... h", "aaa+bbb-ccc{ddd]",
				"aaa bbb ccc ddd", "who? when? where?", "who. when. where."));
		assertSearchIgnoreRelevance(
			"M* A* *S *H",
			Arrays.asList(
				"m*a*s*h", "m... a... s... h", "aaa+bbb-ccc{ddd]",
				"aaa bbb ccc ddd", "who? when? where?", "who. when. where."));

		assertSearchIgnoreRelevance(
			"When?", Arrays.asList("who? when? where?", "who. when. where."));
		assertSearchIgnoreRelevance(
			"Who? When?",
			Arrays.asList("who? when? where?", "who. when. where."));
		assertSearchIgnoreRelevance(
			"Who? *en? Where?",
			Arrays.asList("who? when? where?", "who. when. where."));
		assertSearchIgnoreRelevance(
			"Who? * Where?",
			Arrays.asList(
				"who? when? where?", "who. when. where.", "aaa+bbb-ccc{ddd]",
				"aaa bbb ccc ddd", "m*a*s*h", "m... a... s... h"));
		assertSearchIgnoreRelevance(
			"Who?   When?   Where?",
			Arrays.asList("who? when? where?", "who. when. where."));
		assertSearchIgnoreRelevance(
			"Wh?? W?en? Wher??",
			Arrays.asList("who? when? where?", "who. when. where."));
		assertSearchIgnoreRelevance(
			"Wh* W*en* Wher*",
			Arrays.asList("who? when? where?", "who. when. where."));
	}

	@Override
	protected FieldQueryBuilder createFieldQueryBuilder() {
		return new SubstringFieldQueryBuilder() {
			{
				keywordTokenizer = new SimpleKeywordTokenizer();
			}
		};
	}

	@Override
	protected String getField() {
		return Field.TREE_PATH;
	}

	protected String[] toLowerCase(String[] values) {
		String[] clone = values.clone();

		StringUtil.lowerCase(clone);

		return clone;
	}

	@Override
	protected String[] transformFieldValues(String... values) {
		return toLowerCase(values);
	}

}