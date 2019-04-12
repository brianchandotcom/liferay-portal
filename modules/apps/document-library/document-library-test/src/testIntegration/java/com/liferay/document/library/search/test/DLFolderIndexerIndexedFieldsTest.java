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

package com.liferay.document.library.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.test.util.search.DLFolderSearchFixture;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Wade Cao
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
@Sync
public class DLFolderIndexerIndexedFieldsTest extends BaseDLIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		dlFolderSearchFixture = new DLFolderSearchFixture(
			dlAppLocalService, dlFileEntryLocalService, dlFolderLocalService);

		setGroup(dlFixture.addGroup());
		setIndexerClass(DLFolder.class);
		setUser(dlFixture.addUser());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		dlFolderSearchFixture.tearDown();
	}

	@Test
	public void testIndexedFields() throws Exception {
		ServiceContext serviceContext = getServiceContext();

		DLFolder parentFolder = dlFolderSearchFixture.addFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH),
			RandomTestUtil.randomString(), serviceContext);

		String folderName = RandomTestUtil.randomString();

		DLFolder childFolder = dlFolderSearchFixture.addFolder(
			parentFolder.getFolderId(), folderName,
			RandomTestUtil.randomString(), serviceContext);

		Document document = dlSearchFixture.searchOnlyOne(
			folderName, LocaleUtil.US);

		indexedFieldsFixture.postProcessDocument(document);

		Map<String, String> map = new HashMap<>();

		populateExpectedFieldValues(childFolder, map);

		FieldValuesAssert.assertFieldValues(map, document, folderName);
	}

	protected ServiceContext getServiceContext() {
		try {
			return ServiceContextTestUtil.getServiceContext(
				dlFixture.getGroupId(), dlFixture.getUserId());
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	protected void populateDates(DLFolder dlFolder, Map<String, String> map) {
		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, dlFolder.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, dlFolder.getModifiedDate(), map);
		indexedFieldsFixture.populateDate(
			Field.PUBLISH_DATE, dlFolder.getCreateDate(), map);

		indexedFieldsFixture.populateExpirationDateWithForever(map);
	}

	protected Map<String, String> populateExpectedFieldValues(
			DLFolder dlFolder, Map<String, String> map)
		throws Exception {

		map.put(Field.COMPANY_ID, String.valueOf(dlFolder.getCompanyId()));
		map.put(Field.DESCRIPTION, dlFolder.getDescription());
		map.put(Field.ENTRY_CLASS_NAME, dlFolder.getModelClassName());
		map.put(Field.ENTRY_CLASS_PK, String.valueOf(dlFolder.getFolderId()));
		map.put(Field.FOLDER_ID, String.valueOf(dlFolder.getParentFolderId()));
		map.put(Field.GROUP_ID, String.valueOf(dlFolder.getGroupId()));
		map.put(Field.HIDDEN, String.valueOf(dlFolder.isHidden()));
		map.put(Field.SCOPE_GROUP_ID, String.valueOf(dlFolder.getGroupId()));
		map.put(Field.STAGING_GROUP, "false");
		map.put(Field.STATUS, String.valueOf(dlFolder.getStatus()));
		map.put(Field.TITLE, dlFolder.getName());
		map.put(
			Field.TITLE.concat("_sortable"),
			StringUtil.lowerCase(dlFolder.getName()));
		map.put(Field.USER_ID, String.valueOf(dlFolder.getUserId()));
		map.put(Field.USER_NAME, StringUtil.lowerCase(dlFolder.getUserName()));

		map.put("visible", "true");

		populateDates(dlFolder, map);
		populateLocalizedTitles(dlFolder, map);
		populateTreePath(dlFolder, map);

		indexedFieldsFixture.populatePriority("0.0", map);
		indexedFieldsFixture.populateRoleIdFields(
			dlFolder.getCompanyId(), dlFolder.getModelClassName(),
			dlFolder.getPrimaryKey(), dlFolder.getGroupId(), null, map);
		indexedFieldsFixture.populateUID(
			dlFolder.getModelClassName(), dlFolder.getFolderId(), map);
		indexedFieldsFixture.populateViewCount(
			DLFolder.class, dlFolder.getFolderId(), map);

		return map;
	}

	protected void populateLocalizedTitles(
		DLFolder dlFolder, Map<String, String> map) {

		String title = StringUtil.lowerCase(dlFolder.getName());

		map.put("localized_title", title);

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(dlFolder.getGroupId())) {

			String key = "localized_title_".concat(
				String.valueOf(availableLocale));

			map.put(key, title);
			map.put(key.concat("_sortable"), title);
		}
	}

	protected void populateTreePath(
		DLFolder dlFolder, Map<String, String> map) {

		List<String> treePathValues = new ArrayList<>(
			Arrays.asList(
				StringUtil.split(dlFolder.getTreePath(), CharPool.SLASH)));

		if (treePathValues.size() == 1) {
			map.put(Field.TREE_PATH, treePathValues.get(0));
		}
		else if (treePathValues.size() > 1) {
			map.put(Field.TREE_PATH, treePathValues.toString());
		}
	}

	protected DLFolderSearchFixture dlFolderSearchFixture;

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

}