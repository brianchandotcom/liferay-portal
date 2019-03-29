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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.util.LocalizationImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseAssetTagNamesFieldQueryBuilderTestCase
	extends BaseTitleFieldQueryBuilderTestCase {

	@Override
	@Test
	public void testMultiwordPrefixes() throws Exception {
		addDocument("Name Tags");
		addDocument("Names Tab");
		addDocument("Tabs Names Tags");
		addDocument("Tag Names");

		List<String> results1 = Arrays.asList(
			"Name Tags", "Names Tab", "Tabs Names Tags", "Tag Names");

		assertSearch("name ta", results1);
		assertSearch("names ta", results1);

		List<String> results2 = Arrays.asList(
			"Names Tab", "Tabs Names Tags", "Name Tags", "Tag Names");

		assertSearch("name tab", results2);
		assertSearch("name tabs", results2);
		assertSearch("names tab", results2);
		assertSearch("names tabs", results2);

		List<String> results3 = Arrays.asList(
			"Name Tags", "Tabs Names Tags", "Tag Names", "Names Tab");

		assertSearch("name tag", results3);
		assertSearch("name tags", results3);
		assertSearch("names tag", results3);
		assertSearch("names tags", results3);

		List<String> results4 = Arrays.asList("Tabs Names Tags", "Names Tab");

		assertSearch("tab na", results4);

		List<String> results5 = Arrays.asList(
			"Tabs Names Tags", "Names Tab", "Name Tags", "Tag Names");

		assertSearch("tab names", results5);
		assertSearch("tabs names", results5);
		assertSearch("tabs names tags", results5);
		assertSearch("tags names tabs", results5);

		List<String> results6 = Arrays.asList("Names Tab", "Tabs Names Tags");

		assertSearch("tabs na ta", results6);

		List<String> results7 = Arrays.asList(
			"Tag Names", "Name Tags", "Tabs Names Tags");

		assertSearch("tag na", results7);

		List<String> results8 = Arrays.asList(
			"Tag Names", "Name Tags", "Tabs Names Tags", "Names Tab");

		assertSearch("tag name", results8);
		assertSearch("tag names", results8);
		assertSearch("tags names", results8);

		List<String> results9 = Arrays.asList(
			"Name Tags", "Tag Names", "Tabs Names Tags");

		assertSearch("tags na ta", results9);

		assertSearch("zz name", 4);
		assertSearch("zz names", 4);
		assertSearch("zz tab", 2);
		assertSearch("zz tabs", 2);
		assertSearch("zz tag", 3);
		assertSearch("zz tags", 3);

		assertSearchNoHits("zz na");
		assertSearchNoHits("zz ta");
	}

	@Override
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
		assertSearch("\"Tags here\"", 1);
		assertSearch("\"Tags\" here", 1);
		assertSearch("\"TAGS\"", 2);

		assertSearch("\"   more   \"   tags   \"   here   \"", 1);

		assertSearchNoHits("\"more\" other \"here\"");
		assertSearchNoHits("\"name\" of \"tags\"");
		assertSearchNoHits("\"names\" of \"tAgs\"");
	}

	@Override
	@Test
	public void testStopwords() throws Exception {
		addDocument("Names of Tags");
		addDocument("More names of tags");

		assertSearchNoHits("of");

		assertSearch("Names of tags", 2);
		assertSearch("tags names", 2);
	}

	@Override
	protected String getField() {
		return _FIELD;
	}

	private static String _getLocalizedName(String name, Locale locale) {
		Localization localization = new LocalizationImpl();

		return localization.getLocalizedName(
			name, LocaleUtil.toLanguageId(locale));
	}

	private static final String _FIELD = _getLocalizedName(
		Field.ASSET_TAG_NAMES, LocaleUtil.US);

}