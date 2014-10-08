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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Julio Camarero
 * @author Jorge Ferrer
 */
public abstract class BaseWorkflowHandler<T> implements WorkflowHandler<T> {

	@Override
	public AssetRenderer getAssetRenderer(long classPK) throws PortalException {
		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		if (assetRendererFactory != null) {
			return assetRendererFactory.getAssetRenderer(
				classPK, AssetRendererFactory.TYPE_LATEST);
		}
		else {
			return null;
		}
	}

	@Override
	public AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	@Override
	public String getIconCssClass() {
		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		if (assetRendererFactory != null) {
			return assetRendererFactory.getIconCssClass();
		}

		return "icon-file-alt";
	}

	@Override
	public String getIconPath(LiferayPortletRequest liferayPortletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSummary(long,
	 *             PortletRequest, PortletResponse)}
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
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

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
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

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
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

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
			LiferayPortletURL liferayPortletURL = PortletURLFactoryUtil.create(
				serviceContext.getRequest(), PortletKeys.MY_WORKFLOW_TASKS,
				PortalUtil.getControlPanelPlid(serviceContext.getCompanyId()),
				PortletRequest.RENDER_PHASE);

			liferayPortletURL.setControlPanelCategory("my");
			liferayPortletURL.setParameter(
				"struts_action", "/my_workflow_tasks/edit_workflow_task");
			liferayPortletURL.setParameter(
				"workflowTaskId", String.valueOf(workflowTaskId));
			liferayPortletURL.setWindowState(WindowState.MAXIMIZED);

			return liferayPortletURL.toString();
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
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

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
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

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
	public String render(
		long classPK, RenderRequest renderRequest,
		RenderResponse renderResponse, String template) {

		try {
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.render(
					renderRequest, renderResponse, template);
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
	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK, T model,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		WorkflowInstanceLinkLocalServiceUtil.startWorkflowInstance(
			companyId, groupId, userId, getClassName(), classPK,
			workflowContext);
	}

	protected String getIconPath(ThemeDisplay themeDisplay) {
		return themeDisplay.getPathThemeImages() + "/common/page.png";
	}

	private static final boolean _ASSET_TYPE_SEARCHABLE = true;

	private static final boolean _SCOPEABLE = true;

	private static final boolean _VISIBLE = true;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWorkflowHandler.class);

}