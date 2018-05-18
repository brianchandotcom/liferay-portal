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

package com.liferay.commerce.address.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.CommerceRegionNameException;
import com.liferay.commerce.exception.NoSuchRegionException;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
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
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceRegion"
	},
	service = MVCActionCommand.class
)
public class EditCommerceRegionMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceRegions(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceRegionIds = null;

		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");

		if (commerceRegionId > 0) {
			deleteCommerceRegionIds = new long[] {commerceRegionId};
		}
		else {
			deleteCommerceRegionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceRegionIds"),
				0L);
		}

		for (long deleteCommerceRegionId : deleteCommerceRegionIds) {
			_commerceRegionService.deleteCommerceRegion(deleteCommerceRegionId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceRegion(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceRegions(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchRegionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceRegionNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceCountry");
			}
			else {
				throw e;
			}
		}
	}

	protected CommerceRegion updateCommerceRegion(ActionRequest actionRequest)
		throws Exception {

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");

		String name = ParamUtil.getString(actionRequest, "name");
		String code = ParamUtil.getString(actionRequest, "code");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceRegion.class.getName(), actionRequest);

		CommerceRegion commerceRegion = null;

		if (commerceRegionId <= 0) {
			commerceRegion = _commerceRegionService.addCommerceRegion(
				commerceCountryId, name, code, priority, active,
				serviceContext);
		}
		else {
			commerceRegion = _commerceRegionService.updateCommerceRegion(
				commerceRegionId, name, code, priority, active, serviceContext);
		}

		return commerceRegion;
	}

	@Reference
	private CommerceRegionService _commerceRegionService;

	@Reference
	private Portal _portal;

}