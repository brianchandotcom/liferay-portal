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

package com.liferay.fragment.entry.processor.internal.util;

import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.formatter.InfoCollectionTextFormatter;
import com.liferay.info.formatter.InfoTextFormatter;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.ERCInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.info.type.Labeled;
import com.liferay.info.type.WebImage;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
import java.text.ParseException;

import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = FragmentEntryProcessorHelper.class)
public class FragmentEntryProcessorHelperImpl
	implements FragmentEntryProcessorHelper {

	@Override
	public String getEditableValue(JSONObject jsonObject, Locale locale) {
		String value = jsonObject.getString(
			_language.getLanguageId(locale), null);

		if (value != null) {
			return value;
		}

		return jsonObject.getString(
			_language.getLanguageId(LocaleUtil.getSiteDefault()),
			jsonObject.getString("defaultValue"));
	}

	@Override
	public Object getFieldValue(
			JSONObject editableValueJSONObject,
			Map<Long, InfoItemFieldValues> infoDisplaysFieldValues,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (!isMapped(editableValueJSONObject) &&
			!isMappedCollection(editableValueJSONObject) &&
			!isMappedDisplayPage(editableValueJSONObject)) {

			return null;
		}

		long classPK = 0;
		String className = StringPool.BLANK;
		String fieldName = StringPool.BLANK;
		Object object = null;

		if (isMapped(editableValueJSONObject)) {
			className = _portal.getClassName(
				editableValueJSONObject.getLong("classNameId"));
			classPK = editableValueJSONObject.getLong("classPK");
			String externalReferenceCode = editableValueJSONObject.getString(
				"externalReferenceCode");

			fieldName = editableValueJSONObject.getString("fieldId");

			InfoItemIdentifier infoItemIdentifier = null;

			if (Validator.isNotNull(externalReferenceCode)) {
				infoItemIdentifier = new ERCInfoItemIdentifier(
					externalReferenceCode);
			}
			else {
				infoItemIdentifier = new ClassPKInfoItemIdentifier(classPK);
			}

			if (fragmentEntryProcessorContext.getPreviewClassPK() > 0) {
				infoItemIdentifier = new ClassPKInfoItemIdentifier(
					fragmentEntryProcessorContext.getPreviewClassPK());

				if (Validator.isNotNull(
						fragmentEntryProcessorContext.getPreviewVersion())) {

					infoItemIdentifier.setVersion(
						fragmentEntryProcessorContext.getPreviewVersion());
				}
			}

			object = _getInfoItem(className, infoItemIdentifier);
		}
		else if (isMappedCollection(editableValueJSONObject)) {
			InfoItemReference infoItemReference =
				fragmentEntryProcessorContext.getContextInfoItemReference();

			if (infoItemReference == null) {
				return null;
			}

			className = infoItemReference.getClassName();
			classPK = infoItemReference.getClassPK();

			fieldName = editableValueJSONObject.getString("collectionFieldId");

			object = _getInfoItem(infoItemReference);
		}
		else if (isMappedDisplayPage(editableValueJSONObject)) {
			HttpServletRequest httpServletRequest =
				fragmentEntryProcessorContext.getHttpServletRequest();

			if (httpServletRequest == null) {
				return null;
			}

			LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
				(LayoutDisplayPageObjectProvider<?>)
					httpServletRequest.getAttribute(
						LayoutDisplayPageWebKeys.
							LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER);

			if (layoutDisplayPageObjectProvider == null) {
				return null;
			}

			className = layoutDisplayPageObjectProvider.getClassName();
			classPK = layoutDisplayPageObjectProvider.getClassPK();

			fieldName = editableValueJSONObject.getString("mappedField");

			object = layoutDisplayPageObjectProvider.getDisplayObject();
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if ((trashHandler != null) && trashHandler.isInTrash(classPK)) {
			return null;
		}

		InfoItemFieldValuesProvider infoItemFieldValuesProvider =
			_getInfoItemFieldValuesProvider(className);

		if (infoItemFieldValuesProvider == null) {
			return null;
		}

		InfoItemFieldValues infoItemFieldValues = infoDisplaysFieldValues.get(
			classPK);

		if (infoItemFieldValues == null) {
			infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(object);

			infoDisplaysFieldValues.put(classPK, infoItemFieldValues);
		}

		return _getMappedInfoItemFieldValue(
			editableValueJSONObject, fieldName, infoItemFieldValuesProvider,
			fragmentEntryProcessorContext.getLocale(), object);
	}

	@Override
	public long getFileEntryId(
		InfoItemReference infoItemReference, String fieldName, Locale locale) {

		return _getFileEntryId(
			infoItemReference.getClassName(), _getInfoItem(infoItemReference),
			fieldName, locale);
	}

	@Override
	public long getFileEntryId(
			long classNameId, long classPK, String fieldName, Locale locale)
		throws PortalException {

		if (classNameId == 0) {
			return 0;
		}

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, _portal.getClassName(classNameId),
				infoItemIdentifier.getInfoItemServiceFilter());

		if (infoItemObjectProvider == null) {
			return 0;
		}

		Object object = infoItemObjectProvider.getInfoItem(infoItemIdentifier);

		if (object == null) {
			return 0;
		}

		return _getFileEntryId(
			_portal.getClassName(classNameId), object, fieldName, locale);
	}

	@Override
	public long getFileEntryId(String className, long classPK) {
		if (!Objects.equals(className, FileEntry.class.getName())) {
			return 0;
		}

		return classPK;
	}

	@Override
	public long getFileEntryId(WebImage webImage) {
		InfoItemReference infoItemReference = webImage.getInfoItemReference();

		if ((infoItemReference == null) ||
			!Objects.equals(
				infoItemReference.getClassName(), FileEntry.class.getName())) {

			return 0;
		}

		InfoItemIdentifier fileEntryInfoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		if (!(fileEntryInfoItemIdentifier instanceof
				ClassPKInfoItemIdentifier)) {

			return 0;
		}

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)fileEntryInfoItemIdentifier;

		return classPKInfoItemIdentifier.getClassPK();
	}

	@Override
	public boolean isMapped(JSONObject jsonObject) {
		long classNameId = jsonObject.getLong("classNameId");
		long classPK = jsonObject.getLong("classPK");
		String externalReferenceCode = jsonObject.getString(
			"externalReferenceCode");
		String fieldId = jsonObject.getString("fieldId");

		if ((classNameId > 0) &&
			((classPK > 0) || Validator.isNotNull(externalReferenceCode)) &&
			Validator.isNotNull(fieldId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMappedCollection(JSONObject jsonObject) {
		if (jsonObject.has("collectionFieldId")) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isMappedDisplayPage(JSONObject jsonObject) {
		if (jsonObject.has("mappedField")) {
			return true;
		}

		return false;
	}

	private String _getDateValue(
		JSONObject editableValueJSONObject, Date date, String defaultPattern,
		Locale locale) {

		if (editableValueJSONObject == null) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				defaultPattern, locale);

			return dateFormat.format(date);
		}

		JSONObject configJSONObject = editableValueJSONObject.getJSONObject(
			"config");

		if (configJSONObject == null) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				defaultPattern, locale);

			return dateFormat.format(date);
		}

		JSONObject dateFormatJSONObject = configJSONObject.getJSONObject(
			"dateFormat");

		if (dateFormatJSONObject == null) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				defaultPattern, locale);

			return dateFormat.format(date);
		}

		String pattern = dateFormatJSONObject.getString(
			_language.getLanguageId(locale), null);

		if (Validator.isNull(pattern)) {
			pattern = dateFormatJSONObject.getString(
				_language.getLanguageId(LocaleUtil.getSiteDefault()), null);
		}

		if (Validator.isNull(pattern)) {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				defaultPattern, locale);

			return dateFormat.format(date);
		}

		try {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				pattern, locale);

			return dateFormat.format(date);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				defaultPattern, locale);

			return dateFormat.format(date);
		}
	}

	private String _getDefaultPattern(Locale locale) {
		if (_defaultPatterns.containsKey(locale)) {
			return _defaultPatterns.get(locale);
		}

		String defaultPattern =
			DateTimeFormatterBuilder.getLocalizedDateTimePattern(
				FormatStyle.SHORT, null, IsoChronology.INSTANCE, locale);

		_defaultPatterns.put(locale, defaultPattern);

		return defaultPattern;
	}

	private long _getFileEntryId(
		String className, Object displayObject, String fieldName,
		Locale locale) {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			return 0;
		}

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(
				displayObject, fieldName);

		Object value = StringPool.BLANK;

		if (infoFieldValue != null) {
			value = infoFieldValue.getValue(locale);
		}

		if (!(value instanceof WebImage)) {
			return 0;
		}

		WebImage webImage = (WebImage)value;

		return getFileEntryId(webImage);
	}

	private InfoCollectionTextFormatter<Object> _getInfoCollectionTextFormatter(
		String itemClassName) {

		if (itemClassName.equals(String.class.getName())) {
			return _INFO_COLLECTION_TEXT_FORMATTER;
		}

		InfoCollectionTextFormatter<Object> infoCollectionTextFormatter =
			(InfoCollectionTextFormatter<Object>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoCollectionTextFormatter.class, itemClassName);

		if (infoCollectionTextFormatter == null) {
			infoCollectionTextFormatter = _INFO_COLLECTION_TEXT_FORMATTER;
		}

		return infoCollectionTextFormatter;
	}

	private Object _getInfoItem(InfoItemReference infoItemReference) {
		if (infoItemReference == null) {
			return null;
		}

		return _getInfoItem(
			infoItemReference.getClassName(),
			infoItemReference.getInfoItemIdentifier());
	}

	private Object _getInfoItem(
		String className, InfoItemIdentifier infoItemIdentifier) {

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				infoItemIdentifier.getInfoItemServiceFilter());

		try {
			return infoItemObjectProvider.getInfoItem(infoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}
		}

		return null;
	}

	private InfoItemFieldValuesProvider<Object> _getInfoItemFieldValuesProvider(
		String className) {

		className = _infoSearchClassMapperRegistry.getClassName(className);

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						className);
			}

			return null;
		}

		return infoItemFieldValuesProvider;
	}

	private Object _getMappedInfoItemFieldValue(
		JSONObject editableValueJSONObject, String fieldName,
		InfoItemFieldValuesProvider infoItemFieldValuesProvider, Locale locale,
		Object object) {

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(object, fieldName);

		if (infoFieldValue == null) {
			return null;
		}

		Object value = infoFieldValue.getValue(locale);

		if (value == null) {
			return null;
		}

		if (value instanceof WebImage) {
			WebImage webImage = (WebImage)value;

			JSONObject valueJSONObject = webImage.toJSONObject();

			long fileEntryId = getFileEntryId(webImage);

			if (fileEntryId != 0) {
				valueJSONObject.put("fileEntryId", String.valueOf(fileEntryId));
			}

			return valueJSONObject;
		}

		if (value instanceof Collection) {
			Collection<Object> collection = (Collection<Object>)value;

			if (collection.isEmpty()) {
				return StringPool.BLANK;
			}

			Iterator<Object> iterator = collection.iterator();

			Object firstItem = iterator.next();

			Class<?> firstItemClass = firstItem.getClass();

			InfoCollectionTextFormatter<Object> infoCollectionTextFormatter =
				_getInfoCollectionTextFormatter(firstItemClass.getName());

			return infoCollectionTextFormatter.format(collection, locale);
		}

		if (value instanceof String) {
			InfoField infoField = infoFieldValue.getInfoField();

			if (infoField.getInfoFieldType() instanceof DateInfoFieldType) {
				Locale dateLocale = LocaleUtil.getSiteDefault();

				if (infoField.isLocalizable()) {
					InfoLocalizedValue<String> infoLocalizedValue =
						(InfoLocalizedValue<String>)infoFieldValue.getValue();

					dateLocale = infoLocalizedValue.getDefaultLocale();

					Set<Locale> availableLocales =
						infoLocalizedValue.getAvailableLocales();

					if (availableLocales.contains(locale)) {
						dateLocale = locale;
					}
				}

				try {
					DateFormat dateFormat =
						DateFormatFactoryUtil.getSimpleDateFormat(
							_getShortTimeStylePattern(dateLocale), dateLocale);

					Date date = dateFormat.parse(value.toString());

					return _getDateValue(
						editableValueJSONObject, date,
						_getShortTimeStylePattern(locale), locale);
				}
				catch (ParseException parseException1) {
					if (_log.isDebugEnabled()) {
						_log.debug(parseException1);
					}

					try {
						DateFormat dateFormat =
							DateFormatFactoryUtil.getSimpleDateFormat(
								_getDefaultPattern(dateLocale), dateLocale);

						return _getDateValue(
							editableValueJSONObject,
							dateFormat.parse(value.toString()),
							_getDefaultPattern(locale), locale);
					}
					catch (ParseException parseException2) {
						if (_log.isDebugEnabled()) {
							_log.debug(parseException2);
						}

						return value;
					}
				}
			}
			else if (infoField.getInfoFieldType() instanceof
						TextInfoFieldType) {

				return _html.escape((String)value);
			}

			return (String)value;
		}

		if (value instanceof Labeled) {
			Labeled labeledFieldValue = (Labeled)value;

			return labeledFieldValue.getLabel(locale);
		}

		if (value instanceof Date) {
			Date date = (Date)value;

			return _getDateValue(
				editableValueJSONObject, date,
				_getShortTimeStylePattern(locale), locale);
		}

		Class<?> fieldValueClass = value.getClass();

		InfoTextFormatter<Object> infoTextFormatter =
			(InfoTextFormatter<Object>)
				_infoItemServiceRegistry.getFirstInfoItemService(
					InfoTextFormatter.class, fieldValueClass.getName());

		if (infoTextFormatter != null) {
			return infoTextFormatter.format(value, locale);
		}

		return value.toString();
	}

	private String _getShortTimeStylePattern(Locale locale) {
		if (_shortTimeStylePatterns.containsKey(locale)) {
			return _shortTimeStylePatterns.get(locale);
		}

		String sortTimeStylePattern =
			DateTimeFormatterBuilder.getLocalizedDateTimePattern(
				FormatStyle.SHORT, FormatStyle.SHORT, IsoChronology.INSTANCE,
				locale);

		_shortTimeStylePatterns.put(locale, sortTimeStylePattern);

		return sortTimeStylePattern;
	}

	private static final InfoCollectionTextFormatter<Object>
		_INFO_COLLECTION_TEXT_FORMATTER =
			new CommaSeparatedInfoCollectionTextFormatter();

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryProcessorHelperImpl.class);

	private static final Map<Locale, String> _defaultPatterns = new HashMap<>();
	private static final Map<Locale, String> _shortTimeStylePatterns =
		new HashMap<>();

	@Reference
	private Html _html;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}