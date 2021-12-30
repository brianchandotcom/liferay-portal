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

package com.liferay.commerce.catalog.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCatalogException;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_CATALOGS,
		"mvc.command.name=/commerce_catalogs/edit_commerce_catalog_external_reference_code"
	},
	service = MVCActionCommand.class
)
public class EditCommerceCatalogExternalReferenceCodeMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_updateCommerceCatalogExternalReferenceCode(actionRequest);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCatalogException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				_log.error(exception, exception);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
	}

	private void _updateCommerceCatalogExternalReferenceCode(
			ActionRequest actionRequest)
		throws Exception {

		long commerceCatalogId = ParamUtil.getLong(
			actionRequest, "commerceCatalogId");

		String externalReferenceCode = ParamUtil.getString(
			actionRequest, "externalReferenceCode");

		_commerceCatalogService.updateCommerceCatalogExternalReferenceCode(
			externalReferenceCode, commerceCatalogId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceCatalogExternalReferenceCodeMVCActionCommand.class);

	@Reference
	private CommerceCatalogService _commerceCatalogService;

}