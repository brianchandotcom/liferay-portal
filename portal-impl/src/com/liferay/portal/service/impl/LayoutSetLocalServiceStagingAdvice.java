/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ColorSchemeFactoryUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ThemeFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.LayoutSetStagingHandler;
import com.liferay.portal.service.LayoutSetLocalService;
import com.liferay.portal.staging.StagingAdvicesThreadLocal;
import com.liferay.portal.util.ClassLoaderUtil;

import java.io.InputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class LayoutSetLocalServiceStagingAdvice
	extends LayoutSetLocalServiceImpl implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return methodInvocation.proceed();
		}

		Method method = methodInvocation.getMethod();

		String methodName = method.getName();

		if (!_layoutSetLocalServiceStagingAdviceMethodNames.contains(
				methodName)) {

			return wrapReturnValue(methodInvocation.proceed());
		}

		Object returnValue = null;

		Object thisObject = methodInvocation.getThis();
		Object[] arguments = methodInvocation.getArguments();

		if (methodName.equals("updateLayoutSetPrototypeLinkEnabled") &&
			(arguments.length == 5)) {

			updateLayoutSetPrototypeLinkEnabled(
				(LayoutSetLocalService)thisObject, (Long)arguments[0],
				(Boolean)arguments[1], (Boolean)arguments[2],
				(String)arguments[3]);
		}
		else if (methodName.equals("updateLogo") && (arguments.length == 5)) {
			updateLogo(
				(LayoutSetLocalService)thisObject, (Long)arguments[0],
				(Boolean)arguments[1], (Boolean)arguments[2],
				(InputStream)arguments[3], (Boolean)arguments[4]);
		}
		else if (methodName.equals("updateLookAndFeel") &&
				 (arguments.length == 6)) {

			returnValue = updateLookAndFeel(
				(LayoutSetLocalService)thisObject, (Long)arguments[0],
				(Boolean)arguments[1], (String)arguments[2],
				(String)arguments[3], (String)arguments[4],
				(Boolean)arguments[5]);
		}
		else if (methodName.equals("updateSettings")) {
			returnValue = updateSettings(
				(LayoutSetLocalService)thisObject, (Long)arguments[0],
				(Boolean)arguments[1], (String)arguments[2]);
		}
		else {
			try {
				Class<?> clazz = getClass();

				Class<?>[] parameterTypes = ArrayUtil.append(
					new Class<?>[] {LayoutSetLocalService.class},
					method.getParameterTypes());

				Method layoutSetLocalServiceStagingAdviceMethod =
					clazz.getMethod(methodName, parameterTypes);

				arguments = ArrayUtil.append(
					new Object[] {thisObject}, arguments);

				returnValue = layoutSetLocalServiceStagingAdviceMethod.invoke(
					this, arguments);
			}
			catch (InvocationTargetException ite) {
				throw ite.getTargetException();
			}
			catch (NoSuchMethodException nsme) {
				returnValue = methodInvocation.proceed();
			}
		}

		return wrapReturnValue(returnValue);
	}

	public void updateLayoutSetPrototypeLinkEnabled(
			LayoutSetLocalService layoutSetLocalService, long groupId,
			boolean privateLayout, boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet = wrapLayoutSet(layoutSet);

		LayoutSetBranch layoutSetBranch = LayoutStagingUtil.getLayoutSetBranch(
			layoutSet);

		if (layoutSetBranch == null) {
			layoutSetLocalService.updateLayoutSetPrototypeLinkEnabled(
				groupId, privateLayout, layoutSetPrototypeLinkEnabled,
				layoutSetPrototypeUuid);

			return;
		}

		if (Validator.isNull(layoutSetPrototypeUuid)) {
			layoutSetPrototypeUuid =
				layoutSetBranch.getLayoutSetPrototypeUuid();
		}

		if (Validator.isNull(layoutSetPrototypeUuid) &&
			layoutSetPrototypeLinkEnabled) {

			throw new IllegalStateException(
				"Cannot set layoutSetPrototypeLinkEnabled to true when " +
					"layoutSetPrototypeUuid is null");
		}

		layoutSetBranch.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);
		layoutSetBranch.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);

		layoutSetBranchPersistence.update(layoutSetBranch);
	}

	public LayoutSet updateLogo(
			LayoutSetLocalService layoutSetLocalService, long groupId,
			boolean privateLayout, boolean logo, InputStream is,
			boolean cleanUpStream)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet = wrapLayoutSet(layoutSet);

		LayoutSetBranch layoutSetBranch = LayoutStagingUtil.getLayoutSetBranch(
			layoutSet);

		if (layoutSetBranch == null) {
			return layoutSetLocalService.updateLogo(
				groupId, privateLayout, logo, is, cleanUpStream);
		}

		layoutSetBranch.setModifiedDate(new Date());
		layoutSetBranch.setLogo(logo);

		if (logo) {
			long logoId = layoutSetBranch.getLogoId();

			if (logoId <= 0) {
				logoId = counterLocalService.increment();

				layoutSet.setLogoId(logoId);
			}
		}
		else {
			layoutSet.setLogoId(0);
		}

		layoutSetBranchPersistence.update(layoutSetBranch);

		if (logo) {
			imageLocalService.updateImage(
				layoutSetBranch.getLogoId(), is, cleanUpStream);
		}
		else {
			imageLocalService.deleteImage(layoutSetBranch.getLogoId());
		}

		return layoutSet;
	}

	public LayoutSet updateLookAndFeel(
			LayoutSetLocalService target, long groupId, boolean privateLayout,
			String themeId, String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet = wrapLayoutSet(layoutSet);

		LayoutSetBranch layoutSetBranch = LayoutStagingUtil.getLayoutSetBranch(
			layoutSet);

		if (layoutSetBranch == null) {
			return target.updateLookAndFeel(
				groupId, privateLayout, themeId, colorSchemeId, css, wapTheme);
		}

		layoutSetBranch.setModifiedDate(new Date());

		if (Validator.isNull(themeId)) {
			themeId = ThemeFactoryUtil.getDefaultRegularThemeId(
				layoutSetBranch.getCompanyId());
		}

		if (Validator.isNull(colorSchemeId)) {
			colorSchemeId =
				ColorSchemeFactoryUtil.getDefaultRegularColorSchemeId();
		}

		if (wapTheme) {
			layoutSetBranch.setWapThemeId(themeId);
			layoutSetBranch.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layoutSetBranch.setThemeId(themeId);
			layoutSetBranch.setColorSchemeId(colorSchemeId);
			layoutSetBranch.setCss(css);
		}

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	public LayoutSet updateSettings(
			LayoutSetLocalService target, long groupId, boolean privateLayout,
			String settings)
		throws PortalException, SystemException {

		LayoutSet layoutSet = layoutSetPersistence.findByG_P(
			groupId, privateLayout);

		layoutSet = wrapLayoutSet(layoutSet);

		LayoutSetBranch layoutSetBranch = LayoutStagingUtil.getLayoutSetBranch(
			layoutSet);

		if (layoutSetBranch == null) {
			return target.updateSettings(groupId, privateLayout, settings);
		}

		layoutSetBranch.setModifiedDate(new Date());
		layoutSetBranch.setSettings(settings);

		layoutSetBranchPersistence.update(layoutSetBranch);

		return layoutSet;
	}

	protected LayoutSet unwrapLayoutSet(LayoutSet layoutSet) {
		LayoutSetStagingHandler layoutSetStagingHandler =
			LayoutStagingUtil.getLayoutSetStagingHandler(layoutSet);

		if (layoutSetStagingHandler == null) {
			return layoutSet;
		}

		return layoutSetStagingHandler.getLayoutSet();
	}

	protected LayoutSet wrapLayoutSet(LayoutSet layoutSet) {
		LayoutSetStagingHandler layoutSetStagingHandler =
			LayoutStagingUtil.getLayoutSetStagingHandler(layoutSet);

		if (layoutSetStagingHandler != null) {
			return layoutSet;
		}

		Group group = null;

		try {
			group = layoutSet.getGroup();
		}
		catch (Exception e) {
			return layoutSet;
		}

		if (!LayoutStagingUtil.isBranchingLayoutSet(
				group, layoutSet.getPrivateLayout())) {

			return layoutSet;
		}

		return (LayoutSet)ProxyUtil.newProxyInstance(
			ClassLoaderUtil.getPortalClassLoader(),
			new Class[] {LayoutSet.class},
			new LayoutSetStagingHandler(layoutSet));
	}

	protected List<LayoutSet> wrapLayoutSets(List<LayoutSet> layoutSets) {
		if (layoutSets.isEmpty()) {
			return layoutSets;
		}

		List<LayoutSet> wrappedLayoutSets = new ArrayList<LayoutSet>(
			layoutSets.size());

		for (int i = 0; i < layoutSets.size(); i++) {
			LayoutSet wrappedLayoutSet = wrapLayoutSet(layoutSets.get(i));

			wrappedLayoutSets.add(wrappedLayoutSet);
		}

		return wrappedLayoutSets;
	}

	protected Object wrapReturnValue(Object returnValue) {
		if (returnValue instanceof LayoutSet) {
			returnValue = wrapLayoutSet((LayoutSet)returnValue);
		}
		else if (returnValue instanceof List<?>) {
			List<?> list = (List<?>)returnValue;

			if (!list.isEmpty() && (list.get(0) instanceof LayoutSet)) {
				returnValue = wrapLayoutSets((List<LayoutSet>)returnValue);
			}
		}

		return returnValue;
	}

	private static Set<String> _layoutSetLocalServiceStagingAdviceMethodNames =
		new HashSet<String>();

	static {
		_layoutSetLocalServiceStagingAdviceMethodNames.add(
			"updateLayoutSetPrototypeLinkEnabled");
		_layoutSetLocalServiceStagingAdviceMethodNames.add("updateLogo");
		_layoutSetLocalServiceStagingAdviceMethodNames.add("updateLookAndFeel");
		_layoutSetLocalServiceStagingAdviceMethodNames.add("updateSettings");
	}

}