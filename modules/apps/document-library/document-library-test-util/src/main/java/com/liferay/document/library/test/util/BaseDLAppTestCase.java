/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.test.util;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Alexander Chow
 */
public abstract class BaseDLAppTestCase {

	public static final String CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	@ClassRule
	@Rule
	public static final PermissionCheckerMethodTestRule
		permissionCheckerTestRule = PermissionCheckerMethodTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_name = PrincipalThreadLocal.getName();

		group = GroupTestUtil.addGroup();

		targetGroup = GroupTestUtil.addGroup();

		try {
			_dlAppService.deleteFolder(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"Test Folder");
		}
		catch (NoSuchFolderException noSuchFolderException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFolderException);
			}
		}

		parentFolder = _dlAppService.addFolder(
			null, group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));

		RoleTestUtil.addResourcePermission(
			RoleConstants.GUEST, DLConstants.RESOURCE_NAME,
			ResourceConstants.SCOPE_GROUP, String.valueOf(group.getGroupId()),
			ActionKeys.VIEW);
	}

	@After
	public void tearDown() throws Exception {
		PrincipalThreadLocal.setName(_name);
	}

	@DeleteAfterTestRun
	protected Group group;

	protected Folder parentFolder;

	@DeleteAfterTestRun
	protected Group targetGroup;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseDLAppTestCase.class);

	@Inject
	private DLAppService _dlAppService;

	private String _name;

}