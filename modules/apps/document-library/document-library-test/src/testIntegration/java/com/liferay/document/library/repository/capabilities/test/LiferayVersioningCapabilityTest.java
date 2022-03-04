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

package com.liferay.document.library.repository.capabilities.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.versioning.VersionPurger;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class LiferayVersioningCapabilityTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDoesNotLimitTheNumberOfVersionsPerFileEntry()
		throws Exception {

		_withMaximumNumberOfVersionsConfigured(
			0,
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId());

				FileEntry fileEntry = _addRandomFileEntry(serviceContext);

				for (int i = 0; i < 10; i++) {
					_generateNewVersion(fileEntry, serviceContext);
				}

				Assert.assertEquals(
					11,
					fileEntry.getFileVersionsCount(
						WorkflowConstants.STATUS_ANY));
			});
	}

	@Test
	public void testLimitsTheNumberOfVersionsPerFileEntry() throws Exception {
		_withMaximumNumberOfVersionsConfigured(
			2,
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId());

				FileEntry fileEntry = _addRandomFileEntry(serviceContext);

				for (int i = 0; i < 10; i++) {
					_generateNewVersion(fileEntry, serviceContext);
				}

				Assert.assertEquals(
					2,
					fileEntry.getFileVersionsCount(
						WorkflowConstants.STATUS_ANY));

				List<FileVersion> fileVersions = fileEntry.getFileVersions(
					WorkflowConstants.STATUS_ANY);

				FileVersion fileVersion1 = fileVersions.get(0);

				Assert.assertEquals("1.10", fileVersion1.getVersion());

				FileVersion fileVersion2 = fileVersions.get(1);

				Assert.assertEquals("1.9", fileVersion2.getVersion());
			});
	}

	@Test
	public void testLimitsTheNumberOfVersionsPerFileEntryToOne()
		throws Exception {

		_withMaximumNumberOfVersionsConfigured(
			1,
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId());

				FileEntry fileEntry = _addRandomFileEntry(serviceContext);

				for (int i = 0; i < 10; i++) {
					_generateNewVersion(fileEntry, serviceContext);
				}

				Assert.assertEquals(
					1,
					fileEntry.getFileVersionsCount(
						WorkflowConstants.STATUS_ANY));

				List<FileVersion> fileVersions = fileEntry.getFileVersions(
					WorkflowConstants.STATUS_ANY);

				FileVersion fileVersion1 = fileVersions.get(0);

				Assert.assertEquals("1.10", fileVersion1.getVersion());
			});
	}

	@Test
	public void testNotifiesAboutEachFileVersionDeletion() throws Exception {
		_withMaximumNumberOfVersionsConfigured(
			2,
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId());

				FileEntry fileEntry = _addRandomFileEntry(serviceContext);

				List<FileVersion> deletedFileVersions = new ArrayList<>();

				Bundle bundle = FrameworkUtil.getBundle(
					LiferayVersioningCapabilityTest.class);

				BundleContext bundleContext = bundle.getBundleContext();

				ServiceRegistration<VersionPurger.VersionPurgedListener>
					capabilityServiceRegistration =
						bundleContext.registerService(
							VersionPurger.VersionPurgedListener.class,
							deletedFileVersions::add, null);

				try {
					for (int i = 0; i < 10; i++) {
						_generateNewVersion(fileEntry, serviceContext);
					}

					Assert.assertEquals(
						deletedFileVersions.toString(), 9,
						deletedFileVersions.size());
				}
				finally {
					capabilityServiceRegistration.unregister();
				}
			});
	}

	@Test
	public void testRespectsTheLimitWhenCheckedInAndOut() throws Exception {
		int numberOfVersions = 2;

		_withMaximumNumberOfVersionsConfigured(
			numberOfVersions,
			() -> {
				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId());

				FileEntry fileEntry = _addRandomFileEntry(serviceContext);

				for (int i = 0; i < (numberOfVersions + 10); i++) {
					DLAppServiceUtil.checkOutFileEntry(
						fileEntry.getFileEntryId(), serviceContext);

					DLAppServiceUtil.checkInFileEntry(
						fileEntry.getFileEntryId(),
						DLVersionNumberIncrease.MAJOR,
						StringUtil.randomString(), serviceContext);
				}

				List<FileVersion> fileVersions = fileEntry.getFileVersions(
					WorkflowConstants.STATUS_ANY);

				Assert.assertEquals(
					"The number of versions stored are: ", numberOfVersions,
					fileVersions.size());

				FileVersion fileVersion = fileVersions.get(0);

				Assert.assertEquals("13.0", fileVersion.getVersion());

				fileVersion = fileVersions.get(1);

				Assert.assertEquals("12.0", fileVersion.getVersion());
			});
	}

	private FileEntry _addRandomFileEntry(ServiceContext serviceContext)
		throws PortalException {

		return DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);
	}

	private FileEntry _generateNewVersion(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		return DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileEntry.getFileName(),
			fileEntry.getMimeType(), fileEntry.getTitle(), StringPool.BLANK,
			fileEntry.getDescription(), RandomTestUtil.randomString(),
			DLVersionNumberIncrease.MINOR, TestDataConstants.TEST_BYTE_ARRAY,
			fileEntry.getExpirationDate(), fileEntry.getReviewDate(),
			serviceContext);
	}

	private void _withMaximumNumberOfVersionsConfigured(
			int maximumNumberOfVersions,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"maximumNumberOfVersions", maximumNumberOfVersions
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.configuration." +
						"DLConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@DeleteAfterTestRun
	private Group _group;

}