/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocalManager;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = BackgroundTaskThreadLocalManager.class)
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

		threadLocalValues.put(
			"clusterInvoke", ClusterInvokeThreadLocal.isEnabled());
		threadLocalValues.put("companyId", CompanyThreadLocal.getCompanyId());
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
			CompanyThreadLocal.setCompanyId(_requireCompany(companyId));
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
			Group group = _groupLocalService.fetchGroup(groupId);

			if ((group != null) && (group.getCompanyId() == companyId)) {
				GroupThreadLocal.setGroupId(groupId);
			}
		}

		String principalName = GetterUtil.getString(
			threadLocalValues.get("principalName"));

		if (Validator.isNotNull(principalName)) {
			User user = _userLocalService.fetchUser(
				GetterUtil.getLong(principalName));

			if ((user != null) && (user.getCompanyId() == companyId)) {
				PrincipalThreadLocal.setName(principalName);

				PermissionThreadLocal.setPermissionChecker(
					_permissionCheckerFactory.create(user));
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

	protected static final String KEY_THREAD_LOCAL_VALUES = "threadLocalValues";

	@Reference
	protected CompanyLocalService companyLocalService;

	private long _requireCompany(long companyId) {
		Company company = companyLocalService.fetchCompany(companyId);

		if (company != null) {
			return companyId;
		}

		throw new StaleBackgroundTaskException(
			"Unable to find company " + companyId);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Reference
	private UserLocalService _userLocalService;

}