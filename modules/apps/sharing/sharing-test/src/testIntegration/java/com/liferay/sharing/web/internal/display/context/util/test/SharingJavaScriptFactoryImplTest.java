/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.display.context.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.display.context.util.SharingJavaScriptFactory;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Gergely Szalay
 */
@RunWith(Arquillian.class)
public class SharingJavaScriptFactoryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_creatorUser = UserTestUtil.addUser();
		_group = GroupTestUtil.addGroup();

		_dlFolder = _dlFolderLocalService.addFolder(
			null, _group.getCreatorUserId(), _group.getGroupId(),
			_group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _creatorUser.getUserId()));

		_assetEntryLocalService.updateEntry(
			_creatorUser.getUserId(), _group.getGroupId(),
			DLFolder.class.getName(), _dlFolder.getFolderId(), new long[0],
			new String[0]);

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testCreateCopyLinkClickMethod() throws Exception {
		_testCreateCopyLinkClickMethodWithoutSharePermission();
		_testCreateCopyLinkClickMethodWithSharePermission();
	}

	@Test
	public void testCreateManageCollaboratorsOnClickMethod() throws Exception {
		_testCreateManageCollaboratorsOnClickMethodWithoutSharePermission();
		_testCreateManageCollaboratorsOnClickMethodWithSharePermission();
	}

	@Test
	public void testCreateSharingOnClickMethod() throws Exception {
		_testCreateSharingOnClickMethodWithoutSharePermission();
		_testCreateSharingOnClickMethodWithSharePermission();
	}

	private MockHttpServletRequest _createMockHttpServletRequest(User user) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private void _testCreateCopyLinkClickMethodWithoutSharePermission()
		throws Exception {

		String result = _sharingJavaScriptFactory.createCopyLinkClickMethod(
			DLFolder.class.getName(), _dlFolder.getFolderId(),
			_createMockHttpServletRequest(_user));

		Assert.assertNull(result);
	}

	private void _testCreateCopyLinkClickMethodWithSharePermission()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _creatorUser.getUserId(), 0, _user.getUserId(),
			_classNameLocalService.getClassNameId(DLFolder.class.getName()),
			_dlFolder.getFolderId(), _group.getGroupId(), true,
			List.of(SharingEntryAction.VIEW, SharingEntryAction.UPDATE), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _creatorUser.getUserId()));

		String result = _sharingJavaScriptFactory.createCopyLinkClickMethod(
			DLFolder.class.getName(), _dlFolder.getFolderId(),
			_createMockHttpServletRequest(_user));

		Assert.assertNotNull(result);

		Assert.assertTrue(result.contains("Liferay.Sharing.copyLink"));
	}

	private void _testCreateManageCollaboratorsOnClickMethodWithoutSharePermission()
		throws Exception {

		String result =
			_sharingJavaScriptFactory.createManageCollaboratorsOnClickMethod(
				DLFolder.class.getName(), _dlFolder.getFolderId(),
				_createMockHttpServletRequest(_user));

		Assert.assertNull(result);
	}

	private void _testCreateManageCollaboratorsOnClickMethodWithSharePermission()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _creatorUser.getUserId(), 0, _user.getUserId(),
			_classNameLocalService.getClassNameId(DLFolder.class.getName()),
			_dlFolder.getFolderId(), _group.getGroupId(), true,
			List.of(SharingEntryAction.VIEW, SharingEntryAction.UPDATE), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _creatorUser.getUserId()));

		String result =
			_sharingJavaScriptFactory.createManageCollaboratorsOnClickMethod(
				DLFolder.class.getName(), _dlFolder.getFolderId(),
				_createMockHttpServletRequest(_user));

		Assert.assertNotNull(result);

		Assert.assertTrue(
			result.contains("Liferay.Sharing.manageCollaborators"));
	}

	private void _testCreateSharingOnClickMethodWithoutSharePermission()
		throws Exception {

		String result = _sharingJavaScriptFactory.createSharingOnClickMethod(
			DLFolder.class.getName(), _dlFolder.getFolderId(),
			_createMockHttpServletRequest(_user));

		Assert.assertNull(result);
	}

	private void _testCreateSharingOnClickMethodWithSharePermission()
		throws Exception {

		_sharingEntryLocalService.addSharingEntry(
			null, _creatorUser.getUserId(), 0, _user.getUserId(),
			_classNameLocalService.getClassNameId(DLFolder.class.getName()),
			_dlFolder.getFolderId(), _group.getGroupId(), true,
			List.of(SharingEntryAction.VIEW, SharingEntryAction.UPDATE), null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _creatorUser.getUserId()));

		String result = _sharingJavaScriptFactory.createSharingOnClickMethod(
			DLFolder.class.getName(), _dlFolder.getFolderId(),
			_createMockHttpServletRequest(_user));

		Assert.assertNotNull(result);

		Assert.assertTrue(result.contains("Liferay.Sharing.share"));
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private User _creatorUser;

	@DeleteAfterTestRun
	private DLFolder _dlFolder;

	@Inject
	private DLFolderLocalService _dlFolderLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	@Inject
	private SharingJavaScriptFactory _sharingJavaScriptFactory;

	@DeleteAfterTestRun
	private User _user;

}