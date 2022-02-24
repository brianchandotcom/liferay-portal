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

import com.liferay.exportimport.kernel.staging.LayoutStagingUtil;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutStagingHandler;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.SystemEventLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionUtil;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntry;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.aop.AopInvocationHandler;
import com.liferay.portlet.exportimport.staging.ProxiedLayoutsThreadLocal;
import com.liferay.portlet.exportimport.staging.StagingAdvicesThreadLocal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutLocalServiceStagingAdvice implements BeanFactoryAware {

	public LayoutLocalServiceStagingAdvice() {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + hashCode());
		}
	}

	public void afterPropertiesSet() throws BeansException {
		AopInvocationHandler aopInvocationHandler =
			ProxyUtil.fetchInvocationHandler(
				_beanFactory.getBean(LayoutLocalService.class.getName()),
				AopInvocationHandler.class);

		aopInvocationHandler.setTarget(
			ProxyUtil.newProxyInstance(
				LayoutLocalServiceStagingAdvice.class.getClassLoader(),
				new Class<?>[] {
					IdentifiableOSGiService.class, LayoutLocalService.class,
					BaseLocalService.class
				},
				new LayoutLocalServiceStagingInvocationHandler(
					this, aopInvocationHandler.getTarget())));

		layoutLocalServiceHelper =
			(LayoutLocalServiceHelper)_beanFactory.getBean(
				LayoutLocalServiceHelper.class.getName());
	}

	public void deleteLayout(
			LayoutLocalService layoutLocalService, Layout layout,
			ServiceContext serviceContext)
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

				doDeleteLayout(layoutLocalService, layout, serviceContext);
			}
		}
		else {
			doDeleteLayout(layoutLocalService, layout, serviceContext);
		}
	}

	public void deleteLayout(
			LayoutLocalService layoutLocalService, long groupId,
			boolean privateLayout, long layoutId, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		deleteLayout(layoutLocalService, layout, serviceContext);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		_beanFactory = beanFactory;
	}

	public Layout updateLayout(
			LayoutLocalService layoutLocalService, long groupId,
			boolean privateLayout, long layoutId, long parentLayoutId,
			Map<Locale, String> nameMap, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> keywordsMap,
			Map<Locale, String> robotsMap, String type, boolean hidden,
			Map<Locale, String> friendlyURLMap, boolean hasIconImage,
			byte[] iconBytes, long masterLayoutPlid, long styleBookEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout

		parentLayoutId = layoutLocalServiceHelper.getParentLayoutId(
			groupId, privateLayout, parentLayoutId);

		String name = nameMap.get(LocaleUtil.getSiteDefault());

		Map<Locale, String> layoutFriendlyURLMap =
			layoutLocalServiceHelper.getFriendlyURLMap(
				groupId, privateLayout, layoutId, name, friendlyURLMap);

		String friendlyURL = layoutFriendlyURLMap.get(
			LocaleUtil.getSiteDefault());

		layoutLocalServiceHelper.validate(
			groupId, privateLayout, layoutId, parentLayoutId, name, type,
			hidden, layoutFriendlyURLMap, serviceContext);

		layoutLocalServiceHelper.validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Layout layout = LayoutUtil.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (LayoutStagingUtil.isBranchingLayout(layout)) {
			layout = getProxiedLayout(layout);
		}

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateLayout(
				groupId, privateLayout, layoutId, parentLayoutId, nameMap,
				titleMap, descriptionMap, keywordsMap, robotsMap, type, hidden,
				friendlyURLMap, hasIconImage, iconBytes, masterLayoutPlid,
				styleBookEntryId, serviceContext);
		}

		layoutLocalService.updateAsset(
			serviceContext.getUserId(), layout,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		if (parentLayoutId != layout.getParentLayoutId()) {
			int priority = layoutLocalServiceHelper.getNextPriority(
				groupId, privateLayout, parentLayoutId,
				layout.getSourcePrototypeLayoutUuid(), -1);

			layout.setPriority(priority);
		}

		layout.setParentLayoutId(parentLayoutId);
		layoutRevision.setNameMap(nameMap);
		layoutRevision.setTitleMap(titleMap);
		layoutRevision.setDescriptionMap(descriptionMap);
		layoutRevision.setKeywordsMap(keywordsMap);
		layoutRevision.setRobotsMap(robotsMap);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);

		if (!hasIconImage) {
			layout.setIconImageId(0);
			layoutRevision.setIconImageId(0);
		}
		else {
			PortalUtil.updateImageId(
				layout, hasIconImage, iconBytes, "iconImageId", 0, 0, 0);
		}

		boolean layoutPrototypeLinkEnabled = ParamUtil.getBoolean(
			serviceContext, "layoutPrototypeLinkEnabled");

		layout.setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);

		layout.setExpandoBridgeAttributes(serviceContext);

		LayoutUtil.update(layout);

		LayoutFriendlyURLLocalServiceUtil.updateLayoutFriendlyURLs(
			layout.getUserId(), layout.getCompanyId(), layout.getGroupId(),
			layout.getPlid(), layout.isPrivateLayout(), layoutFriendlyURLMap,
			serviceContext);

		boolean hasWorkflowTask = StagingUtil.hasWorkflowTask(
			serviceContext.getUserId(), layoutRevision);

		serviceContext.setAttribute("revisionInProgress", hasWorkflowTask);

		int workflowAction = serviceContext.getWorkflowAction();

		try {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			LayoutRevisionLocalServiceUtil.updateLayoutRevision(
				serviceContext.getUserId(),
				layoutRevision.getLayoutRevisionId(),
				layoutRevision.getLayoutBranchId(), layoutRevision.getName(),
				layoutRevision.getTitle(), layoutRevision.getDescription(),
				layoutRevision.getKeywords(), layoutRevision.getRobots(),
				layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
				layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
				layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
				serviceContext);
		}
		finally {
			serviceContext.setWorkflowAction(workflowAction);
		}

		return layout;
	}

	public Layout updateLayout(
			LayoutLocalService layoutLocalService, long groupId,
			boolean privateLayout, long layoutId, String typeSettings)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return layoutLocalService.updateLayout(
				groupId, privateLayout, layoutId, typeSettings);
		}

		Layout layout = LayoutUtil.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (LayoutStagingUtil.isBranchingLayout(layout)) {
			layout = getProxiedLayout(layout);
		}

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateLayout(
				groupId, privateLayout, layoutId, typeSettings);
		}

		layout.setTypeSettings(typeSettings);

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
			layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layout;
	}

	public Layout updateLookAndFeel(
			LayoutLocalService layoutLocalService, long groupId,
			boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return layoutLocalService.updateLookAndFeel(
				groupId, privateLayout, layoutId, themeId, colorSchemeId, css);
		}

		Layout layout = LayoutUtil.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (LayoutStagingUtil.isBranchingLayout(layout)) {
			layout = getProxiedLayout(layout);
		}

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateLookAndFeel(
				groupId, privateLayout, layoutId, themeId, colorSchemeId, css);
		}

		layout.setThemeId(themeId);
		layout.setColorSchemeId(colorSchemeId);
		layout.setCss(css);

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
			layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layout;
	}

	public Layout updateName(
			LayoutLocalService layoutLocalService, Layout layout, String name,
			String languageId)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return layoutLocalService.updateName(layout, name, languageId);
		}

		layout = wrapLayout(layout);

		LayoutRevision layoutRevision = LayoutStagingUtil.getLayoutRevision(
			layout);

		if (layoutRevision == null) {
			return layoutLocalService.updateName(layout, name, languageId);
		}

		layoutLocalServiceHelper.validateName(name, languageId);

		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

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
			layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layout;
	}

	protected void doDeleteLayout(
			LayoutLocalService layoutLocalService, Layout layout,
			ServiceContext serviceContext)
		throws PortalException {

		boolean mergeLayoutPrototypesIsInProgress = false;

		try {
			mergeLayoutPrototypesIsInProgress =
				MergeLayoutPrototypesThreadLocal.isInProgress();

			MergeLayoutPrototypesThreadLocal.setInProgress(true);

			SystemEventHierarchyEntry systemEventHierarchyEntry =
				SystemEventHierarchyEntryThreadLocal.push(
					Layout.class, layout.getPlid());

			if (systemEventHierarchyEntry == null) {
				layoutLocalService.deleteLayout(layout, serviceContext);
			}
			else {
				try {
					layoutLocalService.deleteLayout(layout, serviceContext);

					systemEventHierarchyEntry =
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
		finally {
			MergeLayoutPrototypesThreadLocal.setInProgress(
				mergeLayoutPrototypesIsInProgress);
		}
	}

	protected Layout getProxiedLayout(Layout layout) {
		ObjectValuePair<ServiceContext, Map<Layout, Object>> objectValuePair =
			ProxiedLayoutsThreadLocal.getProxiedLayouts();

		ServiceContext currentServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (objectValuePair != null) {
			ServiceContext serviceContext = objectValuePair.getKey();

			if (serviceContext == currentServiceContext) {
				Map<Layout, Object> proxiedLayouts = objectValuePair.getValue();

				Object proxiedLayout = proxiedLayouts.get(layout);

				if (proxiedLayout != null) {
					Layout cachedProxiedLayout = (Layout)proxiedLayout;

					if (layout.getMvccVersion() ==
							cachedProxiedLayout.getMvccVersion()) {

						return cachedProxiedLayout;
					}

					proxiedLayouts.remove(layout);
				}

				proxiedLayout = _proxyProviderFunction.apply(
					new LayoutStagingHandler(layout));

				proxiedLayouts.put(layout, proxiedLayout);

				return (Layout)proxiedLayout;
			}
		}

		Object proxiedLayout = _proxyProviderFunction.apply(
			new LayoutStagingHandler(layout));

		ProxiedLayoutsThreadLocal.setProxiedLayouts(
			new ObjectValuePair<>(
				currentServiceContext,
				HashMapBuilder.<Layout, Object>put(
					layout, proxiedLayout
				).build()));

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

		if ((layoutStagingHandler != null) ||
			!LayoutStagingUtil.isBranchingLayout(layout)) {

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
					LayoutSet layoutSet = firstLayout.getLayoutSet();

					layoutSetBranchId = StagingUtil.getRecentLayoutSetBranchId(
						UserLocalServiceUtil.getUser(userId),
						layoutSet.getLayoutSetId());
				}
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"No layout set branch found for user " + userId,
						exception);
				}
			}
		}

		List<Layout> wrappedLayouts = new ArrayList<>(layouts.size());

		for (Layout layout : layouts) {
			Layout wrappedLayout = wrapLayout(layout);

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
		else if (returnValue instanceof Map<?, ?>) {
			Map<Object, Object> map = (Map<Object, Object>)returnValue;

			if (map.isEmpty()) {
				return returnValue;
			}

			map.replaceAll(
				(key, value) -> wrapReturnValue(value, showIncomplete));
		}

		return returnValue;
	}

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
		_layoutLocalServiceStagingAdviceMethodNames = new HashSet<>(
			Arrays.asList(
				"create", "createLayout", "deleteLayout", "getLayouts",
				"updateLayout", "updateLookAndFeel", "updateName"));
	private static final Function<InvocationHandler, Layout>
		_proxyProviderFunction = ProxyUtil.getProxyProviderFunction(
			Layout.class, ModelWrapper.class);

	private BeanFactory _beanFactory;

	private class LayoutLocalServiceStagingInvocationHandler
		implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] arguments)
			throws Throwable {

			if (!StagingAdvicesThreadLocal.isEnabled()) {
				return _invoke(method, arguments);
			}

			String methodName = method.getName();

			if (!_layoutLocalServiceStagingAdviceMethodNames.contains(
					methodName)) {

				return wrapReturnValue(_invoke(method, arguments), false);
			}

			Object returnValue = null;

			Class<?>[] parameterTypes = method.getParameterTypes();

			if (methodName.equals("create") ||
				methodName.equals("createLayout")) {

				return _invoke(method, arguments);
			}
			else if (methodName.equals("deleteLayout")) {
				if ((arguments.length == 2) &&
					(arguments[0] instanceof Layout)) {

					deleteLayout(
						(LayoutLocalService)_targetObject, (Layout)arguments[0],
						(ServiceContext)arguments[1]);
				}
				else if (arguments.length == 3) {
					deleteLayout(
						(LayoutLocalService)_targetObject, (Layout)arguments[0],
						(ServiceContext)arguments[2]);
				}
				else if (arguments.length == 4) {
					deleteLayout(
						(LayoutLocalService)_targetObject, (Long)arguments[0],
						(Boolean)arguments[1], (Long)arguments[2],
						(ServiceContext)arguments[3]);
				}
				else {
					return wrapReturnValue(_invoke(method, arguments), false);
				}
			}
			else if (methodName.equals("getLayouts")) {
				boolean showIncomplete = false;

				if ((arguments.length == 6) &&
					parameterTypes[3].equals(Boolean.TYPE)) {

					showIncomplete = (Boolean)arguments[3];
				}
				else if ((arguments.length == 7) &&
						 parameterTypes[3].equals(Boolean.TYPE)) {

					showIncomplete = (Boolean)arguments[3];
				}
				else if (Arrays.equals(parameterTypes, _GET_LAYOUTS_TYPES)) {
					showIncomplete = true;
				}

				return wrapReturnValue(
					_invoke(method, arguments), showIncomplete);
			}
			else if (methodName.equals("updateLayout") &&
					 ((arguments.length == 15) || (arguments.length == 16) ||
					  (arguments.length == 17))) {

				Map<Locale, String> friendlyURLMap = null;

				if (Arrays.equals(
						parameterTypes, _UPDATE_LAYOUT_PARAMETER_TYPES)) {

					friendlyURLMap = HashMapBuilder.put(
						LocaleUtil.getSiteDefault(), (String)arguments[11]
					).build();
				}
				else {
					friendlyURLMap = (Map<Locale, String>)arguments[11];
				}

				long masterLayoutPlid = 0;
				long styleBookEntryId = 0;

				ServiceContext serviceContext = null;

				if (arguments.length == 15) {
					serviceContext = (ServiceContext)arguments[14];
				}
				else if (arguments.length == 16) {
					masterLayoutPlid = (Long)arguments[14];

					serviceContext = (ServiceContext)arguments[15];
				}
				else if (arguments.length == 17) {
					masterLayoutPlid = (Long)arguments[14];
					styleBookEntryId = (Long)arguments[15];

					serviceContext = (ServiceContext)arguments[16];
				}

				returnValue = updateLayout(
					(LayoutLocalService)_targetObject, (Long)arguments[0],
					(Boolean)arguments[1], (Long)arguments[2],
					(Long)arguments[3], (Map<Locale, String>)arguments[4],
					(Map<Locale, String>)arguments[5],
					(Map<Locale, String>)arguments[6],
					(Map<Locale, String>)arguments[7],
					(Map<Locale, String>)arguments[8], (String)arguments[9],
					(Boolean)arguments[10], friendlyURLMap,
					(Boolean)arguments[12], (byte[])arguments[13],
					masterLayoutPlid, styleBookEntryId, serviceContext);
			}
			else {
				try {
					Class<?> clazz = LayoutLocalServiceStagingAdvice.class;

					parameterTypes = ArrayUtil.append(
						new Class<?>[] {LayoutLocalService.class},
						parameterTypes);

					Method layoutLocalServiceStagingAdviceMethod =
						clazz.getMethod(methodName, parameterTypes);

					arguments = ArrayUtil.append(
						new Object[] {_targetObject}, arguments);

					returnValue = layoutLocalServiceStagingAdviceMethod.invoke(
						_layoutLocalServiceStagingAdvice, arguments);
				}
				catch (InvocationTargetException invocationTargetException) {
					throw invocationTargetException.getTargetException();
				}
				catch (NoSuchMethodException noSuchMethodException) {
					if (_log.isDebugEnabled()) {
						_log.debug(noSuchMethodException);
					}

					returnValue = _invoke(method, arguments);
				}
			}

			return wrapReturnValue(returnValue, false);
		}

		private LayoutLocalServiceStagingInvocationHandler(
			LayoutLocalServiceStagingAdvice layoutLocalServiceStagingAdvice,
			Object targetObject) {

			_layoutLocalServiceStagingAdvice = layoutLocalServiceStagingAdvice;
			_targetObject = targetObject;
		}

		private Object _invoke(Method method, Object[] arguments)
			throws Throwable {

			try {
				return method.invoke(_targetObject, arguments);
			}
			catch (InvocationTargetException invocationTargetException) {
				throw invocationTargetException.getCause();
			}
		}

		private final LayoutLocalServiceStagingAdvice
			_layoutLocalServiceStagingAdvice;
		private final Object _targetObject;

	}

}