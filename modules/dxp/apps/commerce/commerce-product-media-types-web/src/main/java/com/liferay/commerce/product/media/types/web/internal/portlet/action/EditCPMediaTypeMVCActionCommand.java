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

package com.liferay.commerce.product.media.types.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPMediaTypeException;
import com.liferay.commerce.product.model.CPMediaType;
import com.liferay.commerce.product.service.CPMediaTypeService;
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
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_MEDIA_TYPES,
		"mvc.command.name=editMediaType"
	},
	service = MVCActionCommand.class
)
public class EditCPMediaTypeMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPMediaTypes(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPMediaTypeIds = null;

		long cpMediaTypeId = ParamUtil.getLong(actionRequest, "cpMediaTypeId");

		if (cpMediaTypeId > 0) {
			deleteCPMediaTypeIds = new long[] {cpMediaTypeId};
		}
		else {
			deleteCPMediaTypeIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPMediaTypeIds"), 0L);
		}

		for (long deleteCPMediaTypeId : deleteCPMediaTypeIds) {
			_cpMediaTypeService.deleteCPMediaType(deleteCPMediaTypeId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPMediaTypes(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCPMediaType(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPMediaTypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected CPMediaType updateCPMediaType(ActionRequest actionRequest)
		throws Exception {

		long cpMediaTypeId = ParamUtil.getLong(actionRequest, "cpMediaTypeId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		int priority = ParamUtil.getInteger(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPMediaType.class.getName(), actionRequest);

		CPMediaType cpMediaType = null;

		if (cpMediaTypeId <= 0) {

			// Add commerce product media type

			cpMediaType = _cpMediaTypeService.addCPMediaType(
				titleMap, descriptionMap, priority, serviceContext);
		}
		else {

			// Update commerce product media type

			cpMediaType = _cpMediaTypeService.updateCPMediaType(
				cpMediaTypeId, titleMap, descriptionMap, priority,
				serviceContext);
		}

		return cpMediaType;
	}

	@Reference
	private CPMediaTypeService _cpMediaTypeService;

}