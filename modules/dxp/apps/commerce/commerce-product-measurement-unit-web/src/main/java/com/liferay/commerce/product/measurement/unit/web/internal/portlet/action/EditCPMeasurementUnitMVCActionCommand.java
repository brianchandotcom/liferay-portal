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

package com.liferay.commerce.product.measurement.unit.web.internal.portlet.action;

import com.liferay.commerce.admin.web.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.product.exception.CPMeasurementUnitKeyException;
import com.liferay.commerce.product.exception.NoSuchCPMeasurementUnitException;
import com.liferay.commerce.product.model.CPMeasurementUnit;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
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

import java.util.Locale;
import java.util.Map;

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
		"mvc.command.name=editCPMeasurementUnit"
	},
	service = MVCActionCommand.class
)
public class EditCPMeasurementUnitMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPMeasurementUnits(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCPMeasurementUnitIds = null;

		long cpMeasurementUnitId = ParamUtil.getLong(
			actionRequest, "cpMeasurementUnitId");

		if (cpMeasurementUnitId > 0) {
			deleteCPMeasurementUnitIds = new long[] {cpMeasurementUnitId};
		}
		else {
			deleteCPMeasurementUnitIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCPMeasurementUnitIds"),
				0L);
		}

		for (long deleteCPMeasurementUnitId : deleteCPMeasurementUnitIds) {
			_cpMeasurementUnitService.deleteCPMeasurementUnit(
				deleteCPMeasurementUnitId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPMeasurementUnits(actionRequest);
			}

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPMeasurementUnit(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPMeasurementUnitException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CPMeasurementUnitKeyException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCPMeasurementUnit");
			}
			else {
				throw e;
			}
		}
	}

	protected CPMeasurementUnit updateCPMeasurementUnit(
			ActionRequest actionRequest)
		throws PortalException {

		long cpMeasurementUnitId = ParamUtil.getLong(
			actionRequest, "cpMeasurementUnitId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		String key = ParamUtil.getString(actionRequest, "key");
		double rate = ParamUtil.getDouble(actionRequest, "rate");
		boolean primary = ParamUtil.getBoolean(actionRequest, "primary");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		int type = ParamUtil.getInteger(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPMeasurementUnit.class.getName(), actionRequest);

		CPMeasurementUnit cpMeasurementUnit = null;

		if (cpMeasurementUnitId <= 0) {
			cpMeasurementUnit = _cpMeasurementUnitService.addCPMeasurementUnit(
				nameMap, key, rate, primary, priority, type, serviceContext);
		}
		else {
			cpMeasurementUnit =
				_cpMeasurementUnitService.updateCPMeasurementUnit(
					cpMeasurementUnitId, nameMap, key, rate, primary, priority,
					type, serviceContext);
		}

		return cpMeasurementUnit;
	}

	@Reference
	private CPMeasurementUnitService _cpMeasurementUnitService;

}