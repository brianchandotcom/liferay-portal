/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.search.test.util.HitsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class RoleIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() {
		_indexer = _indexerRegistry.getIndexer(Role.class);
	}

	@Test
	public void testSearchByName() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(role.getCompanyId());
		searchContext.setKeywords(role.getName());

		Hits hits = _indexer.search(searchContext);

		Document document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			String.valueOf(role.getRoleId()),
			document.get(Field.ENTRY_CLASS_PK));
	}

	@Test
	public void testSearchByType() throws Exception {
		Role role = RoleTestUtil.addRole(RoleConstants.TYPE_ORGANIZATION);

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(role.getCompanyId());
		searchContext.setKeywords(role.getName());

		Hits hits = _indexer.search(searchContext);

		Document document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			String.valueOf(role.getRoleId()),
			document.get(Field.ENTRY_CLASS_PK));

		role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		searchContext = new SearchContext();

		searchContext.setCompanyId(role.getCompanyId());
		searchContext.setKeywords(role.getName());

		hits = _indexer.search(searchContext);

		document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			String.valueOf(role.getRoleId()),
			document.get(Field.ENTRY_CLASS_PK));

		role = RoleTestUtil.addRole(RoleConstants.TYPE_SITE);

		searchContext = new SearchContext();

		searchContext.setCompanyId(role.getCompanyId());
		searchContext.setKeywords(role.getName());

		hits = _indexer.search(searchContext);

		document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			String.valueOf(role.getRoleId()),
			document.get(Field.ENTRY_CLASS_PK));

		role = RoleTestUtil.addRole(RoleConstants.TYPE_ACCOUNT);

		searchContext = new SearchContext();

		searchContext.setCompanyId(role.getCompanyId());
		searchContext.setKeywords(role.getName());

		hits = _indexer.search(searchContext);

		HitsAssert.assertNoHits(hits);

		searchContext.setAttribute(
			"types", new long[] {RoleConstants.TYPE_ACCOUNT});

		hits = _indexer.search(searchContext);

		document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			String.valueOf(role.getRoleId()),
			document.get(Field.ENTRY_CLASS_PK));
	}

	private static Indexer<Role> _indexer;

	@Inject
	private static IndexerRegistry _indexerRegistry;

}