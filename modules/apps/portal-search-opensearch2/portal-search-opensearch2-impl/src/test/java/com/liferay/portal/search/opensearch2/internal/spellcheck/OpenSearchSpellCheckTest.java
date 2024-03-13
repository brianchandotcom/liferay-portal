/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.spellcheck;

import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.indexing.LiferayOpenSearchIndexingFixtureFactory;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;
import com.liferay.portal.search.test.util.spellcheck.BaseSpellCheckTestCase;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class OpenSearchSpellCheckTest extends BaseSpellCheckTestCase {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Override
	@Test
	public void testMultipleWords() throws Exception {
		indexSpellCheckWord("space");
		indexSpellCheckWord("search");

		assertSpellCheck("search space", "searc spac");
	}

	@Override
	@Test
	public void testMultipleWordsMap() throws Exception {
		indexSpellCheckWord("space");
		indexSpellCheckWord("search");

		assertSpellCheckMap("{searc=[search], spac=[space]}", "searc spac");
	}

	@Override
	@Test
	public void testQuotedWords() throws Exception {
		indexSpellCheckWord("space");
		indexSpellCheckWord("search");

		assertSpellCheck("space", "\"spac\"");
		assertSpellCheck("space search", "\"spac searc\"");
	}

	@Override
	@Test
	public void testRepeated() throws Exception {
		indexSpellCheckWord("space");
		indexSpellCheckWord("search");

		assertSpellCheck("space search space search", "spac searc spac searc");
	}

	@Override
	@Test
	public void testRepeatedMap() throws Exception {
		indexSpellCheckWord("space");
		indexSpellCheckWord("search");

		assertSpellCheckMap(
			"{spac=[space], searc=[search]}", "spac searc spac searc");
	}

	@Override
	@Test
	public void testSpellCheck() throws Exception {
		indexSpellCheckWord("space");

		assertSpellCheck("space", "spac");
	}

	@Override
	@Test
	public void testSpellCheckMap() throws Exception {
		indexSpellCheckWord("space");

		assertSpellCheckMap("{spac=[space]}", "spac");
	}

	@Override
	@Test
	public void testWhitespace() throws Exception {
		indexSpellCheckWord("space");
		indexSpellCheckWord("search");

		assertSpellCheck("space search", "spac Searc");
		assertSpellCheck("space search", " spac Searc   ");
		assertSpellCheck("space search", "spac    Searc");
	}

	@Override
	@Test
	public void testWhitespaceMap() throws Exception {
		indexSpellCheckWord("space");
		indexSpellCheckWord("search");

		assertSpellCheckMap(
			"{s=[], pa=[], ce=[], searc=[search], h=[]}",
			"s pa  ce    Searc h");
	}

	@Override
	protected IndexingFixture createIndexingFixture() {
		return LiferayOpenSearchIndexingFixtureFactory.getInstance();
	}

}