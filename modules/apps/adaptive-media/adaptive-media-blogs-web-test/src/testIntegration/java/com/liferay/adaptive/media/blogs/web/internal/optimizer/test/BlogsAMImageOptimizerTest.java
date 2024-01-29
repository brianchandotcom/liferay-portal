/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
		_deleteAllAMImageConfigurationEntries();
	}

	@Test
	public void testBlogsAMImageOptimizerOptimizesEveryAMImageConfigurationEntryInSpecificCompany()
		throws Exception {

		_addBlogEntryWithCoverImage();

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(TestPropsValues.getCompanyId());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	@Test
	public void testBlogsAMImageOptimizerOptimizesEveryAMImageConfigurationEntryInSpecificCompanyWithMultipleGroups()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		try {
			_addBlogEntryWithCoverImage();
			_addBlogEntryWithCoverImage(
				TestPropsValues.getUserId(), group.getGroupId());

			AMImageConfigurationEntry amImageConfigurationEntry1 =
				_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());
			AMImageConfigurationEntry amImageConfigurationEntry2 =
				_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());

			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));

			_amImageOptimizer.optimize(TestPropsValues.getCompanyId());

			Assert.assertEquals(
				2,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				2,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));
		}
		finally {
			_groupLocalService.deleteGroup(group);
		}
	}

	@Test
	public void testBlogsAMImageOptimizerOptimizesEveryAMImageConfigurationEntryOnlyInSpecificCompany()
		throws Exception {

		String originalName = PrincipalThreadLocal.getName();

		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.getAdminUser(company.getCompanyId());

		Group group = GroupTestUtil.addGroup(
			company.getCompanyId(), user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		try {
			PrincipalThreadLocal.setName(user.getUserId());

			_addBlogEntryWithCoverImage();
			_addBlogEntryWithCoverImage(user.getUserId(), group.getGroupId());

			AMImageConfigurationEntry amImageConfigurationEntry1 =
				_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());
			AMImageConfigurationEntry amImageConfigurationEntry2 =
				_addAMImageConfigurationEntry(company.getCompanyId());

			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					company.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));

			_amImageOptimizer.optimize(TestPropsValues.getCompanyId());

			Assert.assertEquals(
				1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				0,
				_amImageEntryLocalService.getAMImageEntriesCount(
					company.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));

			_amImageOptimizer.optimize(company.getCompanyId());

			Assert.assertEquals(
				1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					company.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));
		}
		finally {
			_companyLocalService.deleteCompany(company);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Test
	public void testBlogsAMImageOptimizerOptimizesForSpecificAMImageConfigurationEntry()
		throws Exception {

		_addBlogEntryWithCoverImage();

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());
		AMImageConfigurationEntry amImageConfigurationEntry2 =
			_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry1.getUUID());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));

		_amImageOptimizer.optimize(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry2.getUUID());

		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	protected static final String IMAGE_CROP_REGION =
		"{\"height\": 0, \"width\": 00, \"x\": 0, \"y\": 0}";

	private AMImageConfigurationEntry _addAMImageConfigurationEntry(
			long companyId)
		throws Exception {

		String amImageConfigurationEntryName = RandomTestUtil.randomString();

		AMImageConfigurationEntry amImageConfigurationEntry =
			_amImageConfigurationHelper.addAMImageConfigurationEntry(
				companyId, amImageConfigurationEntryName, StringPool.BLANK,
				amImageConfigurationEntryName,
				HashMapBuilder.put(
					"max-height", String.valueOf(RandomTestUtil.randomLong())
				).put(
					"max-width", String.valueOf(RandomTestUtil.randomLong())
				).build());

		_amImageConfigurationEntries.add(amImageConfigurationEntry);

		return amImageConfigurationEntry;
	}

	private BlogsEntry _addBlogEntryWithCoverImage() throws Exception {
		return _addBlogEntryWithCoverImage(
			TestPropsValues.getUserId(), _group.getGroupId());
	}

	private BlogsEntry _addBlogEntryWithCoverImage(long userId, long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		_dlAppLocalService.addFileEntry(
			null, userId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, null, serviceContext);

		BlogsEntry blogsEntry = _blogsEntryLocalService.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), serviceContext);

		_blogsEntryLocalService.addCoverImage(
			blogsEntry.getEntryId(),
			new ImageSelector(
				_getImageBytes(), RandomTestUtil.randomString() + ".jpg",
				ContentTypes.IMAGE_JPEG, IMAGE_CROP_REGION));

		return _blogsEntryLocalService.getEntry(blogsEntry.getEntryId());
	}

	private void _deleteAllAMImageConfigurationEntries() throws Exception {
		for (AMImageConfigurationEntry amImageConfigurationEntry :
				_amImageConfigurationEntries) {

			_amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());
		}
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			BlogsAMImageOptimizerTest.class, "dependencies/image.jpg");
	}

	private final List<AMImageConfigurationEntry> _amImageConfigurationEntries =
		new ArrayList<>();

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Inject(filter = "adaptive.media.key=blogs", type = AMImageOptimizer.class)
	private AMImageOptimizer _amImageOptimizer;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

}