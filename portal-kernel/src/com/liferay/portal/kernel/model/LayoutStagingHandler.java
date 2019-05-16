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

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.LayoutBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LayoutTypePortletFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class LayoutStagingHandler implements InvocationHandler, Serializable {

	public LayoutStagingHandler(Layout layout) {
		this(layout, null);
	}

	public Layout getLayout() {
		return _layout;
	}

	public LayoutRevision getLayoutRevision() {
		return _layoutRevision;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			String methodName = method.getName();

			if (methodName.equals("getWrappedModel")) {
				return _layout;
			}

			if (_layoutRevision == null) {
				return method.invoke(_layout, arguments);
			}

			if (methodName.equals("clone")) {
				return _clone();
			}

			if (methodName.equals("getLayoutType")) {
				return _getLayoutType();
			}

			if (methodName.equals("getRegularURL")) {
				Class<?> layoutRevisionClass = _layoutRevision.getClass();

				method = layoutRevisionClass.getMethod(
					methodName, HttpServletRequest.class);

				return method.invoke(_layoutRevision, arguments);
			}

			if (methodName.equals("toEscapedModel")) {
				if (_layout.isEscapedModel()) {
					return this;
				}

				return _toEscapedModel();
			}

			Object bean = _layout;

			if (_layoutRevisionMethodNames.contains(methodName)) {
				try {
					Class<?> layoutRevisionClass = _layoutRevision.getClass();

					method = layoutRevisionClass.getMethod(
						methodName, method.getParameterTypes());

					bean = _layoutRevision;
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

	public void setLayoutRevision(LayoutRevision layoutRevision) {
		_layoutRevision = layoutRevision;
	}

	private LayoutStagingHandler(Layout layout, LayoutRevision layoutRevision) {
		_layout = layout;

		try {
			_layoutRevision = _getLayoutRevision(layout, layoutRevision);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException(e);
		}
	}

	private Object _clone() {
		return ProxyUtil.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(),
			new Class<?>[] {Layout.class},
			new LayoutStagingHandler(
				(Layout)_layout.clone(),
				(LayoutRevision)_layoutRevision.clone()));
	}

	private LayoutRevision _getLayoutRevision(
			Layout layout, LayoutRevision layoutRevision)
		throws PortalException {

		if (layoutRevision != null) {
			return layoutRevision;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext == null) || !serviceContext.isSignedIn()) {
			LayoutRevision lastLayoutRevision =
				LayoutRevisionLocalServiceUtil.fetchLastLayoutRevision(
					layout.getPlid(), true);

			if (lastLayoutRevision == null) {
				lastLayoutRevision =
					LayoutRevisionLocalServiceUtil.fetchLastLayoutRevision(
						layout.getPlid(), false);
			}

			return lastLayoutRevision;
		}

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		long layoutSetBranchId = ParamUtil.getLong(
			serviceContext, "layoutSetBranchId");

		LayoutSet layoutSet = layout.getLayoutSet();

		LayoutSetBranch layoutSetBranch =
			LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(
				serviceContext.getUserId(), layout.getGroupId(),
				layout.isPrivateLayout(), layoutSet.getLayoutSetId(),
				layoutSetBranchId);

		layoutSetBranchId = layoutSetBranch.getLayoutSetBranchId();

		long layoutRevisionId = ParamUtil.getLong(
			serviceContext, "layoutRevisionId");

		if (layoutRevisionId > 0) {
			layoutRevision = LayoutRevisionLocalServiceUtil.fetchLayoutRevision(
				layoutRevisionId);
		}

		if ((layoutRevisionId <= 0) ||
			!_isBelongsToLayout(layoutRevision, layout)) {

			layoutRevisionId = StagingUtil.getRecentLayoutRevisionId(
				user, layoutSetBranchId, layout.getPlid());

			layoutRevision = LayoutRevisionLocalServiceUtil.fetchLayoutRevision(
				layoutRevisionId);
		}

		if ((layoutRevision != null) && !layoutRevision.isInactive()) {
			return layoutRevision;
		}

		layoutRevision =
			LayoutRevisionLocalServiceUtil.fetchLatestLayoutRevision(
				layoutSetBranchId, layout.getPlid());

		if (layoutRevision != null) {
			StagingUtil.setRecentLayoutRevisionId(
				user, layoutSetBranchId, layout.getPlid(),
				layoutRevision.getLayoutRevisionId());

			return layoutRevision;
		}

		LayoutBranch layoutBranch =
			LayoutBranchLocalServiceUtil.getMasterLayoutBranch(
				layoutSetBranchId, layout.getPlid(), serviceContext);

		int workflowAction = serviceContext.getWorkflowAction();

		try {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);

			layoutRevision = LayoutRevisionLocalServiceUtil.addLayoutRevision(
				serviceContext.getUserId(), layoutSetBranchId,
				layoutBranch.getLayoutBranchId(),
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
				false, layout.getPlid(), LayoutConstants.DEFAULT_PLID,
				layout.isPrivateLayout(), layout.getName(), layout.getTitle(),
				layout.getDescription(), layout.getKeywords(),
				layout.getRobots(), layout.getTypeSettings(),
				layout.getIconImage(), layout.getIconImageId(),
				layout.getThemeId(), layout.getColorSchemeId(), layout.getCss(),
				serviceContext);
		}
		finally {
			serviceContext.setWorkflowAction(workflowAction);
		}

		boolean explicitCreation = ParamUtil.getBoolean(
			serviceContext, "explicitCreation");

		if (!explicitCreation) {
			LayoutRevisionLocalServiceUtil.updateStatus(
				serviceContext.getUserId(),
				layoutRevision.getLayoutRevisionId(),
				WorkflowConstants.STATUS_INCOMPLETE, serviceContext);
		}

		return layoutRevision;
	}

	private LayoutType _getLayoutType() {
		return LayoutTypePortletFactoryUtil.create(
			(Layout)ProxyUtil.newProxyInstance(
				PortalClassLoaderUtil.getClassLoader(),
				new Class<?>[] {Layout.class},
				new LayoutStagingHandler(_layout, _layoutRevision)));
	}

	private boolean _isBelongsToLayout(
		LayoutRevision layoutRevision, Layout layout) {

		if (layoutRevision == null) {
			return false;
		}

		if (layoutRevision.getPlid() == layout.getPlid()) {
			return true;
		}

		return false;
	}

	private Object _toEscapedModel() {
		return ProxyUtil.newProxyInstance(
			PortalClassLoaderUtil.getClassLoader(),
			new Class<?>[] {Layout.class},
			new LayoutStagingHandler(
				_layout.toEscapedModel(), _layoutRevision.toEscapedModel()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutStagingHandler.class);

	private static final Set<String> _layoutRevisionMethodNames = new HashSet<>(
		Arrays.asList(
			"getColorScheme", "getColorSchemeId", "getCss", "getCssText",
			"getDescription", "getGroupId", "getHTMLTitle", "getIconImage",
			"getIconImageId", "getKeywords", "getLayoutSet", "getModifiedDate",
			"getName", "getRobots", "getTarget", "getTheme", "getThemeId",
			"getThemeSetting", "getTitle", "getTypeSettings",
			"getTypeSettingsProperties", "getTypeSettingsProperty",
			"isContentDisplayPage", "isEscapedModel", "isIconImage",
			"isInheritLookAndFeel", "setColorSchemeId", "setCss",
			"setDescription", "setDescriptionMap", "setEscapedModel",
			"setGroupId", "setIconImage", "setIconImageId", "setKeywords",
			"setKeywordsMap", "setModifiedDate", "setName", "setNameMap",
			"setRobots", "setRobotsMap", "setThemeId", "setTitle",
			"setTitleMap", "setTypeSettings", "setTypeSettingsProperties"));

	private final Layout _layout;
	private LayoutRevision _layoutRevision;

}