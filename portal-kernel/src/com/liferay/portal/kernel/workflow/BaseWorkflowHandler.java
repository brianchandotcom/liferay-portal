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

package com.liferay.portal.kernel.workflow;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Julio Camarero
 * @author Jorge Ferrer
 */
public abstract class BaseWorkflowHandler<T> implements WorkflowHandler<T> {

	@Override
	public AssetRenderer<T> getAssetRenderer(long classPK)
		throws PortalException {

		AssetRendererFactory<T> assetRendererFactory =
			getAssetRendererFactory();

		if (assetRendererFactory != null) {
			return assetRendererFactory.getAssetRenderer(
				classPK, AssetRendererFactory.TYPE_LATEST);
		}

		return null;
	}

	@Override
	public AssetRendererFactory<T> getAssetRendererFactory() {
		return (AssetRendererFactory<T>)AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	@Override
	public String getIconCssClass() {
		AssetRendererFactory<?> assetRendererFactory =
			getAssetRendererFactory();

		if (assetRendererFactory != null) {
			return assetRendererFactory.getIconCssClass();
		}

		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getIconPath(LiferayPortletRequest liferayPortletRequest) {
		return StringPool.BLANK;
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #getSummary(long, PortletRequest, PortletResponse)}
	 */
	@Deprecated
	@Override
	public String getSummary(long classPK, Locale locale) {
		return getSummary(classPK, null, null);
	}

	@Override
	public String getSummary(
		long classPK, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		try {
			AssetRenderer<?> assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getSummary(
					portletRequest, portletResponse);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	@Override
	public String getTitle(long classPK, Locale locale) {
		try {
			AssetRenderer<?> assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getTitle(locale);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	@Override
	public PortletURL getURLEdit(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		try {
			AssetRenderer<?> assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getURLEdit(
					liferayPortletRequest, liferayPortletResponse);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	@Override
	public String getURLEditWorkflowTask(
			long workflowTaskId, ServiceContext serviceContext)
		throws PortalException {

		try {
			PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				serviceContext.getRequest(), PortletKeys.MY_WORKFLOW_TASK,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mvcPath", "/edit_workflow_task.jsp");
			portletURL.setParameter(
				"workflowTaskId", String.valueOf(workflowTaskId));
			portletURL.setWindowState(WindowState.MAXIMIZED);

			return portletURL.toString();
		}
		catch (WindowStateException wse) {
			throw new PortalException(wse);
		}
	}

	@Override
	public PortletURL getURLViewDiffs(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		try {
			AssetRenderer<?> assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getURLViewDiffs(
					liferayPortletRequest, liferayPortletResponse);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	@Override
	public String getURLViewInContext(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		try {
			AssetRenderer<?> assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getURLViewInContext(
					liferayPortletRequest, liferayPortletResponse,
					noSuchEntryRedirect);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		return WorkflowDefinitionLinkLocalServiceUtil.
			fetchWorkflowDefinitionLink(
				companyId, groupId, getClassName(), 0, 0);
	}

	@Override
	public boolean include(
		long classPK, HttpServletRequest request, HttpServletResponse response,
		String template) {

		try {
			AssetRenderer<?> assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.include(request, response, template);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return false;
	}

	@Override
	public boolean isAssetTypeSearchable() {
		return _ASSET_TYPE_SEARCHABLE;
	}

	@Override
	public boolean isScopeable() {
		return _SCOPEABLE;
	}

	@Override
	public boolean isVisible() {
		return _VISIBLE;
	}

	@Override
	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK, T model,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		WorkflowInstanceLinkLocalServiceUtil.startWorkflowInstance(
			companyId, groupId, userId, getClassName(), classPK,
			workflowContext);
	}

	private static final boolean _ASSET_TYPE_SEARCHABLE = true;

	private static final boolean _SCOPEABLE = true;

	private static final boolean _VISIBLE = true;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWorkflowHandler.class);

}