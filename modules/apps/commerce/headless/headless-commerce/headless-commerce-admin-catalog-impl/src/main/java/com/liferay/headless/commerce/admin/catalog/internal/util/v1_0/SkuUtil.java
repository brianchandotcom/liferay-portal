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
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuOption;
import com.liferay.headless.commerce.admin.catalog.internal.util.DateConfigUtil;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.Calendar;

/**
 * @author Alessio Antonio Rendina
 */
public class SkuUtil {

	public static CPInstance addOrUpdateCPInstance(
			CPInstanceService cpInstanceService, Sku sku,
			CPDefinition cpDefinition, ServiceContext serviceContext)
		throws PortalException {

		long replacementCProductId = 0;
		String replacementCPInstanceUuid = null;

		if (GetterUtil.getBoolean(sku.getDiscontinued())) {
			CPInstance discontinuedCPInstance = null;

			if (Validator.isNotNull(
					sku.getReplacementSkuExternalReferenceCode())) {

				discontinuedCPInstance =
					cpInstanceService.fetchByExternalReferenceCode(
						sku.getReplacementSkuExternalReferenceCode(),
						cpDefinition.getCompanyId());
			}

			long replacementSkuId = GetterUtil.getLong(
				sku.getReplacementSkuId());

			if ((discontinuedCPInstance == null) && (replacementSkuId > 0)) {
				discontinuedCPInstance = cpInstanceService.fetchCPInstance(
					replacementSkuId);
			}

			if (discontinuedCPInstance != null) {
				CPDefinition discontinuedCPDefinition =
					discontinuedCPInstance.getCPDefinition();

				replacementCProductId =
					discontinuedCPDefinition.getCProductId();

				replacementCPInstanceUuid =
					discontinuedCPInstance.getCPInstanceUuid();
			}
		}

		Calendar discontinuedCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (sku.getDiscontinuedDate() != null) {
			discontinuedCalendar = DateConfigUtil.convertDateToCalendar(
				sku.getDiscontinuedDate());
		}

		DateConfig discontinuedDateConfig = new DateConfig(
			discontinuedCalendar);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (sku.getDisplayDate() != null) {
			displayCalendar = DateConfigUtil.convertDateToCalendar(
				sku.getDisplayDate());
		}

		DateConfig displayDateConfig = new DateConfig(displayCalendar);

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		if (sku.getExpirationDate() != null) {
			expirationCalendar = DateConfigUtil.convertDateToCalendar(
				sku.getExpirationDate());
		}

		DateConfig expirationDateConfig = new DateConfig(expirationCalendar);

		return cpInstanceService.addOrUpdateCPInstance(
			sku.getExternalReferenceCode(), cpDefinition.getCPDefinitionId(),
			cpDefinition.getGroupId(), sku.getSku(), sku.getGtin(),
			sku.getManufacturerPartNumber(),
			GetterUtil.get(sku.getPurchasable(), false), _getOptions(sku),
			GetterUtil.get(sku.getWidth(), 0.0),
			GetterUtil.get(sku.getHeight(), 0.0),
			GetterUtil.get(sku.getDepth(), 0.0),
			GetterUtil.get(sku.getWeight(), 0.0),
			(BigDecimal)GetterUtil.get(sku.getPrice(), BigDecimal.ZERO),
			(BigDecimal)GetterUtil.get(sku.getPromoPrice(), BigDecimal.ZERO),
			(BigDecimal)GetterUtil.get(sku.getCost(), BigDecimal.ZERO),
			GetterUtil.get(sku.getPublished(), false),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.get(sku.getNeverExpire(), false), sku.getUnspsc(),
			GetterUtil.get(sku.getDiscontinued(), false),
			replacementCPInstanceUuid, replacementCProductId,
			discontinuedDateConfig.getMonth(), discontinuedDateConfig.getDay(),
			discontinuedDateConfig.getYear(), serviceContext);
	}

	private static String _getOptions(Sku sku) {
		SkuOption[] skuOptions = sku.getSkuOptions();

		if (skuOptions == null) {
			return StringPool.BLANK;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (SkuOption skuOption : skuOptions) {
			jsonArray.put(
				JSONUtil.put(
					"key", skuOption.getKey()
				).put(
					"value", JSONUtil.put(skuOption.getValue())
				));
		}

		return jsonArray.toString();
	}

}