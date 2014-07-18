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
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.StagingLocalServiceUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
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
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sampsa Sohlman
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class TrashEntryLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		cleanUpTrashEntries();
	}

	@After
	public void tearDown() throws Exception {
		cleanUpTrashEntries();
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

		createFileEntryTrash(group.getGroupId(), 0);

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

			actual += createTrashEntriesForTwoDaysExpiryTest(group);
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

			actual += createTrashEntriesForTwoDaysExpiryTest(group);
		}

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			actual, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testOneGroup() throws Exception {
		Group group = createGroup(TestPropsValues.getCompanyId());

		createFileEntryTrash(group.getGroupId(), 500);
		createFileEntryTrash(group.getGroupId(), 0);

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			1, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testWithLayoutGroup() throws Exception {
		Group group = setTrashEntriesMaxAge(
			createGroup(TestPropsValues.getCompanyId()), 2);

		verifyCleanUpAfterTwoDays(createLayoutGroup(group));
	}

	@Test
	public void testWithStaging() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Group group = setTrashEntriesMaxAge(createGroup(companyId), 2);

		User user = UserTestUtil.getAdminUser(companyId);

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false,
			ServiceContextTestUtil.getServiceContext(group, user.getUserId()));

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

		group = createLayoutGroup(group.getStagingGroup());

		verifyCleanUpAfterTwoDays(group);
	}

	@Test
	public void testWithStagingTrashDisabled() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Group group = setTrashEnableForGroup(createGroup(companyId), false);

		User user = UserTestUtil.getAdminUser(companyId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, user.getUserId());

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false, serviceContext);

		Group stagingGroup = group.getStagingGroup();

		createFileEntryTrash(stagingGroup.getGroupId(), 0);

		TrashEntryLocalServiceUtil.checkEntries();

		// Note that this should be empty

		Assert.assertEquals(
			0, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	protected void cleanUpTrashEntries() {
		List<TrashEntry> trashEntries =
			TrashEntryLocalServiceUtil.getTrashEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (TrashEntry trashEntry : trashEntries) {
			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					trashEntry.getClassName());

			try {
				trashHandler.deleteTrashEntry(trashEntry.getClassPK());
			}
			catch (PortalException pe) {
				_log.error(pe, pe);

				TrashEntryLocalServiceUtil.deleteEntry(trashEntry);
			}
		}
	}

	protected long createCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany(
			RandomTestUtil.randomString());

		_companies.add(company);

		return company.getCompanyId();
	}

	protected void createFileEntryTrash(long groupId, int reduceDays)
		throws Exception {

		createFileEntryTrash(groupId, reduceDays, 0);
	}

	protected void createFileEntryTrash(
			long groupId, int reduceDays, int reduceMinutes)
		throws Exception {

		FileEntry fileEntry =
			DLAppTestUtil.addFileEntry(
				groupId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		User user = UserTestUtil.getAdminUser(fileEntry.getCompanyId());

		DLAppLocalServiceUtil.moveFileEntryToTrash(
			user.getUserId(), fileEntry.getFileEntryId());

		if (reduceDays > 0) {
			TrashEntry trashEntry =
				TrashEntryLocalServiceUtil.getEntry(
					DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			Date createDate = trashEntry.getCreateDate();

			DateTime dateTime = new DateTime(createDate.getTime());

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

	protected Group createGroup(long companyId) throws Exception {
		User user = UserTestUtil.getAdminUser(companyId);

		return GroupTestUtil.addGroup(
			companyId, user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), "This is a test group.");
	}

	protected Group createLayoutGroup(Group group) throws Exception {
		Layout layout = LayoutTestUtil.addLayout(group);

		String name = String.valueOf(layout.getPlid());

		User user = UserTestUtil.getAdminUser(group.getCompanyId());

		return GroupLocalServiceUtil.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, name, null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);
	}

	protected int createTrashEntriesForTwoDaysExpiryTest(Group group)
		throws Exception {

		createFileEntryTrash(group.getGroupId(), 1);
		createFileEntryTrash(group.getGroupId(), 1, 1400);
		createFileEntryTrash(group.getGroupId(), 2, 1);
		createFileEntryTrash(group.getGroupId(), 3);

		return 2;
	}

	protected Group setTrashEnableForGroup(Group group, boolean value)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			"trashEnabled", String.valueOf(value));

		group.setTypeSettingsProperties(typeSettingsProperties);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	protected Group setTrashEntriesMaxAge(Group group, int days)
		throws Exception {

		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		int companyTrashEntriesMaxAge = PrefsPropsUtil.getInteger(
			group.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		if (days > 0) {
			days *= 1440;
		}
		else {
			days = GetterUtil.getInteger(
				typeSettingsProperties.getProperty("trashEntriesMaxAge"),
				companyTrashEntriesMaxAge);
		}

		if (days != companyTrashEntriesMaxAge) {
			typeSettingsProperties.setProperty(
				"trashEntriesMaxAge",
				String.valueOf(GetterUtil.getInteger(days)));
		}
		else {
			typeSettingsProperties.remove("trashEntriesMaxAge");
		}

		group.setTypeSettingsProperties(typeSettingsProperties);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	protected void verifyCleanUpAfterTwoDays(Group group) throws Exception {
		int actual = createTrashEntriesForTwoDaysExpiryTest(group);

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			actual, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	private static Log _log = LogFactoryUtil.getLog(
		TrashEntryLocalServiceTest.class);

	@DeleteAfterTestRun
	private List<Company> _companies = new ArrayList<Company>();

	@DeleteAfterTestRun
	private List<Group> _groups = new ArrayList<Group>();

}