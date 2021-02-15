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
import com.liferay.dynamic.data.mapping.form.renderer.constants.DDMFormRendererConstants;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFieldParameterNameUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesFactoryUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMFormValuesFactory.class)
public class DDMFormValuesFactoryImpl implements DDMFormValuesFactory {

	@Override
	public DDMFormValues create(
		HttpServletRequest httpServletRequest, DDMForm ddmForm) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		setDDMFormValuesAvailableLocales(
			httpServletRequest, ddmForm, ddmFormValues);
		setDDMFormValuesDefaultLocale(
			httpServletRequest, ddmForm, ddmFormValues);
		setDDMFormFieldValues(httpServletRequest, ddmFormValues);

		return ddmFormValues;
	}

	@Override
	public DDMFormValues create(
		PortletRequest portletRequest, DDMForm ddmForm) {

		return create(_portal.getHttpServletRequest(portletRequest), ddmForm);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
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
			Set<String> filteredDDMFormFieldParameterNames =
				filterDDMFormFieldParameterNames(
					ddmFormField, ddmFormFieldParameterNames);

			String ddmFormFieldParameterPrefix = getDDMFormFieldParameterPrefix(
				ddmFormField, parentDDMFormFieldParameterName);

			boolean containsDefaultDDMFormFieldParameterName =
				containsDefaultDDMFormFieldParameterName(
					filteredDDMFormFieldParameterNames,
					ddmFormFieldParameterPrefix);

			if (!containsDefaultDDMFormFieldParameterName) {
				String defaultDDMFormFieldParameterName =
					createDefaultDDMFormFieldParameterName(
						ddmFormField, parentDDMFormFieldParameterName);

				ddmFormFieldParameterNames.add(
					defaultDDMFormFieldParameterName);

				checkDDMFormFieldParameterNames(
					ddmFormField.getNestedDDMFormFields(), StringPool.BLANK,
					ddmFormFieldParameterNames);
			}

			for (String filteredDDMFormFieldParameterName :
					filteredDDMFormFieldParameterNames) {

				checkDDMFormFieldParameterNames(
					ddmFormField.getNestedDDMFormFields(),
					filteredDDMFormFieldParameterName,
					ddmFormFieldParameterNames);
			}
		}
	}

	protected boolean containsDefaultDDMFormFieldParameterName(
		Set<String> filteredDDMFormFieldParameterNames,
		String ddmFormFieldParameterPrefix) {

		for (String filteredDDMFormFieldParameterName :
				filteredDDMFormFieldParameterNames) {

			if (filteredDDMFormFieldParameterName.startsWith(
					ddmFormFieldParameterPrefix)) {

				return true;
			}
		}

		return false;
	}

	protected DDMFormFieldValue createDDMFormFieldValue(
		HttpServletRequest httpServletRequest, DDMForm ddmForm,
		DDMFormValues ddmFormValues, String ddmFormFieldParameterName,
		Map<String, DDMFormField> ddmFormFieldsMap) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		String[] lastDDMFormFieldParameterNameParts =
			DDMFormFieldParameterNameUtil.getLastDDMFormFieldParameterNameParts(
				ddmFormFieldParameterName);

		String fieldName = lastDDMFormFieldParameterNameParts
			[DDMFormFieldParameterNameUtil.DDM_FORM_FIELD_NAME_INDEX];

		ddmFormFieldValue.setName(fieldName);

		ddmFormFieldValue.setInstanceId(
			lastDDMFormFieldParameterNameParts
				[DDMFormFieldParameterNameUtil.
					DDM_FORM_FIELD_INSTANCE_ID_INDEX]);

		DDMFormField ddmFormField = ddmFormFieldsMap.get(fieldName);

		ddmFormFieldValue.setFieldReference(ddmFormField.getFieldReference());

		if (ddmFormField.isTransient()) {
			return ddmFormFieldValue;
		}

		if (ddmFormField.isLocalizable()) {
			setDDMFormFieldValueLocalizedValue(
				httpServletRequest, ddmFormField.getType(),
				ddmFormFieldParameterName, ddmFormField.getPredefinedValue(),
				ddmFormFieldValue, ddmFormValues.getAvailableLocales(),
				ddmFormValues.getDefaultLocale());
		}
		else {
			setDDMFormFieldValueUnlocalizedValue(
				httpServletRequest, ddmFormField.getType(),
				ddmFormFieldParameterName, ddmFormField.getPredefinedValue(),
				ddmFormFieldValue, ddmFormValues.getAvailableLocales(),
				ddmFormValues.getDefaultLocale());
		}

		return ddmFormFieldValue;
	}

	protected Map<String, DDMFormFieldValue> createDDMFormFieldValuesMap(
		HttpServletRequest httpServletRequest, DDMForm ddmForm,
		DDMFormValues ddmFormValues) {

		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap = new HashMap<>();

		Set<String> ddmFormFieldParameterNames = getDDMFormFieldParameterNames(
			httpServletRequest, ddmForm);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (String ddmFormFieldParameterName : ddmFormFieldParameterNames) {
			DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
				httpServletRequest, ddmForm, ddmFormValues,
				ddmFormFieldParameterName, ddmFormFieldsMap);

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

	protected Set<String> filterDDMFormFieldParameterNames(
		DDMFormField ddmFormField, Set<String> ddmFormFieldParameterNames) {

		Set<String> filteredDDMFormFieldParameterNames = new HashSet<>();

		for (String ddmFormFieldParameterName : ddmFormFieldParameterNames) {
			String[] ddmFormFieldParameterNameParts =
				DDMFormFieldParameterNameUtil.
					getLastDDMFormFieldParameterNameParts(
						ddmFormFieldParameterName);

			String fieldName = ddmFormFieldParameterNameParts
				[DDMFormFieldParameterNameUtil.DDM_FORM_FIELD_NAME_INDEX];

			if (fieldName.equals(ddmFormField.getName())) {
				filteredDDMFormFieldParameterNames.add(
					ddmFormFieldParameterName);
			}
		}

		return filteredDDMFormFieldParameterNames;
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

	protected String getDDMFormFieldParameterPrefix(
		DDMFormField ddmFormField, String parentDDMFormFieldParameterName) {

		if (Validator.isNull(parentDDMFormFieldParameterName)) {
			return ddmFormField.getName();
		}

		return StringBundler.concat(
			parentDDMFormFieldParameterName, StringPool.POUND,
			ddmFormField.getName());
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

		if (ArrayUtil.isEmpty(
				ParamUtil.getParameterValues(
					httpServletRequest, sb.toString()))) {

			sb.setIndex(sb.index() - 1);

			sb.append(LanguageUtil.getLanguageId(LocaleUtil.getSiteDefault()));
		}

		DDMFormFieldValueRequestParameterRetriever
			ddmFormFieldValueRequestParameterRetriever =
				getDDMFormFieldValueRequestParameterRetriever(fieldType);

		return ddmFormFieldValueRequestParameterRetriever.get(
			httpServletRequest, sb.toString(),
			GetterUtil.getString(defaultDDMFormFieldParameterValue));
	}

	protected DDMFormFieldValueRequestParameterRetriever
		getDDMFormFieldValueRequestParameterRetriever(String fieldType) {

		if (!_serviceTrackerMap.containsKey(fieldType)) {
			return _defaultDDMFormFieldValueRequestParameterRetriever;
		}

		return _serviceTrackerMap.getService(fieldType);
	}

	protected List<DDMFormFieldValue> getDDMFormFieldValues(
		HttpServletRequest httpServletRequest, DDMForm ddmForm,
		DDMFormValues ddmFormValues) {

		Map<String, DDMFormFieldValue> ddmFormFieldValuesMap =
			createDDMFormFieldValuesMap(
				httpServletRequest, ddmForm, ddmFormValues);

		return DDMFormValuesFactoryUtil.getDDMFormFieldValues(
			ddmFormFieldValuesMap, ddmForm.getDDMFormFields());
	}

	protected Locale getDefaultLocale(
		HttpServletRequest httpServletRequest, Locale defaultLocale,
		Set<Locale> availableLocales) {

		String defaultLanguageId = ParamUtil.getString(
			httpServletRequest, "defaultLanguageId");

		if (Validator.isNotNull(defaultLanguageId)) {
			return LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		Locale httpServletRequestLocale = LocaleUtil.fromLanguageId(
			LanguageUtil.getLanguageId(httpServletRequest));

		if (availableLocales.contains(httpServletRequestLocale)) {
			return httpServletRequestLocale;
		}

		return defaultLocale;
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

	protected void setDDMFormFieldValueLocalizedValue(
		HttpServletRequest httpServletRequest, String fieldType,
		String ddmFormFieldParameterName, LocalizedValue predefinedValue,
		DDMFormFieldValue ddmFormFieldValue, Set<Locale> availableLocales,
		Locale defaultLocale) {

		Value value = new LocalizedValue(defaultLocale);

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
			httpServletRequest, ddmFormValues.getDDMForm(), ddmFormValues);

		ddmFormValues.setDDMFormFieldValues(ddmFormFieldValues);
	}

	protected void setDDMFormFieldValueUnlocalizedValue(
		HttpServletRequest httpServletRequest, String fieldType,
		String ddmFormFieldParameterName, LocalizedValue predefinedValue,
		DDMFormFieldValue ddmFormFieldValue, Set<Locale> availableLocales,
		Locale defaultLocale) {

		String ddmFormFieldParameterValue = getDDMFormFieldParameterValue(
			httpServletRequest, fieldType, ddmFormFieldParameterName,
			predefinedValue.getString(defaultLocale), defaultLocale);

		Value value = new UnlocalizedValue(ddmFormFieldParameterValue);

		ddmFormFieldValue.setValue(value);
	}

	protected void setDDMFormValuesAvailableLocales(
		HttpServletRequest httpServletRequest, DDMForm ddmForm,
		DDMFormValues ddmFormValues) {

		String[] availableLocalesString = ParamUtil.getStringValues(
			httpServletRequest, "availableLocales");

		if (ArrayUtil.isEmpty(availableLocalesString)) {
			for (Locale availableLocale : ddmForm.getAvailableLocales()) {
				ddmFormValues.addAvailableLocale(availableLocale);
			}
		}
		else {
			for (String availableLocaleString : availableLocalesString) {
				ddmFormValues.addAvailableLocale(
					LocaleUtil.fromLanguageId(availableLocaleString));
			}
		}
	}

	protected void setDDMFormValuesDefaultLocale(
		HttpServletRequest httpServletRequest, DDMForm ddmForm,
		DDMFormValues ddmFormValues) {

		ddmFormValues.setDefaultLocale(
			getDefaultLocale(
				httpServletRequest, ddmForm.getDefaultLocale(),
				ddmForm.getAvailableLocales()));
	}

	private final DDMFormFieldValueRequestParameterRetriever
		_defaultDDMFormFieldValueRequestParameterRetriever =
			new DefaultDDMFormFieldValueRequestParameterRetriever();

	@Reference
	private Portal _portal;

	private ServiceTrackerMap
		<String, DDMFormFieldValueRequestParameterRetriever> _serviceTrackerMap;

}