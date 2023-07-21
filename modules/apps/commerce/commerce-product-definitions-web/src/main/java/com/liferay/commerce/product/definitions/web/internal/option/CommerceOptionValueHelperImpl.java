/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.option;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.option.CommerceOptionValue;
import com.liferay.commerce.product.option.CommerceOptionValueHelper;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.util.CPJSONUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(service = CommerceOptionValueHelper.class)
public class CommerceOptionValueHelperImpl
	implements CommerceOptionValueHelper {

	@Override
	public List<CommerceOptionValue> getCPDefinitionCommerceOptionValues(
			long cpDefinitionId, String json)
		throws PortalException {

		Map<Long, List<Long>>
			cpDefinitionOptionRelCPDefinitionOptionValueRelIds =
				_cpDefinitionOptionRelLocalService.
					getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
						cpDefinitionId, json);

		if (cpDefinitionOptionRelCPDefinitionOptionValueRelIds.isEmpty()) {
			return Collections.emptyList();
		}

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		for (Map.Entry<Long, List<Long>> entry :
				cpDefinitionOptionRelCPDefinitionOptionValueRelIds.entrySet()) {

			Long key = entry.getKey();
			List<Long> value = entry.getValue();

			for (long cpDefinitionOptionValueRelId : value) {
				CPDefinitionOptionRel cpDefinitionOptionRel =
					_cpDefinitionOptionRelLocalService.
						fetchCPDefinitionOptionRel(key);

				if (cpDefinitionOptionRel == null) {
					continue;
				}

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionValueRelId);

				if (cpDefinitionOptionValueRel == null) {
					continue;
				}

				CommerceOptionValue.Builder commerceOptionValueBuilder =
					new CommerceOptionValue.Builder();

				commerceOptionValueBuilder.optionKey(
					cpDefinitionOptionRel.getKey());
				commerceOptionValueBuilder.optionValueKey(
					cpDefinitionOptionValueRel.getKey());

				if (!cpDefinitionOptionRel.isPriceContributor()) {
					commerceOptionValues.add(
						commerceOptionValueBuilder.build());

					continue;
				}

				commerceOptionValueBuilder.priceType(
					cpDefinitionOptionRel.getPriceType());

				commerceOptionValueBuilder.price(
					cpDefinitionOptionValueRel.getPrice());

				commerceOptionValueBuilder.quantity(
					cpDefinitionOptionValueRel.getQuantity());

				CPInstance cpDefinitionOptionValueRelCPInstance =
					cpDefinitionOptionValueRel.fetchCPInstance();

				if (cpDefinitionOptionValueRelCPInstance != null) {
					commerceOptionValueBuilder.cpInstanceId(
						cpDefinitionOptionValueRelCPInstance.getCPInstanceId());

					if (cpDefinitionOptionRel.isPriceTypeDynamic()) {
						commerceOptionValueBuilder.price(
							cpDefinitionOptionValueRelCPInstance.getPrice());
					}
				}

				commerceOptionValues.add(commerceOptionValueBuilder.build());
			}
		}

		return commerceOptionValues;
	}

	@Override
	public CommerceOptionValue toCommerceOptionValue(String json)
		throws JSONException {

		return _toCommerceOptionValue(_jsonFactory.createJSONObject(json));
	}

	@Override
	public List<CommerceOptionValue> toCommerceOptionValues(String json)
		throws JSONException {

		List<CommerceOptionValue> commerceOptionValues = new ArrayList<>();

		JSONArray commerceOptionValuesJSONArray = CPJSONUtil.toJSONArray(json);

		for (int i = 0; i < commerceOptionValuesJSONArray.length(); i++) {
			JSONObject jsonObject = commerceOptionValuesJSONArray.getJSONObject(
				i);

			JSONArray valueJSONArray = CPJSONUtil.getJSONArray(
				jsonObject, "value");

			if (valueJSONArray.length() > 0) {
				for (int j = 0; j < valueJSONArray.length(); j++) {
					String value = valueJSONArray.getString(j);

					jsonObject.put("value", value);

					commerceOptionValues.add(
						_toCommerceOptionValue(jsonObject));
				}
			}
			else {
				commerceOptionValues.add(_toCommerceOptionValue(jsonObject));
			}
		}

		return commerceOptionValues;
	}

	private CommerceOptionValue _toCommerceOptionValue(JSONObject jsonObject) {
		CommerceOptionValue.Builder commerceOptionValueBuilder =
			new CommerceOptionValue.Builder();

		commerceOptionValueBuilder.optionKey(jsonObject.getString("key"));

		JSONArray valueJSONArray = CPJSONUtil.getJSONArray(jsonObject, "value");

		if (valueJSONArray.length() > 0) {
			commerceOptionValueBuilder.optionValueKey(
				valueJSONArray.getString(0));
		}

		if (jsonObject.has("priceType")) {
			commerceOptionValueBuilder.priceType(
				jsonObject.getString("priceType"));
		}

		if (jsonObject.has("price")) {
			commerceOptionValueBuilder.price(
				new BigDecimal(jsonObject.getString("price")));
		}

		if (jsonObject.has("quantity")) {
			commerceOptionValueBuilder.quantity(
				BigDecimal.valueOf(jsonObject.getInt("quantity")));
		}

		if (jsonObject.has("cpInstanceId")) {
			commerceOptionValueBuilder.cpInstanceId(
				jsonObject.getLong("cpInstanceId"));
		}

		return commerceOptionValueBuilder.build();
	}

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}