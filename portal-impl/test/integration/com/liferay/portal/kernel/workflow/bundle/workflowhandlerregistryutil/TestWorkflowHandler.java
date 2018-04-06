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

package com.liferay.portal.kernel.workflow.bundle.workflowhandlerregistryutil;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestWorkflowHandler implements WorkflowHandler<Object> {

	@Override
	public AssetRenderer<Object> getAssetRenderer(long classPK) {
		return null;
	}

	@Override
	public AssetRendererFactory<Object> getAssetRendererFactory() {
		return null;
	}

	@Override
	public String getClassName() {
		_atomicBoolean.set(Boolean.TRUE);

		return TestWorkflowHandler.class.getName();
	}

	@Override
	public String getIconCssClass() {
		return null;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public String getIconPath(LiferayPortletRequest liferayPortletRequest) {
		return null;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	@Override
	public String getSummary(long classPK, Locale locale) {
		return null;
	}

	@Override
	public String getSummary(
		long classPK, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		return null;
	}

	@Override
	public String getTitle(long classPK, Locale locale) {
		return null;
	}

	@Override
	public String getType(Locale locale) {
		return null;
	}

	@Override
	public PortletURL getURLEdit(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return null;
	}

	@Override
	public String getURLEditWorkflowTask(
		long workflowTaskId, ServiceContext serviceContext) {

		return null;
	}

	@Override
	public PortletURL getURLViewDiffs(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		return null;
	}

	@Override
	public String getURLViewInContext(
		long classPK, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		return null;
	}

	@Override
	public WorkflowDefinitionLink getWorkflowDefinitionLink(
		long companyId, long groupId, long classPK) {

		return null;
	}

	@Override
	public boolean include(
		long classPK, HttpServletRequest request, HttpServletResponse response,
		String template) {

		return false;
	}

	@Override
	public boolean isAssetTypeSearchable() {
		return false;
	}

	@Override
	public boolean isScopeable() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public void startWorkflowInstance(
		long companyId, long groupId, long userId, long classPK, Object model,
		Map<String, Serializable> workflowContext) {
	}

	@Override
	public Object updateStatus(
		int status, Map<String, Serializable> workflowContext) {

		_atomicBoolean.set(Boolean.TRUE);

		return null;
	}

	@Reference(target = "(test=AtomicState)")
	protected void setAtomicBoolean(AtomicBoolean atomicBoolean) {
		_atomicBoolean = atomicBoolean;
	}

	private AtomicBoolean _atomicBoolean;

}