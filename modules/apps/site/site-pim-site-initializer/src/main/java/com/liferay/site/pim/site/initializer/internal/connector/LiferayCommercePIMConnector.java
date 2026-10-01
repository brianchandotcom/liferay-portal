/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.internal.connector;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.function.UnsafeTriFunction;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.pim.site.initializer.connector.PIMConnector;
import com.liferay.site.pim.site.initializer.connector.PIMConnectorChannelField;
import com.liferay.site.pim.site.initializer.internal.util.PIMConnectorFieldMappingsUtil;

import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Stefano Motta
 */
@Component(service = PIMConnector.class)
public class LiferayCommercePIMConnector extends BasePIMConnector {

	public static final String KEY = "liferay-commerce";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public List<PIMConnectorChannelField> getPIMConnectorChannelFields() {
		return ListUtil.fromArray(
			_CHANNEL_FIELD_CATALOG_ID, _CHANNEL_FIELD_DEPTH,
			_CHANNEL_FIELD_DESCRIPTION, _CHANNEL_FIELD_HEIGHT,
			_CHANNEL_FIELD_NAME, _CHANNEL_FIELD_PRECISION,
			_CHANNEL_FIELD_PRODUCT_OPTIONS,
			_CHANNEL_FIELD_PRODUCT_SPECIFICATIONS, _CHANNEL_FIELD_PRODUCT_TYPE,
			_CHANNEL_FIELD_SKU, _CHANNEL_FIELD_TAGS,
			_CHANNEL_FIELD_UNIT_OF_MEASURE_KEY,
			_CHANNEL_FIELD_UNIT_OF_MEASURE_NAME, _CHANNEL_FIELD_WEIGHT,
			_CHANNEL_FIELD_WIDTH);
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	@Override
	protected JSONObject createProductJSONObject(
			Map<String, List<ObjectEntry>> pimFieldMappingObjectEntriesMap,
			List<ObjectEntry> pimProductObjectEntries)
		throws PortalException {

		ObjectEntry objectEntry = pimProductObjectEntries.get(0);

		ObjectDefinition objectDefinition =
			objectDefinitionLocalService.fetchObjectDefinition(
				objectEntry.getObjectDefinitionId());

		List<Map<String, Serializable>> valuesList = TransformUtil.transform(
			pimProductObjectEntries, objectEntryLocalService::getValues);

		Map<String, Serializable> values = valuesList.get(0);

		return JSONUtil.put(
			"active", true
		).put(
			"catalogId",
			() -> PIMConnectorFieldMappingsUtil.getChannelFieldValue(
				objectDefinition, pimFieldMappingObjectEntriesMap,
				_CHANNEL_FIELD_CATALOG_ID, values)
		).put(
			"description",
			() -> {
				Object description =
					PIMConnectorFieldMappingsUtil.getChannelFieldValue(
						objectDefinition, pimFieldMappingObjectEntriesMap,
						_CHANNEL_FIELD_DESCRIPTION, values);

				if (description == null) {
					return null;
				}

				return JSONUtil.put("en_US", description);
			}
		).put(
			"externalReferenceCode", objectEntry.getExternalReferenceCode()
		).put(
			"name",
			() -> {
				Object name =
					PIMConnectorFieldMappingsUtil.getChannelFieldValue(
						objectDefinition, pimFieldMappingObjectEntriesMap,
						_CHANNEL_FIELD_NAME, values);

				if (name == null) {
					return null;
				}

				return JSONUtil.put("en_US", name);
			}
		).put(
			"productOptions",
			() -> _toJSONArray(
				objectDefinition,
				pimFieldMappingObjectEntriesMap.get(
					_CHANNEL_FIELD_PRODUCT_OPTIONS.getName()),
				(curObjectEntry, sourceFieldName, priority) ->
					_createProductOptionJSONObject(
						objectDefinition, curObjectEntry, priority,
						sourceFieldName, valuesList))
		).put(
			"productSpecifications",
			() -> _toJSONArray(
				objectDefinition,
				pimFieldMappingObjectEntriesMap.get(
					_CHANNEL_FIELD_PRODUCT_SPECIFICATIONS.getName()),
				(curObjectEntry, sourceFieldName, priority) ->
					_createProductSpecificationJSONObject(
						objectDefinition, curObjectEntry, priority,
						sourceFieldName, values))
		).put(
			"productType",
			() -> {
				Object productType =
					PIMConnectorFieldMappingsUtil.getChannelFieldValue(
						objectDefinition, pimFieldMappingObjectEntriesMap,
						_CHANNEL_FIELD_PRODUCT_TYPE, values);

				if (Objects.equals(productType, StringPool.FALSE)) {
					return "simple";
				}

				if (Objects.equals(productType, StringPool.TRUE)) {
					return "virtual";
				}

				return productType;
			}
		).put(
			"skus",
			() -> JSONUtil.toJSONArray(
				valuesList,
				curValues -> _createSkuJSONObject(
					objectDefinition, pimFieldMappingObjectEntriesMap,
					curValues))
		).put(
			"tags",
			() -> PIMConnectorFieldMappingsUtil.getChannelFieldValue(
				objectDefinition, pimFieldMappingObjectEntriesMap,
				_CHANNEL_FIELD_TAGS, values)
		);
	}

	private JSONObject _createProductOptionJSONObject(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry,
		int priority, String sourceFieldName,
		List<Map<String, Serializable>> valuesList) {

		JSONArray jsonArray = jsonFactory.createJSONArray();
		Set<String> valueKeys = new HashSet<>();

		for (Map<String, Serializable> values : valuesList) {
			String value = PIMConnectorFieldMappingsUtil.getValue(
				objectDefinition, objectEntry, values);

			if (Validator.isNull(value)) {
				continue;
			}

			String valueKey = _friendlyURLNormalizer.normalize(value);

			if (!valueKeys.add(valueKey)) {
				continue;
			}

			jsonArray.put(
				JSONUtil.put(
					"key", valueKey
				).put(
					"name", JSONUtil.put("en_US", value)
				).put(
					"priority", jsonArray.length()
				));
		}

		if (jsonArray.length() == 0) {
			return null;
		}

		return JSONUtil.put(
			"name",
			JSONUtil.put(
				"en_US",
				PIMConnectorFieldMappingsUtil.getLabel(
					objectDefinition, objectEntry))
		).put(
			"optionExternalReferenceCode", sourceFieldName
		).put(
			"priority", priority
		).put(
			"productOptionValues", jsonArray
		).put(
			"skuContributor", true
		);
	}

	private JSONObject _createProductSpecificationJSONObject(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry,
		int priority, String sourceFieldName,
		Map<String, Serializable> values) {

		String value = PIMConnectorFieldMappingsUtil.getValue(
			objectDefinition, objectEntry, values);

		if (Validator.isNull(value)) {
			return null;
		}

		return JSONUtil.put(
			"label",
			JSONUtil.put(
				"en_US",
				PIMConnectorFieldMappingsUtil.getLabel(
					objectDefinition, objectEntry))
		).put(
			"priority", priority
		).put(
			"specificationKey", sourceFieldName
		).put(
			"value", JSONUtil.put("en_US", value)
		);
	}

	private JSONObject _createSkuJSONObject(
		ObjectDefinition objectDefinition,
		Map<String, List<ObjectEntry>> objectEntriesMap,
		Map<String, Serializable> values) {

		return JSONUtil.put(
			"depth",
			() -> PIMConnectorFieldMappingsUtil.getChannelFieldValue(
				objectDefinition, objectEntriesMap, _CHANNEL_FIELD_DEPTH,
				values)
		).put(
			"height",
			() -> PIMConnectorFieldMappingsUtil.getChannelFieldValue(
				objectDefinition, objectEntriesMap, _CHANNEL_FIELD_HEIGHT,
				values)
		).put(
			"published", true
		).put(
			"purchasable", true
		).put(
			"sku",
			() -> PIMConnectorFieldMappingsUtil.getChannelFieldValue(
				objectDefinition, objectEntriesMap, _CHANNEL_FIELD_SKU, values)
		).put(
			"skuOptions",
			() -> _toJSONArray(
				objectDefinition,
				objectEntriesMap.get(_CHANNEL_FIELD_PRODUCT_OPTIONS.getName()),
				(objectEntry, sourceFieldName, priority) ->
					_createSkuOptionJSONObject(
						objectDefinition, objectEntry, sourceFieldName, values))
		).put(
			"skuUnitOfMeasures",
			() -> {
				Object unitOfMeasureKey =
					PIMConnectorFieldMappingsUtil.getChannelFieldValue(
						objectDefinition, objectEntriesMap,
						_CHANNEL_FIELD_UNIT_OF_MEASURE_KEY, values);

				if (unitOfMeasureKey == null) {
					return null;
				}

				return JSONUtil.putAll(
					JSONUtil.put(
						"incrementalOrderQuantity", 1
					).put(
						"key", unitOfMeasureKey
					).put(
						"name",
						JSONUtil.put(
							"en_US",
							() ->
								PIMConnectorFieldMappingsUtil.
									getChannelFieldValue(
										objectDefinition, objectEntriesMap,
										_CHANNEL_FIELD_UNIT_OF_MEASURE_NAME,
										values))
					).put(
						"precision",
						() ->
							PIMConnectorFieldMappingsUtil.getChannelFieldValue(
								objectDefinition, objectEntriesMap,
								_CHANNEL_FIELD_PRECISION, values)
					).put(
						"primary", true
					).put(
						"rate", 1
					));
			}
		).put(
			"weight",
			() -> PIMConnectorFieldMappingsUtil.getChannelFieldValue(
				objectDefinition, objectEntriesMap, _CHANNEL_FIELD_WEIGHT,
				values)
		).put(
			"width",
			() -> PIMConnectorFieldMappingsUtil.getChannelFieldValue(
				objectDefinition, objectEntriesMap, _CHANNEL_FIELD_WIDTH,
				values)
		);
	}

	private JSONObject _createSkuOptionJSONObject(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry,
		String sourceFieldName, Map<String, Serializable> values) {

		String value = _friendlyURLNormalizer.normalize(
			PIMConnectorFieldMappingsUtil.getValue(
				objectDefinition, objectEntry, values));

		if (Validator.isNull(value)) {
			return null;
		}

		return JSONUtil.put(
			"key", sourceFieldName
		).put(
			"value", value
		);
	}

	private JSONArray _toJSONArray(
		ObjectDefinition objectDefinition, List<ObjectEntry> objectEntries,
		UnsafeTriFunction
			<ObjectEntry, String, Integer, JSONObject, RuntimeException>
				unsafeTriFunction) {

		JSONArray jsonArray = jsonFactory.createJSONArray();
		Set<String> sourceFieldNames = new HashSet<>();

		for (ObjectEntry objectEntry :
				PIMConnectorFieldMappingsUtil.filterObjectEntries(
					objectDefinition, objectEntries)) {

			if (PIMConnectorFieldMappingsUtil.isFixedValue(objectEntry)) {
				continue;
			}

			String sourceFieldName = _friendlyURLNormalizer.normalize(
				MapUtil.getString(objectEntry.getValues(), "sourceFieldName"));

			if (!sourceFieldNames.add(sourceFieldName)) {
				continue;
			}

			JSONObject jsonObject = unsafeTriFunction.apply(
				objectEntry, sourceFieldName, jsonArray.length());

			if (jsonObject == null) {
				continue;
			}

			jsonArray.put(jsonObject);
		}

		if (jsonArray.length() == 0) {
			return null;
		}

		return jsonArray;
	}

	private static final PIMConnectorChannelField _CHANNEL_FIELD_CATALOG_ID =
		new PIMConnectorChannelField("catalog-id", false, "catalogId", true);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_DEPTH =
		new PIMConnectorChannelField("depth", false, "skus[].depth", false);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_DESCRIPTION =
		new PIMConnectorChannelField(
			"description", false, "description", false);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_HEIGHT =
		new PIMConnectorChannelField("height", false, "skus[].height", false);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_NAME =
		new PIMConnectorChannelField("name", false, "name", true);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_PRECISION =
		new PIMConnectorChannelField(
			"precision", false, "skus[].skuUnitOfMeasures[].precision", false);

	private static final PIMConnectorChannelField
		_CHANNEL_FIELD_PRODUCT_OPTIONS = new PIMConnectorChannelField(
			"product-options", true, "productOptions", false);

	private static final PIMConnectorChannelField
		_CHANNEL_FIELD_PRODUCT_SPECIFICATIONS = new PIMConnectorChannelField(
			"product-specifications", true, "productSpecifications", false);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_PRODUCT_TYPE =
		new PIMConnectorChannelField(
			"product-type", false, "productType", true);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_SKU =
		new PIMConnectorChannelField("sku", false, "skus[].sku", true);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_TAGS =
		new PIMConnectorChannelField("tags", true, "tags", false);

	private static final PIMConnectorChannelField
		_CHANNEL_FIELD_UNIT_OF_MEASURE_KEY = new PIMConnectorChannelField(
			"unit-of-measure-key", false, "skus[].skuUnitOfMeasures[].key",
			false);

	private static final PIMConnectorChannelField
		_CHANNEL_FIELD_UNIT_OF_MEASURE_NAME = new PIMConnectorChannelField(
			"unit-of-measure-name", false, "skus[].skuUnitOfMeasures[].name",
			false);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_WEIGHT =
		new PIMConnectorChannelField("weight", false, "skus[].weight", false);

	private static final PIMConnectorChannelField _CHANNEL_FIELD_WIDTH =
		new PIMConnectorChannelField("width", false, "skus[].width", false);

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

}