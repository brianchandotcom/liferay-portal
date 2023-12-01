/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.portlet.tab;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.workflow.comparator.WorkflowComparatorFactory;
import com.liferay.portal.workflow.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.manager.WorkflowLogManager;
import com.liferay.portal.workflow.portlet.tab.BaseWorkflowPortletTab;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTab;
import com.liferay.portal.workflow.web.internal.configuration.WorkflowInstanceWebConfiguration;
import com.liferay.portal.workflow.web.internal.display.context.MyWorkflowInstanceEditDisplayContext;
import com.liferay.portal.workflow.web.internal.display.context.MyWorkflowInstanceViewDisplayContext;
import com.liferay.portal.workflow.web.internal.display.context.WorkflowInstanceEditDisplayContext;
import com.liferay.portal.workflow.web.internal.display.context.WorkflowInstanceViewDisplayContext;
import com.liferay.portal.workflow.web.internal.request.preprocessor.helper.WorkflowPreprocessorHelper;

import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	configurationPid = "com.liferay.portal.workflow.web.internal.configuration.WorkflowInstanceWebConfiguration",
	property = "portal.workflow.tabs.name=" + WorkflowWebKeys.WORKFLOW_TAB_INSTANCE,
	service = WorkflowPortletTab.class
)
public class WorkflowInstancePortletTab extends BaseWorkflowPortletTab {

	@Override
	public String getName() {
		return WorkflowWebKeys.WORKFLOW_TAB_INSTANCE;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public void prepareDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			WorkflowInstanceWebConfiguration.class.getName(),
			workflowInstanceWebConfiguration);
	}

	@Override
	public void prepareProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		String actionName = ParamUtil.getString(
			actionRequest, ActionRequest.ACTION_NAME);

		if (StringUtil.equalsIgnoreCase(actionName, "invokeTaglibDiscussion")) {
			workflowPreprocessorHelper.hideDefaultSuccessMessage(actionRequest);
		}
	}

	@Override
	public void prepareRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			_setWorkflowInstanceDisplayContextRenderRequestAttribute(
				renderRequest, renderResponse);
			_setWorkflowInstanceRenderRequestAttribute(renderRequest);
		}
		catch (Exception exception) {
			if (workflowPreprocessorHelper.isSessionErrorException(exception)) {
				if (_log.isWarnEnabled()) {
					_log.warn(exception);
				}

				workflowPreprocessorHelper.hideDefaultErrorMessage(
					renderRequest);

				SessionErrors.add(renderRequest, exception.getClass());
			}
			else {
				throw new PortletException(exception);
			}
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		workflowInstanceWebConfiguration = ConfigurableUtil.createConfigurable(
			WorkflowInstanceWebConfiguration.class, properties);
	}

	@Override
	protected String getJspPath() {
		return "/instance/view.jsp";
	}

	@Reference
	protected Portal portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.workflow.web)"
	)
	protected ServletContext servletContext;

	@Reference
	protected WorkflowComparatorFactory workflowComparatorFactory;

	protected volatile WorkflowInstanceWebConfiguration
		workflowInstanceWebConfiguration;

	@Reference
	protected WorkflowLogManager workflowLogManager;

	@Reference
	protected WorkflowPreprocessorHelper workflowPreprocessorHelper;

	private void _setWorkflowInstanceDisplayContextRenderRequestAttribute(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		if (Objects.equals(
				portletDisplay.getPortletName(),
				WorkflowPortletKeys.USER_WORKFLOW)) {

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new MyWorkflowInstanceViewDisplayContext(
					portal.getLiferayPortletRequest(renderRequest),
					portal.getLiferayPortletResponse(renderResponse),
					workflowComparatorFactory, workflowLogManager));
			renderRequest.setAttribute(
				WorkflowWebKeys.WORKFLOW_INSTANCE_EDIT_DISPLAY_CONTEXT,
				new MyWorkflowInstanceEditDisplayContext(
					portal.getLiferayPortletRequest(renderRequest),
					portal.getLiferayPortletResponse(renderResponse),
					workflowComparatorFactory, workflowLogManager));
		}
		else {
			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new WorkflowInstanceViewDisplayContext(
					portal.getLiferayPortletRequest(renderRequest),
					portal.getLiferayPortletResponse(renderResponse),
					workflowComparatorFactory, workflowLogManager));
			renderRequest.setAttribute(
				WorkflowWebKeys.WORKFLOW_INSTANCE_EDIT_DISPLAY_CONTEXT,
				new WorkflowInstanceEditDisplayContext(
					portal.getLiferayPortletRequest(renderRequest),
					portal.getLiferayPortletResponse(renderResponse),
					workflowComparatorFactory, workflowLogManager));
		}
	}

	private void _setWorkflowInstanceRenderRequestAttribute(
			RenderRequest renderRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long workflowInstanceId = ParamUtil.getLong(
			renderRequest, "workflowInstanceId");

		WorkflowInstance workflowInstance = null;

		if (workflowInstanceId != 0) {
			workflowInstance = WorkflowInstanceManagerUtil.getWorkflowInstance(
				themeDisplay.getCompanyId(), workflowInstanceId);
		}

		renderRequest.setAttribute(WebKeys.WORKFLOW_INSTANCE, workflowInstance);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowInstancePortletTab.class);

}