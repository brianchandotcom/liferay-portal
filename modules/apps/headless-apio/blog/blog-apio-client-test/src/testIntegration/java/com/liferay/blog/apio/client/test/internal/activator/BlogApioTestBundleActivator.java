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

package com.liferay.blog.apio.client.test.internal.activator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.apio.test.util.AuthConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Víctor Galán
 */
public class BlogApioTestBundleActivator implements BundleActivator {

	public static final String CONTENT_SPACE_NAME =
		BlogApioTestBundleActivator.class.getSimpleName() + "ContentSpaceName";

	@Override
	public void start(BundleContext bundleContext) {
		_bundleContext = bundleContext;
		_serviceReference = bundleContext.getServiceReference(
			GroupLocalService.class);

		_groupLocalService = bundleContext.getService(_serviceReference);

		try {
			AuthConfigurationTestUtil.deployOAuthConfiguration(bundleContext);

			_prepareTest();
		}
		catch (Exception e) {
			_cleanUp();

			_log.error("Error found during startup ", e);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();
	}

	private void _cleanUp() {
		try {
			GroupTestUtil.deleteGroup(_group);

			_bundleContext.ungetService(_serviceReference);
		}
		catch (Throwable t) {
			_log.error(t, t);
		}
	}

	private void _prepareTest() {
		try {
			User user = UserTestUtil.getAdminUser(
				TestPropsValues.getCompanyId());

			Map<Locale, String> nameMap = Collections.singletonMap(
				LocaleUtil.getDefault(), CONTENT_SPACE_NAME);

			_group = _groupLocalService.addGroup(
				user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null,
				0, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
				GroupConstants.TYPE_SITE_OPEN, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
				StringPool.SLASH +
					FriendlyURLNormalizerUtil.normalize(CONTENT_SPACE_NAME),
				true, true, ServiceContextTestUtil.getServiceContext());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogApioTestBundleActivator.class);

	private BundleContext _bundleContext;
	private Group _group;
	private GroupLocalService _groupLocalService;
	private ServiceReference<GroupLocalService> _serviceReference;

}