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

package com.liferay.adaptive.media.document.library.web.internal.counter.test;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLTrashLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class DLAMImageCounterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company1 = CompanyTestUtil.addCompany();

		_user1 = UserTestUtil.getAdminUser(_company1.getCompanyId());

		_group1 = GroupTestUtil.addGroup(
			_company1.getCompanyId(), _user1.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_company2 = CompanyTestUtil.addCompany();

		_user2 = UserTestUtil.getAdminUser(_company2.getCompanyId());

		_group2 = GroupTestUtil.addGroup(
			_company2.getCompanyId(), _user2.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
	}

	@Test
	public void testDLAMImageCounterOnlyCountsDefaultRepositoryImages()
		throws Exception {

		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1, _user1.getUserId());

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		_portletFileRepository.addPortletFileEntry(
			_group1.getGroupId(), _user1.getUserId(),
			BlogsEntry.class.getName(), RandomTestUtil.randomLong(),
			BlogsConstants.SERVICE_NAME,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _getImageBytes(),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG, true);

		Assert.assertEquals(
			1,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
	}

	@Test
	public void testDLAMImageCounterOnlyCountsDefaultRepositoryImagesPerCompany()
		throws Exception {

		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company2.getCompanyId()));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1, _user1.getUserId());

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		_portletFileRepository.addPortletFileEntry(
			_group1.getGroupId(), _user1.getUserId(),
			BlogsEntry.class.getName(), RandomTestUtil.randomLong(),
			BlogsConstants.SERVICE_NAME,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _getImageBytes(),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG, true);

		Assert.assertEquals(
			1,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company2.getCompanyId()));
	}

	@Test
	public void testDLAMImageCounterOnlyCountsImagesNotInTrash()
		throws Exception {

		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1, _user1.getUserId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		Assert.assertEquals(
			1,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));

		_dlTrashLocalService.moveFileEntryToTrash(
			_user1.getUserId(), _group1.getGroupId(),
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
	}

	@Test
	public void testDLAMImageCounterOnlyCountsImagesWithSupportedMimeTypes()
		throws Exception {

		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1, _user1.getUserId());

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		_dlAppLocalService.addFileEntry(
			null, _user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);

		Assert.assertEquals(
			1,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(DLAMImageCounterTest.class, "image.jpg");
	}

	@Inject(
		filter = "adaptive.media.key=document-library",
		type = AMImageCounter.class
	)
	private AMImageCounter _amImageCounter;

	@DeleteAfterTestRun
	private Company _company1;

	@DeleteAfterTestRun
	private Company _company2;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLTrashLocalService _dlTrashLocalService;

	private Group _group1;
	private Group _group2;

	@Inject
	private PortletFileRepository _portletFileRepository;

	private User _user1;
	private User _user2;

}