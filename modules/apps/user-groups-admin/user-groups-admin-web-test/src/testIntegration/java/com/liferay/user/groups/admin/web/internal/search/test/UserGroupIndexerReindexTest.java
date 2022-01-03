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

package com.liferay.user.groups.admin.web.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.search.model.uid.UIDFactory;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.GroupBlueprint;
import com.liferay.users.admin.test.util.search.GroupSearchFixture;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class UserGroupIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		GroupSearchFixture groupSearchFixture = new GroupSearchFixture();

		Group group = groupSearchFixture.addGroup(new GroupBlueprint());

		UserGroupFixture userGroupFixture = new UserGroupFixture(
			group, userGroupLocalService);

		_group = group;

		_groups = groupSearchFixture.getGroups();

		_userGroupFixture = userGroupFixture;
		_userGroups = userGroupFixture.getUserGroups();
	}

	@Test
	public void testReindexing() throws Exception {
		UserGroup userGroup = _userGroupFixture.createUserGroup();

		String searchTerm = userGroup.getName();

		_assertFieldValue(Field.NAME, searchTerm, searchTerm);

		_deleteDocument(userGroup.getCompanyId(), uidFactory.getUID(userGroup));

		_assertNoHits(searchTerm);

		_reindexAllIndexerModels();

		_assertFieldValue(Field.NAME, searchTerm, searchTerm);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected SearchResponse search(String searchTerm) {
		return searcher.search(
			searchRequestBuilderFactory.builder(
			).companyId(
				_group.getCompanyId()
			).fields(
				StringPool.STAR
			).modelIndexerClasses(
				UserGroup.class
			).queryString(
				searchTerm
			).build());
	}

	@Inject(
		filter = "indexer.class.name=com.liferay.portal.kernel.model.UserGroup"
	)
	protected Indexer<UserGroup> indexer;

	@Inject
	protected IndexWriterHelper indexWriterHelper;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@Inject
	protected Searcher searcher;

	@Inject
	protected SearchRequestBuilderFactory searchRequestBuilderFactory;

	@Inject
	protected UIDFactory uidFactory;

	@Inject
	protected UserGroupLocalService userGroupLocalService;

	private void _assertFieldValue(
		String fieldName, String fieldValue, String searchTerm) {

		FieldValuesAssert.assertFieldValue(
			fieldName, fieldValue, search(searchTerm));
	}

	private void _assertNoHits(String searchTerm) {
		FieldValuesAssert.assertFieldValues(
			Collections.emptyMap(), search(searchTerm));
	}

	private void _deleteDocument(long companyId, String uid) throws Exception {
		indexWriterHelper.deleteDocument(
			indexer.getSearchEngineId(), companyId, uid, true);
	}

	private void _reindexAllIndexerModels() throws Exception {
		indexer.reindex(new String[] {String.valueOf(_group.getCompanyId())});
	}

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	private UserGroupFixture _userGroupFixture;

	@DeleteAfterTestRun
	private List<UserGroup> _userGroups;

}