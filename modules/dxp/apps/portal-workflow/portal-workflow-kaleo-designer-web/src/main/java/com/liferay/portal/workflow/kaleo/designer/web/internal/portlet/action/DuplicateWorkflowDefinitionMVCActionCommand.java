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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.Locale;
import java.util.Map;

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
		"mvc.command.name=duplicateWorkflowDefinition"
	},
	service = MVCActionCommand.class
)
public class DuplicateWorkflowDefinitionMVCActionCommand
	extends PublishKaleoDefinitionVersionMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String randomNamespace = ParamUtil.getString(
			actionRequest, "randomNamespace");

		WorkflowDefinition workflowDefinition = _getWorkflowDefinition(
			themeDisplay,
			ParamUtil.getString(actionRequest, "duplicatedDefinitionName"));

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, randomNamespace + "title");

		validateTitle(actionRequest, titleMap);

		String name = ParamUtil.getString(actionRequest, "name");
		String content = ParamUtil.getString(actionRequest, "content");

		if ((workflowDefinition != null) && workflowDefinition.isActive()) {
			workflowDefinitionManager.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(actionRequest, titleMap), name, content.getBytes());
		}
		else {
			workflowDefinitionManager.saveWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(actionRequest, titleMap), name, content.getBytes());
		}

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), name);

		setRedirectAttribute(actionRequest, kaleoDefinitionVersion);

		sendRedirect(actionRequest, actionResponse);
	}

	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		String duplicatedDefinitionTitle = ParamUtil.getString(
			actionRequest, "duplicatedDefinitionTitle");

		return LanguageUtil.format(
			getResourceBundle(actionRequest), "duplicated-from-x",
			StringUtil.quote(duplicatedDefinitionTitle));
	}

	private WorkflowDefinition _getWorkflowDefinition(
		ThemeDisplay themeDisplay, String name) {

		try {
			return workflowDefinitionManager.getLatestWorkflowDefinition(
				themeDisplay.getCompanyId(), name);
		}
		catch (WorkflowException workflowException) {
			if (_log.isWarnEnabled()) {
				_log.warn(workflowException, workflowException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DuplicateWorkflowDefinitionMVCActionCommand.class);

}