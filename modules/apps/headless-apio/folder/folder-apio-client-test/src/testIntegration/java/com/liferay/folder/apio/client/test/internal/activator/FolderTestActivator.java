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

package com.liferay.folder.apio.client.test.internal.activator;

import com.liferay.portal.apio.test.util.AuthConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.callback.PermissionCheckerTestCallback;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Rubén Pulido
 */
public class FolderTestActivator implements BundleActivator {

	public static final String CONTENT_SPACE_NAME =
		FolderTestActivator.class.getSimpleName() + "ContentSpaceName";

	@Override
	public void start(BundleContext bundleContext) {
		_createDemoData(bundleContext);
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();
	}

	private void _cleanUp() {
		try {
			GroupTestUtil.deleteGroup(_group);

			_permissionCheckerTestCallback.afterMethod(null, null, null);
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	private void _createDemoData(BundleContext bundleContext) {
		try {
			_permissionCheckerTestCallback.beforeMethod(null, null);

			_group = GroupTestUtil.addGroup(
				GroupConstants.DEFAULT_PARENT_GROUP_ID, CONTENT_SPACE_NAME,
				new ServiceContext());

			AuthConfigurationTestUtil.deployOAuthConfiguration(bundleContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FolderTestActivator.class);

	private static final PermissionCheckerTestCallback
		_permissionCheckerTestCallback = PermissionCheckerTestCallback.INSTANCE;

	private Group _group;

}