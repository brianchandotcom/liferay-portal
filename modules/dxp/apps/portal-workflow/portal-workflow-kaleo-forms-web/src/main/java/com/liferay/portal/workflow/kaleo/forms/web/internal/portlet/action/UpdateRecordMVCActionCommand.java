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

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.workflow.kaleo.forms.constants.KaleoFormsActionKeys;
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
		"mvc.command.name=/kaleo_forms/update_record"
	},
	service = MVCActionCommand.class
)
public class UpdateRecordMVCActionCommand
	extends BaseKaleoFormsMVCActionCommand {

	/**
	 * Updates the <code>DDLRecord</code> (in the
	 * <code>com.liferay.dynamic.data.lists.api</code> module), checking the
	 * permission for the action ID
	 * <code>KaleoFormsActionKeys.COMPLETE_FORM</code> (in the
	 * <code>com.liferay.portal.workflow.kaleo.forms.api</code> module).
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

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(),
			portal.getUploadPortletRequest(actionRequest));

		checkKaleoProcessPermission(
			serviceContext, KaleoFormsActionKeys.COMPLETE_FORM);

		updateDDLRecord(serviceContext);
	}

}