/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.journal.web.internal.optimizer.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.optimizer.AMImageOptimizer;
import com.liferay.adaptive.media.image.service.AMImageEntryLocalService;
import com.liferay.adaptive.media.journal.web.internal.counter.test.JournalArticleAMImageCounterTest;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
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
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class JournalArticleAMImageOptimizerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		_deleteAllAMImageConfigurationEntries();
	}

	@Test
	public void testJournalArticleAMImageOptimizerOptimizesEveryAMImageConfigurationEntryInSpecificCompany()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());

		_addPortletFileEntry();

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
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	@Test
	public void testJournalArticleAMImageOptimizerOptimizesEveryAMImageConfigurationEntryOnlyInSpecificCompany()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		User user = UserTestUtil.getAdminUser(company.getCompanyId());

		Group group = GroupTestUtil.addGroup(
			company.getCompanyId(), user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		try {
			int company1Count = _amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId());
			int company2Count = _amImageCounter.countExpectedAMImageEntries(
				company.getCompanyId());

			_addPortletFileEntry();

			_addPortletFileEntry(group.getGroupId(), user.getUserId());

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
				company1Count + 1,
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
				company1Count + 1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					TestPropsValues.getCompanyId(),
					amImageConfigurationEntry1.getUUID()));
			Assert.assertEquals(
				company2Count + 1,
				_amImageEntryLocalService.getAMImageEntriesCount(
					company.getCompanyId(),
					amImageConfigurationEntry2.getUUID()));
		}
		finally {
			_companyLocalService.deleteCompany(company);
		}
	}

	@Test
	public void testJournalArticleAMImageOptimizerOptimizesForSpecificAMImageConfigurationEntry()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());

		_addPortletFileEntry();

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
			count + 1,
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
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry2.getUUID()));
	}

	@Test
	public void testJournalArticleAMImageOptimizerOptimizesImagesOnlyFromJournalRepository()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());

		_addPortletFileEntry();

		_addFileEntry();

		AMImageConfigurationEntry amImageConfigurationEntry =
			_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID()));

		_amImageOptimizer.optimize(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry.getUUID());

		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID()));
	}

	@Test
	public void testJournalArticleAMImageOptimizerOptimizesImagesWithSupportedMimeTypes()
		throws Exception {

		int count = _amImageCounter.countExpectedAMImageEntries(
			TestPropsValues.getCompanyId());

		AMImageConfigurationEntry amImageConfigurationEntry1 =
			_addAMImageConfigurationEntry(TestPropsValues.getCompanyId());

		Assert.assertEquals(
			0,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));

		_addPortletFileEntry();

		_portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalArticle.class.getName(), RandomTestUtil.randomLong(),
			JournalConstants.SERVICE_NAME,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			TestDataConstants.TEST_BYTE_ARRAY, RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, true);

		_amImageOptimizer.optimize(
			TestPropsValues.getCompanyId(),
			amImageConfigurationEntry1.getUUID());

		Assert.assertEquals(
			count + 1,
			_amImageEntryLocalService.getAMImageEntriesCount(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry1.getUUID()));
	}

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

	private FileEntry _addFileEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), null, null, null, serviceContext);
	}

	private void _addPortletFileEntry() throws Exception {
		_addPortletFileEntry(_group.getGroupId(), TestPropsValues.getUserId());
	}

	private void _addPortletFileEntry(long groupId, long userId)
		throws Exception {

		_portletFileRepository.addPortletFileEntry(
			groupId, userId, JournalArticle.class.getName(),
			RandomTestUtil.randomLong(), JournalConstants.SERVICE_NAME,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _getImageBytes(),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG, true);
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
			JournalArticleAMImageCounterTest.class, "dependencies/image.jpg");
	}

	private final List<AMImageConfigurationEntry> _amImageConfigurationEntries =
		new ArrayList<>();

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject(
		filter = "adaptive.media.key=journal-article",
		type = AMImageCounter.class
	)
	private AMImageCounter _amImageCounter;

	@Inject
	private AMImageEntryLocalService _amImageEntryLocalService;

	@Inject(
		filter = "adaptive.media.key=journal-article",
		type = AMImageOptimizer.class
	)
	private AMImageOptimizer _amImageOptimizer;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletFileRepository _portletFileRepository;

	private ServiceContext _serviceContext;

}