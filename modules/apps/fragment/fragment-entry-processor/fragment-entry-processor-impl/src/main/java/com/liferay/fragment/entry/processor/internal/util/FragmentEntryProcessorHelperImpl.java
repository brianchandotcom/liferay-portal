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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.formatter.InfoCollectionTextFormatter;
import com.liferay.info.formatter.InfoTextFormatter;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.type.Labeled;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentEntryProcessorHelper.class)
public class FragmentEntryProcessorHelperImpl
	implements FragmentEntryProcessorHelper {

	@Override
	public String formatMappedValue(Object fieldValue, Locale locale) {
		if (fieldValue == null) {
			return null;
		}

		String formattedFieldValue;

		if (fieldValue instanceof Collection) {
			Collection<Object> collection = (Collection<Object>)fieldValue;

			if (collection.isEmpty()) {
				return StringPool.BLANK;
			}

			Iterator<Object> iterator = collection.iterator();

			Object firstItem = iterator.next();

			Class<?> firstItemClass = firstItem.getClass();

			String itemClassName = firstItemClass.getName();

			InfoCollectionTextFormatter<Object> infoCollectionTextFormatter =
				_getInfoCollectionTextFormatter(itemClassName);

			formattedFieldValue = infoCollectionTextFormatter.format(
				collection, locale);
		}
		else {
			if (fieldValue instanceof String) {
				formattedFieldValue = (String)fieldValue;
			}
			else if (fieldValue instanceof Labeled) {
				Labeled labeledFieldValue = (Labeled)fieldValue;

				formattedFieldValue = labeledFieldValue.getLabel(locale);
			}
			else {
				Class<?> fieldValueClass = fieldValue.getClass();

				String itemClassName = fieldValueClass.getName();

				InfoTextFormatter<Object> infoTextFormatter =
					(InfoTextFormatter<Object>)
						_infoItemServiceTracker.getFirstInfoItemService(
							InfoTextFormatter.class, itemClassName);

				if (infoTextFormatter == null) {
					formattedFieldValue = fieldValue.toString();
				}
				else {
					formattedFieldValue = infoTextFormatter.format(
						fieldValue, locale);
				}
			}
		}

		return formattedFieldValue;
	}

	@Override
	public String getEditableValue(JSONObject jsonObject, Locale locale) {
		return _getEditableValueByLocale(jsonObject, locale);
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
			_infoItemServiceTracker.getFirstInfoItemService(
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
	public Object getMappedCollectionValue(
		Optional<InfoItemReference> infoItemReferenceOptional,
		JSONObject jsonObject, Locale locale) {

		if (!isMappedCollection(jsonObject)) {
			return _jsonFactory.createJSONObject();
		}

		if (!infoItemReferenceOptional.isPresent()) {
			return null;
		}

		InfoItemReference infoItemReference = infoItemReferenceOptional.get();

		// LPS-111037

		String className = infoItemReference.getClassName();

		if (Objects.equals(className, DLFileEntry.class.getName())) {
			className = FileEntry.class.getName();
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						className);
			}

			return null;
		}

		return getMappedInfoItemFieldValue(
			jsonObject.getString("collectionFieldId"),
			infoItemFieldValuesProvider, locale,
			_getInfoItem(infoItemReference));
	}

	@Override
	public Object getMappedInfoItemFieldValue(
			JSONObject jsonObject,
			Map<Long, InfoItemFieldValues> infoItemFieldValuesMap,
			Locale locale, String mode, long previewClassPK,
			String previewVersion)
		throws PortalException {

		if (!isMapped(jsonObject) && !isAssetDisplayPage(mode)) {
			return _jsonFactory.createJSONObject();
		}

		long classNameId = jsonObject.getLong("classNameId");

		String className = _portal.getClassName(classNameId);

		long classPK = jsonObject.getLong("classPK");

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className,
				infoItemIdentifier.getInfoItemServiceFilter());

		if (infoItemObjectProvider == null) {
			return null;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		if ((trashHandler != null) && trashHandler.isInTrash(classPK)) {
			return null;
		}

		if (previewClassPK > 0) {
			infoItemIdentifier = new ClassPKInfoItemIdentifier(previewClassPK);

			if (Validator.isNotNull(previewVersion)) {
				infoItemIdentifier.setVersion(previewVersion);
			}
		}

		Object object = infoItemObjectProvider.getInfoItem(infoItemIdentifier);

		if (object == null) {
			return null;
		}

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			(InfoItemFieldValuesProvider<Object>)
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						className);
			}

			return null;
		}

		InfoItemFieldValues infoItemFieldValues = infoItemFieldValuesMap.get(
			classPK);

		if (infoItemFieldValues == null) {
			infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(object);

			infoItemFieldValuesMap.put(classPK, infoItemFieldValues);
		}

		return getMappedInfoItemFieldValue(
			jsonObject.getString("fieldId"), infoItemFieldValuesProvider,
			locale, object);
	}

	@Override
	public Object getMappedInfoItemFieldValue(
		String fieldName,
		InfoItemFieldValuesProvider infoItemFieldValuesProvider, Locale locale,
		Object object) {

		if (infoItemFieldValuesProvider == null) {
			return null;
		}

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(object, fieldName);

		if (infoFieldValue == null) {
			return null;
		}

		Object value = infoFieldValue.getValue(locale);

		if (value instanceof WebImage) {
			WebImage webImage = (WebImage)value;

			return webImage.toJSONObject();
		}

		return formatMappedValue(value, locale);
	}

	@Override
	public boolean isAssetDisplayPage(String mode) {
		if (Objects.equals(
				mode, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isMapped(JSONObject jsonObject) {
		long classNameId = jsonObject.getLong("classNameId");
		long classPK = jsonObject.getLong("classPK");
		String fieldId = jsonObject.getString("fieldId");

		if ((classNameId > 0) && (classPK > 0) &&
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
	public boolean isMappedLayout(JSONObject jsonObject) {
		if (jsonObject.has("layout")) {
			return true;
		}

		return false;
	}

	private String _getEditableValueByLocale(
		JSONObject jsonObject, Locale locale) {

		String value = jsonObject.getString(
			_language.getLanguageId(locale), null);

		if (value != null) {
			return value;
		}

		value = jsonObject.getString(
			_language.getLanguageId(LocaleUtil.getSiteDefault()));

		if (Validator.isNull(value)) {
			value = jsonObject.getString("defaultValue");
		}

		return value;
	}

	private long _getFileEntryId(
		String className, Object displayObject, String fieldName,
		Locale locale) {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
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
				_infoItemServiceTracker.getFirstInfoItemService(
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

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, infoItemReference.getClassName(),
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

	private static final InfoCollectionTextFormatter<Object>
		_INFO_COLLECTION_TEXT_FORMATTER =
			new CommaSeparatedInfoCollectionTextFormatter();

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryProcessorHelperImpl.class);

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}