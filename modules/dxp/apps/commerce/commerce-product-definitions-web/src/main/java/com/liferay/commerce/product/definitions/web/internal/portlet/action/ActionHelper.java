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
import com.liferay.commerce.product.model.CommerceProductDefinition;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CommerceProductDefinitionOptionRelService;
import com.liferay.commerce.product.service.CommerceProductDefinitionOptionValueRelService;
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

		if (commerceProductDefinitionId <= 0) {

			//Try to get from an related entity if exist

			CommerceProductDefinitionOptionRel
				commerceProductDefinitionOptionRel =
					getCommerceProductDefinitionOptionRel(renderRequest);

			if (commerceProductDefinitionOptionRel != null) {
				commerceProductDefinitionId =
					commerceProductDefinitionOptionRel.
						getCommerceProductDefinitionId();
			}
		}

		if (commerceProductDefinitionId > 0) {
			commerceProductDefinition =
				_commerceProductDefinitionService.
					fetchCommerceProductDefinition(commerceProductDefinitionId);
		}

		if (commerceProductDefinition != null) {
			renderRequest.setAttribute(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION,
				commerceProductDefinition);
		}

		return commerceProductDefinition;
	}

	public CommerceProductDefinitionOptionRel
			getCommerceProductDefinitionOptionRel(
				RenderRequest renderRequest)
		throws PortalException {

		CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel =
			null;

		commerceProductDefinitionOptionRel =
			(CommerceProductDefinitionOptionRel)renderRequest.getAttribute(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_REL);

		if (commerceProductDefinitionOptionRel != null) {
			return commerceProductDefinitionOptionRel;
		}

		long commerceProductDefinitionOptionRelId = ParamUtil.getLong(
			renderRequest, "commerceProductDefinitionOptionRelId");

		if (commerceProductDefinitionOptionRelId > 0) {
			commerceProductDefinitionOptionRel =
				_commerceProductDefinitionOptionRelService.
					fetchCommerceProductDefinitionOptionRel(
						commerceProductDefinitionOptionRelId);
		}

		if (commerceProductDefinitionOptionRel != null) {
			renderRequest.setAttribute(
				CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_REL,
				commerceProductDefinitionOptionRel);
		}

		return commerceProductDefinitionOptionRel;
	}

	public List<CommerceProductDefinitionOptionRel>
			getCommerceProductDefinitionOptionRels(
				ResourceRequest resourceRequest)
		throws Exception {

		long[] commerceProductDefinitionOptionRelIds = ParamUtil.getLongValues(
			resourceRequest, "rowIdsCommerceProductDefinitionOptionRel");

		List<CommerceProductDefinitionOptionRel>
			commerceProductDefinitionOptionRels = new ArrayList<>();

		for (long commerceProductDefinitionOptionRelId :
				commerceProductDefinitionOptionRelIds) {

			CommerceProductDefinitionOptionRel
				commerceProductDefinitionOptionRel =
					_commerceProductDefinitionOptionRelService.
						getCommerceProductDefinitionOptionRel(
							commerceProductDefinitionOptionRelId);

			commerceProductDefinitionOptionRels.add(
				commerceProductDefinitionOptionRel);
		}

		return commerceProductDefinitionOptionRels;
	}

	public List<CommerceProductDefinitionOptionValueRel>
			getCommerceProductDefinitionOptionValueRels(
				ResourceRequest resourceRequest)
		throws Exception {

		long[] commerceProductDefinitionOptionValueRelIds =
			ParamUtil.getLongValues(
				resourceRequest,
				"rowIdsCommerceProductDefinitionOptionValueRel");

		List<CommerceProductDefinitionOptionValueRel>
			commerceProductDefinitionOptionValueRels = new ArrayList<>();

		for (long commerceProductDefinitionOptionValueRelId :
				commerceProductDefinitionOptionValueRelIds) {

			CommerceProductDefinitionOptionValueRel
				commerceProductDefinitionOptionValueRel =
					_commerceProductDefinitionOptionValueRelService.
						getCommerceProductDefinitionOptionValueRel(
							commerceProductDefinitionOptionValueRelId);

			commerceProductDefinitionOptionValueRels.add(
				commerceProductDefinitionOptionValueRel);
		}

		return commerceProductDefinitionOptionValueRels;
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
	private CommerceProductDefinitionOptionRelService
		_commerceProductDefinitionOptionRelService;

	@Reference
	private CommerceProductDefinitionOptionValueRelService
		_commerceProductDefinitionOptionValueRelService;

	@Reference
	private CommerceProductDefinitionService _commerceProductDefinitionService;

}