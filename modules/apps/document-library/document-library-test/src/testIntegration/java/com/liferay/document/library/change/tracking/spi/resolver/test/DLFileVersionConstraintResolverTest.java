/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.change.tracking.spi.resolver.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class DLFileVersionConstraintResolverTest {

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
	public void testResolveConflict() throws Exception {
		String fileName = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		FileEntry fileEntry1 = _addFileEntry(fileName, title);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(fileEntry1.getGroupId());

		CTCollection ctCollection1 = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection1.getCtCollectionId())) {

			fileEntry1 = _dlAppService.updateFileEntry(
				fileEntry1.getFileEntryId(), fileEntry1.getFileName(),
				fileEntry1.getMimeType(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), fileEntry1.getDescription(),
				RandomTestUtil.randomString(), DLVersionNumberIncrease.MINOR,
				null, fileEntry1.getSize(), fileEntry1.getDisplayDate(),
				fileEntry1.getExpirationDate(), fileEntry1.getReviewDate(),
				serviceContext);

			fileEntry1 = _dlAppService.updateFileEntry(
				fileEntry1.getFileEntryId(), fileEntry1.getFileName(),
				fileEntry1.getMimeType(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), fileEntry1.getDescription(),
				RandomTestUtil.randomString(), DLVersionNumberIncrease.MINOR,
				null, fileEntry1.getSize(), fileEntry1.getDisplayDate(),
				fileEntry1.getExpirationDate(), fileEntry1.getReviewDate(),
				serviceContext);
		}

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			FileEntry fileEntry2 = _dlAppService.getFileEntry(
				fileEntry1.getFileEntryId());

			_dlAppService.updateFileEntry(
				fileEntry2.getFileEntryId(), fileEntry2.getFileName(),
				fileEntry2.getMimeType(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), fileEntry2.getDescription(),
				RandomTestUtil.randomString(), DLVersionNumberIncrease.MINOR,
				null, fileEntry2.getSize(), fileEntry2.getDisplayDate(),
				fileEntry2.getExpirationDate(), fileEntry2.getReviewDate(),
				serviceContext);
		}

		Map<Long, List<ConflictInfo>> conflictsMap =
			_ctCollectionLocalService.checkConflicts(ctCollection1);

		Assert.assertFalse(conflictsMap.isEmpty());

		for (Map.Entry<Long, List<ConflictInfo>> entry :
				conflictsMap.entrySet()) {

			for (ConflictInfo conflictInfo : entry.getValue()) {
				Assert.assertTrue(conflictInfo.isResolved());
			}
		}
	}

	private FileEntry _addFileEntry(String fileName, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Folder folder = _dlAppLocalService.addFolder(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		return _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), folder.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			ContentTypes.TEXT_PLAIN, title, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, "liferay".getBytes(), null, null, null,
			serviceContext);
	}

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLAppService _dlAppService;

	@DeleteAfterTestRun
	private Group _group;

}