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

package com.liferay.portal.workflow.kaleo.forms.web.internal.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + KaleoFormsPortletKeys.KALEO_FORMS_ADMIN,
		"mvc.command.name=/kaleo_forms/deactivate_workflow_definition"
	},
	service = MVCActionCommand.class
)
public class DeactivateWorkflowDefinitionMVCActionCommand
	extends BaseKaleoFormsMVCActionCommand {

	/**
	 * Deactivates the <code>WorkflowDefinition</code> (in
	 * <code>com.liferay.portal.kernel</code>) by using its name and version
	 * from the action request. If deactivation fails, an error key is submitted
	 * to <code>SessionErrors</code> (in
	 * <code>com.liferay.portal.kernel</code>).
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @param  actionResponse the response to receive the render parameters
	 * @throws Exception if an exception occurred
	 */
	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		int version = ParamUtil.getInteger(actionRequest, "version");

		try {
			WorkflowDefinitionManagerUtil.updateActive(
				themeDisplay.getCompanyId(), themeDisplay.getUserId(), name,
				version, false);
		}
		catch (Exception exception) {
			if (isSessionErrorException(exception)) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception, exception);
				}

				SessionErrors.add(
					actionRequest, exception.getClass(), exception);

				sendRedirect(actionRequest, actionResponse);
			}
			else {
				throw exception;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeactivateWorkflowDefinitionMVCActionCommand.class);

}