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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.internal.analysis.SimpleKeywordTokenizer;
import com.liferay.portal.search.internal.analysis.SubstringFieldQueryBuilder;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
public abstract class BaseSubstringFieldQueryBuilderTestCase
	extends BaseFieldQueryBuilderTestCase {

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

	protected void testBasicWordMatches() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("g", 4);
		assertSearch("meta", 2);
		assertSearch("\"meta\"", 2);
		assertSearch("name", 2);
		assertSearch("NaMe*", 2);
		assertSearch("nameTAG", 1);
		assertSearch("tag1", 1);
		assertSearch("\"tag1\"", 1);

		assertSearch("ame", 2);
		assertSearch("METAG", 1);
		assertSearch("METAG*", 1);

		assertSearch("*METAG", 1);
		assertSearch("tag2", 0);
		assertSearch("1", 1);

		assertSearch("META G", 4);
		assertSearch("META-G", 1);
		assertSearch("name tag", 3);
		assertSearch("name-tag", 0);
		assertSearch("NA-META-G", 1);
		assertSearch("nA mEtA g", 4);
		assertSearch("\"na, meta, g\"", 0);
		assertSearch("tag name", 3);
		assertSearch("\"Tag (Name)\"", 0);
		assertSearch("tag 1", 3);
		assertSearch("tag(142857)", 0);

		assertSearch("\"NA G\"", 0);
		assertSearch("\"Name Tag\"", 0);
		assertSearch("\"tag 1\"", 0);
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

	protected void testNull() throws Exception {
		addDocument("null");
		addDocument("anulled");
		addDocument("The word null is in this sentence");
		addDocument("Ultimate Nullifier");
		addDocument("llun");

		assertSearch(
			StringPool.NULL, "null", "anulled",
			"the word null is in this sentence", "ultimate nullifier");
	}

	protected void testNumbers() throws Exception {
		addDocument("Nametag5");
		addDocument("2Tagname");
		addDocument("LETTERS ONLY");

		assertSearch("2", 1);
		assertSearch("2Tag", 1);
		assertSearch("2Tagname", 1);
		assertSearch("Name", 2);
		assertSearch("Nametag", 1);
		assertSearch("Nametag5", 1);

		assertSearch("5", 1);
		assertSearch("5Nametag", 0);
		assertSearch("5Tagname", 0);
		assertSearch("Nametag2", 0);
		assertSearch("Nametag9", 0);
		assertSearch("Tagname", 1);
		assertSearch("Tagname5", 0);
		assertSearch("Tagname2", 0);
		assertSearch("Tagname9", 0);
	}

	protected void testPhrases() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags here");

		assertSearch("\"TAGS\"", 2);
		assertSearch("\"more\"", 1);
		assertSearch("\"More\"", 1);
		assertSearch("\"ags here\"", 1);
		assertSearch("\"HERE\"", 1);
		assertSearch("\"  tags  \"", 0);
		assertSearch("\"ames of tag\"", 2);
		assertSearch("\"ames of tag\" \"here tags\"", 2);
		assertSearch("\"ore name\" \"ags her\"", 1);
		assertSearch("\"ore name\" \"here tags\"", 1);
		assertSearch("\"ags her\"  ame  \"mor\"", 2);
		assertSearch("\"here tags\"  ame  \"ore\"", 2);
	}

	protected void testStopwords() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags");

		assertSearch("of", 2);
		assertSearch("ames of tags", 2);
		assertSearch("ags ames", 2);
	}

	protected void testSubstrings() throws Exception {
		addDocument("Nametag");
		addDocument("NA-META-G");
		addDocument("Tag Name");
		addDocument("TAG1");

		assertSearch("me", 3);
		assertSearch("me*", 3);
		assertSearch("*me", 3);
		assertSearch("*me*", 3);
		assertSearch("met", 2);
		assertSearch("*met*", 2);
		assertSearch("Na", 3);
		assertSearch("NA*", 3);
		assertSearch("*NA", 3);
		assertSearch("*NA*", 3);
		assertSearch("Namet", 1);
		assertSearch("namet*", 1);
		assertSearch("*namet", 1);
		assertSearch("*namet*", 1);
		assertSearch("Ta", 4);
		assertSearch("Ta*", 4);
		assertSearch("*Ta", 4);
		assertSearch("*Ta*", 4);
		assertSearch("tag", 3);
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