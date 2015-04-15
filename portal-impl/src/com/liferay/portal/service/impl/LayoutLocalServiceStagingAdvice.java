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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.LayoutStagingUtil;
import com.liferay.portal.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntry;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutStagingHandler;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.SystemEventLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.staging.ProxiedLayoutsThreadLocal;
import com.liferay.portal.staging.StagingAdvicesThreadLocal;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PortalUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
public class LayoutLocalServiceStagingAdvice implements MethodInterceptor {

	public LayoutLocalServiceStagingAdvice() {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + hashCode());
		}
	}

	public void deleteLayout(
			LayoutLocalService layoutLocalService, Layout layout,
			boolean updateLayoutSet, ServiceContext serviceContext)
		throws PortalException {

		long layoutSetBranchId = ParamUtil.getLong(
			serviceContext, "layoutSetBranchId");

		if (layoutSetBranchId > 0) {
			LayoutRevisionLocalServiceUtil.deleteLayoutRevisions(
				layoutSetBranchId, layout.getPlid());

			List<LayoutRevision> notIncompleteLayoutRevisions =
				LayoutRevisionUtil.findByP_NotS(
					layout.getPlid(), WorkflowConstants.STATUS_INCOMPLETE);

			if (notIncompleteLayoutRevisions.isEmpty()) {
				LayoutRevisionLocalServiceUtil.deleteLayoutLayoutRevisions(
					layout.getPlid());

				doDeleteLayout(
					layoutLocalService, layout, updateLayoutSet,
					serviceContext);
			}
		}
		else {
			doDeleteLayout(
				layoutLocalService, layout, updateLayoutSet, serviceContext);
		}
	}

	public void deleteLayout(
			LayoutLocalService layoutLocalService, long groupId, long layoutId,
			ServiceContext serviceContext)
		throws PortalException {

		Layout layout = layoutLocalService.getLayout(groupId, layoutId);

		deleteLayout(layoutLocalService, layout, true, serviceContext);
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		if (!StagingAdvicesThreadLocal.isEnabled()) {
			return methodInvocation.proceed();
		}

		Method method = methodInvocation.getMethod();

		String methodName = method.getName();

		boolean showIncomplete = false;

		if (!_layoutLocalServiceStagingAdviceMethodNames.contains(methodName)) {
			return wrapReturnValue(methodInvocation.proceed(), showIncomplete);
		}

		Object returnValue = null;

		Class<?>[] parameterTypes = method.getParameterTypes();

		Object thisObject = methodInvocation.getThis();
		Object[] arguments = methodInvocation.getArguments();

		if (methodName.equals("createLayout")) {
			return methodInvocation.proceed();
		}
		else if (methodName.equals("deleteLayout")) {
			if (arguments.length == 3) {
				deleteLayout(
					(LayoutLocalService)thisObject, (Layout)arguments[0],
					(Boolean)arguments[1], (ServiceContext)arguments[2]);
			}
			else if (arguments.length == 4) {
				deleteLayout(
					(LayoutLocalService)thisObject, (Long)arguments[0],
					(Long)arguments[2], (ServiceContext)arguments[3]);
			}
			else {
				return wrapReturnValue(
					methodInvocation.proceed(), showIncomplete);
			}
		}
		else if (methodName.equals("getLayouts")) {
			if (arguments.length == 6) {
				showIncomplete = (Boolean)arguments[3];
			}
			else if (Arrays.equals(parameterTypes, _GET_LAYOUTS_TYPES)) {
				showIncomplete = true;
			}

			return wrapReturnValue(methodInvocation.proceed(), showIncomplete);
		}
		else if (methodName.equals("updateLayout") &&
				 (arguments.length == 15)) {

			Map<Locale, String> friendlyURLMap = null;

			if (Arrays.equals(parameterTypes, _UPDATE_LAYOUT_PARAMETER_TYPES)) {
				friendlyURLMap = new HashMap<>();

				friendlyURLMap.put(
					LocaleUtil.getSiteDefault(), (String)arguments[11]);
			}
			else {
				friendlyURLMap = (Map<Locale, String>)arguments[11];
			}

			returnValue = updateLayout(
				(LayoutLocalService)thisObject, (Long)arguments[0],
				(Long)arguments[2], (Long)arguments[3],
				(Map<Locale, String>)arguments[4],
				(Map<Locale, String>)arguments[5],
				(Map<Locale, String>)arguments[6],
				(Map<Locale, String>)arguments[7],
				(Map<Locale, String>)arguments[8], (String)arguments[9],
				(Boolean)arguments[10], friendlyURLMap, (Boolean)arguments[12],
				(byte[])arguments[13], (ServiceContext)arguments[14]);
		}
		else {
			try {
				Class<?> clazz = getClass();

				parameterTypes = ArrayUtil.append(
					new Class<?>[] {LayoutLocalService.class}, parameterTypes);

				Method layoutLocalServiceStagingAdviceMethod = clazz.getMethod(
					methodName, parameterTypes);

				arguments = ArrayUtil.append(
					new Object[] {thisObject}, arguments);

				returnValue = layoutLocalServiceStagingAdviceMethod.invoke(
					this, arguments);
			}
			catch (InvocationTargetException ite) {
				throw ite.getTargetException();
			}
			catch (NoSuchMethodException nsme) {
				returnValue = methodInvocation.proceed();
			}
		}

		returnValue = wrapReturnValue(returnValue, showIncomplete);

		return returnValue;
	}

	public Layout updateLayout(
			LayoutLocalService layoutLocalService, long groupId, long layoutId,
			long parentLayoutId, Map<Locale, String> nameMap,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			Map<Locale, String> keywordsMap, Map<Locale, String> robotsMap,
			String type, boolean hidden, Map<Locale, String> friendlyURLMap,
			boolean iconImage, byte[] iconBytes, ServiceContext serviceContext)
		throws PortalException {

		// Layout

		parentLayoutId = layoutLocalServiceHelper.getParentLayoutId(
			groupId, parentLayoutId);
		String name = nameMap.get(LocaleUtil.getSiteDefault());
		friendlyURLMap = layoutLocalServiceHelper.getFriendlyURLMap(
			groupId, layoutId, StringPool.BLANK, friendlyURLMap);
		String friendlyURL = friendlyURLMap.get(LocaleUtil.getSiteDefault());

		layoutLocalServiceHelper.validate(
			groupId, layoutId, parentLayoutId, name, type, hidden,
			friendlyURLMap);

		layoutLocalServiceHelper.validateParentLayoutId(
			groupId, layoutId, parentLayoutId);

		Layout originalLayout = LayoutUtil.findByG_L(groupId, layoutId);

		Layout layout = wrapLayout(originalLayout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateLayout(
				groupId, layoutId, parentLayoutId, nameMap, titleMap,
				descriptionMap, keywordsMap, robotsMap, type, hidden,
				friendlyURLMap, iconImage, iconBytes, serviceContext);
		}

		if (parentLayoutId != originalLayout.getParentLayoutId()) {
			int priority = layoutLocalServiceHelper.getNextPriority(
				groupId, parentLayoutId,
				originalLayout.getSourcePrototypeLayoutUuid(), -1);

			originalLayout.setPriority(priority);
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

		PortalUtil.updateImageId(
			layoutRevision, iconImage, iconBytes, "iconImageId", 0, 0, 0);

		boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
			serviceContext, "layoutPrototypeLinkEnabled");

		originalLayout.setLayoutPrototypeLinkEnabled(
			layoutPrototypeLinkEnabled);

		originalLayout.setExpandoBridgeAttributes(serviceContext);

		LayoutUtil.update(originalLayout);

		LayoutFriendlyURLLocalServiceUtil.updateLayoutFriendlyURLs(
			originalLayout.getUserId(), originalLayout.getCompanyId(),
			originalLayout.getGroupId(), originalLayout.getPlid(),
			friendlyURLMap, serviceContext);

		boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
			serviceContext.getUserId(), layoutRevision);

		serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		LayoutRevisionLocalServiceUtil.updateLayoutRevision(
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

	public Layout updateLayout(
			LayoutLocalService layoutLocalService, long groupId, long layoutId,
			String typeSettings)
		throws PortalException {

		Layout layout = LayoutUtil.findByG_L(groupId, layoutId);

		layout = wrapLayout(layout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateLayout(
				groupId, layoutId, typeSettings);
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

		LayoutRevisionLocalServiceUtil.updateLayoutRevision(
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

	public Layout updateLookAndFeel(
			LayoutLocalService layoutLocalService, long groupId, long layoutId,
			String themeId, String colorSchemeId, String css, boolean wapTheme)
		throws PortalException {

		Layout layout = LayoutUtil.findByG_L(groupId, layoutId);

		layout = wrapLayout(layout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateLookAndFeel(
				groupId, layoutId, themeId, colorSchemeId, css, wapTheme);
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

		LayoutRevisionLocalServiceUtil.updateLayoutRevision(
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

	public Layout updateName(
			LayoutLocalService layoutLocalService, Layout layout, String name,
			String languageId)
		throws PortalException {

		layout = wrapLayout(layout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateName(layout, name, languageId);
		}

		layoutLocalServiceHelper.validateName(name, languageId);

		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
			serviceContext.getUserId(), layoutRevision);

		serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		LayoutRevisionLocalServiceUtil.updateLayoutRevision(
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

	protected void doDeleteLayout(
			LayoutLocalService layoutLocalService, Layout layout,
			boolean updateLayoutSet, ServiceContext serviceContext)
		throws PortalException {

		if (SystemEventHierarchyEntryThreadLocal.push(
				Layout.class, layout.getPlid()) == null) {

			layoutLocalService.deleteLayout(
				layout, updateLayoutSet, serviceContext);
		}
		else {
			try {
				layoutLocalService.deleteLayout(
					layout, updateLayoutSet, serviceContext);

				SystemEventHierarchyEntry systemEventHierarchyEntry =
					SystemEventHierarchyEntryThreadLocal.peek();

				SystemEventLocalServiceUtil.addSystemEvent(
					0, layout.getGroupId(), Layout.class.getName(),
					layout.getPlid(), layout.getUuid(), null,
					SystemEventConstants.TYPE_DELETE,
					systemEventHierarchyEntry.getExtraData());
			}
			finally {
				SystemEventHierarchyEntryThreadLocal.pop(
					Layout.class, layout.getPlid());
			}
		}
	}

	protected Layout getProxiedLayout(Layout layout) {
		Map<Layout, Object> proxiedLayouts =
			ProxiedLayoutsThreadLocal.getProxiedLayouts();

		Object proxiedLayout = proxiedLayouts.get(layout);

		if (proxiedLayout != null) {
			return (Layout)proxiedLayout;
		}

		proxiedLayout = ProxyUtil.newProxyInstance(
			ClassLoaderUtil.getPortalClassLoader(), new Class[] {Layout.class},
			new LayoutStagingHandler(layout));

		proxiedLayouts.put(layout, proxiedLayout);

		return (Layout)proxiedLayout;
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

		return getProxiedLayout(layout);
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
			long userId = 0;

			try {
				userId = GetterUtil.getLong(PrincipalThreadLocal.getName());

				if (userId > 0) {
					User user = UserLocalServiceUtil.getUser(userId);

					LayoutSet layoutSet = firstLayout.getLayoutSet();

					layoutSetBranchId = StagingUtil.getRecentLayoutSetBranchId(
						user, layoutSet.getLayoutSetId());
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("No layout set branch found for user " + userId);
				}
			}
		}

		List<Layout> wrappedLayouts = new ArrayList<>(layouts.size());

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
			List<?> list = (List<?>)returnValue;

			if (!list.isEmpty()) {
				Object object = list.get(0);

				if (object instanceof Layout) {
					returnValue = wrapLayouts(
						(List<Layout>)returnValue, showIncomplete);
				}
			}
		}

		return returnValue;
	}

	@BeanReference(type = LayoutLocalServiceHelper.class)
	protected LayoutLocalServiceHelper layoutLocalServiceHelper;

	private static final Class<?>[] _GET_LAYOUTS_TYPES = {
		Long.TYPE, Boolean.TYPE, Long.TYPE
	};

	private static final Class<?>[] _UPDATE_LAYOUT_PARAMETER_TYPES = {
		long.class, boolean.class, long.class, long.class, Map.class, Map.class,
		Map.class, Map.class, Map.class, String.class, boolean.class,
		String.class, Boolean.class, byte[].class, ServiceContext.class
	};

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutLocalServiceStagingAdvice.class);

	private static final Set<String>
		_layoutLocalServiceStagingAdviceMethodNames = new HashSet<>();

	static {
		_layoutLocalServiceStagingAdviceMethodNames.add("createLayout");
		_layoutLocalServiceStagingAdviceMethodNames.add("deleteLayout");
		_layoutLocalServiceStagingAdviceMethodNames.add("getLayouts");
		_layoutLocalServiceStagingAdviceMethodNames.add("updateLayout");
		_layoutLocalServiceStagingAdviceMethodNames.add("updateLookAndFeel");
		_layoutLocalServiceStagingAdviceMethodNames.add("updateName");
	}

}