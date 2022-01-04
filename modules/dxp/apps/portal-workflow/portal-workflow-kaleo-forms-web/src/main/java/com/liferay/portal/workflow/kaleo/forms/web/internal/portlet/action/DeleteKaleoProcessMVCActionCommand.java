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

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
		"mvc.command.name=/kaleo_forms_admin/delete_kaleo_process"
	},
	service = MVCActionCommand.class
)
public class DeleteKaleoProcessMVCActionCommand
	extends BaseKaleoFormsMVCActionCommand {

	/**
	 * Deletes the <code>KaleoProcess</code> (in the
	 * <code>com.liferay.portal.workflow.kaleo.forms.api</code> module)
	 * associated with the Kaleo process IDs from the action request.
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

		long[] kaleoProcessIds = _getKaleoProcessIds(actionRequest);

		for (final long kaleoProcessId : kaleoProcessIds) {
			kaleoProcessService.deleteKaleoProcess(kaleoProcessId);
		}
	}

	/**
	 * Returns an array of the Kaleo process IDs in the action request.
	 *
	 * @param  actionRequest the request from which to get the request
	 *         parameters
	 * @return an array of the Kaleo process IDs
	 */
	private long[] _getKaleoProcessIds(ActionRequest actionRequest) {
		long kaleoProcessId = ParamUtil.getLong(
			actionRequest, "kaleoProcessId");

		if (kaleoProcessId > 0) {
			return new long[] {kaleoProcessId};
		}

		return StringUtil.split(
			ParamUtil.getString(actionRequest, "kaleoProcessIds"), 0L);
	}

}