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
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.SearchEngineHelper;
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

import java.util.List;
import java.util.Locale;

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
public class DDMFormInstanceIndexerReindexTest {

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

		setUpDDMFormInstanceFixture();

		setUpDDMFormInstanceIndexerFixture();
	}

	@Test
	public void testReindexing() throws Exception {
		Locale locale = LocaleUtil.US;

		String searchTerm = user.getFullName();

		ddmFormInstanceFixture.updateDisplaySettings(locale);

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceFixture.addDDMFormInstance();

		Document document = ddmFormInstanceIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		ddmFormInstanceIndexerFixture.deleteDocument(document);

		ddmFormInstanceIndexerFixture.searchNoOne(searchTerm, locale);

		ddmFormInstanceIndexerFixture.reindex(ddmFormInstance.getCompanyId());

		ddmFormInstanceIndexerFixture.searchOnlyOne(searchTerm);

		ddmFormInstanceIndexerFixture.deleteDocument(document);
	}

	protected void setUpDDMFormInstanceFixture() {
		ddmFormInstanceFixture = new DDMFormInstanceFixture(group, user);

		_ddmFormInstances = ddmFormInstanceFixture.getDdmFormInstances();
	}

	protected void setUpDDMFormInstanceIndexerFixture() {
		ddmFormInstanceIndexerFixture = new IndexerFixture<>(
			DDMFormInstance.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();

		user = userSearchFixture.addUser(RandomTestUtil.randomString(), group);
	}

	protected DDMFormInstanceFixture ddmFormInstanceFixture;
	protected IndexerFixture<DDMFormInstance> ddmFormInstanceIndexerFixture;
	protected Group group;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected User user;
	protected UserSearchFixture userSearchFixture;

	@DeleteAfterTestRun
	private List<DDMFormInstance> _ddmFormInstances;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}