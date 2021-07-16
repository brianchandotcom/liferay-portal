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
import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramEntryException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CPDefinitionDiagramSettingService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_definition_diagram_setting"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionDiagramSettingMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("updateCPDefinitionDiagramSetting")) {
				updateCPDefinitionDiagramSetting(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCPAttachmentFileEntryException ||
				exception instanceof NoSuchCPDefinitionDiagramEntryException ||
				exception instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw exception;
			}
		}
	}

	protected CPDefinitionDiagramSetting updateCPDefinitionDiagramSetting(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionDiagramSettingId = ParamUtil.getLong(
			actionRequest, "cpDefinitionDiagramSettingId");

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			actionRequest, "cpAttachmentFileEntryId");
		String type = ParamUtil.getString(actionRequest, "type");

		if (cpDefinitionDiagramSettingId <= 0) {
			long cpDefinitionId = ParamUtil.getLong(
				actionRequest, "cpDefinitionId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CPDefinitionDiagramSetting.class.getName(), actionRequest);

			return _cpDefinitionDiagramSettingService.
				addCPDefinitionDiagramSetting(
					serviceContext.getUserId(), cpDefinitionId,
					cpAttachmentFileEntryId, null, 0D, type);
		}

		return _cpDefinitionDiagramSettingService.
			updateCPDefinitionDiagramSetting(
				cpDefinitionDiagramSettingId, cpAttachmentFileEntryId, null, 0D,
				type);
	}

	@Reference
	private CPDefinitionDiagramSettingService
		_cpDefinitionDiagramSettingService;

}