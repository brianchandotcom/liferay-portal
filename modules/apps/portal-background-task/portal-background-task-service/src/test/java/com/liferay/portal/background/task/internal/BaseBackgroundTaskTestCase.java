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

package com.liferay.portal.background.task.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

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
		RegistryUtil.setRegistry(new BasicRegistryImpl());

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

		PermissionCheckerFactory permissionCheckerFactory = Mockito.mock(
			PermissionCheckerFactory.class);

		PermissionChecker permissionChecker = Mockito.mock(
			PermissionChecker.class);

		Mockito.when(
			permissionCheckerFactory.create(Mockito.any(User.class))
		).thenReturn(
			permissionChecker
		);

		backgroundTaskThreadLocalManagerImpl.setPermissionCheckerFactory(
			permissionCheckerFactory);

		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		Mockito.when(
			userLocalService.fetchUser(Mockito.anyLong())
		).thenReturn(
			Mockito.mock(User.class)
		);

		backgroundTaskThreadLocalManagerImpl.setUserLocalService(
			userLocalService);

		_companyId = 1234L;
		_clusterInvokeEnabled = true;
		_defaultLocale = LocaleUtil.US;
		_groupId = 1234L;
		_siteDefaultLocale = LocaleUtil.CANADA;
		_themeDisplayLocale = LocaleUtil.FRANCE;

		_principalName = String.valueOf(1234L);
	}

	@After
	public void tearDown() throws Exception {
		resetThreadLocals();
	}

	protected void assertThreadLocalValues() {
		Assert.assertEquals(
			Long.valueOf(_companyId), CompanyThreadLocal.getCompanyId());
		Assert.assertEquals(
			_clusterInvokeEnabled, ClusterInvokeThreadLocal.isEnabled());
		Assert.assertEquals(
			_defaultLocale, LocaleThreadLocal.getDefaultLocale());
		Assert.assertEquals(
			Long.valueOf(_groupId), GroupThreadLocal.getGroupId());
		Assert.assertEquals(_principalName, PrincipalThreadLocal.getName());
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
		Assert.assertEquals(_companyId, threadLocalValues.get("companyId"));
		Assert.assertEquals(
			_clusterInvokeEnabled, threadLocalValues.get("clusterInvoke"));
		Assert.assertEquals(
			_defaultLocale, threadLocalValues.get("defaultLocale"));
		Assert.assertEquals(_groupId, threadLocalValues.get("groupId"));
		Assert.assertEquals(
			_principalName, threadLocalValues.get("principalName"));
		Assert.assertNull(threadLocalValues.get("principalPassword"));
		Assert.assertEquals(
			_siteDefaultLocale, threadLocalValues.get("siteDefaultLocale"));
		Assert.assertEquals(
			_themeDisplayLocale, threadLocalValues.get("themeDisplayLocale"));
	}

	protected void initalizeThreadLocals() {
		CompanyThreadLocal.setCompanyId(_companyId);
		ClusterInvokeThreadLocal.setEnabled(true);
		GroupThreadLocal.setGroupId(_groupId);
		LocaleThreadLocal.setDefaultLocale(_defaultLocale);
		LocaleThreadLocal.setSiteDefaultLocale(_siteDefaultLocale);
		LocaleThreadLocal.setThemeDisplayLocale(_themeDisplayLocale);
		PrincipalThreadLocal.setName(_principalName);
	}

	protected HashMap<String, Serializable> initializeThreadLocalValues() {
		HashMap<String, Serializable> threadLocalValues = new HashMap<>();

		threadLocalValues.put("clusterInvoke", _clusterInvokeEnabled);
		threadLocalValues.put("companyId", _companyId);
		threadLocalValues.put("defaultLocale", _defaultLocale);
		threadLocalValues.put("groupId", _groupId);
		threadLocalValues.put("principalName", _principalName);
		threadLocalValues.put("siteDefaultLocale", _siteDefaultLocale);
		threadLocalValues.put("themeDisplayLocale", _themeDisplayLocale);

		return threadLocalValues;
	}

	protected void resetThreadLocals() {
		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);

		CentralizedThreadLocal.clearShortLivedThreadLocals();
	}

	protected BackgroundTaskThreadLocalManagerImpl
		backgroundTaskThreadLocalManagerImpl;

	private boolean _clusterInvokeEnabled;
	private long _companyId;
	private Locale _defaultLocale;
	private long _groupId;
	private String _principalName;
	private Locale _siteDefaultLocale;
	private Locale _themeDisplayLocale;

}