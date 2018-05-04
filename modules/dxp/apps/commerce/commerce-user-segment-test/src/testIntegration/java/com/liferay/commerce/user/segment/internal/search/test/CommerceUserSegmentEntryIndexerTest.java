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

package com.liferay.commerce.user.segment.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.user.segment.exception.CommerceUserSegmentEntrySystemException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.search.test.util.HitsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Locale;
import java.util.Map;

import org.frutilla.FrutillaRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luca Pellizzon
 */
@RunWith(Arquillian.class)
public class CommerceUserSegmentEntryIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_indexer = _indexerRegistry.getIndexer(CommerceUserSegmentEntry.class);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddCommerceUserSegmentEntry() throws Exception {
		frutillaRule.scenario(
			"Add user segment entry to index"
		).given(
			"I add a user segment entry"
		).when(
			"The user segment entry is added to the index"
		).then(
			"I should be able to find the user segment entry in the index"
		);

		long groupId = _group.getGroupId();

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			_addCommerceUserSegmentEntry(
				groupId, RandomTestUtil.randomBoolean());

		Hits hits = _searchCommerceUserSegmentEntries(
			commerceUserSegmentEntry.getCompanyId(), groupId);

		Document document = HitsAssert.assertOnlyOne(hits);

		Assert.assertEquals(
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId(),
			document.get(Field.ENTRY_CLASS_PK));
		Assert.assertEquals(
			commerceUserSegmentEntry.getName(), document.get(Field.NAME));
	}

	@Test
	public void testDeleteCommerceUserSegmentEntry() throws Exception {
		frutillaRule.scenario(
			"Delete user segment entry"
		).given(
			"A user segment entry"
		).when(
			"I delete the user segment entry"
		).and(
			"I search the user segment entry in the index"
		).then(
			"The user segment entry should not be found"
		);

		long groupId = _group.getGroupId();

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			_addCommerceUserSegmentEntry(groupId, false);

		_commerceUserSegmentEntryLocalService.deleteCommerceUserSegmentEntry(
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId());

		Hits hits = _searchCommerceUserSegmentEntries(
			commerceUserSegmentEntry.getCompanyId(), groupId);

		Assert.assertEquals(hits.toString(), 0, hits.getLength());
	}

	@Test
	public void testDeleteSystemCommerceUserSegmentEntry() throws Exception {
		frutillaRule.scenario(
			"Delete system user segment entry"
		).given(
			"A system user segment entry"
		).when(
			"I delete the user segment entry"
		).then(
			"A CommerceUserSegmentEntrySystemException should be thrown"
		);

		long groupId = _group.getGroupId();

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			_addCommerceUserSegmentEntry(groupId, true);

		boolean check = true;

		try {
			_commerceUserSegmentEntryLocalService.
				deleteCommerceUserSegmentEntry(
					commerceUserSegmentEntry.getCommerceUserSegmentEntryId());
		}
		catch (CommerceUserSegmentEntrySystemException cusese) {
			check = false;
		}

		Assert.assertEquals(false, check);
	}

	@Rule
	public final FrutillaRule frutillaRule = new FrutillaRule();

	private CommerceUserSegmentEntry _addCommerceUserSegmentEntry(
			long groupId, boolean system)
		throws PortalException {

		Map<Locale, String> nameMap = RandomTestUtil.randomLocaleStringMap();
		String key = RandomTestUtil.randomString();
		boolean active = RandomTestUtil.randomBoolean();
		double priority = RandomTestUtil.randomDouble();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return _commerceUserSegmentEntryLocalService.
			addCommerceUserSegmentEntry(
				nameMap, key, active, system, priority, serviceContext);
	}

	private SearchContext _getSearchContext(long companyId, long groupId) {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(companyId);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setEntryClassNames(
			new String[] {CommerceUserSegmentEntry.class.getName()});

		return searchContext;
	}

	private Hits _searchCommerceUserSegmentEntries(long companyId, long groupId)
		throws SearchException {

		SearchContext searchContext = _getSearchContext(companyId, groupId);

		return _indexer.search(searchContext);
	}

	private static Indexer<CommerceUserSegmentEntry> _indexer;

	@Inject
	private static IndexerRegistry _indexerRegistry;

	@Inject
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}