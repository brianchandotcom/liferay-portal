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
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class ExportImportIndexedFieldsTest {

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

		setUpIndexedFieldsFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		ExportImportConfiguration exportImportConfiguration =
			exportImportFixture.createExportImport();

		String searchTerm = exportImportConfiguration.getName();

		Document document = exportImportIndexerFixture.searchOnlyOne(
			searchTerm, _addGroupAttribute());

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(exportImportConfiguration), document,
			searchTerm);
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

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
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
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private HashMap<String, Serializable> _addGroupAttribute() {
		HashMap<String, Serializable> map = new HashMap<>();

		map.put(Field.GROUP_ID, _group.getGroupId());

		return map;
	}

	private Map<String, String> _expectedFieldValues(
		ExportImportConfiguration exportImportConfiguration) {

		Map<String, String> map = new HashMap<>();

		map.put(
			Field.COMPANY_ID,
			String.valueOf(exportImportConfiguration.getCompanyId()));
		map.put(Field.DESCRIPTION, exportImportConfiguration.getDescription());
		map.put(
			Field.ENTRY_CLASS_NAME, ExportImportConfiguration.class.getName());
		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(
				exportImportConfiguration.getExportImportConfigurationId()));
		map.put(
			Field.GROUP_ID,
			String.valueOf(exportImportConfiguration.getGroupId()));
		map.put(Field.NAME, exportImportConfiguration.getName());
		map.put(
			Field.SCOPE_GROUP_ID,
			String.valueOf(exportImportConfiguration.getGroupId()));
		map.put(Field.STAGING_GROUP, String.valueOf(_group.isStagingGroup()));
		map.put(
			Field.STATUS,
			String.valueOf(exportImportConfiguration.getStatus()));
		map.put(
			Field.TYPE, String.valueOf(exportImportConfiguration.getType()));
		map.put(
			Field.USER_ID,
			String.valueOf(exportImportConfiguration.getUserId()));
		map.put(
			Field.USER_NAME,
			StringUtil.lowerCase(exportImportConfiguration.getUserName()));
		map.put(
			"exportImportConfigurationId",
			String.valueOf(
				exportImportConfiguration.getExportImportConfigurationId()));
		map.put(
			"exportImportConfigurationId_sortable",
			String.valueOf(
				exportImportConfiguration.getExportImportConfigurationId()));
		map.put(
			"name_sortable",
			StringUtil.lowerCase(exportImportConfiguration.getName()));

		Map<String, Serializable> setttingMap =
			exportImportConfiguration.getSettingsMap();

		map.put("setting_locale", String.valueOf(setttingMap.get("locale")));
		map.put(
			"setting_privateLayout",
			String.valueOf(setttingMap.get("privateLayout")));

		map.put("setting_sourceGroupId", String.valueOf(0));
		map.put(
			"setting_targetGroupId",
			String.valueOf(setttingMap.get("targetGroupId")));
		map.put("setting_userId", String.valueOf(setttingMap.get("userId")));

		_populateDates(exportImportConfiguration, map);

		indexedFieldsFixture.populateUID(
			ExportImportConfiguration.class.getName(),
			exportImportConfiguration.getExportImportConfigurationId(), map);

		return map;
	}

	private void _populateDates(
		ExportImportConfiguration exportImportConfiguration,
		Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, exportImportConfiguration.getCreateDate(), map);

		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, exportImportConfiguration.getModifiedDate(),
			map);
	}

	@DeleteAfterTestRun
	private List<ExportImportConfiguration> _exportImportConfigurations;

	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}