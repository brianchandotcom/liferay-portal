/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolFields;
import com.liferay.mcp.server.rest.internal.search.index.util.WordUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Petteri Karttunen
 */
public class MCPToolIndexReaderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetEntityName() {
		Assert.assertEquals(
			"blog posting", _getEntityName("create a blog posting in a site"));
	}

	@Test
	public void testGetEntityNameWhenABoundaryWordFollowsTheOfPhrase() {
		Assert.assertEquals(
			"site", _getEntityName("list entries of a site in a folder"));
	}

	@Test
	public void testGetEntityNameWhenOnlyTheVerbSurvives() {
		Assert.assertNull(_getEntityName("delete an object"));
		Assert.assertNull(_getEntityName("get the entry"));
	}

	@Test
	public void testGetEntityNameWhenTheOfPhraseEndsWithAnArticle() {
		Assert.assertEquals(
			"blog postings", _getEntityName("list the blog postings of the"));
	}

	@Test
	public void testGetEntityNameWhenTheOfPhraseNamesTheEntity() {
		Assert.assertEquals(
			"site", _getEntityName("list the blog entries of a site"));
	}

	@Test
	public void testGetEntityNameWhenTheSearchNamesAnEntityLiterally() {
		Assert.assertNull(_getEntityName("create an entity"));
		Assert.assertNull(_getEntityName("list the entities"));
	}

	@Test
	public void testGetFilteredQuery() {
		Query query = QueriesUtil.booleanQuery();

		Assert.assertSame(query, _getFilteredQuery(null, query));

		BooleanQuery booleanQuery = (BooleanQuery)_getFilteredQuery(
			SetUtil.fromArray("headless-delivery-v1.0#postSiteBlogPosting"),
			query);

		List<Query> filterQueryClauses = booleanQuery.getFilterQueryClauses();

		TermsQuery termsQuery = (TermsQuery)filterQueryClauses.get(0);

		Assert.assertEquals(MCPToolFields.UID, termsQuery.getField());
		Assert.assertArrayEquals(
			new String[] {"headless-delivery-v1.0#postSiteBlogPosting"},
			termsQuery.getValues());

		List<Query> mustQueryClauses = booleanQuery.getMustQueryClauses();

		Assert.assertSame(query, mustQueryClauses.get(0));
	}

	@Test
	public void testHasAssociationWords() {
		Assert.assertFalse(_hasAssociationWords("add a user"));
		Assert.assertTrue(_hasAssociationWords("add a user to a site"));
		Assert.assertTrue(_hasAssociationWords("assign a role to a user"));
	}

	@Test
	public void testHasBatchWords() {
		Assert.assertFalse(_hasBatchWords("delete five users"));
		Assert.assertFalse(_hasBatchWords("delete tenant users"));
		Assert.assertTrue(_hasBatchWords("delete twenty users at once"));
		Assert.assertTrue(_hasBatchWords("delete users in bulk"));
	}

	@Test
	public void testHasBatchWordsWhenTheNumberIsAnIdentifier() {
		Assert.assertFalse(_hasBatchWords("get site 20123 details"));
		Assert.assertFalse(_hasBatchWords("update blog posting 40567"));
	}

	private String _getEntityName(String search) {
		String[] words = WordUtil.toSearchWords(search);

		return ReflectionTestUtil.invoke(
			_mcpToolIndexReader, "_getEntityName",
			new Class<?>[] {boolean.class, String[].class},
			_hasAssociationWords(search), words);
	}

	private Query _getFilteredQuery(Set<String> allowedToolKeys, Query query) {
		return ReflectionTestUtil.invoke(
			_mcpToolIndexReader, "_getFilteredQuery",
			new Class<?>[] {Set.class, Query.class}, allowedToolKeys, query);
	}

	private boolean _hasAssociationWords(String search) {
		return ReflectionTestUtil.invoke(
			_mcpToolIndexReader, "_hasAssociationWords",
			new Class<?>[] {String[].class},
			(Object)WordUtil.toSearchWords(search));
	}

	private boolean _hasBatchWords(String search) {
		return ReflectionTestUtil.invoke(
			_mcpToolIndexReader, "_hasBatchWords",
			new Class<?>[] {String[].class},
			(Object)WordUtil.toSearchWords(search));
	}

	private final MCPToolIndexReader _mcpToolIndexReader =
		new MCPToolIndexReader();

}