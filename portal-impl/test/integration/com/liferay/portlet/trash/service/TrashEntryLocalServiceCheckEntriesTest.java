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

package com.liferay.portlet.trash.service;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.StagingLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.test.CompanyTestUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Sampsa Sohlman
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class TrashEntryLocalServiceCheckEntriesTest  extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_groups = new ArrayList<Group>();

		List<TrashEntry> list = TrashEntryLocalServiceUtil.getTrashEntries(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (list.size() > 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Cleaning TrashEntries due previous tests did not" +
					" clean up them properly");
			}

			clearTrashEntries(list);
		}
	}

	@After
	public void tearDown() throws Exception {
		clearTrashEntries(
			TrashEntryLocalServiceUtil.getTrashEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		for (Group group : _groups) {
			List<BackgroundTask> backgroundTasks =
				BackgroundTaskLocalServiceUtil.getBackgroundTasks(
					group.getGroupId(),
					BackgroundTaskConstants.STATUS_IN_PROGRESS);

			int counter = 0;

			while (!backgroundTasks.isEmpty() && (counter < 200)) {
				Thread.sleep(100);
				counter++;
				backgroundTasks =
					BackgroundTaskLocalServiceUtil.getBackgroundTasks(
						group.getGroupId(),
						BackgroundTaskConstants.STATUS_IN_PROGRESS);
			}

			GroupLocalServiceUtil.deleteGroup(group.getGroupId());
		}

		if (_companies != null) {
			for (Long companyId : _companies ) {
				CompanyLocalServiceUtil.deleteCompany(companyId);
			}
		}
	}

	@Test
	public void testGroupMaxAgeChanged() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		Group group = setTrashEntriesMaxAge(createGroup(companyId), 2);
		verifyCleanUpAfterTwoDays(group);
	}

	@Test
	public void testGroupTrashDisabled() throws Exception {
		Group group = createGroup(TestPropsValues.getCompanyId());
		createFileEntriesAndMoveThemToTrash(group.getGroupId(), 5, 0);
		setTrashEnableForGroup(group, false);
		TrashEntryLocalServiceUtil.checkEntries();
		Assert.assertEquals(
			0, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testMultiCompanyCleanUp() throws Exception {
		int actual = 0;

		for (int i = 0; i < 4; i++ ) {
			Group group = setTrashEntriesMaxAge(
				createGroup(createCompany()), 2);
			actual = actual + createTrashEntriesForTwoDaysExpiryTest(group);
		}

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			actual, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testMultiGroupTrashCleanUp() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		int actual = 0;

		for (int i = 0; i < 10; i++) {
			Group group = setTrashEntriesMaxAge(createGroup(companyId), 2);
			actual = actual + createTrashEntriesForTwoDaysExpiryTest(group);
		}

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			actual, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testOneGroup() throws Exception {
		Group group = createGroup(TestPropsValues.getCompanyId());
		createFileEntriesAndMoveThemToTrash(group.getGroupId(), 5, 500);
		createFileEntriesAndMoveThemToTrash(group.getGroupId(), 5, 0);
		TrashEntryLocalServiceUtil.checkEntries();
		Assert.assertEquals(
			5, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testWithLayoutGroup() throws Exception {
		Group group = setTrashEntriesMaxAge(
			createGroup(TestPropsValues.getCompanyId()), 2);
		verifyCleanUpAfterTwoDays(createLayoutGroup(group.getGroupId()));
	}

	@Test
	public void testWithStaging() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		Group group = setTrashEntriesMaxAge(createGroup(companyId), 2);

		User user = UserTestUtil.getAdminUser(companyId);

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false,
			ServiceContextTestUtil.getServiceContext(group, user.getUserId()));

		group = GroupLocalServiceUtil.getGroup(group.getGroupId());

		verifyCleanUpAfterTwoDays(group.getStagingGroup());
	}

	@Test
	public void testWithStagingPageScope() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		Group group = setTrashEntriesMaxAge(createGroup(companyId), 2);

		User user = UserTestUtil.getAdminUser(companyId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, user.getUserId());

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false, serviceContext);

		Thread.sleep(2000);

		group = GroupLocalServiceUtil.getGroup(group.getGroupId());

		group = createLayoutGroup(group.getStagingGroup().getGroupId());

		verifyCleanUpAfterTwoDays(group);
	}

	@Test
	public void testWithStagingTrashDisabled() throws Exception {
		long companyId = TestPropsValues.getCompanyId();
		Group group = setTrashEnableForGroup(createGroup(companyId), false);

		User user = UserTestUtil.getAdminUser(companyId);

		ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					group, user.getUserId());

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false, serviceContext);

		group = GroupLocalServiceUtil.getGroup(group.getGroupId());
		Group stagingGroup = group.getStagingGroup();

		createFileEntriesAndMoveThemToTrash(stagingGroup.getGroupId(), 5, 0);

		TrashEntryLocalServiceUtil.checkEntries();

		// Note that this should be empty

		Assert.assertEquals(
			0, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	protected void clearTrashEntries(List<TrashEntry> list) {
		for (TrashEntry entry : list) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(entry.getClassName());

			try {
				trashHandler.deleteTrashEntry(entry.getClassPK());
			}
			catch (Exception e) {

				// To handle situation when garbage from the previous tests
				// does exist

				try {
					TrashEntryLocalServiceUtil.deleteEntry(entry.getEntryId());
					_log.error(e);
				}
				catch (PortalException e1) {
					_log.error(e1);
				}
			}
		}
	}

	protected long createCompany() throws Exception {
		if (_companies == null) {
			_companies = new ArrayList<Long>();
		}

		Company company = CompanyTestUtil.addCompany(
			RandomTestUtil.randomString());

		_companies.add(company.getCompanyId());
		return company.getCompanyId();
	}

	protected void createFileEntriesAndMoveThemToTrash(
			long groupId, int count, int reduceDays)
		throws Exception {

		createFileEntriesAndMoveThemToTrash(groupId, count, reduceDays, 0);
	}

	protected void createFileEntriesAndMoveThemToTrash(
			long groupId, int count, int reduceDays, int reduceMinutes)
		throws Exception {

		for (int i = 0; i < count; i++) {
			FileEntry fileEntry =
				DLAppTestUtil.addFileEntry(
					groupId, groupId,
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			User user = UserTestUtil.getAdminUser(fileEntry.getCompanyId());

			DLAppLocalServiceUtil.moveFileEntryToTrash(
				user.getUserId(), fileEntry.getFileEntryId());

			if (reduceDays > 0) {
				TrashEntry trashEntry =
					TrashEntryLocalServiceUtil.getEntry(
						DLFileEntry.class.getName(),
						fileEntry.getFileEntryId());

				DateTime dateTime = new DateTime(
					trashEntry.getCreateDate().getTime());

				if (reduceDays > 0) {
					dateTime = dateTime.plusDays(-reduceDays);
				}

				if (reduceMinutes > 0) {
					dateTime = dateTime.plusMinutes(-reduceMinutes);
				}

				trashEntry.setCreateDate(dateTime.toDate());

				TrashEntryLocalServiceUtil.updateTrashEntry(trashEntry);
			}
		}
	}

	protected Group createGroup(long companyId) throws Exception {
		User user = UserTestUtil.getAdminUser(companyId);

		Group group = GroupTestUtil.addGroup(
				companyId, user.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				RandomTestUtil.randomString(), "This is a test group.");

		return GroupLocalServiceUtil.getGroup(group.getGroupId());
	}

	protected Group createLayoutGroup(long groupId) throws Exception {
		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Layout layout = LayoutTestUtil.addLayout(group);

		layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

		String name = String.valueOf(layout.getPlid());

		User user = UserTestUtil.getAdminUser(group.getCompanyId());

		Group layoutGroup = GroupLocalServiceUtil.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);

		return GroupLocalServiceUtil.getGroup(layoutGroup.getGroupId());
	}

	protected int createTrashEntriesForTwoDaysExpiryTest(Group group)
		throws Exception {

		createFileEntriesAndMoveThemToTrash(group.getGroupId(), 5, 1);
		createFileEntriesAndMoveThemToTrash(group.getGroupId(), 5, 1, 1400);
		createFileEntriesAndMoveThemToTrash(group.getGroupId(), 5, 2, 1);
		createFileEntriesAndMoveThemToTrash(group.getGroupId(), 5, 3);

		return 10;
	}

	protected Group setTrashEnableForGroup(Group group, boolean value)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"trashEnabled", String.valueOf(value));

		group.setTypeSettingsProperties(typeSettingsProperties);

		GroupLocalServiceUtil.updateGroup(group);

		return GroupLocalServiceUtil.getGroup(group.getGroupId());
	}

	protected Group setTrashEntriesMaxAge(Group group, double days)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
			group.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		if (days > 0) {
			days *= 1440;
		}
		else {
			days = GetterUtil.getInteger(
				typeSettingsProperties.getProperty("trashEntriesMaxAge"),
				trashEntriesMaxAgeCompany);
		}

		if (days != trashEntriesMaxAgeCompany) {
			typeSettingsProperties.setProperty(
				"trashEntriesMaxAge",
				String.valueOf(GetterUtil.getInteger(days)));
		}
		else {
			typeSettingsProperties.remove("trashEntriesMaxAge");
		}

		group.setTypeSettingsProperties(typeSettingsProperties);

		GroupLocalServiceUtil.updateGroup(group);
		return GroupLocalServiceUtil.getGroup(group.getGroupId());
	}

	protected void verifyCleanUpAfterTwoDays(Group group) throws Exception {
		int actual = createTrashEntriesForTwoDaysExpiryTest(group);

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			actual, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	private static Log _log = LogFactoryUtil.getLog(
		TrashEntryLocalServiceCheckEntriesTest.class);

	private List<Long> _companies;
	private List<Group> _groups;
	private int _readCount = 10000;

}