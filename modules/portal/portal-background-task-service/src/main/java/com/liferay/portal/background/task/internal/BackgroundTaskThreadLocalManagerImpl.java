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

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocalManager;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalService;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BackgroundTaskThreadLocalManager.class)
public class BackgroundTaskThreadLocalManagerImpl
	implements BackgroundTaskThreadLocalManager {

	@Override
	public void deserializeThreadLocals(
		Map<String, Serializable> taskContextMap) {

		Map<String, Serializable> threadLocalValues =
			(Map<String, Serializable>)taskContextMap.get(
				KEY_THREAD_LOCAL_VALUES);

		setThreadLocalValues(threadLocalValues);
	}

	@Override
	public Map<String, Serializable> getThreadLocalValues() {
		Map<String, Serializable> threadLocalValues = new HashMap<>();

		threadLocalValues.put("companyId", CompanyThreadLocal.getCompanyId());
		threadLocalValues.put(
			"clusterInvoke", ClusterInvokeThreadLocal.isEnabled());
		threadLocalValues.put(
			"defaultLocale", LocaleThreadLocal.getDefaultLocale());
		threadLocalValues.put("groupId", GroupThreadLocal.getGroupId());
		threadLocalValues.put("principalName", PrincipalThreadLocal.getName());
		threadLocalValues.put(
			"siteDefaultLocale", LocaleThreadLocal.getSiteDefaultLocale());
		threadLocalValues.put(
			"themeDisplayLocale", LocaleThreadLocal.getThemeDisplayLocale());

		return threadLocalValues;
	}

	@Override
	public void serializeThreadLocals(
		Map<String, Serializable> taskContextMap) {

		HashMap<String, Serializable> taskContextThreadLocalValues =
			(HashMap<String, Serializable>)taskContextMap.get(
				KEY_THREAD_LOCAL_VALUES);

		if (taskContextThreadLocalValues == null) {
			taskContextThreadLocalValues = new HashMap<>();

			taskContextMap.put(
				KEY_THREAD_LOCAL_VALUES, taskContextThreadLocalValues);
		}

		Map<String, Serializable> currentThreadLocalValues =
			getThreadLocalValues();

		taskContextThreadLocalValues.putAll(currentThreadLocalValues);
	}

	@Override
	public void setThreadLocalValues(
		Map<String, Serializable> threadLocalValues) {

		if (MapUtil.isEmpty(threadLocalValues)) {
			return;
		}

		long companyId = GetterUtil.getLong(threadLocalValues.get("companyId"));

		if (companyId > 0) {
			CompanyThreadLocal.setCompanyId(companyId);
		}

		Boolean clusterInvoke = (Boolean)threadLocalValues.get("clusterInvoke");

		if (clusterInvoke != null) {
			ClusterInvokeThreadLocal.setEnabled(clusterInvoke);
		}

		Locale defaultLocale = (Locale)threadLocalValues.get("defaultLocale");

		if (defaultLocale != null) {
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
		}

		long groupId = GetterUtil.getLong(threadLocalValues.get("groupId"));

		if (groupId > 0) {
			GroupThreadLocal.setGroupId(groupId);
		}

		String principalName = GetterUtil.getString(
			threadLocalValues.get("principalName"));

		if (Validator.isNotNull(principalName)) {
			PrincipalThreadLocal.setName(principalName);
		}

		if (Validator.isNotNull(principalName)) {
			try {
				User user = _userLocalService.fetchUser(
					PrincipalThreadLocal.getUserId());

				PermissionChecker permissionChecker =
					_permissionCheckerFactory.create(user);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		Locale siteDefaultLocale = (Locale)threadLocalValues.get(
			"siteDefaultLocale");

		if (siteDefaultLocale != null) {
			LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);
		}

		Locale themeDisplayLocale = (Locale)threadLocalValues.get(
			"themeDisplayLocale");

		if (themeDisplayLocale != null) {
			LocaleThreadLocal.setThemeDisplayLocale(themeDisplayLocale);
		}
	}

	@Reference(unbind = "-")
	protected void setPermissionCheckerFactory(
		PermissionCheckerFactory permissionCheckerFactory) {

		_permissionCheckerFactory = permissionCheckerFactory;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected static final String KEY_THREAD_LOCAL_VALUES = "threadLocalValues";

	private volatile PermissionCheckerFactory _permissionCheckerFactory;
	private volatile UserLocalService _userLocalService;

}