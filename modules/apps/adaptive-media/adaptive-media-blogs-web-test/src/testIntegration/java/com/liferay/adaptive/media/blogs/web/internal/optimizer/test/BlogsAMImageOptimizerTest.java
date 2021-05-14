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

package com.liferay.adaptive.media.blogs.web.internal.optimizer.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.optimizer.AMImageOptimizer;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collection;
import java.util.Date;

import org.junit.After;
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
public class BlogsAMImageOptimizerTest {

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

	@After
	public void tearDown() throws Exception {
		_deleteAllAMImageConfigurationEntries(_company1.getCompanyId());
		_deleteAllAMImageConfigurationEntries(_company2.getCompanyId());
	}

	@Test
	public void testBlogsAMImageOptimizerOptimizesEveryAMImageConfigurationEntryInSpecificCompany()
		throws Exception {

		_addBlogEntryWithCoverImage(_user1.getUserId(), _group1.getGroupId());

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(_company1.getCompanyId());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	@Test
	public void testBlogsAMImageOptimizerOptimizesEveryAMImageConfigurationEntryOnlyInSpecificCompany()
		throws Exception {

		_addBlogEntryWithCoverImage(_user1.getUserId(), _group1.getGroupId());
		_addBlogEntryWithCoverImage(_user2.getUserId(), _group2.getGroupId());

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_addAMImageConfigurationEntry(_company2.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company2.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(_company1.getCompanyId());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company2.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(_company2.getCompanyId());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company2.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	@Test
	public void testBlogsAMImageOptimizerOptimizesForSpecificAMImageConfigurationEntry()
		throws Exception {

		_addBlogEntryWithCoverImage(_user1.getUserId(), _group1.getGroupId());

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_addAMImageConfigurationEntry(_company1.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(
			_company1.getCompanyId(), amImageConfigurationEntry1.getUUID());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(
			_company1.getCompanyId(), amImageConfigurationEntry2.getUUID());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				_company1.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	protected static final String IMAGE_CROP_REGION =
		"{\"height\": 0, \"width\": 00, \"x\": 0, \"y\": 0}";

	private AMImageConfigurationEntry _addAMImageConfigurationEntry(
			long companyId)
		throws Exception {

		String amImageConfigurationEntry1Name = RandomTestUtil.randomString();

		return _amImageConfigurationHelper.addAMImageConfigurationEntry(
			companyId, amImageConfigurationEntry1Name, StringPool.BLANK,
			amImageConfigurationEntry1Name,
			HashMapBuilder.put(
				"max-height", String.valueOf(RandomTestUtil.randomLong())
			).put(
				"max-width", String.valueOf(RandomTestUtil.randomLong())
			).build());
	}

	private BlogsEntry _addBlogEntryWithCoverImage(long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		_dlAppLocalService.addFileEntry(
			null, userId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, serviceContext);

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), serviceContext);

		ImageSelector imageSelector = new ImageSelector(
			_getImageBytes(), RandomTestUtil.randomString() + ".jpg",
			ContentTypes.IMAGE_JPEG, IMAGE_CROP_REGION);

		_blogsEntryLocalService.addCoverImage(
			blogsEntry.getEntryId(), imageSelector);

		return _blogsEntryLocalService.getEntry(blogsEntry.getEntryId());
	}

	private void _deleteAllAMImageConfigurationEntries(long companyId)
		throws Exception {

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				companyId, amImageConfigurationEntry -> true);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
				companyId, amImageConfigurationEntry.getUUID());
		}
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(BlogsAMImageOptimizerTest.class, "image.jpg");
	}

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Inject(filter = "adaptive.media.key=blogs", type = AMImageOptimizer.class)
	private AMImageOptimizer _amImageOptimizer;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private Company _company1;

	@DeleteAfterTestRun
	private Company _company2;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	private Group _group1;
	private Group _group2;
	private User _user1;
	private User _user2;

}