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
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bruno Farache
 * @author Macerllus Tavares
 * @author Juan Fernández
 * @author Julio Camarero
 */
public interface WorkflowHandler<T> {

	public AssetRenderer<T> getAssetRenderer(long classPK)
		throws PortalException;

	public AssetRendererFactory<T> getAssetRendererFactory();

	public String getClassName();

	public String getIconCssClass();

	public String getIconPath(LiferayPortletRequest liferayPortletRequest);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getSummary(long,
	 *             PortletRequest, PortletResponse)}
	 */
	@Deprecated
	public String getSummary(long classPK, Locale locale);

	public String getSummary(
		long classPK, PortletRequest portletRequest,
		PortletResponse portletResponse);

	public String getTitle(long classPK, Locale locale);

	public String getType(Locale locale);

	public PortletURL getURLEdit(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse);

	public String getURLEditWorkflowTask(
			long workflowTaskId, ServiceContext serviceContext)
		throws PortalException;

	public PortletURL getURLViewDiffs(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse);

	public String getURLViewInContext(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect);

	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException;

	public boolean include(
		long classPK, HttpServletRequest request, HttpServletResponse response,
		String template);

	public boolean isAssetTypeSearchable();

	public boolean isScopeable();

	public boolean isVisible();

	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK, T model,
			Map<String, Serializable> workflowContext)
		throws PortalException;

	public T updateStatus(int status, Map<String, Serializable> workflowContext)
		throws PortalException;

}