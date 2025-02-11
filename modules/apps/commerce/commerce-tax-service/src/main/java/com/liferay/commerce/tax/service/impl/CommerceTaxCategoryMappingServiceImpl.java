/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.service.impl;

import com.liferay.commerce.tax.service.base.CommerceTaxCategoryMappingServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceTaxCategoryMapping"
	},
	service = AopService.class
)
public class CommerceTaxCategoryMappingServiceImpl
	extends CommerceTaxCategoryMappingServiceBaseImpl {
}