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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelCPInstanceException;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelKeyException;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelPriceException;
import com.liferay.commerce.product.exception.CPDefinitionOptionValueRelQuantityException;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_definition_option_value_rel"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionOptionValueRelMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCPDefinitionOptionValueRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCPDefinitionOptionValueRels(actionRequest);
			}
			else if (cmd.equals("deleteSku")) {
				_resetCPInstanceAndQuantity(actionRequest);
			}
			else if (cmd.equals("updatePreselected")) {
				_updatePreselected(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof
					CPDefinitionOptionValueRelCPInstanceException ||
				exception instanceof CPDefinitionOptionValueRelKeyException ||
				exception instanceof CPDefinitionOptionValueRelPriceException ||
				exception instanceof
					CPDefinitionOptionValueRelQuantityException) {

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/cp_definitions/edit_cp_definition_option_value_rel");
			}
			else {
				_log.error(exception, exception);
			}
		}
	}

	private CPDefinitionOptionValueRel _deleteCPDefinitionOptionValueRels(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionValueRelId");

		if (cpDefinitionOptionValueRelId > 0) {
			return _cpDefinitionOptionValueRelService.
				deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
		}

		return null;
	}

	private CPDefinitionOptionValueRel _resetCPInstanceAndQuantity(
			ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionValueRelId");

		return _cpDefinitionOptionValueRelService.
			resetCPInstanceCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId);
	}

	private CPDefinitionOptionValueRel _updateCPDefinitionOptionValueRel(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionValueRelId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		String key = ParamUtil.getString(actionRequest, "key");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionOptionValueRel.class.getName(), actionRequest);

		if (cpDefinitionOptionValueRelId <= 0) {

			// Add commerce product definition option value rel

			long cpDefinitionOptionRelId = ParamUtil.getLong(
				actionRequest, "cpDefinitionOptionRelId");

			return _cpDefinitionOptionValueRelService.
				addCPDefinitionOptionValueRel(
					cpDefinitionOptionRelId, nameMap, priority, key,
					serviceContext);
		}

		// Update commerce product definition option value rel

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");
		int quantity = ParamUtil.getInteger(actionRequest, "quantity");
		boolean preselected = ParamUtil.getBoolean(
			actionRequest, "preselected");
		BigDecimal price = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "price", BigDecimal.ZERO);

		return _cpDefinitionOptionValueRelService.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId, nameMap, priority, key,
				cpInstanceId, quantity, preselected, price, serviceContext);
	}

	private CPDefinitionOptionValueRel _updatePreselected(
			ActionRequest actionRequest)
		throws PortalException {

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionValueRelId");

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			_cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId);

		if (cpDefinitionOptionValueRel.isPreselected()) {
			return _cpDefinitionOptionValueRelService.
				updateCPDefinitionOptionValueRelPreselected(
					cpDefinitionOptionValueRelId, false);
		}

		return _cpDefinitionOptionValueRelService.
			updateCPDefinitionOptionValueRelPreselected(
				cpDefinitionOptionValueRelId, true);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPDefinitionOptionValueRelMVCActionCommand.class);

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

}