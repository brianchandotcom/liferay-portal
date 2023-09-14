/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDLAMImageCounterOnlyCountsDefaultRepositoryImages()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		_portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			BlogsEntry.class.getName(), RandomTestUtil.randomLong(),
			BlogsConstants.SERVICE_NAME,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _getImageBytes(),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG, true);

		Assert.assertEquals(
			count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testDLAMImageCounterOnlyCountsDefaultRepositoryImagesPerCompany()
		throws Exception {

		Company company2 = CompanyTestUtil.addCompany();

		try {
			int company1Count = _amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId());
			int company2Count = _amImageCounter.countExpectedAMImageEntries(
				company2.getCompanyId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					_group, TestPropsValues.getUserId());

			_dlAppLocalService.addFileEntry(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
				_getImageBytes(), null, null, serviceContext);

			_portletFileRepository.addPortletFileEntry(
				_group.getGroupId(), TestPropsValues.getUserId(),
				BlogsEntry.class.getName(), RandomTestUtil.randomLong(),
				BlogsConstants.SERVICE_NAME,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _getImageBytes(),
				RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG, true);

			Assert.assertEquals(
				company1Count + 1,
				_amImageCounter.countExpectedAMImageEntries(
					TestPropsValues.getCompanyId()));
			Assert.assertEquals(
				company2Count,
				_amImageCounter.countExpectedAMImageEntries(
					company2.getCompanyId()));
		}
		finally {
			_companyLocalService.deleteCompany(company2);
		}
	}

	@Test
	public void testDLAMImageCounterOnlyCountsImagesNotInTrash()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		Assert.assertEquals(
			count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));

		_dlTrashLocalService.moveFileEntryToTrash(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			count,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testDLAMImageCounterOnlyCountsImagesWithSupportedMimeTypes()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, serviceContext);

		Assert.assertEquals(
			count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			DLAMImageCounterTest.class, "dependencies/image.jpg");
	}

	@Inject(
		filter = "adaptive.media.key=document-library",
		type = AMImageCounter.class
	)
	private AMImageCounter _amImageCounter;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLTrashLocalService _dlTrashLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletFileRepository _portletFileRepository;

}