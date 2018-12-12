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

package com.liferay.folder.apio.client.test.activator;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.test.util.AuthConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.callback.PermissionCheckerTestCallback;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Ruben Pulido
 */
public class FolderTestActivator implements BundleActivator {

	public static final String CONTENT_SPACE_NAME =
		FolderTestActivator.class.getSimpleName() + "ContentSpaceName";

	public static final String FOLDER_NAME =
		FolderTestActivator.class.getSimpleName() + "FolderName";

	public static final String SUBFOLDER_NAME =
		FolderTestActivator.class.getSimpleName() + "SubfolderName";

	@Override
	public void start(BundleContext bundleContext) {
		_createDemoData(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_deleteDemoData();
	}

	private static DLFolder _addDLFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		try {
			DLFolder folder = DLFolderLocalServiceUtil.getFolder(
				groupId, parentFolderId, name);

			DLFolderLocalServiceUtil.deleteFolder(folder.getFolderId());
		}
		catch (NoSuchFolderException nsfe) {
		}

		return DLFolderLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), groupId, groupId, false,
			parentFolderId, name, StringPool.BLANK, false,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private void _createDemoData(BundleContext bundleContext) {
		try {
			_permissionCheckerTestCallback.beforeMethod(null, null);

			_group = GroupTestUtil.addGroup(
				GroupConstants.DEFAULT_PARENT_GROUP_ID, CONTENT_SPACE_NAME,
				new ServiceContext());

			AuthConfigurationTestUtil.deployOAuthConfiguration(bundleContext);

			DLFolder dlFolder = _addDLFolder(
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				FOLDER_NAME);

			_addDLFolder(
				_group.getGroupId(), dlFolder.getFolderId(), SUBFOLDER_NAME);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _deleteDemoData() {
		try {
			GroupTestUtil.deleteGroup(_group);

			_permissionCheckerTestCallback.afterMethod(null, null, null);
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FolderTestActivator.class);

	private static final PermissionCheckerTestCallback
		_permissionCheckerTestCallback = PermissionCheckerTestCallback.INSTANCE;

	private Group _group;

}