/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.blogs.web.internal.counter.test;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

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
public class BlogsAMImageCounterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testBlogsAMImageCounterOnlyCountsBlogsImages()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		Assert.assertEquals(
			_count,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));

		_addCoverImage(blogsEntry);

		Assert.assertEquals(
			_count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testBlogsAMImageCounterOnlyCountsBlogsImagesPerCompany()
		throws Exception {

		String originalName = PrincipalThreadLocal.getName();

		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.getAdminUser(company.getCompanyId());

		try {
			PrincipalThreadLocal.setName(user.getUserId());

			BlogsEntry blogsEntry = _addBlogsEntry();

			Assert.assertEquals(
				_count,
				_amImageCounter.countExpectedAMImageEntries(
					TestPropsValues.getCompanyId()));
			Assert.assertEquals(
				0,
				_amImageCounter.countExpectedAMImageEntries(
					company.getCompanyId()));

			_addCoverImage(blogsEntry);

			Assert.assertEquals(
				_count + 1,
				_amImageCounter.countExpectedAMImageEntries(
					TestPropsValues.getCompanyId()));
			Assert.assertEquals(
				0,
				_amImageCounter.countExpectedAMImageEntries(
					company.getCompanyId()));
		}
		finally {
			_companyLocalService.deleteCompany(company);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Test
	public void testBlogsAMImageCounterOnlyCountsBlogsImagesWithMultipleGroups()
		throws Exception {

		BlogsEntry blogsEntry1 = _addBlogsEntry();

		Assert.assertEquals(
			_count,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));

		_addCoverImage(blogsEntry1);

		Group group = GroupTestUtil.addGroup();

		try {
			BlogsEntry blogsEntry2 = _addBlogsEntry(group);

			Assert.assertEquals(
				_count + 1,
				_amImageCounter.countExpectedAMImageEntries(
					TestPropsValues.getCompanyId()));

			_addCoverImage(blogsEntry2);

			Assert.assertEquals(
				_count + 2,
				_amImageCounter.countExpectedAMImageEntries(
					TestPropsValues.getCompanyId()));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	protected static final String IMAGE_CROP_REGION =
		"{\"height\": 0, \"width\": 00, \"x\": 0, \"y\": 0}";

	private BlogsEntry _addBlogsEntry() throws Exception {
		return _addBlogsEntry(_group);
	}

	private BlogsEntry _addBlogsEntry(Group group) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, null, serviceContext);

		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), serviceContext);
	}

	private void _addCoverImage(BlogsEntry blogsEntry) throws Exception {
		_blogsEntryLocalService.addCoverImage(
			blogsEntry.getEntryId(),
			new ImageSelector(
				_getImageBytes(), RandomTestUtil.randomString() + ".jpg",
				ContentTypes.IMAGE_JPEG, IMAGE_CROP_REGION));
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			BlogsAMImageCounterTest.class, "dependencies/image.jpg");
	}

	@Inject(filter = "adaptive.media.key=blogs", type = AMImageCounter.class)
	private AMImageCounter _amImageCounter;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	private int _count;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}