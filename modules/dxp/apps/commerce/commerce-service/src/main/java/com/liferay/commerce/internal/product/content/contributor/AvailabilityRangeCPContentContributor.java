/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.internal.product.content.contributor;

import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.constants.CPContentContributorConstants;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "commerce.product.content.contributor.name=" + CPContentContributorConstants.AVAILABILITY_RANGE_NAME,
	service = CPContentContributor.class
)
public class AvailabilityRangeCPContentContributor
	implements CPContentContributor {

	@Override
	public String getName() {
		return CPContentContributorConstants.AVAILABILITY_RANGE_NAME;
	}

	@Override
	public JSONObject getValue(
			CPInstance cpInstance, HttpServletRequest httpServletRequest)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

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

		boolean available = false;

		if (cpDefinitionInventoryEngine.getStockQuantity(cpInstance) >
				cpDefinitionInventoryEngine.getMinStockQuantity(cpInstance)) {

			available = true;
		}

		if (!available &&
			cpDefinitionInventoryEngine.isBackOrderAllowed(cpInstance)) {

			jsonObject.put(
				CPContentContributorConstants.AVAILABILITY_RANGE_NAME,
				getAvailabilityRangeLabel(
					themeDisplay.getLocale(),
					cpDefinitionInventoryEngine.getAvailabilityRange(
						cpInstance, themeDisplay.getLocale())));
		}

		return jsonObject;
	}

	protected String getAvailabilityRangeLabel(
		Locale locale, String availabilityRange) {

		if (Validator.isNull(availabilityRange)) {
			return StringPool.BLANK;
		}

		return LanguageUtil.format(
			locale, "product-will-be-available-in-x", availabilityRange);
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