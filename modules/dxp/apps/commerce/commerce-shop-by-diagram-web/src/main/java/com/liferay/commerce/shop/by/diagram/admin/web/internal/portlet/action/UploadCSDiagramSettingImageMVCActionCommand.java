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

package com.liferay.commerce.shop.by.diagram.admin.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.upload.CSDiagramSettingImageUploadFileEntryHandler;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.upload.CSDiagramSettingImageUploadResponseHandler;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.upload.UploadHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/upload_cs_diagram_setting_image"
	},
	service = MVCActionCommand.class
)
public class UploadCSDiagramSettingImageMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_uploadHandler.upload(
			_csDiagramSettingImageUploadFileEntryHandler,
			_csDiagramSettingImageUploadResponseHandler, actionRequest,
			actionResponse);
	}

	@Reference
	private CSDiagramSettingImageUploadFileEntryHandler
		_csDiagramSettingImageUploadFileEntryHandler;

	@Reference
	private CSDiagramSettingImageUploadResponseHandler
		_csDiagramSettingImageUploadResponseHandler;

	@Reference
	private UploadHandler _uploadHandler;

}