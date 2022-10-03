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

package com.liferay.web.form.web.internal.portlet.action;

import com.liferay.expando.kernel.exception.ColumnNameException;
import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Localization;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.web.form.web.internal.constants.WebFormPortletKeys;
import com.liferay.web.form.web.internal.util.WebFormUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 * @author Alberto Montero
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + WebFormPortletKeys.WEB_FORM,
	service = ConfigurationAction.class
)
public class WebFormConfigurationAction extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		String cmd = ParamUtil.getString(httpServletRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			return "/edit_field.jsp";
		}

		return "/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		_validateFields(actionRequest);

		if (!SessionErrors.isEmpty(actionRequest)) {
			return;
		}

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

		boolean updateFields = ParamUtil.getBoolean(
			actionRequest, "updateFields");

		PortletPreferences preferences = actionRequest.getPreferences();

		_localization.setLocalizedPreferencesValues(
			actionRequest, preferences, "title");

		Map<Locale, String> titleMap = _localization.getLocalizationMap(
			actionRequest, "title");

		preferences.setValue("title", titleMap.get(defaultLocale));

		_localization.setLocalizedPreferencesValues(
			actionRequest, preferences, "description");

		Map<Locale, String> descriptionMap = _localization.getLocalizationMap(
			actionRequest, "description");

		preferences.setValue("description", descriptionMap.get(defaultLocale));

		if (updateFields) {
			int i = 1;

			String portletResource = ParamUtil.getString(
				actionRequest, "portletResource");

			String databaseTableName = WebFormUtil.getNewDatabaseTableName(
				portletResource);

			preferences.setValue("databaseTableName", databaseTableName);

			int[] formFieldsIndexes = StringUtil.split(
				ParamUtil.getString(actionRequest, "formFieldsIndexes"), 0);

			for (int formFieldsIndex : formFieldsIndexes) {
				Map<Locale, String> fieldLabelMap =
					_localization.getLocalizationMap(
						actionRequest, "fieldLabel" + formFieldsIndex);

				if (Validator.isNull(fieldLabelMap.get(defaultLocale))) {
					continue;
				}

				String fieldType = ParamUtil.getString(
					actionRequest, "fieldType" + formFieldsIndex);
				boolean fieldOptional = ParamUtil.getBoolean(
					actionRequest, "fieldOptional" + formFieldsIndex);
				Map<Locale, String> fieldOptionsMap =
					_localization.getLocalizationMap(
						actionRequest, "fieldOptions" + formFieldsIndex);
				Map<Locale, String> fieldParagraphMap =
					_localization.getLocalizationMap(
						actionRequest, "fieldParagraph" + formFieldsIndex);

				String fieldValidationScript = ParamUtil.getString(
					actionRequest, "fieldValidationScript" + formFieldsIndex);
				String fieldValidationErrorMessage = ParamUtil.getString(
					actionRequest,
					"fieldValidationErrorMessage" + formFieldsIndex);

				if (Validator.isNotNull(fieldValidationScript) ^
					Validator.isNotNull(fieldValidationErrorMessage)) {

					SessionErrors.add(
						actionRequest, "validationDefinitionInvalid" + i);
				}

				_updateModifiedLocales(
					"fieldLabel" + i, fieldLabelMap, preferences);
				_updateModifiedLocales(
					"fieldOptions" + i, fieldOptionsMap, preferences);
				_updateModifiedLocales(
					"fieldParagraph" + i, fieldParagraphMap, preferences);

				preferences.setValue(
					"fieldLabel" + i, fieldLabelMap.get(defaultLocale));
				preferences.setValue("fieldType" + i, fieldType);
				preferences.setValue(
					"fieldOptional" + i, String.valueOf(fieldOptional));
				preferences.setValue("fieldOptions" + i, StringPool.BLANK);
				preferences.setValue("fieldParagraph" + i, StringPool.BLANK);
				preferences.setValue(
					"fieldValidationScript" + i, fieldValidationScript);
				preferences.setValue(
					"fieldValidationErrorMessage" + i,
					fieldValidationErrorMessage);

				i++;
			}

			if (!SessionErrors.isEmpty(actionRequest)) {
				return;
			}

			// Clear previous preferences that are now blank

			String fieldLabel = _localization.getPreferencesValue(
				preferences, "fieldLabel" + i, defaultLanguageId);

			while (Validator.isNotNull(fieldLabel)) {
				Map<Locale, String> fieldLabelMap =
					_localization.getLocalizationMap(
						actionRequest, "fieldLabel" + i);

				for (Locale locale : fieldLabelMap.keySet()) {
					String languageId = LocaleUtil.toLanguageId(locale);

					_localization.setPreferencesValue(
						preferences, "fieldLabel" + i, languageId,
						StringPool.BLANK);
					_localization.setPreferencesValue(
						preferences, "fieldOptions" + i, languageId,
						StringPool.BLANK);
					_localization.setPreferencesValue(
						preferences, "fieldParagraph" + i, languageId,
						StringPool.BLANK);
				}

				preferences.setValue("fieldLabel" + i, StringPool.BLANK);
				preferences.setValue("fieldType" + i, StringPool.BLANK);
				preferences.setValue("fieldOptional" + i, StringPool.BLANK);
				preferences.setValue(
					"fieldValidationScript" + i, StringPool.BLANK);
				preferences.setValue(
					"fieldValidationErrorMessage" + i, StringPool.BLANK);

				i++;

				fieldLabel = _localization.getPreferencesValue(
					preferences, "fieldLabel" + i, defaultLanguageId);
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.store();
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	private void _updateModifiedLocales(
			String parameter, Map<Locale, String> newLocalizationMap,
			PortletPreferences preferences)
		throws Exception {

		Map<Locale, String> oldLocalizationMap =
			_localization.getLocalizationMap(preferences, parameter);

		List<Locale> modifiedLocales = new ArrayList<>();

		if ((newLocalizationMap == null) || newLocalizationMap.isEmpty()) {
			modifiedLocales = Collections.emptyList();
		}

		for (Locale locale : _language.getAvailableLocales()) {
			String oldValue = oldLocalizationMap.get(locale);
			String newValue = newLocalizationMap.get(locale);

			if (!oldValue.equals(newValue)) {
				modifiedLocales.add(locale);
			}
		}

		for (Locale locale : modifiedLocales) {
			String languageId = LocaleUtil.toLanguageId(locale);
			String value = newLocalizationMap.get(locale);

			_localization.setPreferencesValue(
				preferences, parameter, languageId, value);
		}
	}

	private void _validateEmailFields(ActionRequest actionRequest) {
		String subject = getParameter(actionRequest, "subject");

		if (Validator.isNull(subject)) {
			SessionErrors.add(actionRequest, "subjectRequired");
		}

		String[] emailAdresses = WebFormUtil.split(
			getParameter(actionRequest, "emailAddress"));
		String emailFromAddress = GetterUtil.getString(
			getParameter(actionRequest, "emailFromAddress"));

		if ((emailAdresses.length == 0) || Validator.isNull(emailFromAddress)) {
			SessionErrors.add(actionRequest, "emailAddressRequired");
		}

		if (Validator.isNotNull(emailFromAddress) &&
			!Validator.isEmailAddress(emailFromAddress)) {

			SessionErrors.add(actionRequest, "emailAddressInvalid");
		}
		else {
			for (String emailAdress : emailAdresses) {
				emailAdress = emailAdress.trim();

				if (!Validator.isEmailAddress(emailAdress)) {
					SessionErrors.add(actionRequest, "emailAddressInvalid");

					break;
				}
			}
		}
	}

	private void _validateFieldNameLength(ActionRequest actionRequest) {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		int[] formFieldsIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "formFieldsIndexes"), 0);
		boolean saveToDatabase = GetterUtil.getBoolean(
			getParameter(actionRequest, "saveToDatabase"));

		for (int formFieldsIndex : formFieldsIndexes) {
			Map<Locale, String> fieldLabelMap =
				_localization.getLocalizationMap(
					actionRequest, "fieldLabel" + formFieldsIndex);

			for (Map.Entry<Locale, String> entry : fieldLabelMap.entrySet()) {
				String fieldLabelValue = entry.getValue();
				Locale locale = entry.getKey();

				if (locale.equals(defaultLocale) &&
					Validator.isNull(fieldLabelValue)) {

					SessionErrors.add(
						actionRequest, ColumnNameException.class.getName());

					return;
				}

				if (Validator.isNotNull(fieldLabelValue) && saveToDatabase &&
					(fieldLabelValue.length() > 75)) {

					SessionErrors.add(
						actionRequest, "fieldSizeInvalid" + formFieldsIndex);

					return;
				}
			}
		}
	}

	private void _validateFields(ActionRequest actionRequest) throws Exception {
		boolean saveToDatabase = GetterUtil.getBoolean(
			getParameter(actionRequest, "saveToDatabase"));
		boolean saveToFile = GetterUtil.getBoolean(
			getParameter(actionRequest, "saveToFile"));
		boolean sendAsEmail = GetterUtil.getBoolean(
			getParameter(actionRequest, "sendAsEmail"));

		if (!saveToDatabase && !saveToFile && !sendAsEmail) {
			SessionErrors.add(actionRequest, "handlingRequired");
		}

		if (sendAsEmail) {
			_validateEmailFields(actionRequest);
		}

		String successURL = getParameter(actionRequest, "successURL");

		if (Validator.isNotNull(successURL) && !Validator.isUrl(successURL)) {
			SessionErrors.add(actionRequest, "successURLInvalid");
		}

		_validateFieldNameLength(actionRequest);
		_validateUniqueFieldNames(actionRequest);
	}

	private void _validateUniqueFieldNames(ActionRequest actionRequest) {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		Set<String> localizedUniqueFieldNames = new HashSet<>();

		int[] formFieldsIndexes = StringUtil.split(
			ParamUtil.getString(actionRequest, "formFieldsIndexes"), 0);

		for (int formFieldsIndex : formFieldsIndexes) {
			Map<Locale, String> fieldLabelMap =
				_localization.getLocalizationMap(
					actionRequest, "fieldLabel" + formFieldsIndex);

			if (Validator.isNull(fieldLabelMap.get(defaultLocale))) {
				continue;
			}

			for (Map.Entry<Locale, String> entry : fieldLabelMap.entrySet()) {
				String fieldLabelValue = entry.getValue();

				if (Validator.isNull(fieldLabelValue)) {
					continue;
				}

				String languageId = LocaleUtil.toLanguageId(entry.getKey());

				if (!localizedUniqueFieldNames.add(
						languageId + "_" + fieldLabelValue)) {

					SessionErrors.add(
						actionRequest,
						DuplicateColumnNameException.class.getName());

					return;
				}
			}
		}
	}

	@Reference
	private Language _language;

	@Reference
	private Localization _localization;

}