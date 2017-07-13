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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPOptionKeyException;
import com.liferay.commerce.product.exception.NoSuchCPOptionException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
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
		"javax.portlet.name=" + CPPortletKeys.CP_OPTIONS,
		"mvc.command.name=editProductOption"
	},
	service = MVCActionCommand.class
)
public class EditCPOptionMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPOptions(long cpOptionId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPOptionIds = null;

		if (cpOptionId > 0) {
			deleteCPOptionIds = new long[] {cpOptionId};
		}
		else {
			deleteCPOptionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPOptionIds"), 0L);
		}

		for (long deleteCPOptionId : deleteCPOptionIds) {
			_cpOptionService.deleteCPOption(deleteCPOptionId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long cpOptionId = ParamUtil.getLong(actionRequest, "cpOptionId");

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPOptions(cpOptionId, actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCPOption(cpOptionId, actionRequest);
			}
			else if (cmd.equals("setFacetable")) {
				boolean facetable = ParamUtil.getBoolean(
					actionRequest, "facetable");

				_cpOptionService.setFacetable(cpOptionId, facetable);
			}
			else if (cmd.equals("setRequired")) {
				boolean required = ParamUtil.getBoolean(
					actionRequest, "required");

				_cpOptionService.setRequired(cpOptionId, required);
			}
			else if (cmd.equals("setSkuContributor")) {
				boolean skuContributor = ParamUtil.getBoolean(
					actionRequest, "skuContributor");

				_cpOptionService.setSkuContributor(cpOptionId, skuContributor);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPOptionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CPOptionKeyException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editProductOption");

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected CPOption updateCPOption(
			long cpOptionId, ActionRequest actionRequest)
		throws Exception {

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String ddmFormFieldTypeName = ParamUtil.getString(
			actionRequest, "DDMFormFieldTypeName");
		boolean facetable = ParamUtil.getBoolean(actionRequest, "facetable");
		boolean required = ParamUtil.getBoolean(actionRequest, "required");
		boolean skuContributor = ParamUtil.getBoolean(
			actionRequest, "skuContributor");
		String key = ParamUtil.getString(actionRequest, "key");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPOption.class.getName(), actionRequest);

		CPOption cpOption = null;

		if (cpOptionId <= 0) {

			// Add commerce product option

			cpOption = _cpOptionService.addCPOption(
				titleMap, descriptionMap, ddmFormFieldTypeName, facetable,
				required, skuContributor, key, serviceContext);
		}
		else {

			// Update commerce product option

			cpOption = _cpOptionService.updateCPOption(
				cpOptionId, titleMap, descriptionMap, ddmFormFieldTypeName,
				facetable, required, skuContributor, key, serviceContext);
		}

		return cpOption;
	}

	@Reference
	private CPOptionService _cpOptionService;

}