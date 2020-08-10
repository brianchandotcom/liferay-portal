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

package com.liferay.commerce.bom.admin.web.internal.portlet.action;

import com.liferay.commerce.bom.constants.CommerceBOMPortletKeys;
import com.liferay.commerce.bom.exception.NoSuchBOMFolderApplicationRelException;
import com.liferay.commerce.bom.model.CommerceBOMFolderApplicationRel;
import com.liferay.commerce.bom.service.CommerceBOMFolderApplicationRelService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		"javax.portlet.name=" + CommerceBOMPortletKeys.COMMERCE_BOM_ADMIN,
		"mvc.command.name=editCommerceBOMFolderApplicationRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceBOMFolderApplicationRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceBOMFolderApplicationRels(
			ActionRequest actionRequest)
		throws Exception {

		long commerceBOMFolderId = ParamUtil.getLong(
			actionRequest, "commerceBOMFolderId");

		long commerceApplicationModelId = ParamUtil.getLong(
			actionRequest, "commerceApplicationModelId");

		long[] addCommerceApplicationModelIds = null;

		if (commerceApplicationModelId > 0) {
			addCommerceApplicationModelIds = new long[] {
				commerceApplicationModelId
			};
		}
		else {
			addCommerceApplicationModelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "commerceApplicationModelIds"),
				0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceBOMFolderApplicationRel.class.getName(), actionRequest);

		for (long addCommerceApplicationModelId :
				addCommerceApplicationModelIds) {

			_commerceBOMFolderApplicationRelService.
				addCommerceBOMFolderApplicationRel(
					serviceContext.getUserId(), commerceBOMFolderId,
					addCommerceApplicationModelId);
		}
	}

	protected void deleteCommerceBOMFolders(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceBOMFolderApplicationRelIds = null;

		long commerceBOMFolderApplicationRelId = ParamUtil.getLong(
			actionRequest, "commerceBOMFolderApplicationRelId");

		if (commerceBOMFolderApplicationRelId > 0) {
			deleteCommerceBOMFolderApplicationRelIds = new long[] {
				commerceBOMFolderApplicationRelId
			};
		}
		else {
			deleteCommerceBOMFolderApplicationRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceBOMFolderApplicationRelIds"),
				0L);
		}

		for (long deleteCommerceBOMFolderApplicationRelId :
				deleteCommerceBOMFolderApplicationRelIds) {

			_commerceBOMFolderApplicationRelService.
				deleteCommerceBOMFolderApplicationRel(
					deleteCommerceBOMFolderApplicationRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommerceBOMFolderApplicationRels(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceBOMFolders(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchBOMFolderApplicationRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				_log.error(e, e);
			}
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceBOMFolderApplicationRelMVCActionCommand.class);

	@Reference
	private CommerceBOMFolderApplicationRelService
		_commerceBOMFolderApplicationRelService;

}