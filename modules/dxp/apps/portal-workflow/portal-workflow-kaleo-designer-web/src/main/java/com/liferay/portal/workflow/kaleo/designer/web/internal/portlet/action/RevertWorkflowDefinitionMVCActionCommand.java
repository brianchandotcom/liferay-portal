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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.workflow.constants.WorkflowWebKeys;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.text.DateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"mvc.command.name=revertKaleoDefinitionVersion"
	},
	service = MVCActionCommand.class
)
public class RevertWorkflowDefinitionMVCActionCommand
	extends BaseKaleoDesignerMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		String draftVersion = ParamUtil.getString(
			actionRequest, "draftVersion");

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), name, draftVersion);

		actionRequest.setAttribute(
			WorkflowWebKeys.WORKFLOW_DEFINITION_MODIFIED_DATE,
			kaleoDefinitionVersion.getModifiedDate());

		String content = kaleoDefinitionVersion.getContent();

		KaleoDefinition kaleoDefinition =
			kaleoDefinitionVersion.getKaleoDefinition();

		WorkflowDefinition workflowDefinition = null;

		if (kaleoDefinition.isActive()) {
			workflowDefinition =
				workflowDefinitionManager.deployWorkflowDefinition(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					kaleoDefinitionVersion.getTitle(), name,
					content.getBytes());
		}
		else {
			workflowDefinition =
				workflowDefinitionManager.saveWorkflowDefinition(
					themeDisplay.getCompanyId(), themeDisplay.getUserId(),
					kaleoDefinitionVersion.getTitle(), name,
					content.getBytes());
		}

		kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), workflowDefinition.getName());

		actionRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION,
			kaleoDefinitionVersion);

		setRedirectAttribute(actionRequest, kaleoDefinitionVersion);

		sendRedirect(actionRequest, actionResponse);
	}

	/**
	 * Adds a success message to the workflow definition reversion action
	 *
	 * @param  actionRequest The actionRequest object of the action
	 * @review
	 */
	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		ResourceBundle resourceBundle = getResourceBundle(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		DateFormat dateTimeFormat = null;

		if (DateUtil.isFormatAmPm(locale)) {
			dateTimeFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"MMM d, yyyy, hh:mm a", locale);
		}
		else {
			dateTimeFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"MMM d, yyyy, HH:mm", locale);
		}

		Date workflowDefinitionModifiedDate = (Date)actionRequest.getAttribute(
			WorkflowWebKeys.WORKFLOW_DEFINITION_MODIFIED_DATE);

		String dateTime = dateTimeFormat.format(workflowDefinitionModifiedDate);

		return LanguageUtil.format(
			resourceBundle, "restored-to-revision-from-x", dateTime);
	}

}