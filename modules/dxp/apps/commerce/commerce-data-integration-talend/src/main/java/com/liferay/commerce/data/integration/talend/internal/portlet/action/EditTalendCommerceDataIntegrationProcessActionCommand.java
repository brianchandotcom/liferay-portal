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

package com.liferay.commerce.data.integration.talend.internal.portlet.action;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.talend.TalendProcessTypeHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"javax.portlet.name=" + CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
		"mvc.command.name=editTalendCommerceDataIntegrationProcess"
	},
	service = MVCActionCommand.class
)
public class EditTalendCommerceDataIntegrationProcessActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		try {
			UploadPortletRequest uploadPortletRequest =
				_portal.getUploadPortletRequest(actionRequest);

			long commerceDataIntegrationProcessId = ParamUtil.getLong(
				uploadPortletRequest, "commerceDataIntegrationProcessId");

			String contentType = uploadPortletRequest.getContentType(
				"srcArchive");

			String fileName = uploadPortletRequest.getFileName("srcArchive");

			long size = uploadPortletRequest.getSize("srcArchive");

			_talendProcessTypeHelper.addFileEntry(
				_portal.getCompanyId(actionRequest),
				_portal.getUserId(actionRequest),
				commerceDataIntegrationProcessId, fileName, size, contentType,
				uploadPortletRequest.getFileAsStream("srcArchive"));
		}
		catch (Exception e) {
			_log.error(e, e);

			SessionErrors.add(actionRequest, e.getClass());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditTalendCommerceDataIntegrationProcessActionCommand.class);

	@Reference
	private Portal _portal;

	@Reference
	private TalendProcessTypeHelper _talendProcessTypeHelper;

}