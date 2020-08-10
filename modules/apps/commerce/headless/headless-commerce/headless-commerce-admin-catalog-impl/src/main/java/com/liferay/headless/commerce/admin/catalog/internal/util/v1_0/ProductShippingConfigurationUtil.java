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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductShippingConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductShippingConfigurationUtil {

	public static CPDefinition updateCPDefinitionShippingInfo(
			CPDefinitionService cpDefinitionService,
			ProductShippingConfiguration productShippingConfiguration,
			CPDefinition cpDefinition, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionService.updateShippingInfo(
			cpDefinition.getCPDefinitionId(),
			GetterUtil.get(
				productShippingConfiguration.getShippable(),
				cpDefinition.isShippable()),
			GetterUtil.get(
				productShippingConfiguration.getFreeShipping(),
				cpDefinition.isFreeShipping()),
			GetterUtil.get(
				productShippingConfiguration.getShippingSeparately(),
				cpDefinition.isShipSeparately()),
			_getShippingExtraPrice(cpDefinition, productShippingConfiguration),
			_getWidth(cpDefinition, productShippingConfiguration),
			_getHeight(cpDefinition, productShippingConfiguration),
			_getDepth(cpDefinition, productShippingConfiguration),
			_getWeight(cpDefinition, productShippingConfiguration),
			serviceContext);
	}

	private static double _getDepth(
		CPDefinition cpDefinition,
		ProductShippingConfiguration productShippingConfiguration) {

		BigDecimal depth = productShippingConfiguration.getDepth();

		if (depth == null) {
			return cpDefinition.getDepth();
		}

		return depth.doubleValue();
	}

	private static double _getHeight(
		CPDefinition cpDefinition,
		ProductShippingConfiguration productShippingConfiguration) {

		BigDecimal height = productShippingConfiguration.getHeight();

		if (height == null) {
			return cpDefinition.getHeight();
		}

		return height.doubleValue();
	}

	private static double _getShippingExtraPrice(
		CPDefinition cpDefinition,
		ProductShippingConfiguration productShippingConfiguration) {

		BigDecimal shippingExtraPrice =
			productShippingConfiguration.getShippingExtraPrice();

		if (shippingExtraPrice == null) {
			return cpDefinition.getShippingExtraPrice();
		}

		return shippingExtraPrice.doubleValue();
	}

	private static double _getWeight(
		CPDefinition cpDefinition,
		ProductShippingConfiguration productShippingConfiguration) {

		BigDecimal weight = productShippingConfiguration.getWeight();

		if (weight == null) {
			return cpDefinition.getWeight();
		}

		return weight.doubleValue();
	}

	private static double _getWidth(
		CPDefinition cpDefinition,
		ProductShippingConfiguration productShippingConfiguration) {

		BigDecimal width = productShippingConfiguration.getWidth();

		if (width == null) {
			return cpDefinition.getWidth();
		}

		return width.doubleValue();
	}

}