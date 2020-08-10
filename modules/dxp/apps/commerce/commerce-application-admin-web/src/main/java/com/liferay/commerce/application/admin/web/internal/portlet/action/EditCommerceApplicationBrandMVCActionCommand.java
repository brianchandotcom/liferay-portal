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
import com.liferay.commerce.application.exception.NoSuchApplicationBrandException;
import com.liferay.commerce.application.model.CommerceApplicationBrand;
import com.liferay.commerce.application.service.CommerceApplicationBrandService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceApplicationPortletKeys.COMMERCE_APPLICATION_ADMIN,
		"mvc.command.name=editCommerceApplicationBrand"
	},
	service = MVCActionCommand.class
)
public class EditCommerceApplicationBrandMVCActionCommand
	extends BaseMVCActionCommand {

	protected CommerceApplicationBrand addCommerceApplicationBrand(
			ActionRequest actionRequest)
		throws Exception {

		String name = ParamUtil.getString(actionRequest, "name");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceApplicationBrand.class.getName(), actionRequest);

		return _commerceApplicationBrandService.addCommerceApplicationBrand(
			serviceContext.getUserId(), name, !deleteLogo, logoBytes);
	}

	protected void deleteCommerceApplicationBrands(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceApplicationBrandIds = null;

		long commerceApplicationBrandId = ParamUtil.getLong(
			actionRequest, "commerceApplicationBrandId");

		if (commerceApplicationBrandId > 0) {
			deleteCommerceApplicationBrandIds = new long[] {
				commerceApplicationBrandId
			};
		}
		else {
			deleteCommerceApplicationBrandIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceApplicationBrandIds"),
				0L);
		}

		for (long deleteCommerceApplicationBrandId :
				deleteCommerceApplicationBrandIds) {

			_commerceApplicationBrandService.deleteCommerceApplicationBrand(
				deleteCommerceApplicationBrandId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				CommerceApplicationBrand commerceApplicationBrand =
					addCommerceApplicationBrand(actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commerceApplicationBrand);

				JSONObject jsonObject = JSONUtil.put("redirectURL", redirect);

				JSONPortletResponseUtil.writeJSON(
					actionRequest, actionResponse, jsonObject);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceApplicationBrands(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceApplicationBrand(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchApplicationBrandException ||
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

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest,
			CommerceApplicationBrand commerceApplicationBrand)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, CommerceApplicationBrand.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceApplicationBrand");

		portletURL.setParameter(
			"commerceApplicationBrandId",
			String.valueOf(
				commerceApplicationBrand.getCommerceApplicationBrandId()));

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		portletURL.setParameter("redirect", redirect);

		return portletURL.toString();
	}

	protected CommerceApplicationBrand updateCommerceApplicationBrand(
			ActionRequest actionRequest)
		throws Exception {

		long commerceApplicationBrandId = ParamUtil.getLong(
			actionRequest, "commerceApplicationBrandId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean deleteLogo = ParamUtil.getBoolean(actionRequest, "deleteLogo");

		byte[] logoBytes = null;

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		if (fileEntryId > 0) {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			logoBytes = FileUtil.getBytes(fileEntry.getContentStream());
		}

		return _commerceApplicationBrandService.updateCommerceApplicationBrand(
			commerceApplicationBrandId, name, !deleteLogo, logoBytes);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceApplicationBrandMVCActionCommand.class);

	@Reference
	private CommerceApplicationBrandService _commerceApplicationBrandService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}