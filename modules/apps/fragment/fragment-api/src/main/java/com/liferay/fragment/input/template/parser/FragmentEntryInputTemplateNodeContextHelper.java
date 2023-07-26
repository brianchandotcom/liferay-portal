/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.input.template.parser;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.fragment.constants.FragmentConfigurationFieldDataType;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.exception.InfoFormValidationException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.FileInfoFieldType;
import com.liferay.info.field.type.InfoFieldType;
import com.liferay.info.field.type.LongTextInfoFieldType;
import com.liferay.info.field.type.MultiselectInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.OptionInfoFieldType;
import com.liferay.info.field.type.RelationshipInfoFieldType;
import com.liferay.info.field.type.SelectInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryInputTemplateNodeContextHelper {

	public FragmentEntryInputTemplateNodeContextHelper(
		String defaultInputLabel, DLAppLocalService dlAppLocalService,
		FragmentEntryConfigurationParser fragmentEntryConfigurationParser,
		InfoItemServiceRegistry infoItemServiceRegistry,
		InfoSearchClassMapperRegistry infoSearchClassMapperRegistry,
		ItemSelector itemSelector) {

		_defaultInputLabel = defaultInputLabel;
		_dlAppLocalService = dlAppLocalService;
		_fragmentEntryConfigurationParser = fragmentEntryConfigurationParser;
		_infoItemServiceRegistry = infoItemServiceRegistry;
		_infoSearchClassMapperRegistry = infoSearchClassMapperRegistry;
		_itemSelector = itemSelector;
	}

	public InputTemplateNode toInputTemplateNode(
		FragmentEntryLink fragmentEntryLink,
		HttpServletRequest httpServletRequest, InfoForm infoForm,
		Locale locale) {

		String errorMessage = StringPool.BLANK;

		InfoField infoField = null;

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

		String inputLabel = _getInputLabel(
			defaultInputLabel, fragmentEntryLink.getEditableValues(), infoField,
			locale);

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

		String label = StringPool.BLANK;
		String value = StringPool.BLANK;

		if (infoFieldType instanceof MultiselectInfoFieldType) {
			List<OptionInfoFieldType> optionInfoFieldTypes =
				(List<OptionInfoFieldType>)infoField.getAttribute(
					MultiselectInfoFieldType.OPTIONS);

			if (optionInfoFieldTypes == null) {
				optionInfoFieldTypes = Collections.emptyList();
			}

			for (OptionInfoFieldType optionInfoFieldType :
					optionInfoFieldTypes) {

				if (optionInfoFieldType.isActive()) {
					label = optionInfoFieldType.getLabel(locale);
					value = optionInfoFieldType.getValue();

					break;
				}
			}
		}
		else if (infoFieldType instanceof SelectInfoFieldType) {
			List<OptionInfoFieldType> optionInfoFieldTypes =
				(List<OptionInfoFieldType>)infoField.getAttribute(
					SelectInfoFieldType.OPTIONS);

			if (optionInfoFieldTypes == null) {
				optionInfoFieldTypes = Collections.emptyList();
			}

			for (OptionInfoFieldType optionInfoFieldType :
					optionInfoFieldTypes) {

				if (optionInfoFieldType.isActive()) {
					label = optionInfoFieldType.getLabel(locale);
					value = optionInfoFieldType.getValue();

					break;
				}
			}
		}

		Map<String, String> infoFormParameterMap =
			(Map<String, String>)SessionMessages.get(
				httpServletRequest, "infoFormParameterMap");

		if (infoFormParameterMap != null) {
			label = infoFormParameterMap.get(infoField.getName() + "-label");
			value = infoFormParameterMap.get(infoField.getName());
		}
		else {
			value = GetterUtil.getString(
				_getValue(httpServletRequest, infoField.getName()), value);
		}

		InputTemplateNode inputTemplateNode = new InputTemplateNode(
			errorMessage, inputHelpText, inputLabel, name, required,
			inputShowHelpText, inputShowLabel, infoFieldType.getName(), value);

		if (infoFieldType instanceof FileInfoFieldType) {
			String allowedFileExtensions = (String)infoField.getAttribute(
				FileInfoFieldType.ALLOWED_FILE_EXTENSIONS);

			if (allowedFileExtensions == null) {
				allowedFileExtensions = StringPool.BLANK;
			}

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

			Long maximumFileSize = (Long)infoField.getAttribute(
				FileInfoFieldType.MAX_FILE_SIZE);

			if (maximumFileSize == null) {
				maximumFileSize = 0L;
			}

			inputTemplateNode.addAttribute("maxFileSize", maximumFileSize);

			FileInfoFieldType.FileSourceType fileSourceType =
				(FileInfoFieldType.FileSourceType)infoField.getAttribute(
					FileInfoFieldType.FILE_SOURCE);

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
		else if (infoField.getInfoFieldType() instanceof
					LongTextInfoFieldType) {

			inputTemplateNode.addAttribute(
				"maxLength",
				infoField.getAttribute(LongTextInfoFieldType.MAX_LENGTH));
		}
		else if (infoField.getInfoFieldType() instanceof
					MultiselectInfoFieldType) {

			inputTemplateNode.addAttribute(
				"options",
				TransformUtil.transform(
					(List<OptionInfoFieldType>)infoField.getAttribute(
						MultiselectInfoFieldType.OPTIONS),
					optionInfoFieldType -> new InputTemplateNode.Option(
						optionInfoFieldType.getLabel(locale),
						optionInfoFieldType.getValue())));
		}
		else if (infoField.getInfoFieldType() instanceof NumberInfoFieldType) {
			String dataType = "integer";

			if (GetterUtil.getBoolean(
					infoField.getAttribute(NumberInfoFieldType.DECIMAL))) {

				dataType = "decimal";

				Integer decimalPartMaxLength = (Integer)infoField.getAttribute(
					NumberInfoFieldType.DECIMAL_PART_MAX_LENGTH);

				if (decimalPartMaxLength != null) {
					inputTemplateNode.addAttribute(
						"step", _getStep(decimalPartMaxLength));
				}
			}

			inputTemplateNode.addAttribute("dataType", dataType);

			BigDecimal maxValue = (BigDecimal)infoField.getAttribute(
				NumberInfoFieldType.MAX_VALUE);

			if (maxValue != null) {
				inputTemplateNode.addAttribute("max", maxValue);
			}

			BigDecimal minValue = (BigDecimal)infoField.getAttribute(
				NumberInfoFieldType.MIN_VALUE);

			if (minValue != null) {
				inputTemplateNode.addAttribute("min", minValue);
			}
		}
		else if (infoField.getInfoFieldType() instanceof
					RelationshipInfoFieldType) {

			inputTemplateNode.addAttribute(
				"relationshipLabelFieldName",
				infoField.getAttribute(
					RelationshipInfoFieldType.LABEL_FIELD_NAME));

			inputTemplateNode.addAttribute(
				"relationshipURL",
				infoField.getAttribute(RelationshipInfoFieldType.URL));

			inputTemplateNode.addAttribute(
				"relationshipValueFieldName",
				infoField.getAttribute(
					RelationshipInfoFieldType.VALUE_FIELD_NAME));

			if (Validator.isNotNull(label)) {
				inputTemplateNode.addAttribute("selectedOptionLabel", label);
			}
		}
		else if (infoField.getInfoFieldType() instanceof SelectInfoFieldType) {
			List<InputTemplateNode.Option> options = new ArrayList<>();

			List<OptionInfoFieldType> optionInfoFieldTypes =
				(List<OptionInfoFieldType>)infoField.getAttribute(
					SelectInfoFieldType.OPTIONS);

			if (optionInfoFieldTypes == null) {
				optionInfoFieldTypes = Collections.emptyList();
			}

			for (OptionInfoFieldType optionInfoFieldType :
					optionInfoFieldTypes) {

				options.add(
					new InputTemplateNode.Option(
						optionInfoFieldType.getLabel(locale),
						optionInfoFieldType.getValue()));

				if ((value != null) &&
					value.equals(optionInfoFieldType.getValue())) {

					inputTemplateNode.addAttribute(
						"selectedOptionLabel",
						optionInfoFieldType.getLabel(locale));
				}
			}

			inputTemplateNode.addAttribute("options", options);
		}
		else if (infoField.getInfoFieldType() instanceof TextInfoFieldType) {
			inputTemplateNode.addAttribute(
				"maxLength",
				infoField.getAttribute(TextInfoFieldType.MAX_LENGTH));
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

	private String _getInputLabel(
		String defaultInputLabel, String editableValues, InfoField<?> infoField,
		Locale locale) {

		String inputLabel = null;

		JSONObject inputLabelJSONObject =
			(JSONObject)
				_fragmentEntryConfigurationParser.getConfigurationFieldValue(
					editableValues, "inputLabel",
					FragmentConfigurationFieldDataType.OBJECT);

		if (inputLabelJSONObject != null) {
			inputLabel = inputLabelJSONObject.getString(
				LocaleUtil.toLanguageId(locale));
		}

		if (Validator.isNull(inputLabel) && (infoField != null)) {
			InfoLocalizedValue<String> labelInfoLocalizedValue =
				infoField.getLabelInfoLocalizedValue();

			Set<Locale> availableLocales =
				labelInfoLocalizedValue.getAvailableLocales();

			if (availableLocales.contains(locale)) {
				inputLabel = labelInfoLocalizedValue.getValue(locale);
			}
			else {
				inputLabel = inputLabelJSONObject.getString(
					LanguageUtil.getLanguageId(LocaleUtil.getSiteDefault()));
			}
		}

		if (Validator.isNull(inputLabel) && (infoField != null)) {
			inputLabel = infoField.getLabel(locale);
		}

		if (Validator.isNotNull(inputLabel)) {
			return inputLabel;
		}

		return defaultInputLabel;
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

	private String _getValue(
		HttpServletRequest httpServletRequest, String infoFieldName) {

		if (httpServletRequest == null) {
			return StringPool.BLANK;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			(LayoutDisplayPageObjectProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

		if (layoutDisplayPageObjectProvider == null) {
			return StringPool.BLANK;
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				_infoSearchClassMapperRegistry.getClassName(
					layoutDisplayPageObjectProvider.getClassName()));

		if (infoItemFieldValuesProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						layoutDisplayPageObjectProvider.getClassName());
			}

			return StringPool.BLANK;
		}

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(
				layoutDisplayPageObjectProvider.getDisplayObject());

		InfoFieldValue<?> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(infoFieldName);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		if (infoField.getInfoFieldType() == DateInfoFieldType.INSTANCE) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");

			return simpleDateFormat.format(infoFieldValue.getValue());
		}

		Object value = infoFieldValue.getValue();

		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		return String.valueOf(value);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryInputTemplateNodeContextHelper.class);

	private final String _defaultInputLabel;
	private final DLAppLocalService _dlAppLocalService;
	private final FragmentEntryConfigurationParser
		_fragmentEntryConfigurationParser;
	private final InfoItemServiceRegistry _infoItemServiceRegistry;
	private final InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;
	private final ItemSelector _itemSelector;

}