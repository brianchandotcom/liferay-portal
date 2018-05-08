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

package com.liferay.users.admin.indexer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 */
@RunWith(Arquillian.class)
public class OrganizationIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_indexer = _indexerRegistry.getIndexer(Organization.class);
	}

	@Test
	public void testEmptyQuery() throws Exception {
		List<String> names = getNames(StringPool.BLANK);

		String name = RandomTestUtil.randomString();

		addOrganization(name);

		names.add(name);

		assertSearch(StringPool.BLANK, names);
	}

	@Test
	public void testName() throws Exception {
		addOrganization("Abcd");
		addOrganization("cdef");

		assertHits("Abcd", 1);
		assertHits("abcd", 1);
		assertHits("Ab", 1);
		assertHits("ab", 1);
		assertHits("bc", 0);
		assertHits("cd", 1);
		assertHits("Cd", 1);
		assertHits("cD", 1);
		assertHits("Abcde", 0);
		assertHits("bcde", 0);
		assertHits("cde", 1);
	}

	@Test
	public void testNameMultiword() throws Exception {
		addOrganization("Abcd Efgh Ijkl Mnop");
		addOrganization("qrst UVWX yz");

		assertHits("abcd", 1);
		assertHits("efgh", 1);
		assertHits("ij", 1);
		assertHits("kl", 0);

		assertHits("abcd efgh", 1);
		assertHits("abcd ef", 1);
		assertHits("abcd ijkl", 1);
		assertHits("efgh ijkl", 1);

		assertHits("uvwx", 1);
		assertHits("YZ QRST", 1);
		assertHits("YZ AAA", 1);
		assertHits("AAA uVwX", 1);
		assertHits("qrs UVWX", 1);
		assertHits("qrst UVW", 1);
		assertHits("qrs UVW", 0);

		assertHits("\"abcd\"", 1);
		assertHits("\"Abcd\"", 1);
		assertHits("\"efgh\"", 1);
		assertHits("\"eFgh\"", 1);
		assertHits("\"abcd efgh\"", 1);
		assertHits("\"abcd ef\"", 0);
		assertHits("\"abcd ijkl\"", 0);
		assertHits("\"efgh ijkl\"", 1);
		assertHits("\"efgh ij\"", 0);
		assertHits("\"gh ij\"", 0);

		assertHits("ab EFGH ij mn qr uv YZ", 2);
	}

	protected Organization addOrganization(String name) throws Exception {
		long userId = TestPropsValues.getUserId();
		long parentOrganizationId = 0;
		boolean site = false;

		Organization organization = _organizationLocalService.addOrganization(
			userId, parentOrganizationId, name, site);

		_organizations.add(organization);

		return organization;
	}

	protected void assertHits(String keywords, int length) throws Exception {
		Hits hits = search(keywords);

		Assert.assertEquals(hits.toString(), length, hits.getLength());
	}

	protected void assertSearch(String keywords, List<String> names)
		throws Exception {

		Assert.assertEquals(toString(names), toString(getNames(keywords)));
	}

	protected List<String> getNames(String keywords) throws Exception {
		Hits hits = search(keywords);

		Document[] documents = hits.getDocs();

		List<String> names = new ArrayList<>(documents.length);

		for (Document document : documents) {
			names.add(document.get(Field.NAME));
		}

		return names;
	}

	protected Hits search(String keywords) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setEntryClassNames(
			new String[] {Organization.class.getName()});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSelectedFieldNames(Field.NAME);

		return _indexer.search(searchContext);
	}

	protected String toString(List<String> list) {
		Collections.sort(list);

		return list.toString();
	}

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject
	private static OrganizationLocalService _organizationLocalService;

	private Indexer<Organization> _indexer;

	@DeleteAfterTestRun
	private final List<Organization> _organizations = new ArrayList<>();

}