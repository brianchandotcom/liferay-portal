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

package com.liferay.commerce.product.definitions.web.internal.frontend.data.set.provider;

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.context.CommerceContextFactory;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngine;
import com.liferay.commerce.inventory.CPDefinitionInventoryEngineRegistry;
import com.liferay.commerce.inventory.CommerceInventoryChecker;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.CommerceProductOptionValueRelativePriceRequest;
import com.liferay.commerce.price.CommerceProductPriceCalculation;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPInstanceOptionValueRelLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "ddm.data.provider.instance.id=getCPInstanceOptionsValues",
	service = DDMDataProvider.class
)
public class CommerceProductInstanceOptionsValuesDataProvider
	implements DDMDataProvider {

	@Override
	public DDMDataProviderResponse getData(
			DDMDataProviderRequest ddmDataProviderRequest)
		throws DDMDataProviderException {

		DDMDataProviderResponse.Builder ddmDataProviderResponseBuilder =
			DDMDataProviderResponse.Builder.newBuilder();

		long cpDefinitionId = _getParameter(
			ddmDataProviderRequest, "cpDefinitionId");

		long commerceAccountId = _getParameter(
			ddmDataProviderRequest, "commerceAccountId");

		long groupId = _getParameter(ddmDataProviderRequest, "groupId");

		try {
			if (!_commerceProductViewPermission.contains(
					PermissionThreadLocal.getPermissionChecker(),
					commerceAccountId, groupId, cpDefinitionId)) {

				return ddmDataProviderResponseBuilder.build();
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return ddmDataProviderResponseBuilder.build();
		}

		if (cpDefinitionId == 0) {
			return ddmDataProviderResponseBuilder.build();
		}

		Locale locale = ddmDataProviderRequest.getLocale();

		try {

			/**
			 * Extract the filters and the outputs based on fields that were
			 * filled or not in the data provider request
			 * Ex:
			 * If we have size and color as product options,
			 * In this case, we will have four possible states as follow:
			 * 1 - Size and Color both empty -> It won't do any query and it
			 * 	will return an empty result.
			 * 2 - Size filled and Color empty -> It'll assume that size will
			 * 	compose the query filter and color will be the output. So,
			 * 	it will return all colors for the specified size
			 * 3 - Size empty and Color filled - the same approach that item 2
			 * 4 - Size and Color both filled - the same approach that item 1
			 */
			List<CPDefinitionOptionRel> requestedCPDefinitionOptionRels =
				new ArrayList<>();

			List<CPDefinitionOptionValueRel>
				selectedCPDefinitionOptionValueRels = new ArrayList<>();

			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
					cpDefinitionId, true);

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					cpDefinitionOptionRels) {

				Map<String, Object> parameters =
					ddmDataProviderRequest.getParameters();

				String parameterValue = String.valueOf(
					parameters.get(cpDefinitionOptionRel.getKey()));

				// Collect filters and outputs

				if (_jsonHelper.isEmpty(parameterValue)) {
					requestedCPDefinitionOptionRels.add(cpDefinitionOptionRel);

					continue;
				}

				String optionValueKey = parameterValue;

				if (_jsonHelper.isArray(parameterValue)) {
					optionValueKey = _jsonHelper.getFirstElementStringValue(
						parameterValue);
				}

				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					_cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
							optionValueKey);

				if (cpDefinitionOptionValueRel != null) {
					selectedCPDefinitionOptionValueRels.add(
						cpDefinitionOptionValueRel);
				}
			}

			// Do search and populate the outputs if the outputs are not empty

			List<Output> outputs = new ArrayList<>();

			CommerceContext commerceContext = _getCommerceContext(
				_getParameter(ddmDataProviderRequest, "companyId"),
				commerceAccountId, groupId,
				_getParameter(ddmDataProviderRequest, "userId"));

			_addNonskuContributingCPDefinitionOptionValueOutputs(
				cpDefinitionId, ddmDataProviderRequest, locale, outputs,
				commerceContext);

			JSONArray selectedCPDefinitionOptionValuesJSONArray =
				_getCPDefinitionOptionCPDefinitionOptionValuesJSONArray(
					selectedCPDefinitionOptionValueRels);

			CPInstance selectedCPInstance = _cpInstanceHelper.fetchCPInstance(
				cpDefinitionId,
				selectedCPDefinitionOptionValuesJSONArray.toString());

			for (CPDefinitionOptionValueRel selectedCPDefinitionOptionValueRel :
					selectedCPDefinitionOptionValueRels) {

				CPDefinitionOptionRel cpDefinitionOptionRel =
					selectedCPDefinitionOptionValueRel.
						getCPDefinitionOptionRel();

				outputs.add(
					new Output(
						cpDefinitionOptionRel.getKey(), "list",
						_toSelectedCPDefinitionOptionValueRelKeyValuePairs(
							cpDefinitionId,
							cpDefinitionOptionRel.
								getCPDefinitionOptionValueRels(),
							selectedCPDefinitionOptionValueRel,
							selectedCPDefinitionOptionValuesJSONArray,
							selectedCPInstance, locale, commerceContext)));
			}

			for (CPDefinitionOptionRel cpDefinitionOptionRel :
					requestedCPDefinitionOptionRels) {

				List<CPDefinitionOptionValueRel>
					allowedCPDefinitionOptionValueRels =
						_filterBySelectedCPDefinitionOptionValueRelIds(
							cpDefinitionOptionRel,
							selectedCPDefinitionOptionValueRels);

				if (cpDefinitionOptionRel.isPriceContributor()) {
					allowedCPDefinitionOptionValueRels =
						_commerceInventoryChecker.filterByAvailability(
							allowedCPDefinitionOptionValueRels);
				}

				if (cpDefinitionOptionRel.isSkuContributor()) {
					allowedCPDefinitionOptionValueRels =
						_cpDefinitionOptionValueRelLocalService.
							filterByCPInstanceOptionValueRels(
								allowedCPDefinitionOptionValueRels,
								_cpInstanceOptionValueRelCommerceInventoryChecker.
									filterByAvailability(
										_cpInstanceOptionValueRelLocalService.
											getCPDefinitionOptionRelCPInstanceOptionValueRels(
												cpDefinitionOptionRel.
													getCPDefinitionOptionRelId())));
				}

				String optionKey = cpDefinitionOptionRel.getKey();

				outputs.add(
					new Output(
						optionKey, "list",
						_toRequestedCPDefinitionOptionValueRelKeyValuePairs(
							cpDefinitionId, optionKey,
							allowedCPDefinitionOptionValueRels,
							selectedCPDefinitionOptionValuesJSONArray,
							selectedCPInstance, locale, commerceContext)));
			}

			return Output.toDDMDataProviderResponse(outputs);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return ddmDataProviderResponseBuilder.build();
	}

	@Override
	public Class<?> getSettings() {
		throw new UnsupportedOperationException();
	}

	protected static class Output {

		public static DDMDataProviderResponse toDDMDataProviderResponse(
			List<Output> outputs) {

			DDMDataProviderResponse.Builder ddmDataProviderResponseBuilder =
				DDMDataProviderResponse.Builder.newBuilder();

			for (Output output : outputs) {
				ddmDataProviderResponseBuilder.withOutput(
					output._name, output._value);
			}

			return ddmDataProviderResponseBuilder.build();
		}

		public Output(String name, String type, Object value) {
			_name = name;
			_type = type;
			_value = value;
		}

		private final String _name;
		private final String _type;
		private final Object _value;

	}

	private void _addNonskuContributingCPDefinitionOptionValueOutputs(
			long cpDefinitionId, DDMDataProviderRequest ddmDataProviderRequest,
			Locale locale, List<Output> outputs,
			CommerceContext commerceContext)
		throws PortalException {

		Map<CPDefinitionOptionRel, CPDefinitionOptionValueRel>
			selectedCPDefinitionOptionCPDefinitionOptionValues =
				new HashMap<>();

		Map<String, Object> parameters = ddmDataProviderRequest.getParameters();

		List<CPDefinitionOptionRel> nonskuContributingCPDefinitionOptionRels =
			_cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
				cpDefinitionId, false);

		for (CPDefinitionOptionRel nonskuContributingDefinitionOptionRel :
				nonskuContributingCPDefinitionOptionRels) {

			String parameterValue = String.valueOf(
				parameters.get(nonskuContributingDefinitionOptionRel.getKey()));

			String optionValueKey = parameterValue;

			if (_jsonHelper.isArray(parameterValue) &&
				!_jsonHelper.isEmpty(parameterValue)) {

				optionValueKey = _jsonHelper.getFirstElementStringValue(
					parameterValue);
			}

			CPDefinitionOptionValueRel selectedCPDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelLocalService.
					fetchCPDefinitionOptionValueRel(
						nonskuContributingDefinitionOptionRel.
							getCPDefinitionOptionRelId(),
						optionValueKey);

			selectedCPDefinitionOptionCPDefinitionOptionValues.put(
				nonskuContributingDefinitionOptionRel,
				selectedCPDefinitionOptionValueRel);
		}

		for (Map.Entry<CPDefinitionOptionRel, CPDefinitionOptionValueRel>
				entry :
					selectedCPDefinitionOptionCPDefinitionOptionValues.
						entrySet()) {

			CPDefinitionOptionRel cpDefinitionOptionRel = entry.getKey();

			outputs.add(
				new Output(
					cpDefinitionOptionRel.getKey(), "list",
					_toNonskuContributingCPDefinitionOptionValueKeyValuePairs(
						cpDefinitionOptionRel.getCPDefinitionOptionValueRels(),
						entry.getValue(), locale, commerceContext)));
		}
	}

	private JSONArray _addToJSONArray(
		JSONArray jsonArray1, String optionKey, String optionValueKey) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		JSONArray jsonArray2 = _jsonFactory.createJSONArray();

		jsonObject.put(
			"key", optionKey
		).put(
			"value", jsonArray2.put(optionValueKey)
		);

		return jsonArray1.put(jsonObject);
	}

	private List<CPDefinitionOptionValueRel>
			_filterBySelectedCPDefinitionOptionValueRelIds(
				CPDefinitionOptionRel cpDefinitionOptionRel,
				List<CPDefinitionOptionValueRel>
					skuCombinationCPDefinitionOptionValueRels)
		throws PortalException {

		if (skuCombinationCPDefinitionOptionValueRels.isEmpty()) {
			return _cpInstanceHelper.getCPInstanceCPDefinitionOptionValueRels(
				cpDefinitionOptionRel.getCPDefinitionId(),
				cpDefinitionOptionRel.getCPDefinitionOptionRelId());
		}

		return _cpInstanceHelper.filterCPDefinitionOptionValueRels(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
			ListUtil.toList(
				skuCombinationCPDefinitionOptionValueRels,
				CPDefinitionOptionValueRel.
					CP_DEFINITION_OPTION_VALUE_REL_ID_ACCESSOR));
	}

	private CommerceContext _getCommerceContext(
			long companyId, long commerceAccountId, long groupId, long userId)
		throws PortalException {

		return _commerceContextFactory.create(
			companyId,
			_commerceChannelLocalService.getCommerceChannelGroupIdBySiteGroupId(
				groupId),
			userId, 0, commerceAccountId);
	}

	private JSONArray _getCPDefinitionOptionCPDefinitionOptionValuesJSONArray(
			Collection<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels)
		throws PortalException {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionValueRel.getCPDefinitionOptionRel();

			jsonObject.put("key", cpDefinitionOptionRel.getKey());

			JSONArray valueJSONArray = _jsonFactory.createJSONArray();

			jsonObject.put(
				"value",
				valueJSONArray.put(cpDefinitionOptionValueRel.getKey()));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private long _getCPInstanceId(CPInstance cpInstance) {
		if (cpInstance == null) {
			return 0;
		}

		return cpInstance.getCPInstanceId();
	}

	private int _getMinOrderQuantity(CPInstance cpInstance)
		throws PortalException {

		if (cpInstance == null) {
			return 0;
		}

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpInstance.getCPDefinitionId());

		CPDefinitionInventoryEngine cpDefinitionInventoryEngine =
			_cpDefinitionInventoryEngineRegistry.getCPDefinitionInventoryEngine(
				cpDefinitionInventory);

		return cpDefinitionInventoryEngine.getMinOrderQuantity(cpInstance);
	}

	private long _getParameter(
		DDMDataProviderRequest ddmDataProviderRequest, String param) {

		Map<String, Object> parameters = ddmDataProviderRequest.getParameters();

		return GetterUtil.getLong(parameters.get(param));
	}

	private List<KeyValuePair>
			_toNonskuContributingCPDefinitionOptionValueKeyValuePairs(
				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
				CPDefinitionOptionValueRel selectedCPDefinitionOptionValueRel,
				Locale locale, CommerceContext commerceContext)
		throws PortalException {

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			CommerceProductOptionValueRelativePriceRequest.Builder builder =
				new CommerceProductOptionValueRelativePriceRequest.Builder(
					commerceContext, cpDefinitionOptionValueRel);

			CommerceMoney commerceMoney =
				_commerceProductPriceCalculation.
					getCPDefinitionOptionValueRelativePrice(
						builder.selectedCPDefinitionOptionValueRel(
							selectedCPDefinitionOptionValueRel
						).build());

			keyValuePairs.add(
				new KeyValuePair(
					cpDefinitionOptionValueRel.getKey(),
					String.format(
						"%s %s", cpDefinitionOptionValueRel.getName(locale),
						commerceMoney.format(locale))));
		}

		return keyValuePairs;
	}

	private List<KeyValuePair>
			_toRequestedCPDefinitionOptionValueRelKeyValuePairs(
				long cpDefinitionId, String optionKey,
				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
				JSONArray selectedCPDefinitionOptionValuesJSONArray,
				CPInstance selectedCPInstance, Locale locale,
				CommerceContext commerceContext)
		throws PortalException {

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		String jsonArrayString =
			selectedCPDefinitionOptionValuesJSONArray.toString();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			JSONArray clonedJSONArray = _jsonFactory.createJSONArray(
				jsonArrayString);

			_addToJSONArray(
				clonedJSONArray, optionKey,
				cpDefinitionOptionValueRel.getKey());

			CPInstance cpInstance = _cpInstanceHelper.fetchCPInstance(
				cpDefinitionId, clonedJSONArray.toString());

			CommerceProductOptionValueRelativePriceRequest.Builder builder =
				new CommerceProductOptionValueRelativePriceRequest.Builder(
					commerceContext, cpDefinitionOptionValueRel);

			CommerceMoney commerceMoney =
				_commerceProductPriceCalculation.
					getCPDefinitionOptionValueRelativePrice(
						builder.cpInstanceId(
							_getCPInstanceId(cpInstance)
						).cpInstanceMinQuantity(
							_getMinOrderQuantity(cpInstance)
						).selectedCPInstanceId(
							_getCPInstanceId(selectedCPInstance)
						).selectedCPInstanceMinQuantity(
							_getMinOrderQuantity(selectedCPInstance)
						).build());

			keyValuePairs.add(
				new KeyValuePair(
					cpDefinitionOptionValueRel.getKey(),
					String.format(
						"%s %s", cpDefinitionOptionValueRel.getName(locale),
						commerceMoney.format(locale))));
		}

		return keyValuePairs;
	}

	private List<KeyValuePair>
			_toSelectedCPDefinitionOptionValueRelKeyValuePairs(
				long cpDefinitionId,
				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels,
				CPDefinitionOptionValueRel selectedCPDefinitionOptionValueRel,
				JSONArray selectedCPDefinitionOptionValuesJSONArray,
				CPInstance selectedCPInstance, Locale locale,
				CommerceContext commerceContext)
		throws PortalException {

		List<KeyValuePair> keyValuePairs = new ArrayList<>();

		String jsonArrayString =
			selectedCPDefinitionOptionValuesJSONArray.toString();

		CPDefinitionOptionRel cpDefinitionOptionRel =
			selectedCPDefinitionOptionValueRel.getCPDefinitionOptionRel();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			JSONArray clonedJSONArray = _jsonFactory.createJSONArray(
				jsonArrayString);

			if (!_updateJSONArray(
					cpDefinitionOptionRel.getKey(),
					cpDefinitionOptionValueRel.getKey(), clonedJSONArray)) {

				continue;
			}

			CPInstance cpInstance = _cpInstanceHelper.fetchCPInstance(
				cpDefinitionId, clonedJSONArray.toString());

			CommerceProductOptionValueRelativePriceRequest.Builder builder =
				new CommerceProductOptionValueRelativePriceRequest.Builder(
					commerceContext, cpDefinitionOptionValueRel);

			CommerceMoney commerceMoney =
				_commerceProductPriceCalculation.
					getCPDefinitionOptionValueRelativePrice(
						builder.cpInstanceId(
							_getCPInstanceId(cpInstance)
						).cpInstanceMinQuantity(
							_getMinOrderQuantity(cpInstance)
						).selectedCPInstanceId(
							_getCPInstanceId(selectedCPInstance)
						).selectedCPInstanceMinQuantity(
							_getMinOrderQuantity(selectedCPInstance)
						).selectedCPDefinitionOptionValueRel(
							selectedCPDefinitionOptionValueRel
						).build());

			keyValuePairs.add(
				new KeyValuePair(
					cpDefinitionOptionValueRel.getKey(),
					String.format(
						"%s %s", cpDefinitionOptionValueRel.getName(locale),
						commerceMoney.format(locale))));
		}

		return keyValuePairs;
	}

	private boolean _updateJSONArray(
		String key, String value, JSONArray jsonArray1) {

		for (int i = 0; i < jsonArray1.length(); i++) {
			JSONObject jsonObject = jsonArray1.getJSONObject(i);

			String keyValue = jsonObject.getString("key");

			if (!Objects.equals(key, keyValue)) {
				continue;
			}

			JSONArray jsonArray2 = _jsonFactory.createJSONArray();

			jsonObject.put("value", jsonArray2.put(value));

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceProductInstanceOptionsValuesDataProvider.class);

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceContextFactory _commerceContextFactory;

	@Reference(
		target = "(commerce.inventory.checker.target=CPDefinitionOptionValueRel)"
	)
	private CommerceInventoryChecker _commerceInventoryChecker;

	@Reference
	private CommerceProductPriceCalculation _commerceProductPriceCalculation;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionInventoryEngineRegistry
		_cpDefinitionInventoryEngineRegistry;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference(
		target = "(commerce.inventory.checker.target=CPInstanceOptionValueRel)"
	)
	private CommerceInventoryChecker<CPInstanceOptionValueRel>
		_cpInstanceOptionValueRelCommerceInventoryChecker;

	@Reference
	private CPInstanceOptionValueRelLocalService
		_cpInstanceOptionValueRelLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JsonHelper _jsonHelper;

}