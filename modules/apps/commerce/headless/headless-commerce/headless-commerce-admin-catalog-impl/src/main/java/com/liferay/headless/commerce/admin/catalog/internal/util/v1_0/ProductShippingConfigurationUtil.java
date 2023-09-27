/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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