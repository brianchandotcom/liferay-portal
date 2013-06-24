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

package com.liferay.portal.kernel.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.service.WorkflowInstanceLinkLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 * @author Juan Fernández
 * @author Julio Camarero
 * @author Jorge Ferrer
 */
public abstract class BaseWorkflowHandler implements WorkflowHandler {

	@Override
	public AssetRenderer getAssetRenderer(long classPK)
		throws PortalException, SystemException {

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
	public String getIconPath(LiferayPortletRequest liferayPortletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return getIconPath(themeDisplay);
	}

	@Override
	public String getSummary(long classPK, Locale locale) {
		try {
			AssetRenderer assetRenderer = getAssetRenderer(classPK);

			if (assetRenderer != null) {
				return assetRenderer.getSummary(locale);
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

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException, SystemException {

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
			long companyId, long groupId, long userId, long classPK,
			Object model, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

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

	private static Log _log = LogFactoryUtil.getLog(BaseWorkflowHandler.class);

}