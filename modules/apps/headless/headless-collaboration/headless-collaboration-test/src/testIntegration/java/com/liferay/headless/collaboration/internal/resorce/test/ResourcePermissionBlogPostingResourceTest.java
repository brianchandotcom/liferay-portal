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

package com.liferay.headless.collaboration.internal.resorce.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.headless.collaboration.dto.BlogPosting;
import com.liferay.headless.collaboration.resource.BlogPostingResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
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

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class ResourcePermissionBlogPostingResourceTest {

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
	public void testGetBlogPostingPageWithGuestPermissionAndGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithGuestPermissionAndGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
	}

	@Test
	public void testGetBlogPostingPageWithGuestPermissionAndGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithGuestPermissionAndGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithGuestPermissionAndNoGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithGuestPermissionAndNoGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
	}

	@Test
	public void testGetBlogPostingPageWithGuestPermissionAndNoGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithGuestPermissionAndNoGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(0, blogPostingPage.getTotalCount());
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(0, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(true);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndNoGroupPermissionAndAdminUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(1, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndNoGroupPermissionAndGuestUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = _userLocalService.getDefaultUser(
			TestPropsValues.getCompanyId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(0, blogPostingPage.getTotalCount());
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndNoGroupPermissionAndNoSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(0, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testGetBlogPostingPageWithNoGuestPermissionAndNoGroupPermissionAndSiteUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(serviceContext);

		User user = UserTestUtil.addUser(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			Page<BlogPosting> blogPostingPage =
				_blogPostingResource.getContentSpaceBlogPostingPage(
					_group.getGroupId(), PaginationRequest.of(20, 1));

			Assert.assertEquals(0, blogPostingPage.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	private BlogsEntry _addBlogEntry(ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.addEntry(
			serviceContext.getUserId(), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			new Date(), true, true, new String[0],
			RandomTestUtil.randomString(10), null, null, serviceContext);
	}

	@Inject
	private BlogPostingResource _blogPostingResource;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserLocalService _userLocalService;

}