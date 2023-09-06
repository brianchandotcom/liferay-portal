/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.conflict.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@RunWith(Arquillian.class)
public class CTColumnResolutionMaxTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, CTColumnResolutionMaxTest.class.getSimpleName(), null);

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testResolveModificationConflictMax() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLFolder parentDLFolder = _dlFolderLocalService.addFolder(
			null, _group.getCreatorUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			serviceContext);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_dlFolderLocalService.addFolder(
				null, _group.getCreatorUserId(), _group.getGroupId(),
				_group.getGroupId(), false, parentDLFolder.getFolderId(),
				_ctCollection.getName(), null, false, serviceContext);
		}

		Date lastPostDate = parentDLFolder.getLastPostDate();

		_dlFolderLocalService.updateLastPostDate(
			parentDLFolder.getFolderId(),
			new Date(lastPostDate.getTime() + Time.HOUR));

		Map<Long, List<ConflictInfo>> conflictsMap =
			_ctCollectionLocalService.checkConflicts(_ctCollection);

		Assert.assertEquals(conflictsMap.toString(), 1, conflictsMap.size());

		List<ConflictInfo> conflictInfos = conflictsMap.get(
			_classNameLocalService.getClassNameId(DLFolder.class));

		Assert.assertEquals(conflictsMap.toString(), 1, conflictInfos.size());

		ConflictInfo conflictInfo = conflictInfos.get(0);

		Assert.assertEquals(
			parentDLFolder.getPrimaryKey(), conflictInfo.getSourcePrimaryKey());
		Assert.assertEquals(
			parentDLFolder.getPrimaryKey(), conflictInfo.getTargetPrimaryKey());

		Assert.assertTrue(conflictInfo.isResolved());
	}

	@Test
	public void testUnresolvedModificationConflictMax() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String originalName = "original_name";

		DLFolder parentDLFolder = _dlFolderLocalService.addFolder(
			null, _group.getCreatorUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, originalName,
			RandomTestUtil.randomString(), false, serviceContext);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			_dlFolderLocalService.addFolder(
				null, _group.getCreatorUserId(), _group.getGroupId(),
				_group.getGroupId(), false, parentDLFolder.getFolderId(),
				_ctCollection.getName(), null, false, serviceContext);
		}

		parentDLFolder.setName("modified_name");

		Date lastPostDate = parentDLFolder.getLastPostDate();

		parentDLFolder.setLastPostDate(
			new Date(lastPostDate.getTime() + Time.HOUR));

		parentDLFolder = _dlFolderLocalService.updateDLFolder(parentDLFolder);

		Map<Long, List<ConflictInfo>> conflictsMap =
			_ctCollectionLocalService.checkConflicts(_ctCollection);

		Assert.assertEquals(conflictsMap.toString(), 1, conflictsMap.size());

		List<ConflictInfo> conflictInfos = conflictsMap.get(
			_classNameLocalService.getClassNameId(DLFolder.class));

		Assert.assertEquals(conflictsMap.toString(), 1, conflictInfos.size());

		ConflictInfo conflictInfo = conflictInfos.get(0);

		Assert.assertEquals(
			parentDLFolder.getPrimaryKey(), conflictInfo.getSourcePrimaryKey());
		Assert.assertEquals(
			parentDLFolder.getPrimaryKey(), conflictInfo.getTargetPrimaryKey());

		Assert.assertFalse(conflictInfo.isResolved());

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					_ctCollection.getCtCollectionId())) {

			parentDLFolder = _dlFolderLocalService.getFolder(
				parentDLFolder.getFolderId());

			Assert.assertEquals(originalName, parentDLFolder.getName());
		}
	}

	@Inject
	private static ClassNameLocalService _classNameLocalService;

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static DLFolderLocalService _dlFolderLocalService;

	@DeleteAfterTestRun
	private CTCollection _ctCollection;

	@DeleteAfterTestRun
	private Group _group;

}