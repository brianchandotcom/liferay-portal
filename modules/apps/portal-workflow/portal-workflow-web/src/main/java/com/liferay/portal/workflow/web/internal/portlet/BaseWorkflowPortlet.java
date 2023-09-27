/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.portlet;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTab;

import java.io.IOException;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adam Brandizzi
 */
public abstract class BaseWorkflowPortlet extends MVCPortlet {

	public String getDefaultWorkflowPortletTabName() {
		List<String> workflowPortletTabNames = getWorkflowPortletTabNames();

		return workflowPortletTabNames.get(0);
	}

	public abstract List<String> getWorkflowPortletTabNames();

	public List<WorkflowPortletTab> getWorkflowPortletTabs() {
		return TransformUtil.transform(
			getWorkflowPortletTabNames(),
			_workflowPortletTabServiceTrackerMap::getService);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		for (WorkflowPortletTab workflowPortletTab : getWorkflowPortletTabs()) {
			workflowPortletTab.prepareProcessAction(
				actionRequest, actionResponse);
		}

		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		addRenderRequestAttributes(renderRequest);

		for (WorkflowPortletTab workflowPortletTab : getWorkflowPortletTabs()) {
			workflowPortletTab.prepareRender(renderRequest, renderResponse);
		}

		super.render(renderRequest, renderResponse);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_workflowPortletTabServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, WorkflowPortletTab.class,
				"portal.workflow.tabs.name");
	}

	protected void addRenderRequestAttributes(RenderRequest renderRequest) {
		renderRequest.setAttribute(
			WorkflowWebKeys.SELECTED_WORKFLOW_PORTLET_TAB,
			_getSelectedWorkflowPortletTab(renderRequest));
		renderRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_PORTLET_TABS, getWorkflowPortletTabs());
	}

	@Deactivate
	protected void deactivate() {
		_workflowPortletTabServiceTrackerMap.close();
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		for (WorkflowPortletTab workflowPortletTab : getWorkflowPortletTabs()) {
			workflowPortletTab.prepareDispatch(renderRequest, renderResponse);
		}

		super.doDispatch(renderRequest, renderResponse);
	}

	private WorkflowPortletTab _getSelectedWorkflowPortletTab(
		RenderRequest renderRequest) {

		String workflowPortletTabName = ParamUtil.get(
			renderRequest, "tab", getDefaultWorkflowPortletTabName());

		return _workflowPortletTabServiceTrackerMap.getService(
			workflowPortletTabName);
	}

	private ServiceTrackerMap<String, WorkflowPortletTab>
		_workflowPortletTabServiceTrackerMap;

}