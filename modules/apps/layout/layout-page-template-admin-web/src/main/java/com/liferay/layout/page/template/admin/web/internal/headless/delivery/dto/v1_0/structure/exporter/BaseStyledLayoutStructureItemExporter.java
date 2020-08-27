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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.exporter;

import com.liferay.headless.delivery.dto.v1_0.ClassPKReference;
import com.liferay.headless.delivery.dto.v1_0.ContextReference;
import com.liferay.headless.delivery.dto.v1_0.FragmentImage;
import com.liferay.headless.delivery.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.delivery.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.delivery.dto.v1_0.Mapping;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseStyledLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	protected Function<Object, String> getImageURLTransformerFunction() {
		return object -> {
			if (object instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)object;

				return jsonObject.getString("url");
			}

			if (object instanceof String) {
				return (String)object;
			}

			return StringPool.BLANK;
		};
	}

	protected boolean isSaveFragmentMappedValue(
		JSONObject jsonObject, boolean saveMapping) {

		if (saveMapping && jsonObject.has("classNameId") &&
			jsonObject.has("classPK") && jsonObject.has("fieldId")) {

			return true;
		}

		if (saveMapping && jsonObject.has("collectionFieldId")) {
			return true;
		}

		if (saveMapping && jsonObject.has("mappedField")) {
			return true;
		}

		return false;
	}

	protected FragmentImage toBackgroundFragmentImage(
		JSONObject jsonObject, boolean saveMappingConfiguration) {

		if (jsonObject == null) {
			return null;
		}

		String urlValue = jsonObject.getString("url");

		return new FragmentImage() {
			{
				title = toTitleFragmentInlineValue(jsonObject, urlValue);

				setUrl(
					() -> {
						if (isSaveFragmentMappedValue(
								jsonObject, saveMappingConfiguration)) {

							return toFragmentMappedValue(
								toDefaultMappingValue(
									jsonObject,
									getImageURLTransformerFunction()),
								jsonObject);
						}

						if (Validator.isNull(urlValue)) {
							return null;
						}

						return new FragmentInlineValue() {
							{
								value = urlValue;
							}
						};
					});
			}
		};
	}

	protected FragmentInlineValue toDefaultMappingValue(
		JSONObject jsonObject, Function<Object, String> transformerFunction) {

		long classNameId = jsonObject.getLong("classNameId");

		if (classNameId == 0) {
			return null;
		}

		String className = null;

		try {
			className = portal.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get class name for default mapping value",
					exception);
			}
		}

		if (Validator.isNull(className)) {
			return null;
		}

		InfoDisplayContributor<Object> infoDisplayContributor =
			(InfoDisplayContributor<Object>)
				infoDisplayContributorTracker.getInfoDisplayContributor(
					className);

		if (infoDisplayContributor == null) {
			return null;
		}

		long classPK = jsonObject.getLong("classPK");

		try {
			InfoDisplayObjectProvider<Object> infoDisplayObjectProvider =
				(InfoDisplayObjectProvider<Object>)
					infoDisplayContributor.getInfoDisplayObjectProvider(
						classPK);

			if (infoDisplayObjectProvider == null) {
				return null;
			}

			Map<String, Object> fieldValues =
				infoDisplayContributor.getInfoDisplayFieldsValues(
					infoDisplayObjectProvider.getDisplayObject(),
					LocaleUtil.getMostRelevantLocale());

			Object fieldValue = fieldValues.get(
				jsonObject.getString("fieldId"));

			if (transformerFunction != null) {
				fieldValue = transformerFunction.apply(fieldValue);
			}

			String valueString = GetterUtil.getString(fieldValue);

			if (Validator.isNull(valueString)) {
				return null;
			}

			return new FragmentInlineValue() {
				{
					value = valueString;
				}
			};
		}
		catch (Exception exception) {
			_log.error("Unable to get default mapped value", exception);
		}

		return null;
	}

	protected FragmentMappedValue toFragmentMappedValue(
		FragmentInlineValue fragmentInlineValue, JSONObject jsonObject) {

		return new FragmentMappedValue() {
			{
				mapping = new Mapping() {
					{
						defaultFragmentInlineValue = fragmentInlineValue;
						itemReference = toItemReference(jsonObject);

						setFieldKey(
							() -> {
								String collectionFieldId = jsonObject.getString(
									"collectionFieldId");

								if (Validator.isNotNull(collectionFieldId)) {
									return collectionFieldId;
								}

								String fieldId = jsonObject.getString(
									"fieldId");

								if (Validator.isNotNull(fieldId)) {
									return fieldId;
								}

								String mappedField = jsonObject.getString(
									"mappedField");

								if (Validator.isNotNull(mappedField)) {
									return mappedField;
								}

								return null;
							});
					}
				};
			}
		};
	}

	protected String toItemClassName(JSONObject jsonObject) {
		String classNameIdString = jsonObject.getString("classNameId");

		if (Validator.isNull(classNameIdString)) {
			return null;
		}

		long classNameId = 0;

		try {
			classNameId = Long.parseLong(classNameIdString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class name could not be set since class name " +
							"ID %s could not be parsed to a long",
						classNameIdString),
					numberFormatException);
			}

			return null;
		}

		String className = null;

		try {
			className = portal.getClassName(classNameId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Item class name could not be set since no class name " +
						"could be obtained for class name ID " + classNameId,
					exception);
			}

			return null;
		}

		return className;
	}

	protected Long toitemClassPK(JSONObject jsonObject) {
		String classPKString = jsonObject.getString("classPK");

		if (Validator.isNull(classPKString)) {
			return null;
		}

		Long classPK = null;

		try {
			classPK = Long.parseLong(classPKString);
		}
		catch (NumberFormatException numberFormatException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					String.format(
						"Item class PK could not be set since class PK %s " +
							"could not be parsed to a long",
						classPKString),
					numberFormatException);
			}

			return null;
		}

		return classPK;
	}

	protected Object toItemReference(JSONObject jsonObject) {
		String collectionFieldId = jsonObject.getString("collectionFieldId");
		String fieldId = jsonObject.getString("fieldId");
		String mappedField = jsonObject.getString("mappedField");

		if (Validator.isNull(collectionFieldId) && Validator.isNull(fieldId) &&
			Validator.isNull(mappedField)) {

			return null;
		}

		if (Validator.isNotNull(collectionFieldId)) {
			return new ContextReference() {
				{
					contextSource = ContextSource.COLLECTION_ITEM;
				}
			};
		}

		if (Validator.isNotNull(mappedField)) {
			return new ContextReference() {
				{
					contextSource = ContextSource.DISPLAY_PAGE_ITEM;
				}
			};
		}

		return new ClassPKReference() {
			{
				className = toItemClassName(jsonObject);
				classPK = toitemClassPK(jsonObject);
			}
		};
	}

	protected Map<String, Object> toMap(JSONObject jsonObject) {
		Map<String, Object> map = new HashMap<>();

		Set<String> keys = jsonObject.keySet();

		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			Object value = jsonObject.get(key);

			if (value instanceof JSONObject) {
				value = toMap((JSONObject)value);
			}

			map.put(key, value);
		}

		return map;
	}

	protected Map<String, Object> toStyles(
		JSONObject jsonObject, boolean saveMappingConfiguration) {

		if (jsonObject == null) {
			return Collections.emptyMap();
		}

		return new HashMap<String, Object>() {
			{
				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					Object value = jsonObject.get(key);

					if (Objects.equals(key, "backgroundImage")) {
						JSONObject backgroundImageJSONObject =
							(JSONObject)value;

						value = toBackgroundFragmentImage(
							backgroundImageJSONObject,
							saveMappingConfiguration);
					}
					else if (value instanceof JSONObject) {
						value = toMap((JSONObject)value);
					}

					put(key, value);
				}
			}
		};
	}

	protected FragmentInlineValue toTitleFragmentInlineValue(
		JSONObject jsonObject, String urlValue) {

		String title = jsonObject.getString("title");

		if (Validator.isNull(title) || title.equals(urlValue)) {
			return null;
		}

		return new FragmentInlineValue() {
			{
				value = title;
			}
		};
	}

	@Reference
	protected InfoDisplayContributorTracker infoDisplayContributorTracker;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseStyledLayoutStructureItemExporter.class);

}