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

package com.liferay.headless.document.library.internal.resource.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.headless.document.library.dto.Folder;
import com.liferay.headless.document.library.internal.resource.test.util.PaginationRequest;
import com.liferay.headless.document.library.resource.FolderResource;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.vulcan.dto.Page;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class ResourcePermissionFolderResourceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndNoGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndNoGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndNoGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithGuestPermissionAndNoGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(0, page.getTotalCount());
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(0, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndNoGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(1, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndNoGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(0, page.getTotalCount());
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndNoGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(0, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetDocumentsRepositoryFolderPageWithNoGuestPermissionAndNoGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addDLFolder(_group.getGroupId(), serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<Folder> page =
				_folderResource.getDocumentsRepositoryFolderPage(
					_group.getGroupId(), PaginationRequest.of(10, 1));

			Assert.assertEquals(0, page.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	private DLFolder _addDLFolder(long groupId, ServiceContext serviceContext)
		throws Exception {

		return DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), groupId, groupId, false,
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			false, serviceContext);
	}

	@Inject
	private FolderResource _folderResource;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserLocalService _userLocalService;

}