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

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=commerceProductDefinitionOptionValueRelInfoPanel"
	},
	service = MVCResourceCommand.class
)
public class CommerceProductDefinitionOptionValueRelInfoPanelMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<CommerceProductDefinitionOptionValueRel>
			commerceProductDefinitionOptionValueRels =
				_actionHelper.getCommerceProductDefinitionOptionValueRels(
					resourceRequest);

		resourceRequest.setAttribute(
			CommerceProductWebKeys.
				COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_RELS,
			commerceProductDefinitionOptionValueRels);

		include(
			resourceRequest, resourceResponse,
			"/commerce_product_definition_option_value_rel_info_panel.jsp");
	}

	@Reference
	private ActionHelper _actionHelper;

}