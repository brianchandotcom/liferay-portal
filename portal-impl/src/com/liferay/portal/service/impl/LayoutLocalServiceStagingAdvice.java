/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutStagingHandler;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.springframework.core.annotation.Order;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
@Order(1)
public class LayoutLocalServiceStagingAdvice
	extends LayoutLocalServiceImpl implements MethodInterceptor {

	@Override
	public void deleteLayout(
			Layout layout, boolean updateLayoutSet,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		long layoutSetBranchId = ParamUtil.getLong(
			serviceContext, "layoutSetBranchId");

		if (layoutSetBranchId > 0) {
			layoutRevisionLocalService.deleteLayoutRevisions(
				layoutSetBranchId, layout.getPlid());

			List<LayoutRevision> notIncompleteLayoutRevisions =
				layoutRevisionPersistence.findByP_NotS(
					layout.getPlid(), WorkflowConstants.STATUS_INCOMPLETE);

			if (notIncompleteLayoutRevisions.isEmpty()) {
				layoutRevisionLocalService.deleteLayoutLayoutRevisions(
					layout.getPlid());

				super.deleteLayout(layout, updateLayoutSet, serviceContext);
			}
		}
		else {
			super.deleteLayout(layout, updateLayoutSet, serviceContext);
		}
	}

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();

		String methodName = method.getName();

		Object[] arguments = methodInvocation.getArguments();

		boolean showIncomplete = false;

		if (!_layoutLocalServiceStagingAdviceMethodNames.contains(methodName)) {
			return wrapReturnValue(methodInvocation.proceed(), showIncomplete);
		}

		Object returnValue = null;

		if (methodName.equals("deleteLayout") && (arguments.length == 3)) {
			deleteLayout(
				(Layout)arguments[0], (Boolean)arguments[1],
				(ServiceContext)arguments[2]);
		}
		else if (methodName.equals("getLayouts")) {
			if (arguments.length == 6) {
				showIncomplete = (Boolean)arguments[3];
			}

			return wrapReturnValue(methodInvocation.proceed(), showIncomplete);
		}
		else if (methodName.equals("updateLayout") &&
				 (arguments.length == 15)) {

			returnValue = updateLayout(
				(Long)arguments[0], (Boolean)arguments[1], (Long)arguments[2],
				(Long)arguments[3], (Map<Locale, String>)arguments[4],
				(Map<Locale, String>)arguments[5],
				(Map<Locale, String>)arguments[6],
				(Map<Locale, String>)arguments[7],
				(Map<Locale, String>)arguments[8], (String)arguments[9],
				(Boolean)arguments[10], (String)arguments[11],
				(Boolean)arguments[12], (byte[])arguments[13],
				(ServiceContext)arguments[14]);
		}
		else {
			try {
				Class<?> clazz = getClass();

				Method localMethod = clazz.getMethod(
					methodName, method.getParameterTypes());

				returnValue = localMethod.invoke(this, arguments);
			}
			catch (InvocationTargetException ite) {
				throw ite.getTargetException();
			}
			catch (NoSuchMethodException nsme) {
				throw new SystemException(nsme);
			}
		}

		returnValue = wrapReturnValue(returnValue, showIncomplete);

		return returnValue;
	}

	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, boolean hidden, String friendlyURL, Boolean iconImage,
			byte[] iconBytes, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Layout

		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);
		String name = nameMap.get(LocaleUtil.getDefault());
		friendlyURL = getFriendlyURL(
			groupId, privateLayout, layoutId, StringPool.BLANK, friendlyURL);

		validate(
			groupId, privateLayout, layoutId, parentLayoutId, name, type,
			hidden, friendlyURL);

		validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Layout originalLayout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		Layout layout = wrapLayout(originalLayout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return super.updateLayout(
				groupId, privateLayout, layoutId, parentLayoutId, nameMap,
				titleMap, descriptionMap, keywordsMap, robotsMap, type, hidden,
				friendlyURL, iconImage, iconBytes, serviceContext);
		}

		if (parentLayoutId != originalLayout.getParentLayoutId()) {
			originalLayout.setPriority(
				getNextPriority(groupId, privateLayout, parentLayoutId));
		}

		originalLayout.setParentLayoutId(parentLayoutId);
		layoutRevision.setNameMap(nameMap);
		layoutRevision.setTitleMap(titleMap);
		layoutRevision.setDescriptionMap(descriptionMap);
		layoutRevision.setKeywordsMap(keywordsMap);
		layoutRevision.setRobotsMap(robotsMap);
		originalLayout.setType(type);
		originalLayout.setHidden(hidden);
		originalLayout.setFriendlyURL(friendlyURL);

		if (iconImage != null) {
			originalLayout.setIconImage(iconImage.booleanValue());

			if (iconImage.booleanValue()) {
				long iconImageId = originalLayout.getIconImageId();

				if (iconImageId <= 0) {
					iconImageId = counterLocalService.increment();

					originalLayout.setIconImageId(iconImageId);
				}
			}
		}

		layoutPersistence.update(originalLayout, false);

		boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
			serviceContext.getUserId(), layoutRevision);

		serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		layoutRevisionLocalService.updateLayoutRevision(
			serviceContext.getUserId(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
			layoutRevision.getTitle(), layoutRevision.getDescription(),
			layoutRevision.getKeywords(), layoutRevision.getRobots(),
			layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
			layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
			layoutRevision.getColorSchemeId(), layoutRevision.getWapThemeId(),
			layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		// Icon

		if (iconImage != null) {
			if ((iconBytes != null) && (iconBytes.length > 0)) {
				imageLocalService.updateImage(
					originalLayout.getIconImageId(), iconBytes);
			}
		}

		// Expando

		ExpandoBridge expandoBridge = originalLayout.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return layout;
	}

	@Override
	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layout = wrapLayout(layout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return super.updateLayout(
				groupId, privateLayout, layoutId, typeSettings);
		}

		layout.setTypeSettings(typeSettings);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
			serviceContext.getUserId(), layoutRevision);

		serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

		if (!MergeLayoutPrototypesThreadLocal.isInProgress()) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		layoutRevisionLocalService.updateLayoutRevision(
			serviceContext.getUserId(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
			layoutRevision.getTitle(), layoutRevision.getDescription(),
			layoutRevision.getKeywords(), layoutRevision.getRobots(),
			layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
			layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
			layoutRevision.getColorSchemeId(), layoutRevision.getWapThemeId(),
			layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layout;
	}

	@Override
	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layout = wrapLayout(layout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return super.updateLookAndFeel(
				groupId, privateLayout, layoutId, themeId, colorSchemeId, css,
				wapTheme);
		}

		if (wapTheme) {
			layout.setWapThemeId(themeId);
			layout.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setCss(css);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
			serviceContext.getUserId(), layoutRevision);

		serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

		if (!MergeLayoutPrototypesThreadLocal.isInProgress()) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		layoutRevisionLocalService.updateLayoutRevision(
			serviceContext.getUserId(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
			layoutRevision.getTitle(), layoutRevision.getDescription(),
			layoutRevision.getKeywords(), layoutRevision.getRobots(),
			layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
			layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
			layoutRevision.getColorSchemeId(), layoutRevision.getWapThemeId(),
			layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layout;
	}

	@Override
	public Layout updateName(Layout layout, String name, String languageId)
		throws PortalException, SystemException {

		layout = wrapLayout(layout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return super.updateName(layout, name, languageId);
		}

		validateName(name, languageId);

		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
			serviceContext.getUserId(), layoutRevision);

		serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		layoutRevisionLocalService.updateLayoutRevision(
			serviceContext.getUserId(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
			layoutRevision.getTitle(), layoutRevision.getDescription(),
			layoutRevision.getKeywords(), layoutRevision.getRobots(),
			layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
			layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
			layoutRevision.getColorSchemeId(), layoutRevision.getWapThemeId(),
			layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layout;
	}

	protected Layout unwrapLayout(Layout layout) {
		LayoutStagingHandler layoutStagingHandler =
			LayoutStagingUtil.getLayoutStagingHandler(layout);

		if (layoutStagingHandler == null) {
			return layout;
		}

		return layoutStagingHandler.getLayout();
	}

	protected Layout wrapLayout(Layout layout) {
		LayoutStagingHandler layoutStagingHandler =
			LayoutStagingUtil.getLayoutStagingHandler(layout);

		if (layoutStagingHandler != null) {
			return layout;
		}

		if (!LayoutStagingUtil.isBranchingLayout(layout)) {
			return layout;
		}

		return (Layout)ProxyUtil.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(), new Class[] {Layout.class},
			new LayoutStagingHandler(layout));
	}

	protected List<Layout> wrapLayouts(
		List<Layout> layouts, boolean showIncomplete) {

		if (layouts.isEmpty()) {
			return layouts;
		}

		Layout firstLayout = layouts.get(0);

		Layout wrappedFirstLayout = wrapLayout(firstLayout);

		if (wrappedFirstLayout == firstLayout) {
			return layouts;
		}

		long layoutSetBranchId = 0;

		if (!showIncomplete) {
			try {
				long userId = GetterUtil.getLong(
					PrincipalThreadLocal.getName());

				if (userId > 0) {
					User user = UserLocalServiceUtil.getUser(userId);

					LayoutSet layoutSet = firstLayout.getLayoutSet();

					layoutSetBranchId = StagingUtil.getRecentLayoutSetBranchId(
						user, layoutSet.getLayoutSetId());
				}
			}
			catch (Exception e) {
			}
		}

		List<Layout> wrappedLayouts = new ArrayList<Layout>(layouts.size());

		for (int i = 0; i < layouts.size(); i++) {
			Layout wrappedLayout = wrapLayout(layouts.get(i));

			if (showIncomplete ||
				!StagingUtil.isIncomplete(wrappedLayout, layoutSetBranchId)) {

				wrappedLayouts.add(wrappedLayout);
			}
		}

		return wrappedLayouts;
	}

	protected Object wrapReturnValue(
		Object returnValue, boolean showIncomplete) {

		if (returnValue instanceof Layout) {
			returnValue = wrapLayout((Layout)returnValue);
		}
		else if (returnValue instanceof List<?>) {
			returnValue = wrapLayouts(
				(List<Layout>)returnValue, showIncomplete);
		}

		return returnValue;
	}

	private static Set<String> _layoutLocalServiceStagingAdviceMethodNames =
		new HashSet<String>();

	static {
		_layoutLocalServiceStagingAdviceMethodNames.add("deleteLayout");
		_layoutLocalServiceStagingAdviceMethodNames.add("getLayouts");
		_layoutLocalServiceStagingAdviceMethodNames.add("updateLayout");
		_layoutLocalServiceStagingAdviceMethodNames.add("updateLookAndFeel");
		_layoutLocalServiceStagingAdviceMethodNames.add("updateName");
	}

}