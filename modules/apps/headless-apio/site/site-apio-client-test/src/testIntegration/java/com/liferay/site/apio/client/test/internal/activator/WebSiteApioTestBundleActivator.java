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

package com.liferay.site.apio.client.test.internal.activator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.test.util.AuthConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.callback.PermissionCheckerTestCallback;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.util.HashMap;
import java.util.Locale;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Javier Gamarra
 */
public class WebSiteApioTestBundleActivator implements BundleActivator {

	public static final String CHILD_WEB_SITE_NAME =
		WebSiteApioTestBundleActivator.class.getSimpleName() +
			"ChildWebSiteName";

	public static final String WEB_SITE_NAME =
		WebSiteApioTestBundleActivator.class.getSimpleName() + "WebSiteName";

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
			GroupTestUtil.deleteGroup(_childGroup);
			GroupTestUtil.deleteGroup(_parentGroup);

			_permissionCheckerTestCallback.afterMethod(null, null, null);
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	private void _createDemoData(BundleContext bundleContext) {
		try {
			AuthConfigurationTestUtil.deployOAuthConfiguration(bundleContext);

			_permissionCheckerTestCallback.beforeMethod(null, null);

			_parentGroup = GroupTestUtil.addGroup(
				GroupConstants.DEFAULT_PARENT_GROUP_ID, WEB_SITE_NAME,
				new ServiceContext());

			LayoutTestUtil.addLayout(
				_parentGroup.getGroupId(), true,
				new HashMap<Locale, String>() {
					{
						put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
						put(LocaleUtil.US, RandomTestUtil.randomString());
					}
				},
				new HashMap<Locale, String>() {
					{
						put(
							LocaleUtil.SPAIN,
							StringPool.SLASH + RandomTestUtil.randomString());
						put(
							LocaleUtil.US,
							StringPool.SLASH + RandomTestUtil.randomString());
					}
				});

			LayoutTestUtil.addLayout(
				_parentGroup.getGroupId(), false,
				new HashMap<Locale, String>() {
					{
						put(LocaleUtil.SPAIN, RandomTestUtil.randomString());
						put(LocaleUtil.US, RandomTestUtil.randomString());
					}
				},
				new HashMap<Locale, String>() {
					{
						put(
							LocaleUtil.SPAIN,
							StringPool.SLASH + RandomTestUtil.randomString());
						put(
							LocaleUtil.US,
							StringPool.SLASH + RandomTestUtil.randomString());
					}
				});

			_childGroup = GroupTestUtil.addGroup(
				_parentGroup.getGroupId(), CHILD_WEB_SITE_NAME,
				new ServiceContext());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WebSiteApioTestBundleActivator.class);

	private static final PermissionCheckerTestCallback
		_permissionCheckerTestCallback = PermissionCheckerTestCallback.INSTANCE;

	private Group _childGroup;
	private Group _parentGroup;

}