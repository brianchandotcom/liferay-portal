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

package com.liferay.document.library.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLProcessor;
import com.liferay.document.library.kernel.util.DLProcessorRegistry;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class DLProcessorRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_cleanUp = new AtomicBoolean(false);
		_trigger = new AtomicBoolean(false);

		_dlProcessor = new DLProcessor() {

			@Override
			public void afterPropertiesSet() throws Exception {
			}

			@Override
			public void cleanUp(FileEntry fileEntry) {
				_cleanUp.set(true);
			}

			@Override
			public void cleanUp(FileVersion fileVersion) {
				_cleanUp.set(true);
			}

			@Override
			public void copy(
				FileVersion sourceFileVersion,
				FileVersion destinationFileVersion) {
			}

			@Override
			public void exportGeneratedFiles(
					PortletDataContext portletDataContext, FileEntry fileEntry,
					Element fileEntryElement)
				throws Exception {
			}

			@Override
			public String getType() {
				return "TEST";
			}

			@Override
			public void importGeneratedFiles(
					PortletDataContext portletDataContext, FileEntry fileEntry,
					FileEntry importedFileEntry, Element fileEntryElement)
				throws Exception {
			}

			@Override
			public boolean isSupported(FileVersion fileVersion) {
				return true;
			}

			@Override
			public boolean isSupported(String mimeType) {
				return true;
			}

			@Override
			public void trigger(
				FileVersion sourceFileVersion,
				FileVersion destinationFileVersion) {

				_trigger.set(true);
			}

		};

		_dlProcessorRegistry.register(_dlProcessor);

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_dlProcessorRegistry.unregister(_dlProcessor);
	}

	@Test
	public void testCleanUp() throws PortalException {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext());

		_dlProcessorRegistry.cleanUp(fileEntry);

		Assert.assertTrue(_cleanUp.get());
	}

	@Test
	public void testCleanUpFileVersion() throws PortalException {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext());

		_dlProcessorRegistry.cleanUp(fileEntry.getFileVersion());

		Assert.assertTrue(_cleanUp.get());
	}

	@Test
	public void testTrigger() throws PortalException {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext());

		_dlProcessorRegistry.trigger(fileEntry, fileEntry.getFileVersion());

		Assert.assertTrue(_trigger.get());
	}

	@Test
	public void testTriggerAfterDeleteTheFile() throws PortalException {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null,
			ServiceContextTestUtil.getServiceContext());

		FileVersion fileVersion = fileEntry.getFileVersion();

		_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());

		_dlProcessorRegistry.trigger(fileEntry, fileVersion);

		Assert.assertTrue(_trigger.get());
	}

	private AtomicBoolean _cleanUp;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private DLProcessor _dlProcessor;

	@Inject
	private DLProcessorRegistry _dlProcessorRegistry;

	private Group _group;
	private AtomicBoolean _trigger;

}