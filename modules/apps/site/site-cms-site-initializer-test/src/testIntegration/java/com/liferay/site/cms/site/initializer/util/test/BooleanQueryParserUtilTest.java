/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.DateRangeTermQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.TermQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.cms.site.initializer.util.BooleanQueryParserUtil;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class BooleanQueryParserUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testParseFilterWithClauseIn() throws Exception {
		String filterString =
			"cmsRoot eq true and cmsSection eq 'files' and status in (0, 2, 3)";

		BooleanQuery booleanQuery = BooleanQueryParserUtil.parse(
			_queries, filterString);

		List<Query> filterQueryClauses = booleanQuery.getFilterQueryClauses();

		Assert.assertEquals(
			filterQueryClauses.toString(), 2, filterQueryClauses.size());

		List<Query> queries = ListUtil.filter(
			filterQueryClauses,
			filterQueryClause -> {
				TermQuery filterQueryClause1 = (TermQuery)filterQueryClause;

				return Objects.equals(
					filterQueryClause1.getField(), "cms_root");
			});

		Assert.assertEquals(queries.toString(), 1, queries.size());

		TermQuery termQuery = (TermQuery)queries.get(0);

		Assert.assertEquals("true", termQuery.getValue());

		queries = ListUtil.filter(
			filterQueryClauses,
			filterQueryClause -> {
				TermQuery filterQueryClause1 = (TermQuery)filterQueryClause;

				return Objects.equals(
					filterQueryClause1.getField(), "cms_section");
			});

		Assert.assertEquals(queries.toString(), 1, queries.size());
		termQuery = (TermQuery)queries.get(0);

		Assert.assertEquals("files", termQuery.getValue());

		List<Query> shouldQueryClauses = booleanQuery.getShouldQueryClauses();

		Assert.assertEquals(
			shouldQueryClauses.toString(), 1, shouldQueryClauses.size());

		TermsQuery termsQuery = (TermsQuery)shouldQueryClauses.get(0);

		Assert.assertEquals("status", termsQuery.getField());

		Assert.assertArrayEquals(
			new String[] {"0", "2", "3"}, termsQuery.getValues());
	}

	@Test
	public void testParseFilterWithClauseOr() throws Exception {
		String filterString =
			"cmsKind eq 'object' and (cmsSection eq 'contents' or cmsSection " +
				"eq 'files') and status in (0,2,3)";

		BooleanQuery booleanQuery1 = BooleanQueryParserUtil.parse(
			_queries, filterString);

		List<Query> filterQueryClauses = booleanQuery1.getFilterQueryClauses();

		Assert.assertEquals(
			filterQueryClauses.toString(), 1, filterQueryClauses.size());

		TermQuery termQuery = (TermQuery)filterQueryClauses.get(0);

		Assert.assertEquals("cms_kind", termQuery.getField());
		Assert.assertEquals("object", termQuery.getValue());

		List<Query> mustQueryClauses = booleanQuery1.getMustQueryClauses();

		Assert.assertEquals(
			mustQueryClauses.toString(), 1, mustQueryClauses.size());

		BooleanQuery booleanQuery2 = (BooleanQuery)mustQueryClauses.get(0);

		List<Query> shouldQueryClauses = booleanQuery2.getShouldQueryClauses();

		Assert.assertEquals(
			shouldQueryClauses.toString(), 2, shouldQueryClauses.size());

		List<Query> queries = ListUtil.filter(
			shouldQueryClauses,
			filterQueryClause -> {
				TermQuery filterQueryClause1 = (TermQuery)filterQueryClause;

				return Objects.equals(
					filterQueryClause1.getValue(), "contents");
			});

		Assert.assertEquals(queries.toString(), 1, queries.size());
		termQuery = (TermQuery)queries.get(0);

		Assert.assertEquals("cms_section", termQuery.getField());

		queries = ListUtil.filter(
			shouldQueryClauses,
			filterQueryClause -> {
				TermQuery filterQueryClause1 = (TermQuery)filterQueryClause;

				return Objects.equals(filterQueryClause1.getValue(), "files");
			});

		Assert.assertEquals(queries.toString(), 1, queries.size());
		termQuery = (TermQuery)queries.get(0);

		Assert.assertEquals("cms_section", termQuery.getField());

		shouldQueryClauses = booleanQuery1.getShouldQueryClauses();

		Assert.assertEquals(
			shouldQueryClauses.toString(), 1, shouldQueryClauses.size());

		TermsQuery termsQuery = (TermsQuery)shouldQueryClauses.get(0);

		Assert.assertEquals("status", termsQuery.getField());

		Assert.assertArrayEquals(
			new String[] {"0", "2", "3"}, termsQuery.getValues());
	}

	@Test
	public void testParseFilterWithDataRangeClause() {
		String filterString = StringBundler.concat(
			"cmsRoot eq true and cmsSection eq 'files' and status in (0, 2, ",
			"3) and (dateCreated ge 2025-09-28T00:00:00.000Z) and ",
			"(dateCreated le 2025-10-02T23:59:59.999Z)");

		BooleanQuery booleanQuery = BooleanQueryParserUtil.parse(
			_queries, filterString);

		List<Query> filterQueryClauses = booleanQuery.getFilterQueryClauses();

		Assert.assertEquals(
			filterQueryClauses.toString(), 2, filterQueryClauses.size());

		List<Query> queries = ListUtil.filter(
			filterQueryClauses,
			filterQueryClause -> {
				TermQuery filterQueryClause1 = (TermQuery)filterQueryClause;

				return Objects.equals(
					filterQueryClause1.getField(), "cms_root");
			});

		Assert.assertEquals(queries.toString(), 1, queries.size());

		TermQuery termQuery1 = (TermQuery)queries.get(0);

		Assert.assertEquals("true", termQuery1.getValue());

		queries = ListUtil.filter(
			filterQueryClauses,
			filterQueryClause -> {
				TermQuery termQuery2 = (TermQuery)filterQueryClause;

				return Objects.equals(termQuery2.getField(), "cms_section");
			});

		Assert.assertEquals(queries.toString(), 1, queries.size());

		TermQuery termQuery3 = (TermQuery)queries.get(0);

		Assert.assertEquals("files", termQuery3.getValue());

		List<Query> mustQueryClauses = booleanQuery.getMustQueryClauses();

		Assert.assertEquals(
			mustQueryClauses.toString(), 1, mustQueryClauses.size());

		DateRangeTermQuery dateRangeTermQuery =
			(DateRangeTermQuery)mustQueryClauses.get(0);

		Assert.assertEquals("createDate", dateRangeTermQuery.getField());
		Assert.assertTrue(dateRangeTermQuery.isIncludesLower());
		Assert.assertTrue(dateRangeTermQuery.isIncludesUpper());
		Assert.assertEquals(
			"20250928000000", dateRangeTermQuery.getLowerBound());
		Assert.assertEquals(
			"20251002235959", dateRangeTermQuery.getUpperBound());

		List<Query> shouldQueryClauses = booleanQuery.getShouldQueryClauses();

		Assert.assertEquals(
			shouldQueryClauses.toString(), 1, shouldQueryClauses.size());

		TermsQuery termsQuery = (TermsQuery)shouldQueryClauses.get(0);

		Assert.assertEquals("status", termsQuery.getField());

		Assert.assertArrayEquals(
			new String[] {"0", "2", "3"}, termsQuery.getValues());
	}

	@Inject
	private Queries _queries;

}