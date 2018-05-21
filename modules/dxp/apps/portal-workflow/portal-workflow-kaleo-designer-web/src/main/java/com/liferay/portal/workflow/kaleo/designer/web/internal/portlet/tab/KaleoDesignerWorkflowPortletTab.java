/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.tab;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.display.context.KaleoDesignerDisplayContext;
import com.liferay.portal.workflow.kaleo.exception.DuplicateKaleoDefinitionNameException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.portlet.tab.BaseWorkflowPortletTab;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTab;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(
	immediate = true,
	property = {
		"portal.workflow.tabs.name=" + WorkflowWebKeys.WORKFLOW_TAB_DEFINITION,
		"service.ranking:Integer=100"
	},
	service = WorkflowPortletTab.class
)
public class KaleoDesignerWorkflowPortletTab extends BaseWorkflowPortletTab {

	@Override
	public String getName() {
		return WorkflowWebKeys.WORKFLOW_TAB_DEFINITION;
	}

	@Override
	public String getSearchJspPath() {
		return "/designer/kaleo_definition_search.jsp";
	}

	@Override
	public void prepareRender(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		if (!SessionErrors.contains(
				renderRequest, DuplicateKaleoDefinitionNameException.class)) {

			try {
				setKaleoDefinitionVersionRenderRequestAttribute(
					renderRequest, renderResponse);

				setKaleoDesignerServletContextRequestAttribute(renderRequest);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Override
	protected String getJspPath() {
		return "/designer/view_workflow_definitions.jsp";
	}

	@Reference(unbind = "-")
	protected void setKaleoDefinitionVersionLocalService(
		KaleoDefinitionVersionLocalService kaleoDefinitionVersionLocalService) {

		_kaleoDefinitionVersionLocalService =
			kaleoDefinitionVersionLocalService;
	}

	protected void setKaleoDefinitionVersionRenderRequestAttribute(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_kaleoDesignerDisplayContext == null) {
			_kaleoDesignerDisplayContext = new KaleoDesignerDisplayContext(
				renderRequest, _kaleoDefinitionVersionLocalService,
				_resourceBundleLoader, _userLocalService);
		}

		renderRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DESIGNER_DISPLAY_CONTEXT,
			_kaleoDesignerDisplayContext);

		String name = ParamUtil.getString(renderRequest, "name");

		if (_kaleoDesignerDisplayContext == null) {
			_kaleoDesignerDisplayContext = new KaleoDesignerDisplayContext(
				renderRequest, _kaleoDefinitionVersionLocalService,
				_resourceBundleLoader, _userLocalService);
		}

		renderRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DESIGNER_DISPLAY_CONTEXT,
			_kaleoDesignerDisplayContext);

		if (Validator.isNull(name)) {
			return;
		}

		KaleoDefinitionVersion kaleoDefinitionVersion = null;

		String draftVersion = ParamUtil.getString(
			renderRequest, "draftVersion");

		if (Validator.isNull(draftVersion)) {
			kaleoDefinitionVersion =
				_kaleoDefinitionVersionLocalService.
					fetchLatestKaleoDefinitionVersion(
						themeDisplay.getCompanyId(), name, null);
		}
		else {
			kaleoDefinitionVersion =
				_kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					themeDisplay.getCompanyId(), name, draftVersion);
		}

		renderRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION,
			kaleoDefinitionVersion);
	}

	protected void setKaleoDesignerServletContextRequestAttribute(
		RenderRequest renderRequest) {

		renderRequest.setAttribute(
			"portletTabServletContext", getServletContext());
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.workflow.kaleo.designer.web)",
		unbind = "-"
	)
	protected void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected KaleoDefinitionVersionLocalService
		kaleoDefinitionVersionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDesignerWorkflowPortletTab.class);

	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;
	private KaleoDesignerDisplayContext _kaleoDesignerDisplayContext;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.portal.workflow.kaleo.designer.web)"
	)
	private ResourceBundleLoader _resourceBundleLoader;

	@Reference
	private UserLocalService _userLocalService;

}