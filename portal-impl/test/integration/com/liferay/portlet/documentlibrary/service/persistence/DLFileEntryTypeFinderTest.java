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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class DLFileEntryTypeFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			TransactionalTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testFilterCountByKeywords() throws Exception {
		int initialCount = DLFileEntryTypeFinderUtil.filterCountByKeywords(
			_group.getCompanyId(), new long[] {_group.getGroupId()},
			_DL_FILE_ENTRY_TYPE_NAME, true);

		addFileEntryType();

		Assert.assertEquals(
			initialCount + 1,
			DLFileEntryTypeFinderUtil.filterCountByKeywords(
				_group.getCompanyId(), new long[] {_group.getGroupId()},
				_DL_FILE_ENTRY_TYPE_NAME, true));
	}

	@Test
	public void testFilterCountByKeywordsWithBlankKeywords() throws Exception {
		int initialCount = DLFileEntryTypeFinderUtil.filterCountByKeywords(
			_group.getCompanyId(), new long[] {_group.getGroupId()},
			StringPool.BLANK, true);

		addFileEntryType();

		Assert.assertEquals(
			initialCount + 1,
			DLFileEntryTypeFinderUtil.filterCountByKeywords(
				_group.getCompanyId(), new long[] {_group.getGroupId()},
				StringPool.BLANK, true)
		);
	}

	@Test
	public void testFilterCountByKeywordsAsAPowerUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		try {
			int initialCount = DLFileEntryTypeFinderUtil.filterCountByKeywords(
				_group.getCompanyId(), new long[] {_group.getGroupId()},
				_DL_FILE_ENTRY_TYPE_NAME, true);

			addFileEntryType();

			Assert.assertEquals(
				initialCount + 1,
				DLFileEntryTypeFinderUtil.filterCountByKeywords(
					_group.getCompanyId(), new long[] {_group.getGroupId()},
					_DL_FILE_ENTRY_TYPE_NAME, true)
			);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	@Test
	public void testFilterFindByKeywords() throws Exception {
		DLFileEntryType fileEntryType = addFileEntryType();

		List<DLFileEntryType> fileEntryTypes =
			DLFileEntryTypeFinderUtil.filterFindByKeywords(
				_group.getCompanyId(), new long[] {_group.getGroupId()},
				_DL_FILE_ENTRY_TYPE_NAME, true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertTrue(fileEntryTypes.contains(fileEntryType));
		Assert.assertEquals(
			fileEntryTypes.size(),
			DLFileEntryTypeFinderUtil.filterCountByKeywords(
				_group.getCompanyId(), new long[] {_group.getGroupId()},
				_DL_FILE_ENTRY_TYPE_NAME, true)
		);
	}

	@Test
	public void testFilterFindByKeywordsWithBlankKeywords() throws Exception {
		DLFileEntryType fileEntryType = addFileEntryType();

		List<DLFileEntryType> fileEntryTypes =
			DLFileEntryTypeFinderUtil.filterFindByKeywords(
				_group.getCompanyId(), new long[] {_group.getGroupId()},
				StringPool.BLANK, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

		Assert.assertTrue(fileEntryTypes.contains(fileEntryType));
		Assert.assertEquals(
			fileEntryTypes.size(),
			DLFileEntryTypeFinderUtil.filterCountByKeywords(
				_group.getCompanyId(), new long[] {_group.getGroupId()},
				StringPool.BLANK, true)
		);
	}

	@Test
	public void testFilterFindByKeywordsAsAPowerUser() throws Exception {
		User user = UserTestUtil.addGroupUser(_group, RoleConstants.POWER_USER);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		try {
			DLFileEntryType fileEntryType = addFileEntryType();

			List<DLFileEntryType> fileEntryTypes =
				DLFileEntryTypeFinderUtil.filterFindByKeywords(
					_group.getCompanyId(), new long[] {_group.getGroupId()},
					_DL_FILE_ENTRY_TYPE_NAME, true, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			Assert.assertTrue(fileEntryTypes.contains(fileEntryType));
			Assert.assertEquals(
				fileEntryTypes.size(),
				DLFileEntryTypeFinderUtil.filterCountByKeywords(
					_group.getCompanyId(), new long[] {_group.getGroupId()},
					_DL_FILE_ENTRY_TYPE_NAME, true)
			);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	protected DLFileEntryType addFileEntryType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), DLFileEntryMetadata.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		return DLFileEntryTypeLocalServiceUtil.addFileEntryType(
			_user.getUserId(), _group.getGroupId(), _DL_FILE_ENTRY_TYPE_NAME,
			RandomTestUtil.randomString(),
			new long[] {ddmStructure.getStructureId()}, serviceContext);
	}

	private static final String _DL_FILE_ENTRY_TYPE_NAME =
		"DLFileEntryTypeFinderTest";

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}