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

import com.liferay.commerce.product.constants.CommerceProductWebKeys;
import com.liferay.commerce.product.exception.NoSuchProductDefinitionException;
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.service.CommerceProductDefinitionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ActionHelper.class)
public class ActionHelper {

	public CommerceProductDefinition getCommerceProductDefinition(
			RenderRequest renderRequest)
		throws PortalException {

		CommerceProductDefinition commerceProductDefinition = null;

		commerceProductDefinition =
			(CommerceProductDefinition)renderRequest.getAttribute(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION);

		if (commerceProductDefinition != null) {
			return commerceProductDefinition;
		}

		long commerceProductDefinitionId = ParamUtil.getLong(
			renderRequest, "commerceProductDefinitionId");

		if (commerceProductDefinitionId > 0) {
			try {
				commerceProductDefinition =
					_commerceProductDefinitionService.
						getCommerceProductDefinition(
							commerceProductDefinitionId);
			}
			catch (NoSuchProductDefinitionException nspde) {
				return null;
			}
		}

		return commerceProductDefinition;
	}

	public List<CommerceProductDefinition> getCommerceProductDefinitions(
			ResourceRequest resourceRequest)
		throws Exception {

		long[] commerceProductDefinitionIds = ParamUtil.getLongValues(
			resourceRequest, "rowIdsCommerceProductDefinition");

		List<CommerceProductDefinition> commerceProductDefinitions =
			new ArrayList<>();

		for (long commerceProductDefinitionId : commerceProductDefinitionIds) {
			CommerceProductDefinition commerceProductDefinition =
				_commerceProductDefinitionService.getCommerceProductDefinition(
					commerceProductDefinitionId);

			commerceProductDefinitions.add(commerceProductDefinition);
		}

		return commerceProductDefinitions;
	}

	@Reference
	private CommerceProductDefinitionService _commerceProductDefinitionService;

}