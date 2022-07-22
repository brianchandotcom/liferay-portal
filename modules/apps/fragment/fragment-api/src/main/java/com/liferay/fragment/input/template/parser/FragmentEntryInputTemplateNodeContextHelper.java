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

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
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
		String defaultInputLabel, DLAppLocalService dlAppLocalService,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		ItemSelector itemSelector) {

		_defaultInputLabel = defaultInputLabel;
		_dlAppLocalService = dlAppLocalService;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_itemSelector = itemSelector;
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

			SessionErrors.remove(httpServletRequest, infoField.getUniqueId());
		}

		String inputHelpText = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getEditableValues(),
				new FragmentConfigurationField(
					"inputHelpText", "string",
					LanguageUtil.get(locale, "add-your-help-text-here"), true,
					"text"),
				locale));

		String defaultInputLabel = _defaultInputLabel;

		if (infoField != null) {
			defaultInputLabel = infoField.getLabel(locale);
		}

		String inputLabel = GetterUtil.getString(
			_fragmentEntryConfigurationParser.getFieldValue(
				fragmentEntryLink.getEditableValues(),
				new FragmentConfigurationField(
					"inputLabel", "string", defaultInputLabel, true, "text"),
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

		if (infoField == null) {
			return new InputTemplateNode(
				errorMessage, inputHelpText, inputLabel, name, required,
				inputShowHelpText, inputShowLabel, "type", StringPool.BLANK);
		}

		InfoFieldType infoFieldType = infoField.getInfoFieldType();

		String value = StringPool.BLANK;

		Map<String, String> formParameterMap =
			(Map<String, String>)SessionMessages.get(
				httpServletRequest, "infoFormParameterMap");

		if (formParameterMap != null) {
			value = formParameterMap.get(infoField.getName());
		}

		InputTemplateNode inputTemplateNode = new InputTemplateNode(
			errorMessage, inputHelpText, inputLabel, name, required,
			inputShowHelpText, inputShowLabel, infoFieldType.getName(), value);

		if (infoFieldType instanceof FileInfoFieldType) {
			Optional<String> acceptedFileExtensionsOptional =
				infoField.getAttributeOptional(
					FileInfoFieldType.ALLOWED_FILE_EXTENSIONS);

			String allowedFileExtensions =
				acceptedFileExtensionsOptional.orElse(StringPool.BLANK);

			if (Validator.isNotNull(allowedFileExtensions)) {
				StringBundler sb = new StringBundler();

				for (String allowedFileExtension :
						StringUtil.split(allowedFileExtensions)) {

					sb.append(StringPool.PERIOD);
					sb.append(allowedFileExtension.trim());
					sb.append(StringPool.COMMA);
				}

				sb.setIndex(sb.index() - 1);

				allowedFileExtensions = sb.toString();
			}

			inputTemplateNode.addAttribute(
				"allowedFileExtensions", allowedFileExtensions);

			Optional<Long> maximumFileSizeOptional =
				infoField.getAttributeOptional(FileInfoFieldType.MAX_FILE_SIZE);

			inputTemplateNode.addAttribute(
				"maxFileSize", maximumFileSizeOptional.orElse(0L));

			Optional<FileInfoFieldType.FileSourceType> fileSourceTypeOptional =
				infoField.getAttributeOptional(FileInfoFieldType.FILE_SOURCE);

			FileInfoFieldType.FileSourceType fileSourceType =
				fileSourceTypeOptional.orElse(null);

			if (fileSourceType != null) {
				String fileName = null;
				FileEntry fileEntry = null;
				boolean selectFromDocumentLibrary = false;

				if (Validator.isNotNull(value)) {
					fileEntry = _fetchFileEntry(GetterUtil.getLong(value));
				}

				if (fileSourceType ==
						FileInfoFieldType.FileSourceType.DOCUMENTS_AND_MEDIA) {

					selectFromDocumentLibrary = true;

					if (fileEntry != null) {
						fileName = fileEntry.getFileName();
					}
				}
				else if (fileSourceType ==
							FileInfoFieldType.FileSourceType.USER_COMPUTER) {

					if (fileEntry != null) {
						fileName = TempFileEntryUtil.getOriginalTempFileName(
							fileEntry.getFileName());
					}
				}

				if (fileName != null) {
					inputTemplateNode.addAttribute("fileName", fileName);
				}

				inputTemplateNode.addAttribute(
					"selectFromDocumentLibrary", selectFromDocumentLibrary);

				if (selectFromDocumentLibrary) {
					FileItemSelectorCriterion fileItemSelectorCriterion =
						new FileItemSelectorCriterion();

					fileItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
						new FileEntryItemSelectorReturnType());

					RequestBackedPortletURLFactory
						requestBackedPortletURLFactory =
							RequestBackedPortletURLFactoryUtil.create(
								httpServletRequest);

					inputTemplateNode.addAttribute(
						"selectFromDocumentLibraryURL",
						String.valueOf(
							_itemSelector.getItemSelectorURL(
								requestBackedPortletURLFactory,
								fragmentEntryLink.getNamespace() +
									"selectFileEntry",
								fileItemSelectorCriterion)));
				}
			}
		}
		else if (infoField.getInfoFieldType() instanceof NumberInfoFieldType) {
			String dataType = "integer";

			Optional<Boolean> decimalOptional = infoField.getAttributeOptional(
				NumberInfoFieldType.DECIMAL);

			if (decimalOptional.orElse(false)) {
				dataType = "decimal";

				Optional<Integer> decimalPartMaxLengthOptional =
					infoField.getAttributeOptional(
						NumberInfoFieldType.DECIMAL_PART_MAX_LENGTH);

				decimalPartMaxLengthOptional.ifPresent(
					decimalPartMaxLength -> inputTemplateNode.addAttribute(
						"step", _getStep(decimalPartMaxLength)));
			}

			inputTemplateNode.addAttribute("dataType", dataType);

			Optional<BigDecimal> maxValueOptional =
				infoField.getAttributeOptional(NumberInfoFieldType.MAX_VALUE);

			maxValueOptional.ifPresent(
				maxValue -> inputTemplateNode.addAttribute("max", maxValue));

			Optional<BigDecimal> minValueOptional =
				infoField.getAttributeOptional(NumberInfoFieldType.MIN_VALUE);

			minValueOptional.ifPresent(
				minValue -> inputTemplateNode.addAttribute("min", minValue));
		}
		else if (infoField.getInfoFieldType() instanceof
					RelationshipInfoFieldType) {

			Optional<String> optionsLabelFieldNameOptional =
				infoField.getAttributeOptional(
					RelationshipInfoFieldType.LABEL_FIELD_NAME);

			inputTemplateNode.addAttribute(
				"relationshipLabelFieldName",
				optionsLabelFieldNameOptional.orElse(null));

			Optional<String> optionsURLOptional =
				infoField.getAttributeOptional(RelationshipInfoFieldType.URL);

			inputTemplateNode.addAttribute(
				"relationshipURL", optionsURLOptional.orElse(null));

			Optional<String> optionsValueFieldNameOptional =
				infoField.getAttributeOptional(
					RelationshipInfoFieldType.VALUE_FIELD_NAME);

			inputTemplateNode.addAttribute(
				"relationshipValueFieldName",
				optionsValueFieldNameOptional.orElse(null));
		}
		else if (infoField.getInfoFieldType() instanceof SelectInfoFieldType) {
			List<InputTemplateNode.Option> options = new ArrayList<>();

			Optional<List<SelectInfoFieldType.Option>> optionsOptional =
				infoField.getAttributeOptional(SelectInfoFieldType.OPTIONS);

			for (SelectInfoFieldType.Option option :
					optionsOptional.orElse(Collections.emptyList())) {

				options.add(
					new InputTemplateNode.Option(
						option.getLabel(locale), option.getValue()));

				if ((value != null) && value.equals(option.getValue())) {
					inputTemplateNode.addAttribute(
						"selectedOptionLabel", option.getLabel(locale));
				}
			}

			inputTemplateNode.addAttribute("options", options);
		}

		return inputTemplateNode;
	}

	private FileEntry _fetchFileEntry(long fileEntryId) {
		try {
			return _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get file entry " + fileEntryId, portalException);
			}

			return null;
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryInputTemplateNodeContextHelper.class);

	private final String _defaultInputLabel;
	private final DLAppLocalService _dlAppLocalService;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final ItemSelector _itemSelector;

}