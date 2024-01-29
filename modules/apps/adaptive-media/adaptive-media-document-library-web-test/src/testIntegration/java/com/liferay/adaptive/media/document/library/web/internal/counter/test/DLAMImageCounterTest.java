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
import com.liferay.portal.kernel.service.GroupLocalService;
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
		_count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDLAMImageCounterCountsImagesWithMultipleGroups()
		throws Exception {

		_addFileEntry();

		Assert.assertEquals(
			_count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));

		Group group = GroupTestUtil.addGroup();

		try {
			_addFileEntry(group);

			Assert.assertEquals(
				_count + 2,
				_amImageCounter.countExpectedAMImageEntries(
					TestPropsValues.getCompanyId()));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testDLAMImageCounterOnlyCountsDefaultRepositoryImages()
		throws Exception {

		_addFileEntry();

		_addPortletFileEntry();

		Assert.assertEquals(
			_count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testDLAMImageCounterOnlyCountsDefaultRepositoryImagesPerCompany()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		try {
			int count = _amImageCounter.countExpectedAMImageEntries(
				company.getCompanyId());

			_addFileEntry();

			_addPortletFileEntry();

			Assert.assertEquals(
				_count + 1,
				_amImageCounter.countExpectedAMImageEntries(
					TestPropsValues.getCompanyId()));
			Assert.assertEquals(
				count,
				_amImageCounter.countExpectedAMImageEntries(
					company.getCompanyId()));
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Test
	public void testDLAMImageCounterOnlyCountsImagesNotInTrash()
		throws Exception {

		FileEntry fileEntry = _addFileEntry();

		Assert.assertEquals(
			_count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));

		_dlTrashLocalService.moveFileEntryToTrash(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			_count,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testDLAMImageCounterOnlyCountsImagesWithSupportedMimeTypes()
		throws Exception {

		_addFileEntry();

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			TestDataConstants.TEST_BYTE_ARRAY, null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId()));

		Assert.assertEquals(
			_count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	private FileEntry _addFileEntry() throws Exception {
		return _addFileEntry(_group);
	}

	private FileEntry _addFileEntry(Group group) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, null, serviceContext);
	}

	private void _addPortletFileEntry() throws Exception {
		_portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			BlogsEntry.class.getName(), RandomTestUtil.randomLong(),
			BlogsConstants.SERVICE_NAME,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _getImageBytes(),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG, true);
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

	private int _count;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLTrashLocalService _dlTrashLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PortletFileRepository _portletFileRepository;

}