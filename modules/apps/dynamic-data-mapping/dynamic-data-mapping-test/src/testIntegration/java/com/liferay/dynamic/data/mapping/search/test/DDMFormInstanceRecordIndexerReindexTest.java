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

package com.liferay.dynamic.data.mapping.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceRecordIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserIndexerFixture();

		setUpUserSearchFixture();

		setUpDDMFormInstanceRecordFixture();
	}

	@Test
	public void testReindexing() throws Exception {
		String searchTerm = user.getFullName();

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordFixture.createDDMFormInstanceRecord();

		Document document = ddmFormInstanceRecordIndexerFixture.searchOnlyOne(
			searchTerm);

		ddmFormInstanceRecordIndexerFixture.deleteDocument(document);

		ddmFormInstanceRecordIndexerFixture.searchNoOne(searchTerm);

		ddmFormInstanceRecordIndexerFixture.reindex(
			ddmFormInstanceRecord.getCompanyId());

		ddmFormInstanceRecordIndexerFixture.searchOnlyOne(searchTerm);
	}

	protected void setUpDDMFormInstanceRecordFixture() throws Exception {
		ddmFormInstanceRecordFixture = new DDMFormInstanceRecordFixture(
			group, user);

		_ddmFormInstanceRecords =
			ddmFormInstanceRecordFixture.getDdmFormInstanceRecords();
	}

	protected void setUpUserIndexerFixture() {
		ddmFormInstanceRecordIndexerFixture = new IndexerFixture<>(
			DDMFormInstanceRecord.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();

		user = userSearchFixture.addUser(RandomTestUtil.randomString(), group);
	}

	protected DDMFormInstanceRecordFixture ddmFormInstanceRecordFixture;
	protected IndexerFixture<DDMFormInstanceRecord>
		ddmFormInstanceRecordIndexerFixture;
	protected Group group;
	protected User user;
	protected UserSearchFixture userSearchFixture;

	@DeleteAfterTestRun
	private List<DDMFormInstanceRecord> _ddmFormInstanceRecords;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}