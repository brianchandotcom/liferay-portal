/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.search.test.util.SearchTestRule;
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
@Sync
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

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

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