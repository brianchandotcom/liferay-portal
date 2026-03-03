/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.tax.category.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.tax.category.web.internal.display.context.CPTaxCategoryDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletException;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(
	property = {
		"jakarta.portlet.name=" + CPPortletKeys.CP_TAX_CATEGORY,
		"mvc.command.name=/cp_tax_category/edit_cp_tax_category_external_reference_code"
	},
	service = MVCRenderCommand.class
)
public class EditCPTaxCategoryExternalReferenceCodeMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			CPTaxCategoryDisplayContext cpTaxCategoryDisplayContext =
				new CPTaxCategoryDisplayContext(
					_cpTaxCategoryService, _modelResourcePermission,
					_portletResourcePermission, renderRequest, renderResponse);

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT, cpTaxCategoryDisplayContext);
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		return "/cp_tax_category" +
			"/edit_cp_tax_category_external_reference_code.jsp";
	}

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPTaxCategory)"
	)
	private ModelResourcePermission<CPTaxCategory> _modelResourcePermission;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME_TAX + ")")
	private PortletResourcePermission _portletResourcePermission;

}