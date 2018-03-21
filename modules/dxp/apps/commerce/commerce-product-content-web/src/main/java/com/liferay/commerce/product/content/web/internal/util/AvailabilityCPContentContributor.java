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

package com.liferay.commerce.product.content.web.internal.util;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.product.content.contributor.name=" + CPContentContributorConstants.AVAILABILITY_NAME,
	service = CPContentContributor.class
)
public class AvailabilityCPContentContributor implements CPContentContributor {

	@Override
	public String getName() {
		return CPContentContributorConstants.AVAILABILITY_NAME;
	}

	@Override
	public JSONObject getValue(CPInstance cpInstance, Locale locale)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		if (cpInstance == null) {
			return jsonObject;
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		boolean displayAvailability =
			cpDefinitionInventoryEngine.isDisplayAvailability(cpInstance);

		boolean available = false;

		if (cpDefinitionInventoryEngine.getStockQuantity(cpInstance) >
				cpDefinitionInventoryEngine.getMinStockQuantity(cpInstance)) {

			available = true;
		}

		if (displayAvailability && available) {
			jsonObject.put(
				CPContentContributorConstants.AVAILABILITY_NAME,
				LanguageUtil.get(
					locale, CPContentContributorConstants.AVAILABLE));
		}

		if (displayAvailability && !available) {
			jsonObject.put(
				CPContentContributorConstants.AVAILABILITY_NAME,
				LanguageUtil.get(
					locale, CPContentContributorConstants.UNAVAILABLE));
		}

		return jsonObject;
	}

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}