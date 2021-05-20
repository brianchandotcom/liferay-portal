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

package com.liferay.commerce.application.admin.web.internal.portlet.action;

import com.liferay.commerce.application.constants.CommerceApplicationPortletKeys;
import com.liferay.commerce.application.exception.NoSuchApplicationModelException;
import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.commerce.application.service.CommerceApplicationModelService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommerceApplicationPortletKeys.COMMERCE_APPLICATION_ADMIN,
		"mvc.command.name=/commerce_application_admin/edit_commerce_application_model"
	},
	service = MVCActionCommand.class
)
public class EditCommerceApplicationModelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceApplicationModels(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceApplicationModelIds = null;

		long commerceApplicationModelId = ParamUtil.getLong(
			actionRequest, "commerceApplicationModelId");

		if (commerceApplicationModelId > 0) {
			deleteCommerceApplicationModelIds = new long[] {
				commerceApplicationModelId
			};
		}
		else {
			deleteCommerceApplicationModelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceApplicationModelIds"),
				0L);
		}

		for (long deleteCommerceApplicationModelId :
				deleteCommerceApplicationModelIds) {

			_commerceApplicationModelService.deleteCommerceApplicationModel(
				deleteCommerceApplicationModelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceApplicationModel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceApplicationModels(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchApplicationModelException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				_log.error(exception, exception);
			}
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	protected CommerceApplicationModel updateCommerceApplicationModel(
			ActionRequest actionRequest)
		throws Exception {

		long commerceApplicationModelId = ParamUtil.getLong(
			actionRequest, "commerceApplicationModelId");

		String name = ParamUtil.getString(actionRequest, "name");
		String year = ParamUtil.getString(actionRequest, "year");

		CommerceApplicationModel commerceApplicationModel;

		if (commerceApplicationModelId > 0) {
			commerceApplicationModel =
				_commerceApplicationModelService.updateCommerceApplicationModel(
					commerceApplicationModelId, name, year);
		}
		else {
			long commerceApplicationBrandId = ParamUtil.getLong(
				actionRequest, "commerceApplicationBrandId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceApplicationModel.class.getName(), actionRequest);

			commerceApplicationModel =
				_commerceApplicationModelService.addCommerceApplicationModel(
					serviceContext.getUserId(), commerceApplicationBrandId,
					name, year);
		}

		return commerceApplicationModel;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceApplicationModelMVCActionCommand.class);

	@Reference
	private CommerceApplicationModelService _commerceApplicationModelService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}