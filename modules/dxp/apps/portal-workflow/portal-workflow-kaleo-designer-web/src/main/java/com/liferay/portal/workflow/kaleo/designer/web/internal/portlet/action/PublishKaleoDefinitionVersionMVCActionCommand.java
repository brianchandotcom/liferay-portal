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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionFileException;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionTitleException;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.workflow.kaleo.designer.web.constants.KaleoDesignerPortletKeys;
import com.liferay.portal.workflow.kaleo.designer.web.internal.constants.KaleoDesignerWebKeys;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"mvc.command.name=publishKaleoDefinitionVersion"
	},
	service = MVCActionCommand.class
)
public class PublishKaleoDefinitionVersionMVCActionCommand
	extends BaseKaleoDesignerMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		validateTitle(actionRequest, titleMap);

		String content = ParamUtil.getString(actionRequest, "content");

		if (Validator.isNull(content)) {
			throw new WorkflowDefinitionFileException(
				"please-enter-a-valid-definition-before-publishing");
		}

		validateWorkflowDefinition(actionRequest, content.getBytes());

		String name = ParamUtil.getString(actionRequest, "name");

		WorkflowDefinition latestWorkflowDefinition =
			getLatestWorkflowDefinition(themeDisplay.getCompanyId(), name);

		if ((latestWorkflowDefinition == null) ||
			!latestWorkflowDefinition.isActive()) {

			actionRequest.setAttribute(
				KaleoDesignerWebKeys.PUBLISH_DEFINITION_ACTION, Boolean.TRUE);
		}

		WorkflowDefinition workflowDefinition =
			unproxiedWorkflowDefinitionManager.deployWorkflowDefinition(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(),
				getTitle(actionRequest, titleMap), name, content.getBytes());

		KaleoDefinitionVersion kaleoDefinitionVersion =
			kaleoDefinitionVersionLocalService.getLatestKaleoDefinitionVersion(
				themeDisplay.getCompanyId(), workflowDefinition.getName());

		actionRequest.setAttribute(
			KaleoDesignerWebKeys.KALEO_DRAFT_DEFINITION,
			kaleoDefinitionVersion);

		setRedirectAttribute(actionRequest, kaleoDefinitionVersion);
	}

	protected WorkflowDefinition getLatestWorkflowDefinition(
		long companyId, String name) {

		try {
			return unproxiedWorkflowDefinitionManager.
				getLatestWorkflowDefinition(companyId, name);
		}
		catch (WorkflowException workflowException) {
			if (_log.isDebugEnabled()) {
				_log.debug(workflowException, workflowException);
			}
		}

		return null;
	}

	@Override
	protected String getSuccessMessage(ActionRequest actionRequest) {
		ResourceBundle resourceBundle = getResourceBundle(actionRequest);

		boolean definitionPublishing = GetterUtil.getBoolean(
			actionRequest.getAttribute(
				KaleoDesignerWebKeys.PUBLISH_DEFINITION_ACTION));

		if (definitionPublishing) {
			return LanguageUtil.get(
				resourceBundle, "workflow-published-successfully");
		}

		return LanguageUtil.get(
			resourceBundle, "workflow-updated-successfully");
	}

	protected void validateTitle(
			ActionRequest actionRequest, Map<Locale, String> titleMap)
		throws WorkflowDefinitionTitleException {

		String title = titleMap.get(LocaleUtil.getDefault());

		String defaultTitle = LanguageUtil.get(
			getResourceBundle(actionRequest), "untitled-workflow");

		if (titleMap.isEmpty() || Validator.isNull(title) ||
			Objects.equals(title, defaultTitle)) {

			throw new WorkflowDefinitionTitleException();
		}
	}

	protected void validateWorkflowDefinition(
			ActionRequest actionRequest, byte[] bytes)
		throws WorkflowDefinitionFileException {

		try {
			unproxiedWorkflowDefinitionManager.validateWorkflowDefinition(
				bytes);
		}
		catch (WorkflowException workflowException) {
			String message = LanguageUtil.get(
				getResourceBundle(actionRequest),
				"please-enter-a-valid-definition-before-publishing");

			throw new WorkflowDefinitionFileException(
				message, workflowException);
		}
	}

	@Reference(target = "(proxy.bean=false)")
	protected WorkflowDefinitionManager unproxiedWorkflowDefinitionManager;

	private static final Log _log = LogFactoryUtil.getLog(
		PublishKaleoDefinitionVersionMVCActionCommand.class);

}