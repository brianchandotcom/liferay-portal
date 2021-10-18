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

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Adolfo Pérez
 */
public class WorkflowHandlerWrapper<T> implements WorkflowHandler<T> {

	public WorkflowHandlerWrapper(WorkflowHandler<T> workflowHandler) {
		_workflowHandler = workflowHandler;
	}

	@Override
	public AssetRenderer<T> getAssetRenderer(long classPK)
		throws PortalException {

		return _workflowHandler.getAssetRenderer(classPK);
	}

	@Override
	public AssetRendererFactory<T> getAssetRendererFactory() {
		return _workflowHandler.getAssetRendererFactory();
	}

	@Override
	public String getClassName() {
		return _workflowHandler.getClassName();
	}

	@Override
	public String getIconCssClass() {
		return _workflowHandler.getIconCssClass();
	}

	@Override
	public String getNotificationLink(
			long workflowTaskId, ServiceContext serviceContext)
		throws PortalException {

		return _workflowHandler.getNotificationLink(
			workflowTaskId, serviceContext);
	}

	@Override
	public String getSummary(
		long classPK, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		return _workflowHandler.getSummary(
			classPK, portletRequest, portletResponse);
	}

	@Override
	public String getTitle(long classPK, Locale locale) {
		return _workflowHandler.getTitle(classPK, locale);
	}

	@Override
	public String getType(Locale locale) {
		return _workflowHandler.getType(locale);
	}

	@Override
	public PortletURL getURLEdit(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return _workflowHandler.getURLEdit(
			classPK, liferayPortletRequest, liferayPortletResponse);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #getNotificationLink(long, ServiceContext)}}
	 */
	@Deprecated
	@Override
	public String getURLEditWorkflowTask(
			long workflowTaskId, ServiceContext serviceContext)
		throws PortalException {

		return _workflowHandler.getURLEditWorkflowTask(
			workflowTaskId, serviceContext);
	}

	@Override
	public PortletURL getURLViewDiffs(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return _workflowHandler.getURLViewDiffs(
			classPK, liferayPortletRequest, liferayPortletResponse);
	}

	@Override
	public String getURLViewInContext(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return _workflowHandler.getURLViewInContext(
			classPK, liferayPortletRequest, liferayPortletResponse,
			noSuchEntryRedirect);
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classPK)
		throws PortalException {

		return _workflowHandler.getWorkflowDefinitionLink(
			companyId, groupId, classPK);
	}

	@Override
	public boolean include(
		long classPK, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String template) {

		return _workflowHandler.include(
			classPK, httpServletRequest, httpServletResponse, template);
	}

	@Override
	public boolean isAssetTypeSearchable() {
		return _workflowHandler.isAssetTypeSearchable();
	}

	@Override
	public boolean isScopeable() {
		return _workflowHandler.isScopeable();
	}

	@Override
	public boolean isVisible() {
		return _workflowHandler.isVisible();
	}

	@Override
	public boolean isVisible(Group group) {
		return _workflowHandler.isVisible(group);
	}

	@Override
	public void startWorkflowInstance(
			long companyId, long groupId, long userId, long classPK, T model,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		_workflowHandler.startWorkflowInstance(
			companyId, groupId, userId, classPK, model, workflowContext);
	}

	@Override
	public T updateStatus(int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		return _workflowHandler.updateStatus(status, workflowContext);
	}

	@Override
	public T updateStatus(
			T model, int status, Map<String, Serializable> workflowContext)
		throws PortalException {

		return _workflowHandler.updateStatus(model, status, workflowContext);
	}

	private final WorkflowHandler<T> _workflowHandler;

}