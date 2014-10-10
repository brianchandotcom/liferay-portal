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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.util.RepositoryTrashUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
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
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.CompanyTestUtil;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sampsa Sohlman
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class TrashEntryLocalServiceCheckEntriesTest {

	@Before
	public void setUp() throws Exception {
		deleteTrashEntries();
	}

	@After
	public void tearDown() throws Exception {
		deleteTrashEntries();
	}

	@Test
	public void testCompanies() throws Exception {
		for (int i = 0; i < _COMPANIES_COUNT; i++ ) {
			Group group = updateTrashEntriesMaxAge(
				createGroup(createCompany()), _MAX_AGE);

			createTrashEntries(group);
		}

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			_COMPANIES_COUNT * _NOT_EXPIRED_TRASH_ENTRIES_COUNT,
			TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testCustomMaxAge() throws Exception {
		Group group = updateTrashEntriesMaxAge(
			createGroup(TestPropsValues.getCompanyId()), 2);

		deleteTrashEntries(group);
	}

	@Test
	public void testGroups() throws Exception {
		for (int i = 0; i < _GROUPS_COUNT; i++) {
			Group group = updateTrashEntriesMaxAge(
				createGroup(TestPropsValues.getCompanyId()), _MAX_AGE);

			createTrashEntries(group);
		}

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			_GROUPS_COUNT * _NOT_EXPIRED_TRASH_ENTRIES_COUNT,
			TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testGroupTrashDisabled() throws Exception {
		Group group = createGroup(TestPropsValues.getCompanyId());

		createFileEntryTrashEntry(group, false);

		disableTrashForGroup(group);

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			0, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	@Test
	public void testLayoutGroup() throws Exception {
		Group group = updateTrashEntriesMaxAge(
			createGroup(TestPropsValues.getCompanyId()), 2);

		deleteTrashEntries(createLayoutGroup(group));
	}

	@Test
	public void testRegularGroup() throws Exception {
		deleteTrashEntries(createGroup(TestPropsValues.getCompanyId()));
	}

	@Test
	public void testStagingGroup() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Group group = updateTrashEntriesMaxAge(createGroup(companyId), 2);
		User user = UserTestUtil.getAdminUser(companyId);

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false,
			ServiceContextTestUtil.getServiceContext(group, user.getUserId()));

		deleteTrashEntries(group.getStagingGroup());
	}

	@Test
	public void testStagingLayoutScope() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Group group = updateTrashEntriesMaxAge(createGroup(companyId), 2);
		User user = UserTestUtil.getAdminUser(companyId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, user.getUserId());

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false, serviceContext);

		group = createLayoutGroup(group.getStagingGroup());

		deleteTrashEntries(group);
	}

	@Test
	public void testStagingTrashDisabled() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		Group group = disableTrashForGroup(createGroup(companyId));
		User user = UserTestUtil.getAdminUser(companyId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group, user.getUserId());

		StagingLocalServiceUtil.enableLocalStaging(
			user.getUserId(), group, false, false, serviceContext);

		Group stagingGroup = group.getStagingGroup();

		createFileEntryTrashEntry(stagingGroup, false);

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			0, TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	protected long createCompany() throws Exception {
		Company company = CompanyTestUtil.addCompany(
			RandomTestUtil.randomString());

		_companies.add(company);

		return company.getCompanyId();
	}

	protected void createFileEntryTrashEntry(Group group, boolean expired)
		throws Exception {

		FileEntry fileEntry =
			DLAppTestUtil.addFileEntry(
				group.getGroupId(), group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		User user = UserTestUtil.getAdminUser(fileEntry.getCompanyId());

		RepositoryTrashUtil.moveFileEntryToTrash(
			user.getUserId(), fileEntry.getRepositoryId(),
			fileEntry.getFileEntryId());

		if (expired) {
			int maxAge = TrashUtil.getMaxAge(group);

			TrashEntry trashEntry = TrashEntryLocalServiceUtil.getEntry(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			Date createDate = trashEntry.getCreateDate();

			trashEntry.setCreateDate(
				new Date(
					createDate.getTime() - maxAge * Time.MINUTE - Time.DAY));

			TrashEntryLocalServiceUtil.updateTrashEntry(trashEntry);
		}
	}

	protected Group createGroup(long companyId) throws Exception {
		User user = UserTestUtil.getAdminUser(companyId);

		Group group = GroupTestUtil.addGroup(
			companyId, user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(), "This is a test group.");

		_groups.add(group);

		return group;
	}

	protected Group createLayoutGroup(Group group) throws Exception {
		User user = UserTestUtil.getAdminUser(group.getCompanyId());
		Layout layout = LayoutTestUtil.addLayout(group);

		return GroupLocalServiceUtil.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			String.valueOf(layout.getPlid()), null, 0, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, true,
			null);
	}

	protected void createTrashEntries(Group group) throws Exception {
		for (int i = 0; i < _EXPIRED_TRASH_ENTRIES_COUNT; i++) {
			createFileEntryTrashEntry(group, true);
		}

		for (int i = 0; i < _NOT_EXPIRED_TRASH_ENTRIES_COUNT; i++) {
			createFileEntryTrashEntry(group, false);
		}
	}

	protected void deleteTrashEntries() {
		List<TrashEntry> trashEntries =
			TrashEntryLocalServiceUtil.getTrashEntries(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (TrashEntry trashEntry : trashEntries) {
			TrashEntryLocalServiceUtil.deleteEntry(trashEntry);
		}
	}

	protected void deleteTrashEntries(Group group) throws Exception {
		createTrashEntries(group);

		TrashEntryLocalServiceUtil.checkEntries();

		Assert.assertEquals(
			_NOT_EXPIRED_TRASH_ENTRIES_COUNT,
			TrashEntryLocalServiceUtil.getTrashEntriesCount());
	}

	protected Group disableTrashForGroup(Group group) throws Exception {
		UnicodeProperties typeSettingsProperties =
			group.getParentLiveGroupTypeSettingsProperties();

		typeSettingsProperties.setProperty("trashEnabled", StringPool.FALSE);

		group.setTypeSettingsProperties(typeSettingsProperties);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	protected Group updateTrashEntriesMaxAge(Group group, int days)
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
				"trashEntriesMaxAge", String.valueOf(days));
		}
		else {
			typeSettingsProperties.remove("trashEntriesMaxAge");
		}

		group.setTypeSettingsProperties(typeSettingsProperties);

		return GroupLocalServiceUtil.updateGroup(group);
	}

	private static int _COMPANIES_COUNT = 2;

	private static int _EXPIRED_TRASH_ENTRIES_COUNT = 3;

	private static int _GROUPS_COUNT = 2;

	private static int _MAX_AGE = 5;

	private static int _NOT_EXPIRED_TRASH_ENTRIES_COUNT = 4;

	@DeleteAfterTestRun
	private List<Company> _companies = new ArrayList<Company>();

	@DeleteAfterTestRun
	private List<Group> _groups = new ArrayList<Group>();

}