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

package com.liferay.portal.workflow.kaleo.designer.web.internal.portlet.action;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"mvc.command.name=deleteKaleoDefinitionVersion"
	},
	service = MVCActionCommand.class
)
public class DeleteKaleoDefinitionVersionMVCActionCommand
	extends BaseKaleoDesignerMVCActionCommand {

	@Override
	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		MultiSessionMessages.add(
			actionRequest,
			KaleoDesignerPortletKeys.KALEO_DESIGNER + "requestProcessed",
			getSuccessMessage(actionRequest));
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		String name = ParamUtil.getString(actionRequest, "name");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionLocalService.fetchKaleoDefinition(
				name, serviceContext);

		if (kaleoDefinition != null) {
			workflowDefinitionManager.undeployWorkflowDefinition(
				companyId, themeDisplay.getUserId(), name,
				kaleoDefinition.getVersion());
		}
		else {
			kaleoDefinitionVersionLocalService.deleteKaleoDefinitionVersions(
				themeDisplay.getCompanyId(), name);
		}

		setRedirectAttribute(actionRequest, null);
	}

	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		ResourceBundle resourceBundle = getResourceBundle(actionRequest);

		return LanguageUtil.get(
			resourceBundle, "workflow-deleted-successfully");
	}

	@Override
	protected void setRedirectAttribute(
			ActionRequest actionRequest,
			KaleoDefinitionVersion kaleoDefinitionVersion)
		throws Exception {

		LiferayPortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, KaleoDesignerPortletKeys.CONTROL_PANEL_WORKFLOW,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view.jsp");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect, false);

		portletURL.setWindowState(actionRequest.getWindowState());

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

}