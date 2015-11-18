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

package com.liferay.dynamic.data.mapping.form.values.factory.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRequestParameterRetriever;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldValueRequestParameterRetriever;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormValuesFactory.class)
public class DDMFormValuesFactoryImpl implements DDMFormValuesFactory {

	@Override
	public DDMFormValues create(
		HttpServletRequest httpServletRequest, DDMForm ddmForm) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		setDDMFormValuesAvailableLocales(httpServletRequest, ddmFormValues);
		setDDMFormValuesDefaultLocale(httpServletRequest, ddmFormValues);
		setDDMFormFieldValues(httpServletRequest, ddmFormValues);

		return ddmFormValues;
	}

	@Override
	public DDMFormValues create(
		PortletRequest portletRequest, DDMForm ddmForm) {

		return create(
			PortalUtil.getHttpServletRequest(portletRequest), ddmForm);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDMFormFieldValueRequestParameterRetriever.class,
			"ddm.form.field.type.name");
	}

	protected void checkDDMFormFieldParameterNames(
		DDMForm ddmForm, Set<String> ddmFormFieldParameterNames) {

		if (ddmFormFieldParameterNames.isEmpty()) {
			ddmFormFieldParameterNames.addAll(
				createDefaultDDMFormFieldParameterNames(ddmForm));

			return;
		}

		checkDDMFormFieldParameterNames(
			ddmForm.getDDMFormFields(), StringPool.BLANK,
			ddmFormFieldParameterNames);
	}

	protected void checkDDMFormFieldParameterNames(
		List<DDMFormField> ddmFormFields,
		String parentDDMFormFieldParameterName,
		Set<String> ddmFormFieldParameterNames) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (ddmFormField.isTransient()) {
				continue;
			}

			if (containsDDMFormFieldParameterName(
					ddmFormField, ddmFormFieldParameterNames)) {

				continue;
			}

			String defaultDDMFormFieldParameterName =
				createDefaultDDMFormFieldParameterName(
					ddmFormField, parentDDMFormFieldParameterName);

			ddmFormFieldParameterNames.add(defaultDDMFormFieldParameterName);
		}
	}

	protected boolean containsDDMFormFieldParameterName(
		DDMFormField ddmFormField, Set<String> ddmFormFieldParameterNames) {

		for (String ddmFormFieldParameterName : ddmFormFieldParameterNames) {
			String[] ddmFormFieldParameterNameParts =
				getDDMFormFieldParameterNameParts(ddmFormFieldParameterName);

			String fieldName = getFieldName(ddmFormFieldParameterNameParts);

			if (fieldName.equals(ddmFormField.getName())) {
				return true;
			}
		}

		return false;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		HttpServletRequest httpServletRequest, String ddmFormFieldParameterName,
		Map<String, DDMFormField> ddmFormFieldsMap) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		String[] lastDDMFormFieldParameterNameParts =
			getLastDDMFormFieldParameterNameParts(ddmFormFieldParameterName);

		String fieldName = getFieldName(lastDDMFormFieldParameterNameParts);

		ddmFormFieldValue.setName(fieldName);
		ddmFormFieldValue.setInstanceId(
			getFieldInstanceId(lastDDMFormFieldParameterNameParts));

		DDMFormField ddmFormField = ddmFormFieldsMap.get(fieldName);

		if (ddmFormField.isTransient()) {
			return ddmFormFieldValue;
		}

		if (ddmFormField.isLocalizable()) {
			setDDMFormFieldValueLocalizedValue(
				httpServletRequest, ddmFormField.getType(),
				ddmFormFieldParameterName, ddmFormField.getPredefinedValue(),
				ddmFormFieldValue);
		}
		else {
			setDDMFormFieldValueUnlocalizedValue(
				httpServletRequest, ddmFormField.getType(),
				ddmFormFieldParameterName, ddmFormField.getPredefinedValue(),
				ddmFormFieldValue);
		}

		return ddmFormFieldValue;
	}

	protected Map<String, DDMFormFieldValue> createDDMFormFieldValuesMap(
		HttpServletRequest httpServletRequest, DDMForm ddmForm) {

		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap = new HashMap<>();

		Set<String> ddmFormFieldParameterNames = getDDMFormFieldParameterNames(
			httpServletRequest, ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (String ddmFormFieldParameterName : ddmFormFieldParameterNames) {
			DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
				httpServletRequest, ddmFormFieldParameterName,
				ddmFormFieldsMap);

			ddmFormFieldValuesMap.put(
				ddmFormFieldParameterName, ddmFormFieldValue);
		}

		return ddmFormFieldValuesMap;
	}

	protected String createDefaultDDMFormFieldParameterName(
		DDMFormField ddmFormField,
		String parentDefaultDDMFormFieldParameterName) {

		StringBundler sb = new StringBundler(7);

		if (Validator.isNotNull(parentDefaultDDMFormFieldParameterName)) {
			sb.append(parentDefaultDDMFormFieldParameterName);
			sb.append(DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR);
		}

		sb.append(ddmFormField.getName());
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR);
		sb.append(StringUtil.randomString());
		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_PARTS_SEPARATOR);
		sb.append(0);

		return sb.toString();
	}

	protected Set<String> createDefaultDDMFormFieldParameterNames(
		DDMForm ddmForm) {

		Set<String> defaultDDMFormFieldParameterNames = new TreeSet<>();

		poupulateDefaultDDMFormFieldParameterNames(
			ddmForm.getDDMFormFields(), StringPool.BLANK,
			defaultDDMFormFieldParameterNames);

		return defaultDDMFormFieldParameterNames;
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected String extractPrefix(String ddmFormFieldParameterName) {
		return StringUtil.extractLast(
			ddmFormFieldParameterName,
			DDMFormRendererConstants.DDM_FORM_FIELD_NAME_PREFIX);
	}

	protected String extractSuffix(String ddmFormFieldParameterName) {
		int pos = ddmFormFieldParameterName.lastIndexOf(
			DDMFormRendererConstants.DDM_FORM_FIELD_LANGUAGE_ID_SEPARATOR);

		return ddmFormFieldParameterName.substring(0, pos);
	}

	protected Set<Locale> getAvailableLocales(
		HttpServletRequest httpServletRequest) {

		String[] availableLanguageIds = ParamUtil.getParameterValues(
			httpServletRequest, "availableLanguageIds");

		return getAvailableLocales(availableLanguageIds);
	}

	protected Set<Locale> getAvailableLocales(String[] availableLanguageIds) {
		Set<Locale> availableLocales = new HashSet<>();

		for (String availableLanguageId : availableLanguageIds) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				availableLanguageId);

			availableLocales.add(availableLocale);
		}

		if (availableLocales.isEmpty()) {
			availableLocales.add(LocaleUtil.getSiteDefault());
		}

		return availableLocales;
	}

	protected String[] getDDMFormFieldParameterNameParts(
		String ddmFormFieldParameterName) {

		return StringUtil.split(ddmFormFieldParameterName, StringPool.DOLLAR);
	}

	protected Set<String> getDDMFormFieldParameterNames(
		HttpServletRequest httpServletRequest, DDMForm ddmForm) {

		Set<String> ddmFormFieldParameterNames = new TreeSet<>();

		Map<String, String[]> parameterMap =
			httpServletRequest.getParameterMap();

		for (String parameterName : parameterMap.keySet()) {
			if (!isDDMFormFieldParameter(parameterName)) {
				continue;
			}

			ddmFormFieldParameterNames.addAll(
				getDDMFormFieldParameterNames(parameterName));
		}

		checkDDMFormFieldParameterNames(ddmForm, ddmFormFieldParameterNames);

		return ddmFormFieldParameterNames;
	}

	protected Set<String> getDDMFormFieldParameterNames(
		String ddmFormFieldParameterName) {

		Set<String> ddmFormFieldParameterNames = new TreeSet<>();

		ddmFormFieldParameterName = extractPrefix(ddmFormFieldParameterName);
		ddmFormFieldParameterName = extractSuffix(ddmFormFieldParameterName);

		ddmFormFieldParameterNames.add(ddmFormFieldParameterName);

		int pos = ddmFormFieldParameterName.indexOf(
			DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR);

		while (pos != -1) {
			ddmFormFieldParameterNames.add(
				ddmFormFieldParameterName.substring(0, pos));

			pos = ddmFormFieldParameterName.indexOf(
				DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR, pos + 1);
		}

		return ddmFormFieldParameterNames;
	}

	protected String getDDMFormFieldParameterValue(
		HttpServletRequest httpServletRequest, String fieldType,
		String ddmFormFieldParameterName,
		String defaultDDMFormFieldParameterValue, Locale locale) {

		StringBundler sb = new StringBundler(4);

		sb.append(DDMFormRendererConstants.DDM_FORM_FIELD_NAME_PREFIX);
		sb.append(ddmFormFieldParameterName);
		sb.append(
			DDMFormRendererConstants.DDM_FORM_FIELD_LANGUAGE_ID_SEPARATOR);
		sb.append(LocaleUtil.toLanguageId(locale));

		DDMFormFieldValueRequestParameterRetriever
			ddmFormFieldValueRequestParameterRetriever =
				getDDMFormFieldValueRequestParameterRetriever(fieldType);

		return ddmFormFieldValueRequestParameterRetriever.get(
			httpServletRequest, sb.toString(),
			defaultDDMFormFieldParameterValue);
	}

	protected int getDDMFormFieldValueIndex(String ddmFormFieldParameterName) {
		String[] lastDDMFormFieldParameterNameParts =
			getLastDDMFormFieldParameterNameParts(ddmFormFieldParameterName);

		return getFieldIndex(lastDDMFormFieldParameterNameParts);
	}

	protected DDMFormFieldValueRequestParameterRetriever
		getDDMFormFieldValueRequestParameterRetriever(String fieldType) {

		if (!_serviceTrackerMap.containsKey(fieldType)) {
			return _defaultDDMFormFieldValueRequestParameterRetriever;
		}

		return _serviceTrackerMap.getService(fieldType);
	}

	protected List<DDMFormFieldValue> getDDMFormFieldValues(
		HttpServletRequest httpServletRequest, DDMForm ddmForm) {

		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap =
			createDDMFormFieldValuesMap(httpServletRequest, ddmForm);

		return getDDMFormFieldValues(
			ddmFormFieldValuesMap, ddmForm.getDDMFormFields());
	}

	protected List<DDMFormFieldValue> getDDMFormFieldValues(
		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap,
		List<DDMFormField> ddmFormFields) {

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		int i = 0;

		for (DDMFormField ddmFormField : ddmFormFields) {
			Set<String> entryKeys = getEntryKeys(
				ddmFormFieldValuesMap, StringPool.BLANK,
				ddmFormField.getName());

			for (String entryKey : entryKeys) {
				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValuesMap.get(
					entryKey);

				int index = i + getDDMFormFieldValueIndex(entryKey);

				setNestedDDMFormFieldValues(
					ddmFormFieldValuesMap,
					ddmFormField.getNestedDDMFormFields(), ddmFormFieldValue,
					entryKey);

				setDDMFormFieldValueAtIndex(
					ddmFormFieldValues, ddmFormFieldValue, index);
			}

			i = ddmFormFieldValues.size();
		}

		return ddmFormFieldValues;
	}

	protected Locale getDefaultLocale(HttpServletRequest httpServletRequest) {
		String defaultLanguageId = ParamUtil.getString(
			httpServletRequest, "defaultLanguageId");

		if (Validator.isNull(defaultLanguageId)) {
			return LocaleUtil.getSiteDefault();
		}

		return LocaleUtil.fromLanguageId(defaultLanguageId);
	}

	protected String getEntryKeyPrefix(
		String parentEntryKey, String fieldNameFilter) {

		if (Validator.isNull(parentEntryKey)) {
			return StringPool.BLANK;
		}

		return parentEntryKey.concat(
			DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR).concat(
				fieldNameFilter);
	}

	protected Set<String> getEntryKeys(
		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap,
		String parentEntryKey, String fieldNameFilter) {

		Set<String> entryKeys = new HashSet<>();

		String entryKeyPrefix = getEntryKeyPrefix(
			parentEntryKey, fieldNameFilter);

		for (Map.Entry<String, DDMFormFieldValue> entry :
				ddmFormFieldValuesMap.entrySet()) {

			String key = entry.getKey();

			DDMFormFieldValue ddmFormFieldValue = entry.getValue();

			if (key.startsWith(entryKeyPrefix) &&
				Validator.equals(
					ddmFormFieldValue.getName(), fieldNameFilter)) {

				entryKeys.add(key);
			}
		}

		return entryKeys;
	}

	protected int getFieldIndex(String[] ddmFormFieldParameterNameParts) {
		return GetterUtil.getInteger(
			ddmFormFieldParameterNameParts[_DDM_FORM_FIELD_INDEX_INDEX]);
	}

	protected String getFieldInstanceId(
		String[] ddmFormFieldParameterNameParts) {

		return
			ddmFormFieldParameterNameParts[_DDM_FORM_FIELD_INSTANCE_ID_INDEX];
	}

	protected String getFieldName(String[] ddmFormFieldParameterNameParts) {
		return ddmFormFieldParameterNameParts[_DDM_FORM_FIELD_NAME_INDEX];
	}

	protected String getLastDDMFormFieldParameterName(
		String ddmFormFieldParameterName) {

		String lastDDMFormFieldParameterName = StringUtil.extractLast(
			ddmFormFieldParameterName,
			DDMFormRendererConstants.DDM_FORM_FIELDS_SEPARATOR);

		if (lastDDMFormFieldParameterName == null) {
			return ddmFormFieldParameterName;
		}

		return lastDDMFormFieldParameterName;
	}

	protected String[] getLastDDMFormFieldParameterNameParts(
		String ddmFormFieldParameterName) {

		String lastDDMFormFieldParameterName = getLastDDMFormFieldParameterName(
			ddmFormFieldParameterName);

		return getDDMFormFieldParameterNameParts(lastDDMFormFieldParameterName);
	}

	protected boolean isDDMFormFieldParameter(String parameterName) {
		if (parameterName.startsWith(
				DDMFormRendererConstants.DDM_FORM_FIELD_NAME_PREFIX)) {

			return true;
		}

		return false;
	}

	protected void poupulateDefaultDDMFormFieldParameterNames(
		List<DDMFormField> ddmFormFields,
		String parentDefaultDDMFormFieldParameterName,
		Set<String> defaultDDMFormFieldParameterNames) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (ddmFormField.isTransient()) {
				continue;
			}

			String defaultDDMFormFieldParameterName =
				createDefaultDDMFormFieldParameterName(
					ddmFormField, parentDefaultDDMFormFieldParameterName);

			defaultDDMFormFieldParameterNames.add(
				defaultDDMFormFieldParameterName);

			poupulateDefaultDDMFormFieldParameterNames(
				ddmFormField.getNestedDDMFormFields(),
				defaultDDMFormFieldParameterName,
				defaultDDMFormFieldParameterNames);
		}
	}

	protected void setDDMFormFieldValueAtIndex(
		List<DDMFormFieldValue> ddmFormFieldValues,
		DDMFormFieldValue ddmFormFieldValue, int index) {

		if (ddmFormFieldValues.size() < (index + 1)) {
			for (int i = ddmFormFieldValues.size(); i <= index; i++) {
				ddmFormFieldValues.add(null);
			}
		}

		ddmFormFieldValues.set(index, ddmFormFieldValue);
	}

	protected void setDDMFormFieldValueLocalizedValue(
		HttpServletRequest httpServletRequest, String fieldType,
		String ddmFormFieldParameterName, LocalizedValue predefinedValue,
		DDMFormFieldValue ddmFormFieldValue) {

		Locale defaultLocale = getDefaultLocale(httpServletRequest);

		Value value = new LocalizedValue(defaultLocale);

		Set<Locale> availableLocales = getAvailableLocales(httpServletRequest);

		for (Locale availableLocale : availableLocales) {
			String ddmFormFieldParameterValue = getDDMFormFieldParameterValue(
				httpServletRequest, fieldType, ddmFormFieldParameterName,
				predefinedValue.getString(availableLocale), availableLocale);

			value.addString(availableLocale, ddmFormFieldParameterValue);
		}

		ddmFormFieldValue.setValue(value);
	}

	protected void setDDMFormFieldValues(
		HttpServletRequest httpServletRequest, DDMFormValues ddmFormValues) {

		List<DDMFormFieldValue> ddmFormFieldValues = getDDMFormFieldValues(
			httpServletRequest, ddmFormValues.getDDMForm());

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
	}

	protected void setDDMFormFieldValueUnlocalizedValue(
		HttpServletRequest httpServletRequest, String fieldType,
		String ddmFormFieldParameterName, LocalizedValue predefinedValue,
		DDMFormFieldValue ddmFormFieldValue) {

		Locale defaultLocale = getDefaultLocale(httpServletRequest);

		String ddmFormFieldParameterValue = getDDMFormFieldParameterValue(
			httpServletRequest, fieldType, ddmFormFieldParameterName,
			predefinedValue.getString(defaultLocale), defaultLocale);

		Value value = new UnlocalizedValue(ddmFormFieldParameterValue);

		ddmFormFieldValue.setValue(value);
	}

	protected void setDDMFormValuesAvailableLocales(
		HttpServletRequest httpServletRequest, DDMFormValues ddmFormValues) {

		Set<Locale> availableLocales = getAvailableLocales(httpServletRequest);

		ddmFormValues.setAvailableLocales(availableLocales);
	}

	protected void setDDMFormValuesDefaultLocale(
		HttpServletRequest httpServletRequest, DDMFormValues ddmFormValues) {

		Locale defaultLocale = getDefaultLocale(httpServletRequest);

		ddmFormValues.setDefaultLocale(defaultLocale);
	}

	protected void setNestedDDMFormFieldValues(
		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap,
		List<DDMFormField> nestedDDMFormFields,
		DDMFormFieldValue parentDDMFormFieldValue, String parentEntryKey) {

		int i = 0;

		for (DDMFormField nestedDDMFormField : nestedDDMFormFields) {
			Set<String> entryKeys = getEntryKeys(
				ddmFormFieldValuesMap, parentEntryKey,
				nestedDDMFormField.getName());

			for (String entryKey : entryKeys) {
				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValuesMap.get(
					entryKey);

				int index = i + getDDMFormFieldValueIndex(entryKey);

				setNestedDDMFormFieldValues(
					ddmFormFieldValuesMap,
					nestedDDMFormField.getNestedDDMFormFields(),
					ddmFormFieldValue, entryKey);

				setDDMFormFieldValueAtIndex(
					parentDDMFormFieldValue.getNestedDDMFormFieldValues(),
					ddmFormFieldValue, index);
			}

			List<DDMFormFieldValue> parentNestedDDMFormFieldValues =
				parentDDMFormFieldValue.getNestedDDMFormFieldValues();

			i = parentNestedDDMFormFieldValues.size();
		}
	}

	private static final int _DDM_FORM_FIELD_INDEX_INDEX = 2;

	private static final int _DDM_FORM_FIELD_INSTANCE_ID_INDEX = 1;

	private static final int _DDM_FORM_FIELD_NAME_INDEX = 0;

	private final DDMFormFieldValueRequestParameterRetriever
		_defaultDDMFormFieldValueRequestParameterRetriever =
			new DefaultDDMFormFieldValueRequestParameterRetriever();
	private ServiceTrackerMap
		<String, DDMFormFieldValueRequestParameterRetriever> _serviceTrackerMap;

}