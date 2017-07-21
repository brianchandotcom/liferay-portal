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
import com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
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
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editProductDefinitionSpecificationOptionValue"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionSpecificationOptionValueMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCPDefinitionSpecificationOptionValues(
			ActionRequest actionRequest)
		throws Exception {

		long[] addCPSpecificationOptionIds = null;

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long cpSpecificationOptionId = ParamUtil.getLong(
			actionRequest, "cpSpecificationOptionId");

		if (cpSpecificationOptionId > 0) {
			addCPSpecificationOptionIds = new long[] {cpSpecificationOptionId};
		}
		else {
			addCPSpecificationOptionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "cpSpecificationOptionIds"),
				0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionSpecificationOptionValue.class.getName(),
			actionRequest);

		for (int i = 0; i < addCPSpecificationOptionIds.length; i++) {
			cpSpecificationOptionId = addCPSpecificationOptionIds[i];

			CPSpecificationOption cpSpecificationOption =
				_cpSpecificationOptionService.getCPSpecificationOption(
					cpSpecificationOptionId);

			_cpDefinitionSpecificationOptionValueService.
				addCPDefinitionSpecificationOptionValue(
					cpDefinitionId, cpSpecificationOptionId,
					cpSpecificationOption.getCPOptionCategoryId(), null, i,
					serviceContext);
		}
	}

	protected void deleteCPDefinitionSpecificationOptionValues(
			ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPDefinitionSpecificationOptionValueIds = null;

		long cpDefinitionSpecificationOptionValueId = ParamUtil.getLong(
			actionRequest, "cpDefinitionSpecificationOptionValueId");

		if (cpDefinitionSpecificationOptionValueId > 0) {
			deleteCPDefinitionSpecificationOptionValueIds =
				new long[] {cpDefinitionSpecificationOptionValueId};
		}
		else {
			deleteCPDefinitionSpecificationOptionValueIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest,
					"deleteCPDefinitionSpecificationOptionValueIds"),
				0L);
		}

		for (long deleteCPDefinitionSpecificationOptionValueId :
				deleteCPDefinitionSpecificationOptionValueIds) {

			_cpDefinitionSpecificationOptionValueService.
				deleteCPDefinitionSpecificationOptionValue(
					deleteCPDefinitionSpecificationOptionValueId);
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

				addCPDefinitionSpecificationOptionValues(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPDefinitionSpecificationOptionValues(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCPDefinitionSpecificationOptionValue(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof
					NoSuchCPDefinitionSpecificationOptionValueException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected CPDefinitionSpecificationOptionValue
			updateCPDefinitionSpecificationOptionValue(
				ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionSpecificationOptionValueId = ParamUtil.getLong(
			actionRequest, "cpDefinitionSpecificationOptionValueId");

		long cpOptionCategoryId = ParamUtil.getLong(
			actionRequest, "CPOptionCategoryId");
		Map<Locale, String> valueMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "value");
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionSpecificationOptionValue.class.getName(),
			actionRequest);

		return _cpDefinitionSpecificationOptionValueService.
			updateCPDefinitionSpecificationOptionValue(
				cpDefinitionSpecificationOptionValueId, cpOptionCategoryId,
				valueMap, priority, serviceContext);
	}

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;

	@Reference
	private CPSpecificationOptionService _cpSpecificationOptionService;

}