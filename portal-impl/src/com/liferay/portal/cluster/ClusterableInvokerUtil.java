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

package com.liferay.portal.cluster;

import com.liferay.portal.bean.IdentifiableOSGIServiceInvokerUtil;
import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterableContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Locale;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class ClusterableInvokerUtil {

	public static MethodHandler createMethodHandler(
		Class<? extends ClusterInvokeAcceptor> clusterInvokeAcceptorClass,
		Object targetObject, Method method, Object[] args) {

		MethodHandler methodHandler =
			IdentifiableOSGIServiceInvokerUtil.createMethodHandler(
				targetObject, method, args);

		Map<String, Serializable> context =
			ClusterableContextThreadLocal.collectThreadLocalContext();

		_populateContextFromThreadLocals(context);

		String clusterInvokeAcceptorClassName =
			clusterInvokeAcceptorClass.getName();

		if (clusterInvokeAcceptorClass == ClusterInvokeAcceptor.class) {
			clusterInvokeAcceptorClassName = null;
		}

		return new MethodHandler(
			_invokeMethodKey, methodHandler, clusterInvokeAcceptorClassName,
			context);
	}

	@SuppressWarnings("unused")
	private static Object _invoke(
			MethodHandler methodHandler, String clusterInvokeAcceptorClassName,
			Map<String, Serializable> context)
		throws Exception {

		if (Validator.isNotNull(clusterInvokeAcceptorClassName)) {
			ClusterInvokeAcceptor clusterInvokeAcceptor =
				(ClusterInvokeAcceptor)InstanceFactory.newInstance(
					clusterInvokeAcceptorClassName);

			if (!clusterInvokeAcceptor.accept(context)) {
				return null;
			}
		}

		_populateThreadLocalsFromContext(context);

		return methodHandler.invoke();
	}

	private static void _populateContextFromThreadLocals(
		Map<String, Serializable> context) {

		if (!context.containsKey("companyId")) {
			context.put("companyId", CompanyThreadLocal.getCompanyId());
		}

		if (!context.containsKey("defaultLocale")) {
			context.put("defaultLocale", LocaleThreadLocal.getDefaultLocale());
		}

		if (!context.containsKey("groupId")) {
			context.put("groupId", GroupThreadLocal.getGroupId());
		}

		if (!context.containsKey("principalName")) {
			context.put("principalName", PrincipalThreadLocal.getName());
		}

		if (!context.containsKey("principalPassword")) {
			context.put(
				"principalPassword", PrincipalThreadLocal.getPassword());
		}

		if (!context.containsKey("siteDefaultLocale")) {
			context.put(
				"siteDefaultLocale", LocaleThreadLocal.getSiteDefaultLocale());
		}

		if (!context.containsKey("themeDisplayLocale")) {
			context.put(
				"themeDisplayLocale",
				LocaleThreadLocal.getThemeDisplayLocale());
		}
	}

	private static void _populateThreadLocalsFromContext(
		Map<String, Serializable> context) {

		long companyId = GetterUtil.getLong(context.get("companyId"));

		if (companyId > 0) {
			CompanyThreadLocal.setCompanyId(companyId);
		}

		Locale defaultLocale = (Locale)context.get("defaultLocale");

		if (defaultLocale != null) {
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
		}

		long groupId = GetterUtil.getLong(context.get("groupId"));

		if (groupId > 0) {
			GroupThreadLocal.setGroupId(groupId);
		}

		String principalName = GetterUtil.getString(
			context.get("principalName"));

		if (Validator.isNotNull(principalName)) {
			PrincipalThreadLocal.setName(principalName);
		}

		PermissionChecker permissionChecker = null;

		if (Validator.isNotNull(principalName)) {
			try {
				User user = UserLocalServiceUtil.fetchUser(
					PrincipalThreadLocal.getUserId());

				permissionChecker = PermissionCheckerFactoryUtil.create(user);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if (permissionChecker != null) {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}

		String principalPassword = GetterUtil.getString(
			context.get("principalPassword"));

		if (Validator.isNotNull(principalPassword)) {
			PrincipalThreadLocal.setPassword(principalPassword);
		}

		Locale siteDefaultLocale = (Locale)context.get("siteDefaultLocale");

		if (siteDefaultLocale != null) {
			LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);
		}

		Locale themeDisplayLocale = (Locale)context.get("themeDisplayLocale");

		if (themeDisplayLocale != null) {
			LocaleThreadLocal.setThemeDisplayLocale(themeDisplayLocale);
		}
	}

	private static final MethodKey _invokeMethodKey = new MethodKey(
		ClusterableInvokerUtil.class, "_invoke", MethodHandler.class,
		String.class, Map.class);

}