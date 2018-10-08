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

package com.liferay.structured.content.apio.client.test.activator;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Ruben Pulido
 */
public class StructuredContentApioTestBundleActivator
	implements BundleActivator {

	public static final String NOT_A_SITE_MEMBER_EMAIL_ADDRESS =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"NotASiteMemberUser@liferay.com";

	public static final String SITE_MEMBER_EMAIL_ADDRESS =
		StructuredContentApioTestBundleActivator.class.getSimpleName() +
			"SiteMemberUser@liferay.com";

	public static final String SITE_NAME =
		StructuredContentApioTestBundleActivator.class.getSimpleName() + "Site";

	@Override
	public void start(BundleContext bundleContext) {
		_autoCloseables = new ArrayList<>();

		try {
			_prepareTest();
		}
		catch (Exception e) {
			_cleanUp();

			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_cleanUp();
	}

	private User _addUser(String emailAddress, long companyId, long groupId)
		throws Exception {

		User existingUser = UserLocalServiceUtil.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (existingUser != null) {
			UserLocalServiceUtil.deleteUser(existingUser.getUserId());
		}

		User user = UserLocalServiceUtil.addUser(
			UserConstants.USER_ID_DEFAULT, companyId, false, Constants.TEST,
			Constants.TEST, true, StringUtil.randomString(20), emailAddress, 0,
			null, PortalUtil.getSiteDefaultLocale(groupId),
			StringUtil.randomString(20), null, StringUtil.randomString(10), 0,
			0, true, 1, 1, 2000, null, new long[] {groupId}, new long[0],
			new long[0], new long[0], false, new ServiceContext());

		_autoCloseables.add(
			() -> UserLocalServiceUtil.deleteUser(user.getUserId()));

		return user;
	}
	private void _cleanUp() {
		for (AutoCloseable autoCloseable : _autoCloseables) {
			try {
				autoCloseable.close();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private void _prepareTest() throws Exception {
		User user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());
		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getDefault(), SITE_NAME);

		Group group = GroupLocalServiceUtil.addGroup(
			user.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, nameMap,
			GroupConstants.TYPE_SITE_OPEN, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(SITE_NAME),
			true, true, ServiceContextTestUtil.getServiceContext());

		_autoCloseables.add(() -> GroupLocalServiceUtil.deleteGroup(group));

		_addUser(
			SITE_MEMBER_EMAIL_ADDRESS, TestPropsValues.getCompanyId(),
			group.getGroupId());

		_addUser(
			NOT_A_SITE_MEMBER_EMAIL_ADDRESS, TestPropsValues.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StructuredContentApioTestBundleActivator.class);

	private List<AutoCloseable> _autoCloseables;

}