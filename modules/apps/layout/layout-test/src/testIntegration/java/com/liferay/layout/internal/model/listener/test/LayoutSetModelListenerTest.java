/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mauricio Valdivia
 */
@RunWith(Arquillian.class)
public class LayoutSetModelListenerTest {

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
	public void testFaviconRemoval() throws Exception {
		FileEntry faviconFileEntry = _createFaviconFileEntry();

		_layoutSetLocalService.updateFaviconFileEntryId(
			_group.getGroupId(), false, faviconFileEntry.getFileEntryId());

		_assertGuestHasDownloadPermission(faviconFileEntry.getFileEntryId());

		_layoutSetLocalService.updateFaviconFileEntryId(
			_group.getGroupId(), false, 0);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		Assert.assertEquals(0, layoutSet.getFaviconFileEntryId());
	}

	@Test
	public void testMultipleFaviconUpdates() throws Exception {
		FileEntry faviconFileEntry1 = _createFaviconFileEntry();
		FileEntry faviconFileEntry2 = _createFaviconFileEntry();
		FileEntry faviconFileEntry3 = _createFaviconFileEntry();

		_layoutSetLocalService.updateFaviconFileEntryId(
			_group.getGroupId(), false, faviconFileEntry1.getFileEntryId());

		_assertGuestHasDownloadPermission(faviconFileEntry1.getFileEntryId());

		_layoutSetLocalService.updateFaviconFileEntryId(
			_group.getGroupId(), false, faviconFileEntry2.getFileEntryId());

		_assertGuestHasDownloadPermission(faviconFileEntry2.getFileEntryId());

		_layoutSetLocalService.updateFaviconFileEntryId(
			_group.getGroupId(), false, faviconFileEntry3.getFileEntryId());

		_assertGuestHasDownloadPermission(faviconFileEntry3.getFileEntryId());
	}

	private void _assertGuestHasDownloadPermission(long fileEntryId)
		throws Exception {

		Map<Long, Set<String>> roleIdsToActionIds =
			_resourcePermissionLocalService.
				getAvailableResourcePermissionActionIds(
					_group.getCompanyId(), DLFileEntry.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(fileEntryId),
					Arrays.asList(ActionKeys.DOWNLOAD));

		Role guestRole = _roleLocalService.getRole(
			_group.getCompanyId(), RoleConstants.GUEST);

		Set<String> actionIds = roleIdsToActionIds.get(guestRole.getRoleId());

		Assert.assertNotNull(actionIds);
		Assert.assertTrue(
			actionIds.contains(ActionKeys.DOWNLOAD));
	}

	private FileEntry _createFaviconFileEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String fileName = RandomTestUtil.randomString() + ".ico";
		String title = RandomTestUtil.randomString();

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			"image/x-icon", title, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, TestDataConstants.TEST_BYTE_ARRAY, null, null,
			null, serviceContext);
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

}