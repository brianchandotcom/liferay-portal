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

package com.liferay.layout.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Vagner B.C
 */
@RunWith(Arquillian.class)
public class LayoutIndexerReindexTest {

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

		setUpLayoutIndexerFixture();

		setUpLayoutFixture();
	}

	@Test
	public void testReindex() throws Exception {
		Layout layout = layoutFixture.createLayout();

		Locale locale = LocaleThreadLocal.getDefaultLocale();

		String searchTerm = layout.getName(locale);

		layoutIndexerFixture.searchOnlyOne(searchTerm);

		Document document = layoutIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		layoutIndexerFixture.deleteDocument(document);

		layoutIndexerFixture.searchNoOne(searchTerm, locale);

		layoutIndexerFixture.reindex(layout.getCompanyId());

		layoutIndexerFixture.searchOnlyOne(searchTerm, locale);
	}

	protected void setUpLayoutFixture() {
		layoutFixture = new LayoutFixture(_group);

		_layouts = layoutFixture.getLayouts();
	}

	protected void setUpLayoutIndexerFixture() {
		layoutIndexerFixture = new IndexerFixture<>(Layout.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected LayoutFixture layoutFixture;
	protected IndexerFixture<Layout> layoutIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Layout> _layouts;

	@DeleteAfterTestRun
	private List<User> _users;

}