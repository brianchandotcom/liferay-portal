/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.mockito.Mockito;

/**
 * @author Michael C. Han
 */
public abstract class BaseBackgroundTaskTestCase {

	@Before
	public void setUp() throws Exception {
		backgroundTaskThreadLocalManagerImpl =
			new BackgroundTaskThreadLocalManagerImpl();

		CompanyLocalService companyLocalService = Mockito.mock(
			CompanyLocalService.class);

		Mockito.when(
			companyLocalService.fetchCompany(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(Company.class)
		);

		backgroundTaskThreadLocalManagerImpl.companyLocalService =
			companyLocalService;

		GroupLocalService groupLocalService = Mockito.mock(
			GroupLocalService.class);

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			groupLocalService.fetchGroup(_GROUP_ID)
		).thenReturn(
			group
		);

		ReflectionTestUtil.setFieldValue(
			backgroundTaskThreadLocalManagerImpl, "_groupLocalService",
			groupLocalService);

		PermissionCheckerFactory permissionCheckerFactory = Mockito.mock(
			PermissionCheckerFactory.class);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionCheckerFactory.create(Mockito.any(User.class))
		).thenReturn(
			permissionChecker
		);

		ReflectionTestUtil.setFieldValue(
			backgroundTaskThreadLocalManagerImpl, "_permissionCheckerFactory",
			permissionCheckerFactory);

		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		User user = Mockito.mock(User.class);

		Mockito.when(
			user.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			userLocalService.fetchUser(Mockito.anyLong())
		).thenReturn(
			user
		);

		ReflectionTestUtil.setFieldValue(
			backgroundTaskThreadLocalManagerImpl, "_userLocalService",
			userLocalService);
	}

	@After
	public void tearDown() throws Exception {
		_resetThreadLocals();
	}

	protected void assertThreadLocalValues() {
		Assert.assertEquals(
			Long.valueOf(_COMPANY_ID), CompanyThreadLocal.getCompanyId());
		Assert.assertEquals(
			_CLUSTER_INVOKE_ENABLED, ClusterInvokeThreadLocal.isEnabled());
		Assert.assertEquals(
			_defaultLocale, LocaleThreadLocal.getDefaultLocale());
		Assert.assertEquals(
			Long.valueOf(_GROUP_ID), GroupThreadLocal.getGroupId());
		Assert.assertEquals(_PRINCIPAL_NAME, PrincipalThreadLocal.getName());
		Assert.assertEquals(
			_siteDefaultLocale, LocaleThreadLocal.getSiteDefaultLocale());
		Assert.assertEquals(
			_themeDisplayLocale, LocaleThreadLocal.getThemeDisplayLocale());
		Assert.assertNull(PrincipalThreadLocal.getPassword());
	}

	protected void assertThreadLocalValues(
		Map<String, Serializable> threadLocalValues) {

		Assert.assertTrue(MapUtil.isNotEmpty(threadLocalValues));
		Assert.assertEquals(
			threadLocalValues.toString(), 7, threadLocalValues.size());
		Assert.assertEquals(_COMPANY_ID, threadLocalValues.get("companyId"));
		Assert.assertEquals(
			_CLUSTER_INVOKE_ENABLED, threadLocalValues.get("clusterInvoke"));
		Assert.assertEquals(
			_defaultLocale, threadLocalValues.get("defaultLocale"));
		Assert.assertEquals(_GROUP_ID, threadLocalValues.get("groupId"));
		Assert.assertEquals(
			_PRINCIPAL_NAME, threadLocalValues.get("principalName"));
		Assert.assertNull(threadLocalValues.get("principalPassword"));
		Assert.assertEquals(
			_siteDefaultLocale, threadLocalValues.get("siteDefaultLocale"));
		Assert.assertEquals(
			_themeDisplayLocale, threadLocalValues.get("themeDisplayLocale"));
	}

	protected void initalizeThreadLocals() {
		CompanyThreadLocal.setCompanyId(_COMPANY_ID);
		ClusterInvokeThreadLocal.setEnabled(true);
		GroupThreadLocal.setGroupId(_GROUP_ID);
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
		LocaleThreadLocal.setSiteDefaultLocale(_siteDefaultLocale);
		LocaleThreadLocal.setThemeDisplayLocale(_themeDisplayLocale);
		PrincipalThreadLocal.setName(_PRINCIPAL_NAME);
	}

	protected HashMap<String, Serializable> initializeThreadLocalValues() {
		return HashMapBuilder.<String, Serializable>put(
			"clusterInvoke", _CLUSTER_INVOKE_ENABLED
		).put(
			"companyId", _COMPANY_ID
		).put(
			"defaultLocale", _defaultLocale
		).put(
			"groupId", _GROUP_ID
		).put(
			"principalName", _PRINCIPAL_NAME
		).put(
			"siteDefaultLocale", _siteDefaultLocale
		).put(
			"themeDisplayLocale", _themeDisplayLocale
		).build();
	}

	protected BackgroundTaskThreadLocalManagerImpl
		backgroundTaskThreadLocalManagerImpl;

	private void _resetThreadLocals() {
		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	private static final boolean _CLUSTER_INVOKE_ENABLED = true;

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _PRINCIPAL_NAME = String.valueOf(
		RandomTestUtil.randomLong());

	private final Locale _defaultLocale = LocaleUtil.US;
	private final Locale _siteDefaultLocale = LocaleUtil.CANADA;
	private final Locale _themeDisplayLocale = LocaleUtil.FRANCE;

}