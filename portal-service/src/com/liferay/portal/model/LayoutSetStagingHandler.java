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

package com.liferay.portal.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class LayoutSetStagingHandler
	implements InvocationHandler, Serializable {

	public LayoutSetStagingHandler(LayoutSet layoutSet) {
		_layoutSet = layoutSet;

		try {
			_layoutSetBranch = _getLayoutSetBranch(layoutSet);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException(e);
		}
	}

	public LayoutSet getLayoutSet() {
		return _layoutSet;
	}

	public LayoutSetBranch getLayoutSetBranch() {
		return _layoutSetBranch;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			if (_layoutSetBranch == null) {
				return method.invoke(_layoutSet, arguments);
			}

			String methodName = method.getName();

			if (methodName.equals("toEscapedModel")) {
				if (_layoutSet.isEscapedModel()) {
					return this;
				}

				return _toEscapedModel();
			}

			if (methodName.equals("clone")) {
				return _clone();
			}

			Object bean = _layoutSet;

			if (_layoutSetBranchMethodNames.contains(methodName)) {
				try {
					Class<?> layoutSetBranchClass = _layoutSetBranch.getClass();

					method = layoutSetBranchClass.getMethod(
						methodName,
						ReflectionUtil.getParameterTypes(arguments));

					bean = _layoutSetBranch;
				}
				catch (NoSuchMethodException nsme) {
					_log.error(nsme, nsme);
				}
			}

			return method.invoke(bean, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	public void setLayoutSetBranch(LayoutSetBranch layoutSetBranch) {
		_layoutSetBranch = layoutSetBranch;
	}

	private Object _clone() {
		return ProxyUtil.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(), new Class[] {Layout.class},
			new LayoutSetStagingHandler(_layoutSet));
	}

	private LayoutSetBranch _getLayoutSetBranch(LayoutSet layoutSet)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return null;
		}

		long layoutSetBranchId = ParamUtil.getLong(
			serviceContext, "layoutSetBranchId");

		if (layoutSetBranchId > 0) {
			return LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
				layoutSetBranchId);
		}

		if (serviceContext.isSignedIn()) {
			return LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(
				serviceContext.getUserId(), layoutSet.getGroupId(),
				layoutSet.isPrivateLayout(), layoutSet.getLayoutSetId(),
				layoutSetBranchId);
		}

		return LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout());
	}

	private Object _toEscapedModel() {
		return ProxyUtil.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(), new Class[] {Layout.class},
			new LayoutSetStagingHandler(_layoutSet.toEscapedModel()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetStagingHandler.class);

	private static final Set<String> _layoutSetBranchMethodNames =
		new HashSet<String>();

	static {
		_layoutSetBranchMethodNames.add("getColorScheme");
		_layoutSetBranchMethodNames.add("getColorSchemeId");
		_layoutSetBranchMethodNames.add("getCss");
		_layoutSetBranchMethodNames.add("getLayoutSetPrototypeLinkEnabled");
		_layoutSetBranchMethodNames.add("getLayoutSetPrototypeUuid");
		_layoutSetBranchMethodNames.add("getLogo");
		_layoutSetBranchMethodNames.add("getLogoId");
		_layoutSetBranchMethodNames.add("getSettings");
		_layoutSetBranchMethodNames.add("getTheme");
		_layoutSetBranchMethodNames.add("getThemeId");
		_layoutSetBranchMethodNames.add("getWapColorScheme");
		_layoutSetBranchMethodNames.add("getWapColorSchemeId");
		_layoutSetBranchMethodNames.add("getWapTheme");
		_layoutSetBranchMethodNames.add("getWapThemeId");
		_layoutSetBranchMethodNames.add("getSettingsProperties");
		_layoutSetBranchMethodNames.add("getSettings");
		_layoutSetBranchMethodNames.add("getStagingLogoId");
		_layoutSetBranchMethodNames.add("getThemeSetting");
		_layoutSetBranchMethodNames.add("getSettingsProperty");
		_layoutSetBranchMethodNames.add("isLayoutSetPrototypeLinkActive");
		_layoutSetBranchMethodNames.add("isEscapedModel");
		_layoutSetBranchMethodNames.add("isLogo");
		_layoutSetBranchMethodNames.add("setColorSchemeId");
		_layoutSetBranchMethodNames.add("setCss");
		_layoutSetBranchMethodNames.add("setLayoutSetPrototypeLinkEnabled");
		_layoutSetBranchMethodNames.add("setLayoutSetPrototypeUuid");
		_layoutSetBranchMethodNames.add("setEscapedModel");
		_layoutSetBranchMethodNames.add("setLogo");
		_layoutSetBranchMethodNames.add("setLogoId");
		_layoutSetBranchMethodNames.add("setSettings");
		_layoutSetBranchMethodNames.add("setSettingsProperties");
		_layoutSetBranchMethodNames.add("setThemeId");
		_layoutSetBranchMethodNames.add("setWapColorSchemeId");
		_layoutSetBranchMethodNames.add("setWapThemeId");
	}

	private final LayoutSet _layoutSet;
	private LayoutSetBranch _layoutSetBranch;

}