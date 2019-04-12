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

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MBCategoryIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();
		setUpMBFixture();
		setUpMBCategoryIndexerFixture();
	}

	@Test
	public void testReindexMBCategory() throws Exception {
		MBCategory mbCategory = mbFixture.createMBCategory();

		String searchTerm = mbCategory.getUserName();

		mbCategoryIndexerFixture.searchOnlyOne(searchTerm);

		Document document = mbCategoryIndexerFixture.searchOnlyOne(searchTerm);

		mbCategoryIndexerFixture.deleteDocument(document);

		mbCategoryIndexerFixture.searchNoOne(searchTerm);

		mbCategoryIndexerFixture.reindex(mbCategory.getCompanyId());

		mbCategoryIndexerFixture.searchOnlyOne(searchTerm);
	}

	protected void setUpMBCategoryIndexerFixture() {
		mbCategoryIndexerFixture = new IndexerFixture<>(MBCategory.class);
	}

	protected void setUpMBFixture() {
		mbFixture = new MBFixture(_group, _user);

		_mbCategories = mbFixture.getMbCategories();
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);
	}

	protected IndexerFixture<MBCategory> mbCategoryIndexerFixture;
	protected MBFixture mbFixture;
	protected UserSearchFixture userSearchFixture;

	private Group _group;

	@DeleteAfterTestRun
	private List<MBCategory> _mbCategories;

	private User _user;

}