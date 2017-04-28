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

import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
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

	public CPDefinition getCPDefinition(RenderRequest renderRequest)
		throws PortalException {

		CPDefinition cpDefinition = (CPDefinition)renderRequest.getAttribute(
			CPWebKeys.COMMERCE_PRODUCT_DEFINITION);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		long cpDefinitionId = ParamUtil.getLong(
			renderRequest, "cpDefinitionId");

		if (cpDefinitionId <= 0) {
			CPDefinitionOptionRel cpDefinitionOptionRel =
				getCPDefinitionOptionRel(renderRequest);

			if (cpDefinitionOptionRel != null) {
				cpDefinitionId = cpDefinitionOptionRel.getCPDefinitionId();
			}
		}

		if (cpDefinitionId > 0) {
			cpDefinition = _cpDefinitionService.fetchCPDefinition(
				cpDefinitionId);
		}

		if (cpDefinition != null) {
			renderRequest.setAttribute(
				CPWebKeys.COMMERCE_PRODUCT_DEFINITION, cpDefinition);
		}

		return cpDefinition;
	}

	public CPDefinitionOptionRel getCPDefinitionOptionRel(
			RenderRequest renderRequest)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			(CPDefinitionOptionRel)renderRequest.getAttribute(
				CPWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_REL);

		if (cpDefinitionOptionRel != null) {
			return cpDefinitionOptionRel;
		}

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			renderRequest, "cpDefinitionOptionRelId");

		if (cpDefinitionOptionRelId <= 0) {
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				getCPDefinitionOptionValueRel(renderRequest);

			if (cpDefinitionOptionValueRel != null) {
				cpDefinitionOptionRelId =
					cpDefinitionOptionValueRel.getCPDefinitionOptionRelId();
			}
		}

		if (cpDefinitionOptionRelId > 0) {
			cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.fetchCPDefinitionOptionRel(
					cpDefinitionOptionRelId);
		}

		if (cpDefinitionOptionRel != null) {
			renderRequest.setAttribute(
				CPWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_REL,
				cpDefinitionOptionRel);
		}

		return cpDefinitionOptionRel;
	}

	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels = new ArrayList<>();

		long[] cpDefinitionOptionRelIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionOptionRelId : cpDefinitionOptionRelIds) {
			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			cpDefinitionOptionRels.add(cpDefinitionOptionRel);
		}

		return cpDefinitionOptionRels;
	}

	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
			RenderRequest renderRequest)
		throws PortalException {

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			(CPDefinitionOptionValueRel)renderRequest.getAttribute(
				CPWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_REL);

		if (cpDefinitionOptionValueRel != null) {
			return cpDefinitionOptionValueRel;
		}

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			renderRequest, "cpDefinitionOptionValueRelId");

		if (cpDefinitionOptionValueRelId > 0) {
			cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					fetchCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId);
		}

		if (cpDefinitionOptionValueRel != null) {
			renderRequest.setAttribute(
				CPWebKeys.COMMERCE_PRODUCT_DEFINITION_OPTION_VALUE_REL,
				cpDefinitionOptionValueRel);
		}

		return cpDefinitionOptionValueRel;
	}

	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>();

		long[] cpDefinitionOptionValueRelIds = ParamUtil.getLongValues(
			resourceRequest, "rowIdsCPDefinitionOptionValueRel");

		for (long cpDefinitionOptionValueRelId :
				cpDefinitionOptionValueRelIds) {

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

			cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
		}

		return cpDefinitionOptionValueRels;
	}

	public List<CPDefinition> getCPDefinitions(ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinition> cpDefinitions = new ArrayList<>();

		long[] cpDefinitionIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionId : cpDefinitionIds) {
			CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
				cpDefinitionId);

			cpDefinitions.add(cpDefinition);
		}

		return cpDefinitions;
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}