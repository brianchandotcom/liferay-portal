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

package com.liferay.exportimport.internal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class ExportImportIndexerReindexTest {

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

		setUpExportImportFixture();

		setUpExportImportIndexerFixture();
	}

	@Test
	public void testReindexing() throws Exception {
		Locale locale = LocaleUtil.US;

		ExportImportConfiguration exportImportConfiguration =
			exportImportFixture.createExportImport();

		String searchTerm = exportImportConfiguration.getName();

		exportImportFixture.updateDisplaySettings(locale);

		HashMap<String, Serializable> attributes = _addGroupAttribute();

		Document document = exportImportIndexerFixture.searchOnlyOne(
			searchTerm, locale, attributes);

		exportImportIndexerFixture.deleteDocument(document);

		exportImportIndexerFixture.searchNoOne(searchTerm, locale, attributes);

		exportImportIndexerFixture.reindex(
			exportImportConfiguration.getCompanyId());

		exportImportIndexerFixture.searchOnlyOne(searchTerm, attributes);
	}

	protected void setUpExportImportFixture() {
		exportImportFixture = new ExportImportFixture(_group);

		_exportImportConfigurations =
			exportImportFixture.getExportImportConfigurations();
	}

	protected void setUpExportImportIndexerFixture() {
		exportImportIndexerFixture = new IndexerFixture<>(
			ExportImportConfiguration.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_users = userSearchFixture.getUsers();
	}

	protected ExportImportFixture exportImportFixture;
	protected IndexerFixture<ExportImportConfiguration>
		exportImportIndexerFixture;
	protected UserSearchFixture userSearchFixture;

	private HashMap<String, Serializable> _addGroupAttribute() {
		HashMap<String, Serializable> map = new HashMap<>();

		map.put(Field.GROUP_ID, _group.getGroupId());

		return map;
	}

	@DeleteAfterTestRun
	private List<ExportImportConfiguration> _exportImportConfigurations;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}