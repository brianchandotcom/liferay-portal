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

package com.liferay.dynamic.data.lists.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.ArrayList;
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
public class DDLRecordIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpUserSearchFixture();

		setUpDDLRecordFixture();

		setUpIndexerFixture();
	}

	@Test
	public void testReindexing() throws Exception {
		String name = RandomTestUtil.randomString();

		String description = RandomTestUtil.randomString();

		String searchTerm = name;

		DDLRecord ddlRecord = ddlRecordFixture.createDDLRecord(
			name, description, LocaleUtil.US);

		_addToDeleteList(ddlRecord);

		Document document = ddlRecordIndexerFixture.searchOnlyOne(searchTerm);

		ddlRecordIndexerFixture.deleteDocument(document);

		ddlRecordIndexerFixture.searchNoOne(searchTerm);

		ddlRecordIndexerFixture.reindex(ddlRecord.getCompanyId());

		ddlRecordIndexerFixture.searchOnlyOne(searchTerm);
	}

	protected void setUpDDLRecordFixture() throws Exception {
		ddlRecordFixture = new DDLRecordFixture(
			ddlRecordSetLocalService, group, user);

		_ddlRecords = ddlRecordFixture.getDdlRecords();
	}

	protected void setUpIndexerFixture() {
		ddlRecordIndexerFixture = new IndexerFixture<>(DDLRecord.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();

		user = userSearchFixture.addUser(RandomTestUtil.randomString(), group);
	}

	protected DDLRecordFixture ddlRecordFixture;
	protected IndexerFixture<DDLRecord> ddlRecordIndexerFixture;

	@Inject
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	protected Group group;
	protected User user;
	protected UserSearchFixture userSearchFixture;

	private void _addToDeleteList(DDLRecord ddlRecord) throws PortalException {
		DDLRecordVersion recordVersion = ddlRecord.getRecordVersion();

		DDLRecordSet ddlRecordSet = recordVersion.getRecordSet();

		_ddlRecordsSet.add(ddlRecordSet);
	}

	@DeleteAfterTestRun
	private List<DDLRecord> _ddlRecords;

	@DeleteAfterTestRun
	private final List<DDLRecordSet> _ddlRecordsSet = new ArrayList<>();

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}