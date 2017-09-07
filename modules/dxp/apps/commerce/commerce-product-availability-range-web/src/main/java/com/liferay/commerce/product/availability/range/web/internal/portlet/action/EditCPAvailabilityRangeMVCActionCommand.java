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

package com.liferay.commerce.product.availability.range.web.internal.portlet.action;

import com.liferay.commerce.admin.web.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPAvailabilityRangeException;
import com.liferay.commerce.product.model.CPAvailabilityRange;
import com.liferay.commerce.product.service.CPAvailabilityRangeService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCPAvailabilityRange"
	},
	service = MVCActionCommand.class
)
public class EditCPAvailabilityRangeMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPAvailabilityRanges(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCPAvailabilityRangeIds = null;

		long cpAvailabilityRangeId = ParamUtil.getLong(
			actionRequest, "cpAvailabilityRangeId");

		if (cpAvailabilityRangeId > 0) {
			deleteCPAvailabilityRangeIds = new long[] {cpAvailabilityRangeId};
		}
		else {
			deleteCPAvailabilityRangeIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCPAvailabilityRangeIds"),
				0L);
		}

		for (long deleteCPAvailabilityRangeId : deleteCPAvailabilityRangeIds) {
			_cpAvailabilityRangeService.deleteCPAvailabilityRange(
				deleteCPAvailabilityRangeId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPAvailabilityRanges(actionRequest);
			}

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPAvailabilityRange(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPAvailabilityRangeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCPAvailabilityRange(
			ActionRequest actionRequest)
		throws PortalException {

		long cpAvailabilityRangeId = ParamUtil.getLong(
			actionRequest, "cpAvailabilityRangeId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPAvailabilityRange.class.getName(), actionRequest);

		if (cpAvailabilityRangeId <= 0) {
			_cpAvailabilityRangeService.addCPAvailabilityRange(
				titleMap, serviceContext);
		}
		else {
			_cpAvailabilityRangeService.updateCPAvailabilityRange(
				cpAvailabilityRangeId, titleMap, serviceContext);
		}
	}

	@Reference
	private CPAvailabilityRangeService _cpAvailabilityRangeService;

}