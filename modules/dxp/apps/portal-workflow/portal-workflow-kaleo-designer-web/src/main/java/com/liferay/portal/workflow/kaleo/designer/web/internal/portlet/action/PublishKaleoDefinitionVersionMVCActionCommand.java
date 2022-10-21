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

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
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
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(
	property = {
		"javax.portlet.name=" + KaleoDesignerPortletKeys.KALEO_DESIGNER,
		"mvc.command.name=/kaleo_designer/publish_kaleo_definition_version"
	},
	service = MVCActionCommand.class
)
public class PublishKaleoDefinitionVersionMVCActionCommand
	extends BaseKaleoDesignerMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Map<Locale, String> titleMap = localization.getLocalizationMap(
			actionRequest, "title");

		validateTitle(actionRequest, titleMap);

		String content = ParamUtil.getString(actionRequest, "content");

		if (Validator.isNull(content)) {
			throw new WorkflowDefinitionFileException(
				"please-enter-a-valid-definition-before-publishing");
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

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
				_log.debug(workflowException);
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
			return language.get(
				resourceBundle, "workflow-published-successfully");
		}

		return language.get(resourceBundle, "workflow-updated-successfully");
	}

	protected void validateTitle(
			ActionRequest actionRequest, Map<Locale, String> titleMap)
		throws WorkflowDefinitionTitleException {

		String title = titleMap.get(LocaleUtil.getDefault());

		if (titleMap.isEmpty() || Validator.isNull(title)) {
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
			String message = language.get(
				getResourceBundle(actionRequest),
				"please-enter-a-valid-definition-before-publishing");

			throw new WorkflowDefinitionFileException(
				message, workflowException);
		}
	}

	@Reference
	protected Language language;

	@Reference
	protected Localization localization;

	@Reference
	protected WorkflowDefinitionManager unproxiedWorkflowDefinitionManager;

	private static final Log _log = LogFactoryUtil.getLog(
		PublishKaleoDefinitionVersionMVCActionCommand.class);

}