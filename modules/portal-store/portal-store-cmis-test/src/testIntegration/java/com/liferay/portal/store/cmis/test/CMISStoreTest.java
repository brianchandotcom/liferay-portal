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

package com.liferay.portal.store.cmis.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.AssumeTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.store.cmis.test.activator.configuration.ConfigurationAdminBundleActivator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.store.test.BaseStoreTestCase;

import java.util.Calendar;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class CMISStoreTest extends BaseStoreTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new AssumeTestRule("assume"), new LiferayIntegrationTestRule());

	public static void assume() {
		String dlStoreImpl = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		String cmisStoreClassName = "com.liferay.portal.store.cmis.CMISStore";

		Assume.assumeTrue(
			"Property \"" + PropsKeys.DL_STORE_IMPL + "\" is not set to \"" +
				cmisStoreClassName + "\"",
			dlStoreImpl.equals(cmisStoreClassName));
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(getCMISAdminUser());
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	protected User getCMISAdminUser() throws Exception {
		User user = UserLocalServiceUtil.fetchUserByScreenName(
			TestPropsValues.getCompanyId(),
			ConfigurationAdminBundleActivator.CREDENTIALS_USERNAME);

		if (user != null) {
			return user;
		}

		String password =
			ConfigurationAdminBundleActivator.CREDENTIALS_PASSWORD;
		String emailAddress =
			ConfigurationAdminBundleActivator.CREDENTIALS_USERNAME +
				"@liferay.com";

		user = UserLocalServiceUtil.addUser(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), false,
			password, password, false,
			ConfigurationAdminBundleActivator.CREDENTIALS_USERNAME,
			emailAddress, 0, StringPool.BLANK, LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), 0, 0, true, Calendar.JANUARY, 1,
			1970, StringPool.BLANK, new long[] {repositoryId},
			null, null, null, false,
			ServiceContextTestUtil.getServiceContext());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), RoleConstants.ADMINISTRATOR);

		RoleLocalServiceUtil.addUserRole(user.getUserId(), role.getRoleId());

		return user;
	}

	@Override
	protected String getStoreType() {
		return "com.liferay.portal.store.cmis.CMISStore";
	}

}