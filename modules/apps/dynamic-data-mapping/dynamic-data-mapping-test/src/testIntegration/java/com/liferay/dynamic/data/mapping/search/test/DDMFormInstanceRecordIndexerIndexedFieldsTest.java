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
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
public class DDMFormInstanceRecordIndexerIndexedFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpIndexedFieldsFixture();

		setUpDDMFormInstanceRecordIndexerFixture();

		setUpUserSearchFixture();

		setUpDDMFormInstanceRecordFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		String searchTerm = user.getFullName();

		DDMFormInstanceRecord ddmFormInstanceRecord =
			ddmFormInstanceRecordFixture.createDDMFormInstanceRecord();

		Document document = ddmFormInstanceRecordIndexerFixture.searchOnlyOne(
			searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, String> expected = _expectedFieldValues(
			ddmFormInstanceRecord);

		FieldValuesAssert.assertFieldValues(expected, document, searchTerm);
	}

	protected void setUpDDMFormInstanceRecordFixture() throws Exception {
		ddmFormInstanceRecordFixture = new DDMFormInstanceRecordFixture(
			group, user);

		_ddmFormInstanceRecords =
			ddmFormInstanceRecordFixture.getDdmFormInstanceRecords();
	}

	protected void setUpDDMFormInstanceRecordIndexerFixture() {
		ddmFormInstanceRecordIndexerFixture = new IndexerFixture<>(
			DDMFormInstanceRecord.class);
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
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
	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

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

	private Map<String, String> _expectedFieldValues(
			DDMFormInstanceRecord ddmFormInstanceRecord)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		map.put(
			Field.CLASS_NAME_ID,
			String.valueOf(PortalUtil.getClassNameId(DDMFormInstance.class)));

		DDMFormInstance formInstance = ddmFormInstanceRecord.getFormInstance();

		map.put(Field.CLASS_PK, String.valueOf(formInstance.getPrimaryKey()));

		map.put(
			Field.CLASS_TYPE_ID, String.valueOf(formInstance.getPrimaryKey()));

		map.put(
			Field.COMPANY_ID,
			String.valueOf(ddmFormInstanceRecord.getCompanyId()));

		map.put(Field.ENTRY_CLASS_NAME, DDMFormInstanceRecord.class.getName());

		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(ddmFormInstanceRecord.getPrimaryKey()));

		map.put(
			Field.GROUP_ID, String.valueOf(ddmFormInstanceRecord.getGroupId()));

		map.put(Field.RELATED_ENTRY, Boolean.TRUE.toString());

		map.put(
			Field.SCOPE_GROUP_ID,
			String.valueOf(ddmFormInstanceRecord.getGroupId()));

		map.put(Field.STAGING_GROUP, String.valueOf(group.isStagingGroup()));

		map.put(
			Field.STATUS, String.valueOf(ddmFormInstanceRecord.getStatus()));

		map.put(
			Field.USER_ID, String.valueOf(ddmFormInstanceRecord.getUserId()));

		map.put(
			Field.USER_NAME,
			StringUtil.lowerCase(ddmFormInstanceRecord.getUserName()));

		map.put(
			Field.VERSION, String.valueOf(ddmFormInstanceRecord.getVersion()));

		map.put(
			"formInstanceId",
			String.valueOf(ddmFormInstanceRecord.getFormInstanceId()));

		indexedFieldsFixture.populateUID(
			DDMFormInstanceRecord.class.getName(),
			ddmFormInstanceRecord.getFormInstanceRecordId(), map);

		_populateAttributes(ddmFormInstanceRecord, map);

		_populateDates(ddmFormInstanceRecord, map);

		_populateContent(ddmFormInstanceRecord, map);

		_populateRoles(ddmFormInstanceRecord, map);

		return map;
	}

	private String _extractContent(
			DDMFormInstanceRecord ddmFormInstanceRecord, Locale locale)
		throws Exception {

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		if (ddmFormValues == null) {
			return StringPool.BLANK;
		}

		DDMFormInstance ddmFormInstance =
			ddmFormInstanceRecord.getFormInstance();

		String indexableAttributes = _ddmIndexer.extractIndexableAttributes(
			ddmFormInstance.getStructure(), ddmFormValues, locale);

		return indexableAttributes.trim();
	}

	private void _populateAttributes(
			DDMFormInstanceRecord ddmFormInstanceRecord,
			Map<String, String> map)
		throws Exception {

		Document document = new DocumentImpl();

		_ddmIndexer.addAttributes(
			document, ddmFormInstanceRecordFixture.getDdmStructure(),
			ddmFormInstanceRecord.getDDMFormValues());

		Map<String, String> fieldValues = _getFieldValues(document);

		fieldValues.forEach((k, v) -> map.put(k, v));
	}

	private void _populateContent(
			DDMFormInstanceRecord ddmFormInstanceRecord,
			Map<String, String> map)
		throws Exception {

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		Set<Locale> locales = ddmFormValues.getAvailableLocales();

		for (Locale locale : locales) {
			StringBundler sb = new StringBundler(3);

			sb.append("ddmContent");
			sb.append(StringPool.UNDERLINE);
			sb.append(LocaleUtil.toLanguageId(locale));

			map.put(
				sb.toString(), _extractContent(ddmFormInstanceRecord, locale));
		}
	}

	private void _populateDates(
		DDMFormInstanceRecord ddmFormInstanceRecord, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, ddmFormInstanceRecord.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, ddmFormInstanceRecord.getModifiedDate(), map);
	}

	private void _populateRoles(
			DDMFormInstanceRecord ddmFormInstanceRecord,
			Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			ddmFormInstanceRecord.getCompanyId(),
			DDMFormInstance.class.getName(),
			ddmFormInstanceRecord.getFormInstanceId(),
			ddmFormInstanceRecord.getGroupId(), null, map);
	}

	@DeleteAfterTestRun
	private List<DDMFormInstanceRecord> _ddmFormInstanceRecords;

	@Inject
	private DDMIndexer _ddmIndexer;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<User> _users;

}