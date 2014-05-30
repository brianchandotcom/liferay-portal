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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.util.DLConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousMailExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLSubscriptionTest extends BaseSubscriptionTestCase {

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
	}

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntryWithWorkflow(
			group.getGroupId(), group.getGroupId(), containerModelId, true);

		return fileEntry.getFileEntryId();
	}

	@Override
	protected long addBaseModelWithClassType(
			long containerModelId, long classTypeId)
		throws Exception {

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), containerModelId, RandomTestUtil.randomString(),
			classTypeId);

		return fileEntry.getFileEntryId();
	}

	@Override
	protected long addClassType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			DLFileEntry.class.getName());

		DLFileEntryType fileEntryType = DLAppTestUtil.addDLFileEntryType(
			group.getGroupId(), ddmStructure.getStructureId());

		return fileEntryType.getFileEntryTypeId();
	}

	@Override
	protected long addContainerModel(long containerModelId) throws Exception {
		Folder folder = DLAppTestUtil.addFolder(
			group.getGroupId(), containerModelId);

		return folder.getFolderId();
	}

	@Override
	protected void addSubscriptionClassType(long classTypeId) throws Exception {
		DLAppServiceUtil.subscribeFileEntryType(
			group.getGroupId(), classTypeId);
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		DLAppLocalServiceUtil.subscribeFolder(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId);
	}

	@Override
	protected Long getDefaultClassTypeId() throws Exception {
		DLFileEntryType basicEntryType =
			DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		Assert.assertNotNull(basicEntryType);

		return basicEntryType.getPrimaryKey();
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.DOCUMENT_LIBRARY;
	}

	@Override
	protected String getSubscriptionBodyPreferenceName() throws Exception {
		return "emailFileEntryAddedBody";
	}

	@Override
	protected void setAddBaseModelSubscriptionBodyPreferences()
		throws Exception {

		Settings settings = SettingsFactoryUtil.getGroupServiceSettings(
			group.getGroupId(), DLConstants.SERVICE_NAME);

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String germanSubscriptionBodyPreferencesKey =
			LocalizationUtil.getPreferencesKey(
				getSubscriptionBodyPreferenceName(),
				LocaleUtil.toLanguageId(LocaleUtil.GERMANY));

		modifiableSettings.setValue(
			germanSubscriptionBodyPreferencesKey, GERMAN_BODY);

		String spanishSubscriptionBodyPreferencesKey =
			LocalizationUtil.getPreferencesKey(
				getSubscriptionBodyPreferenceName(),
				LocaleUtil.toLanguageId(LocaleUtil.SPAIN));

		modifiableSettings.setValue(
			spanishSubscriptionBodyPreferencesKey, SPANISH_BODY);

		modifiableSettings.store();
	}

}