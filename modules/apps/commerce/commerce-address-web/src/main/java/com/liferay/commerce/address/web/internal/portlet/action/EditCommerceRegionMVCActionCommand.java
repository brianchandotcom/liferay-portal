/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.address.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RegionNameException;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.RegionService;
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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_COUNTRY,
		"mvc.command.name=/commerce_country/edit_commerce_region"
	},
	service = MVCActionCommand.class
)
public class EditCommerceRegionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCommerceRegion(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteRegions(actionRequest);
			}
			else if (cmd.equals("setActive")) {
				setActive(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchRegionException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof RegionNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/commerce_country/edit_commerce_country");
			}
			else {
				throw exception;
			}
		}
	}

	protected void setActive(ActionRequest actionRequest)
		throws PortalException {

		long regionId = ParamUtil.getLong(actionRequest, "regionId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		_regionService.updateActive(regionId, active);
	}

	private void _deleteRegions(ActionRequest actionRequest) throws Exception {
		long[] deleteRegionIds = null;

		long regionId = ParamUtil.getLong(actionRequest, "regionId");

		if (regionId > 0) {
			deleteRegionIds = new long[] {regionId};
		}
		else {
			deleteRegionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteRegionIds"), 0L);
		}

		for (long deleteRegionId : deleteRegionIds) {
			_regionService.deleteRegion(deleteRegionId);
		}
	}

	private Region _updateCommerceRegion(ActionRequest actionRequest)
		throws Exception {

		long regionId = ParamUtil.getLong(actionRequest, "regionId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String regionCode = ParamUtil.getString(actionRequest, "regionCode");
		String name = ParamUtil.getString(actionRequest, "name");
		double position = ParamUtil.getDouble(actionRequest, "position");

		Region region = null;

		if (regionId <= 0) {
			long countryId = ParamUtil.getLong(actionRequest, "countryId");

			region = _regionService.addRegion(
				countryId, active, name, position, regionCode,
				ServiceContextFactory.getInstance(
					Region.class.getName(), actionRequest));
		}
		else {
			region = _regionService.updateRegion(
				regionId, active, name, position, regionCode);
		}

		return region;
	}

	@Reference
	private Portal _portal;

	@Reference
	private RegionService _regionService;

}