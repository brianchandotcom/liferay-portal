/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service.impl;

import com.liferay.commerce.tax.service.base.CommerceTaxCategoryMappingLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 * @author Ivica Cardic
 */
@Component(
	property = "model.class.name=com.liferay.commerce.tax.model.CommerceTaxCategoryMapping",
	service = AopService.class
)
public class CommerceTaxCategoryMappingLocalServiceImpl
	extends CommerceTaxCategoryMappingLocalServiceBaseImpl {
}