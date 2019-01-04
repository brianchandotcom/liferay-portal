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
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.After;
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
public class DDLRecordIndexerIndexedFieldsTest {

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

		setUpIndexedFieldsFixture();

		setUpIndexerFixture();

		defaultLocale = LocaleThreadLocal.getDefaultLocale();
	}

	@After
	public void tearDown() {
		LocaleThreadLocal.setDefaultLocale(defaultLocale);
	}

	@Test
	public void testIndexedFields() throws Exception {
		Locale locale = LocaleUtil.JAPAN;

		setTestLocale(locale);

		String name = "新規";

		String description = RandomTestUtil.randomString();

		DDLRecord ddlRecord = ddlRecordFixture.createDDLRecord(
			name, description, locale);

		String searchTerm = name;

		Document document = ddlRecordIndexerFixture.searchOnlyOne(
			searchTerm, locale);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(ddlRecord), document, searchTerm);
	}

	protected void setTestLocale(Locale locale) throws Exception {
		ddlRecordFixture.updateDisplaySettings(locale);

		LocaleThreadLocal.setDefaultLocale(locale);
	}

	protected void setUpDDLRecordFixture() throws Exception {
		ddlRecordFixture = new DDLRecordFixture(
			ddlRecordSetLocalService, group, user);

		_ddlRecords = ddlRecordFixture.getDdlRecords();
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
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

	@Inject
	protected DDMIndexer ddmIndexer;

	protected Locale defaultLocale;
	protected Group group;
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	@Inject
	protected StorageEngine storageEngine;

	protected User user;
	protected UserSearchFixture userSearchFixture;

	private static Map<String, String> _getFieldValues(Document document) {
		Map<String, Field> fieldsMap = document.getFields();

		Set<Map.Entry<String, Field>> entrySet = fieldsMap.entrySet();

		return entrySet.stream().collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> {
					Field field = entry.getValue();

					String[] values = field.getValues();

					if (values == null) {
						return null;
					}

					if (values.length == 1) {
						return values[0];
					}

					return String.valueOf(Arrays.asList(values));
				}));
	}

	private Map<String, String> _expectedFieldValues(DDLRecord ddlRecord)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(Field.COMPANY_ID, String.valueOf(ddlRecord.getCompanyId()));

		map.put(
			Field.CLASS_NAME_ID,
			String.valueOf(PortalUtil.getClassNameId(DDLRecordSet.class)));

		DDLRecordSet ddlRecordSet = ddlRecord.getRecordSet();

		map.put(Field.CLASS_PK, String.valueOf(ddlRecordSet.getPrimaryKey()));

		map.put(
			Field.CLASS_TYPE_ID, String.valueOf(ddlRecordSet.getPrimaryKey()));

		map.put(Field.ENTRY_CLASS_NAME, DDLRecord.class.getName());

		map.put(
			Field.ENTRY_CLASS_PK, String.valueOf(ddlRecord.getPrimaryKey()));

		map.put(Field.GROUP_ID, String.valueOf(ddlRecord.getGroupId()));

		map.put(Field.RELATED_ENTRY, Boolean.TRUE.toString());

		map.put(Field.SCOPE_GROUP_ID, String.valueOf(ddlRecord.getGroupId()));

		map.put(Field.STAGING_GROUP, String.valueOf(group.isStagingGroup()));

		map.put(Field.STATUS, String.valueOf(ddlRecord.getStatus()));

		map.put(Field.USER_ID, String.valueOf(ddlRecord.getUserId()));

		map.put(Field.USER_NAME, StringUtil.lowerCase(ddlRecord.getUserName()));

		map.put(Field.VERSION, String.valueOf(ddlRecord.getVersion()));

		map.put("recordSetId", String.valueOf(ddlRecord.getRecordSetId()));

		map.put("viewCount", "0");

		map.put("viewCount_sortable", "0");

		map.put("visible", "true");

		indexedFieldsFixture.populatePriority("0.0", map);

		indexedFieldsFixture.populateUID(
			DDLRecord.class.getName(), ddlRecord.getRecordId(), map);

		_populateAttributes(ddlRecord, map);

		_populateContent(ddlRecord, map);

		_populateDates(ddlRecord, map);

		_populateLocalizedTitles(ddlRecord, map);

		_populateRoles(ddlRecord, map);

		return map;
	}

	private String _extractContent(DDLRecord ddlRecord, Locale locale)
		throws Exception {

		DDLRecordVersion recordVersion = ddlRecord.getRecordVersion();

		DDMFormValues ddmFormValues = storageEngine.getDDMFormValues(
			recordVersion.getDDMStorageId());

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		DDLRecordSet recordSet = recordVersion.getRecordSet();

		_ddlRecordsSet.add(recordSet);

		String indexableAttributes = ddmIndexer.extractIndexableAttributes(
			recordSet.getDDMStructure(), ddmFormValues, locale);

		return indexableAttributes.trim();
	}

	private String _getTitle(long recordSetId, Locale locale) {
		try {
			DDLRecordSet recordSet = ddlRecordFixture.getDdlRecordSet(
				recordSetId);

			DDMStructure ddmStructure = recordSet.getDDMStructure();

			String ddmStructureName = ddmStructure.getName(locale);

			String recordSetName = recordSet.getName(locale);

			return LanguageUtil.format(
				locale, "new-x-for-list-x",
				new Object[] {ddmStructureName, recordSetName}, false);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return StringPool.BLANK;
	}

	private void _populateAttributes(
			DDLRecord ddlRecord, Map<String, String> map)
		throws Exception {

		DDLRecordSet recordSet = ddlRecordFixture.getDdlRecordSet(
			ddlRecord.getRecordSetId());

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Document document = new DocumentImpl();

		ddmIndexer.addAttributes(
			document, ddmStructure, ddlRecord.getDDMFormValues());

		Map<String, String> fieldValues = _getFieldValues(document);

		fieldValues.forEach((k, v) -> map.put(k, v));
	}

	private void _populateContent(DDLRecord ddlRecord, Map<String, String> map)
		throws Exception {

		DDMFormValues ddmFormValues = ddlRecord.getDDMFormValues();

		Set<Locale> locales = ddmFormValues.getAvailableLocales();

		for (Locale locale : locales) {
			StringBundler sb = new StringBundler(3);

			sb.append("ddmContent");
			sb.append(StringPool.UNDERLINE);
			sb.append(LocaleUtil.toLanguageId(locale));

			map.put(sb.toString(), _extractContent(ddlRecord, locale));
		}
	}

	private void _populateDates(DDLRecord ddlRecord, Map<String, String> map) {
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, ddlRecord.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, ddlRecord.getModifiedDate(), map);
		indexedFieldsFixture.populateDate(
			Field.PUBLISH_DATE, ddlRecord.getCreateDate(), map);
		indexedFieldsFixture.populateExpirationDateWithForever(map);
	}

	private void _populateLocalizedTitles(
		DDLRecord ddlRecord, Map<String, String> map) {

		String title = StringUtil.lowerCase(
			_getTitle(ddlRecord.getRecordSetId(), LocaleUtil.US));

		map.put("localized_title", title);

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String key = "localized_title_" + languageId;

			map.put(key, title);
			map.put(key.concat("_sortable"), title);
		}
	}

	private void _populateRoles(DDLRecord ddlRecord, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			ddlRecord.getCompanyId(), DDLRecordSet.class.getName(),
			ddlRecord.getRecordSetId(), ddlRecord.getGroupId(), null, map);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDLRecordIndexerIndexedFieldsTest.class);

	@DeleteAfterTestRun
	private List<DDLRecord> _ddlRecords;

	@DeleteAfterTestRun
	private final List<DDLRecordSet> _ddlRecordsSet = new ArrayList<>();

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}