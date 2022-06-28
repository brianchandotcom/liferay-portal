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

package com.liferay.fragment.input.template.parser;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryInputTemplateNodeContextHelper {

	public FragmentEntryInputTemplateNodeContextHelper(
		FragmentCollectionContributorTracker
			fragmentCollectionContributorTracker,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		FragmentRendererTracker fragmentRendererTracker) {

		_fragmentCollectionContributorTracker =
			fragmentCollectionContributorTracker;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_fragmentRendererTracker = fragmentRendererTracker;
	}

	public InputTemplateNode toInputTemplateNode(
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest,
		Optional<InfoForm> infoFormOptional, Locale locale) {

		String errorMessage = StringPool.BLANK;

		InfoField infoField = null;

		InfoForm infoForm = infoFormOptional.orElse(null);

		if (infoForm != null) {
			String fieldName = GetterUtil.getString(
				_fragmentEntryConfigurationParser.getFieldValue(
					fragmentEntryLink.getEditableValues(),
					new FragmentConfigurationField(
						"inputFieldId", "string", "", false, "text"),
					locale));

			infoField = infoForm.getInfoField(fieldName);
		}

		if ((infoField != null) &&
			SessionErrors.contains(
				httpServletRequest, infoField.getUniqueId())) {

			InfoFormValidationException infoFormValidationException =
				(InfoFormValidationException)SessionErrors.get(
					httpServletRequest, infoField.getUniqueId());

			errorMessage = infoFormValidationException.getLocalizedMessage(
				locale);
		}

		String inputHelpText = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getEditableValues(),
				new FragmentConfigurationField(
					"inputHelpText", "string",
					LanguageUtil.get(locale, "add-your-help-text-here"), true,
					"text"),
				locale));
		String inputLabel = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getEditableValues(),
				new FragmentConfigurationField(
					"inputLabel", "string",
					_getFragmentEntryName(fragmentEntryLink, locale), true,
					"text"),
				locale));

		String name = "name";

		if (infoField != null) {
			name = infoField.getName();
		}

		boolean required = false;

		if (((infoField != null) && infoField.isRequired()) ||
			GetterUtil.getBoolean(
				_fragmentEntryConfigurationParser.getFieldValue(
					fragmentEntryLink.getEditableValues(),
					new FragmentConfigurationField(
						"inputRequired", "boolean", "false", false, "checkbox"),
					locale))) {

			required = true;
		}

		boolean inputShowHelpText = GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getEditableValues(),
				new FragmentConfigurationField(
					"inputShowHelpText", "boolean", "false", false, "checkbox"),
				locale));

		boolean inputShowLabel = GetterUtil.getBoolean(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getEditableValues(),
				new FragmentConfigurationField(
					"inputShowLabel", "boolean", "true", false, "checkbox"),
				locale));

		String type = "type";

		if (infoField != null) {
			InfoFieldType infoFieldType = infoField.getInfoFieldType();

			type = infoFieldType.getName();
		}

		InputTemplateNode inputTemplateNode = new InputTemplateNode(
			errorMessage, inputHelpText, inputLabel, name, required,
			inputShowHelpText, inputShowLabel, type, "value");

		if (infoField != null) {
			if (infoField.getInfoFieldType() instanceof NumberInfoFieldType) {
				String dataType = "integer";

				Optional<Boolean> decimalOptional =
					infoField.getAttributeOptional(NumberInfoFieldType.DECIMAL);

				if (decimalOptional.orElse(false)) {
					dataType = "decimal";
				}

				inputTemplateNode.addAttribute("dataType", dataType);

				Optional<BigDecimal> maxValueOptional =
					infoField.getAttributeOptional(
						NumberInfoFieldType.MAX_VALUE);

				maxValueOptional.ifPresent(
					maxValue -> inputTemplateNode.addAttribute(
						"max", maxValue));

				Optional<BigDecimal> minValueOptional =
					infoField.getAttributeOptional(
						NumberInfoFieldType.MIN_VALUE);

				minValueOptional.ifPresent(
					minValue -> inputTemplateNode.addAttribute(
						"min", minValue));

				Optional<Integer> decimalPartMaxLengthOptional =
					infoField.getAttributeOptional(
						NumberInfoFieldType.DECIMAL_PART_MAX_LENGTH);

				decimalPartMaxLengthOptional.ifPresent(
					decimalPartMaxLength -> inputTemplateNode.addAttribute(
						"step", _getStep(decimalPartMaxLength)));
			}

			if (infoField.getInfoFieldType() instanceof SelectInfoFieldType) {
				Optional<List<SelectInfoFieldType.Option>> optionsOptional =
					infoField.getAttributeOptional(SelectInfoFieldType.OPTIONS);

				List<SelectInfoFieldType.Option> options =
					optionsOptional.orElse(new ArrayList<>());

				for (SelectInfoFieldType.Option option : options) {
					inputTemplateNode.addOption(
						option.getLabel(locale), option.getValue());
				}
			}
		}

		return inputTemplateNode;
	}

	private String _getFragmentEntryName(
		FragmentEntryLink fragmentEntryLink, Locale locale) {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				fragmentEntryLink.getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.getName();
		}

		String rendererKey = fragmentEntryLink.getRendererKey();

		if (Validator.isNull(rendererKey)) {
			return StringPool.BLANK;
		}

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries(locale);

		FragmentEntry contributedFragmentEntry = fragmentEntries.get(
			rendererKey);

		if (contributedFragmentEntry != null) {
			return contributedFragmentEntry.getName();
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(
				fragmentEntryLink.getRendererKey());

		if (fragmentRenderer != null) {
			return fragmentRenderer.getLabel(locale);
		}

		return StringPool.BLANK;
	}

	private String _getStep(Integer decimalPartMaxLength) {
		if (decimalPartMaxLength == null) {
			return StringPool.BLANK;
		}

		if (decimalPartMaxLength <= 0) {
			return "0";
		}

		return StringBundler.concat(
			"0.",
			StringUtil.merge(
				Collections.nCopies(decimalPartMaxLength - 1, "0"),
				StringPool.BLANK),
			"1");
	}

	private final FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final FragmentRendererTracker _fragmentRendererTracker;

}