/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util.filter.groupid;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.internal.spi.model.query.contributor.GroupIdQueryPreFilterContributor;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Tibor Lipusz
 * @author André de Oliveira
 */
public abstract class BaseGroupIdQueryPreFilterContributorTestCase
	extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Mockito.doReturn(
			Arrays.asList(INACTIVE_GROUP_ID1, INACTIVE_GROUP_ID2)
		).when(
			groupLocalService
		).getGroupIds(
			Mockito.anyLong(), Mockito.eq(false)
		);

		contributor = new GroupIdQueryPreFilterContributor();
	}

	@Test
	public void testNoEmptyClauses() throws Exception {
		Group group = Mockito.mock(Group.class);

		long groupId = 11111;

		Mockito.doReturn(
			group
		).when(
			groupLocalService
		).getGroup(
			groupId
		);

		Mockito.doReturn(
			false
		).when(
			groupLocalService
		).isLiveGroupActive(
			group
		);

		assertSearch(
			indexingTestHelper -> indexingTestHelper.define(
				searchContext -> {
					searchContext.setGroupIds(new long[] {groupId});

					BooleanFilter booleanFilter = (BooleanFilter)createFilter(
						searchContext);

					assertEmptyClauses(booleanFilter.getMustBooleanClauses());
					assertEmptyClauses(
						booleanFilter.getMustNotBooleanClauses());
					assertEmptyClauses(booleanFilter.getShouldBooleanClauses());
				}));
	}

	@Test
	public void testNumberOfTermsGreaterThanTheMaximumAllowed() {
		long[] inactiveGroupIds = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

		addDocuments(inactiveGroupIds);

		Mockito.doReturn(
			ListUtil.fromArray(inactiveGroupIds)
		).when(
			groupLocalService
		).getGroupIds(
			Mockito.anyLong(), Mockito.eq(false)
		);

		setMaxTermsCount(10);

		assertNumberOfMustNotBooleanClauses(1);

		setMaxTermsCount(5);

		assertNumberOfMustNotBooleanClauses(2);

		setMaxTermsCount(3);

		assertNumberOfMustNotBooleanClauses(4);
	}

	@Test
	public void testScopeEverythingWithInactiveGroups() {
		addDocuments(1, 2, 3, INACTIVE_GROUP_ID1, INACTIVE_GROUP_ID2);

		assertSearch(0, "[1, 2, 3]");

		Mockito.verify(
			groupLocalService, Mockito.never()
		).getActiveGroups(
			Mockito.anyLong(), Mockito.anyBoolean()
		);
	}

	@Test
	public void testScopeSingleGroup() throws Exception {
		Group group = Mockito.mock(Group.class);

		Mockito.doReturn(
			group
		).when(
			groupLocalService
		).getGroup(
			2
		);

		Mockito.doReturn(
			true
		).when(
			groupLocalService
		).isLiveGroupActive(
			group
		);

		addDocuments(1, 2, 3, INACTIVE_GROUP_ID1, INACTIVE_GROUP_ID2);

		assertSearch(2, "[2]");
	}

	protected void addDocuments(long... groupIds) {
		for (long groupId : groupIds) {
			addDocument(
				document -> {
					document.addKeyword(Field.GROUP_ID, groupId);
					document.addKeyword(Field.SCOPE_GROUP_ID, groupId);
				});
		}
	}

	protected void assertEmptyClauses(List<BooleanClause<Filter>> clauses) {
		Assert.assertEquals(clauses.toString(), 0, clauses.size());
	}

	protected void assertNumberOfMustNotBooleanClauses(int expected) {
		assertSearch(
			indexingTestHelper -> indexingTestHelper.define(
				searchContext -> {
					searchContext.setGroupIds(new long[] {0});

					BooleanFilter booleanFilter = (BooleanFilter)createFilter(
						searchContext);

					Assert.assertEquals(
						expected,
						booleanFilter.getMustNotBooleanClauses(
						).size());
				}));
	}

	protected void assertSearch(long scopeGroupId, String expected) {
		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.define(
					searchContext -> {
						searchContext.setGroupIds(new long[] {scopeGroupId});

						indexingTestHelper.setFilter(
							createFilter(searchContext));
					});

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse ->
						DocumentsAssert.assertValuesIgnoreRelevance(
							searchResponse.getRequestString(),
							searchResponse.getDocuments(), Field.GROUP_ID,
							expected));
			});
	}

	protected Filter createFilter(SearchContext searchContext) {
		ReflectionTestUtil.setFieldValue(
			contributor, "_groupLocalService", groupLocalService);

		BooleanFilter booleanFilter = new BooleanFilter();

		contributor.contribute(booleanFilter, searchContext);

		return booleanFilter;
	}

	protected void setMaxTermsCount(int maxTermsCount) {
		ReflectionTestUtil.setFieldValue(
			contributor, "_MAX_TERMS_COUNT", maxTermsCount);
	}

	protected static final long INACTIVE_GROUP_ID1 = 4L;

	protected static final long INACTIVE_GROUP_ID2 = 5L;

	protected GroupIdQueryPreFilterContributor contributor;
	protected GroupLocalService groupLocalService = Mockito.mock(
		GroupLocalService.class);

}