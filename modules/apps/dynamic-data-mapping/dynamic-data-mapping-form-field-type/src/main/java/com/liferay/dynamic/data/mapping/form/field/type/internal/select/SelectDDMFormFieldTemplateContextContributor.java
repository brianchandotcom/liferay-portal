/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type.internal.select;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldOptionsFactory;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.field.type.internal.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldRenderingContext;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.list.type.util.comparator.ListTypeEntryNameComparator;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Collator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = "ddm.form.field.type.name=" + DDMFormFieldTypeConstants.SELECT,
	service = DDMFormFieldTemplateContextContributor.class
)
public class SelectDDMFormFieldTemplateContextContributor
	implements DDMFormFieldTemplateContextContributor {

	@Override
	public Map<String, Object> getParameters(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		ObjectField objectField = _getObjectField(
			ddmFormField, ddmFormFieldRenderingContext);

		return HashMapBuilder.<String, Object>put(
			"alphabeticalOrder",
			GetterUtil.getBoolean(ddmFormField.getProperty("alphabeticalOrder"))
		).put(
			"dataSourceType", ddmFormField.getDataSourceType()
		).put(
			"defaultSearch",
			GetterUtil.getBoolean(ddmFormField.getProperty("defaultSearch"))
		).put(
			"multiple",
			getMultiple(ddmFormField, ddmFormFieldRenderingContext, objectField)
		).put(
			"options",
			() -> {
				DDMFormFieldOptions ddmFormFieldOptions =
					ddmFormFieldOptionsFactory.create(
						ddmFormField, ddmFormFieldRenderingContext);

				return getOptions(
					ddmFormField, ddmFormFieldOptions,
					ddmFormFieldRenderingContext.getLocale(), objectField);
			}
		).put(
			"predefinedValue",
			getValue(
				DDMFormFieldTypeUtil.getPropertyValue(
					ddmFormField, ddmFormFieldRenderingContext.getLocale(),
					"predefinedValue"))
		).put(
			"showEmptyOption",
			GetterUtil.getBoolean(
				ddmFormField.getProperty("showEmptyOption"), true)
		).put(
			"strings", _getStrings(ddmFormFieldRenderingContext)
		).put(
			"tooltip",
			DDMFormFieldTypeUtil.getPropertyValue(
				ddmFormField, ddmFormFieldRenderingContext.getLocale(),
				"tooltip")
		).put(
			"value",
			getValue(
				GetterUtil.getString(
					ddmFormFieldRenderingContext.getValue(), "[]"))
		).build();
	}

	protected boolean getMultiple(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext,
		ObjectField objectField) {

		if (objectField != null) {
			if (objectField.compareBusinessType(
					ObjectFieldConstants.BUSINESS_TYPE_MULTISELECT_PICKLIST)) {

				return true;
			}

			return false;
		}

		Map<String, Object> changedProperties =
			(Map<String, Object>)ddmFormFieldRenderingContext.getProperty(
				"changedProperties");

		if (changedProperties != null) {
			Boolean multiple = (Boolean)changedProperties.get("multiple");

			if (multiple != null) {
				return multiple;
			}
		}

		return ddmFormField.isMultiple();
	}

	protected List<Map<String, String>> getObjectFieldOptions(
		DDMFormField ddmFormField, DDMFormFieldOptions ddmFormFieldOptions,
		ObjectField objectField) {

		if (objectField == null) {
			return Collections.emptyList();
		}

		OrderByComparator<ListTypeEntry> orderByComparator = null;

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		if (GetterUtil.getBoolean(
				ddmFormField.getProperty("alphabeticalOrder"))) {

			orderByComparator = new ListTypeEntryNameComparator(true, locale);
		}

		List<Map<String, String>> options = new ArrayList<>();

		for (ListTypeEntry listTypeEntry :
				_listTypeEntryLocalService.getListTypeEntries(
					objectField.getListTypeDefinitionId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator)) {

			Map<Locale, String> nameMap = listTypeEntry.getNameMap();

			if (!nameMap.containsKey(locale)) {
				continue;
			}

			options.add(
				HashMapBuilder.put(
					"label", nameMap.get(locale)
				).put(
					"reference", listTypeEntry.getKey()
				).put(
					"value",
					() -> {
						String optionValue = ddmFormFieldOptions.getOptionValue(
							listTypeEntry.getKey());

						if (Validator.isNotNull(optionValue)) {
							return optionValue;
						}

						return listTypeEntry.getKey();
					}
				).build());
		}

		return options;
	}

	protected List<Map<String, String>> getOptions(
		DDMFormField ddmFormField, DDMFormFieldOptions ddmFormFieldOptions,
		Locale locale, ObjectField objectField) {

		boolean alphabeticalOrder = GetterUtil.getBoolean(
			ddmFormField.getProperty("alphabeticalOrder"));

		List<Map<String, String>> objectFieldOptions = getObjectFieldOptions(
			ddmFormField, ddmFormFieldOptions, objectField);

		if (ListUtil.isNotEmpty(objectFieldOptions)) {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			Locale serviceContextLocale = LocaleUtil.fromLanguageId(
				serviceContext.getLanguageId());

			if (alphabeticalOrder && (locale != serviceContextLocale)) {
				return _getSortedOptions(
					serviceContextLocale, objectFieldOptions);
			}

			return objectFieldOptions;
		}

		List<Map<String, String>> options = new ArrayList<>();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
			if (optionValue == null) {
				continue;
			}

			options.add(
				HashMapBuilder.put(
					"label",
					() -> {
						LocalizedValue localizedValue =
							ddmFormFieldOptions.getOptionLabels(optionValue);

						return localizedValue.getString(locale);
					}
				).put(
					"reference",
					ddmFormFieldOptions.getOptionReference(optionValue)
				).put(
					"value", optionValue
				).build());
		}

		if (alphabeticalOrder) {
			return _getSortedOptions(locale, options);
		}

		return options;
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		Class<?> clazz = getClass();

		ResourceBundle portalResourceBundle = portal.getResourceBundle(locale);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, clazz.getClassLoader());

		return new AggregateResourceBundle(
			resourceBundle, portalResourceBundle);
	}

	protected List<String> getValue(String valueString) {
		JSONArray jsonArray = null;

		try {
			jsonArray = jsonFactory.createJSONArray(valueString);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return ListUtil.fromString(valueString);
		}

		List<String> values = new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			values.add(String.valueOf(jsonArray.get(i)));
		}

		return values;
	}

	@Reference
	protected DDMFormFieldOptionsFactory ddmFormFieldOptionsFactory;

	@Reference
	protected JSONFactory jsonFactory;

	@Reference
	protected Portal portal;

	private ObjectField _getObjectField(
		DDMFormField ddmFormField,
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.fetchDDMFormInstance(
				ddmFormFieldRenderingContext.getDDMFormInstanceId());

		if (ddmFormInstance == null) {
			return null;
		}

		try {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					ddmFormInstance.getObjectDefinitionId());

			if (objectDefinition == null) {
				return null;
			}

			JSONArray jsonArray = jsonFactory.createJSONArray(
				GetterUtil.getString(
					ddmFormField.getProperty("objectFieldName")));

			return _objectFieldLocalService.getObjectField(
				objectDefinition.getObjectDefinitionId(),
				jsonArray.getString(0));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private List<Map<String, String>> _getSortedOptions(
		Locale locale, List<Map<String, String>> options) {

		Collator collator = CollatorUtil.getInstance(locale);

		options.sort(
			(map1, map2) -> {
				String label1 = map1.get("label");
				String label2 = map2.get("label");

				return collator.compare(label1, label2);
			});

		return options;
	}

	private Map<String, String> _getStrings(
		DDMFormFieldRenderingContext ddmFormFieldRenderingContext) {

		Locale displayLocale = LocaleThreadLocal.getThemeDisplayLocale();

		if (displayLocale == null) {
			displayLocale = ddmFormFieldRenderingContext.getLocale();
		}

		ResourceBundle resourceBundle = getResourceBundle(displayLocale);

		return HashMapBuilder.put(
			"chooseAnOption", _language.get(resourceBundle, "choose-an-option")
		).put(
			"chooseOptions", _language.get(resourceBundle, "choose-options")
		).put(
			"dynamicallyLoadedData",
			_language.get(resourceBundle, "dynamically-loaded-data")
		).put(
			"emptyList", _language.get(resourceBundle, "empty-list")
		).put(
			"search", _language.get(resourceBundle, "search")
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDDMFormFieldTemplateContextContributor.class);

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private Language _language;

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}