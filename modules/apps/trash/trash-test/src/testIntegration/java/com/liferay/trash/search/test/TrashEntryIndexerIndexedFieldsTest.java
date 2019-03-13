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

package com.liferay.trash.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalServiceUtil;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
public class TrashEntryIndexerIndexedFieldsTest {

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
		setUpUserSearchFixture();

		setUpTrashEntryFixture();
		setUpTrashEntryIndexerFixture();

		_fileEntry = trashFixture.createFileEntry();
	}

	@Test
	public void testIndexedFields() throws Exception {
		String searchTerm = _fileEntry.getTitle();

		Document document = trashEntryIndexerFixture.searchOnlyOne(searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, String> map = new HashMap<>();

		FieldValuesAssert.assertFieldValues(
			populateExpectedFieldValues(_fileEntry, map), document, searchTerm);
	}

	protected long getDDMStructureId(FileEntry fileEntry) throws Exception {
		long ddmStructureId = 0;

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		List<DLFileEntryMetadata> dlFileEntryMetadatas =
			dlFileEntryMetadataLocalService.getFileVersionFileEntryMetadatas(
				dlFileVersion.getFileVersionId());

		for (DLFileEntryMetadata dlFileEntryMetadata : dlFileEntryMetadatas) {
			if (dlFileEntryMetadata != null) {
				ddmStructureId = dlFileEntryMetadata.getDDMStructureId();
			}
		}

		return ddmStructureId;
	}

	protected Map<String, String> populateExpectedFieldValues(
			FileEntry fileEntry, Map<String, String> map)
		throws Exception {

		map.put(Field.COMPANY_ID, String.valueOf(fileEntry.getCompanyId()));
		map.put(Field.CLASS_NAME_ID, "0");
		map.put(Field.CLASS_PK, "0");
		map.put(Field.ENTRY_CLASS_NAME, DLFileEntry.class.getName());
		map.put(
			Field.ENTRY_CLASS_PK, String.valueOf(fileEntry.getFileEntryId()));
		map.put(Field.FOLDER_ID, String.valueOf(fileEntry.getFolderId()));
		map.put(Field.GROUP_ID, String.valueOf(fileEntry.getGroupId()));
		map.put(Field.SCOPE_GROUP_ID, String.valueOf(fileEntry.getGroupId()));
		map.put(Field.STAGING_GROUP, "false");
		map.put(Field.STATUS, "8");
		map.put(Field.TITLE, fileEntry.getTitle());
		map.put(
			Field.TITLE + "_sortable",
			StringUtil.lowerCase(fileEntry.getTitle()));
		map.put(Field.TREE_PATH, "");
		map.put(Field.USER_ID, String.valueOf(fileEntry.getUserId()));
		map.put(
			Field.USER_NAME, StringUtil.toLowerCase(fileEntry.getUserName()));

		map.put("classTypeId", "0");
		map.put(
			"dataRepositoryId", String.valueOf(fileEntry.getRepositoryId()));
		map.put("fileEntryTypeId", "0");
		map.put("hidden", "false");

		String mimeType = StringUtil.replace(fileEntry.getMimeType(), '/', '_');

		map.put("mimeType", mimeType);
		map.put("mimeType_String_sortable", mimeType);

		map.put("path", fileEntry.getTitle());
		map.put("readCount", String.valueOf(fileEntry.getReadCount()));
		map.put("size", String.valueOf(fileEntry.getSize()));
		map.put("size_sortable", String.valueOf(fileEntry.getSize()));
		map.put("visible", "false");

		_populateDates(fileEntry, map);
		_populateLocalizedTitles(fileEntry, map);
		_populateTrashEntryFields(fileEntry, map);
		_populateViewCount(fileEntry, map);

		indexedFieldsFixture.populatePriority("0.0", map);
		indexedFieldsFixture.populateRoleIdFields(
			fileEntry.getCompanyId(), DLFileEntry.class.getName(),
			fileEntry.getPrimaryKey(), fileEntry.getGroupId(), null, map);
		indexedFieldsFixture.populateUID(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId(), map);

		return map;
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpTrashEntryFixture() throws Exception {
		trashFixture = new TrashFixture();

		trashFixture.setUp();

		trashFixture.setGroup(_group);

		_trashEntries = trashFixture.getTrashEntries();
	}

	protected void setUpTrashEntryIndexerFixture() {
		trashEntryIndexerFixture = new IndexerFixture<>(TrashEntry.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_group = userSearchFixture.addGroup();

		_groups = userSearchFixture.getGroups();

		_user = userSearchFixture.addUser(
			RandomTestUtil.randomString(), _group);

		_users = userSearchFixture.getUsers();
	}

	@Inject
	protected DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService;

	protected IndexedFieldsFixture indexedFieldsFixture;

	@Inject
	protected IndexerRegistry indexerRegistry;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected IndexerFixture<TrashEntry> trashEntryIndexerFixture;
	protected TrashFixture trashFixture;
	protected UserSearchFixture userSearchFixture;

	private void _populateDates(FileEntry fileEntry, Map<String, String> map) {
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, fileEntry.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.PUBLISH_DATE, fileEntry.getCreateDate(), map);
		indexedFieldsFixture.populateExpirationDateWithForever(map);

		indexedFieldsFixture.populateExpirationDateWithForever(map);
	}

	private void _populateLocalizedTitles(
		FileEntry fileEntry, Map<String, String> map) {

		String title = StringUtil.lowerCase(fileEntry.getTitle());

		map.put("localized_title", title);

		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			String languageId = LocaleUtil.toLanguageId(locale);

			String key = "localized_title_" + languageId;

			map.put(key, title);
			map.put(key.concat("_sortable"), title);
		}
	}

	private void _populateTrashEntryFields(
			FileEntry fileEntry, Map<String, String> map)
		throws Exception {

		fileEntry = DLAppLocalServiceUtil.getFileEntry(
			fileEntry.getFileEntryId());

		TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		map.put(Field.PROPERTIES, fileEntry.getTitle());

		map.put(
			Field.REMOVED_BY_USER_NAME,
			StringUtil.lowerCase(trashEntry.getUserName()));
		map.put(Field.TYPE, "document");

		map.put("path", fileEntry.getTitle());

		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, fileEntry.getModifiedDate(), map);
		indexedFieldsFixture.populateDate(
			Field.REMOVED_DATE, trashEntry.getCreateDate(), map);
	}

	private void _populateViewCount(
		FileEntry fileEntry, Map<String, String> map) {

		map.put("viewCount", String.valueOf(fileEntry.getReadCount()));
		map.put("viewCount_sortable", String.valueOf(fileEntry.getReadCount()));
	}

	private FileEntry _fileEntry;
	private Group _group;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<TrashEntry> _trashEntries;

	private User _user;

	@DeleteAfterTestRun
	private List<User> _users;

}