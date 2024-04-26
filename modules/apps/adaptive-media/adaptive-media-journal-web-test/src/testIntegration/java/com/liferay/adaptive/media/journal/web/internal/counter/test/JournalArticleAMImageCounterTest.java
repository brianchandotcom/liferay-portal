/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.journal.web.internal.counter.test;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
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
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class JournalArticleAMImageCounterTest {

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
	public void testJournalArticleAMImageCounterOnlyCountsImagesWithSupportedMimeTypes()
		throws Exception {

		_addPortletFileEntry();

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

	@Test
	public void testJournalArticleAMImageCounterOnlyCountsJournalRepositoryImages()
		throws Exception {

		_addPortletFileEntry();

		_addFileEntry();

		Assert.assertEquals(
			_count + 1,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));

		_addPortletFileEntry();

		Assert.assertEquals(
			_count + 2,
			_amImageCounter.countExpectedAMImageEntries(
				TestPropsValues.getCompanyId()));
	}

	@Test
	public void testJournalArticleAMImageCounterOnlyCountsJournalRepositoryImagesPerCompany()
		throws Exception {

		Company company = CompanyTestUtil.addCompany();

		try {
			int count = _amImageCounter.countExpectedAMImageEntries(
				company.getCompanyId());

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
		_portletFileRepository.addPortletFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalArticle.class.getName(), RandomTestUtil.randomLong(),
			JournalConstants.SERVICE_NAME,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _getImageBytes(),
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG, true);
	}

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			JournalArticleAMImageCounterTest.class, "dependencies/image.jpg");
	}

	@Inject(
		filter = "adaptive.media.key=journal-article",
		type = AMImageCounter.class
	)
	private AMImageCounter _amImageCounter;

	@Inject
	private CompanyLocalService _companyLocalService;

	private int _count;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletFileRepository _portletFileRepository;

}