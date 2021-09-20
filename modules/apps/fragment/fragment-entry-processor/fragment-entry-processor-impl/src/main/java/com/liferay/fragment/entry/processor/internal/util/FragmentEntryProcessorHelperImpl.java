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

import com.liferay.asset.info.display.contributor.util.ContentAccessor;
import com.liferay.asset.info.display.contributor.util.ContentAccessorUtil;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
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
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getEditableValue(JSONObject, Locale)}
	 */
	@Deprecated
	@Override
	public String getEditableValue(
		JSONObject jsonObject, Locale locale, long[] segmentsExperienceIds) {

		return _getEditableValueByLocale(jsonObject, locale);
	}

	public long getFileEntryId(
			long classNameId, long classPK, String fieldId, Locale locale)
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
			_portal.getClassName(classNameId), object, fieldId, locale);
	}

	public long getFileEntryId(
		Object displayObject, String fieldId, Locale locale) {

		if (Validator.isNull(fieldId) ||
			!(displayObject instanceof ClassedModel)) {

			return 0;
		}

		ClassedModel classedModel = (ClassedModel)displayObject;

		return _getFileEntryId(
			classedModel.getModelClassName(), displayObject, fieldId, locale);
	}

	public long getFileEntryId(String className, long classPK) {
		if (!Objects.equals(className, FileEntry.class.getName())) {
			return 0;
		}

		return classPK;
	}

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
		JSONObject jsonObject,
		FragmentEntryProcessorContext fragmentEntryProcessorContext) {

		if (!isMappedCollection(jsonObject)) {
			return JSONFactoryUtil.createJSONObject();
		}

		Optional<Object> displayObjectOptional =
			fragmentEntryProcessorContext.getDisplayObjectOptional();

		if (!displayObjectOptional.isPresent()) {
			return null;
		}

		Object displayObject = displayObjectOptional.get();

		if (!(displayObject instanceof ClassedModel)) {
			return null;
		}

		ClassedModel classedModel = (ClassedModel)displayObject;

		// LPS-111037

		String className = classedModel.getModelClassName();

		if (classedModel instanceof FileEntry) {
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

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(
				displayObjectOptional.get(),
				jsonObject.getString("collectionFieldId"));

		if (infoFieldValue == null) {
			return null;
		}

		Object value = infoFieldValue.getValue(
			fragmentEntryProcessorContext.getLocale());

		if (value instanceof ContentAccessor) {
			ContentAccessor contentAccessor = (ContentAccessor)infoFieldValue;

			value = contentAccessor.getContent();
		}
		else if (value instanceof WebImage) {
			WebImage webImage = (WebImage)value;

			return webImage.toJSONObject();
		}

		return formatMappedValue(
			value, fragmentEntryProcessorContext.getLocale());
	}

	@Override
	public Object getMappedInfoItemFieldValue(
			JSONObject jsonObject,
			Map<Long, InfoItemFieldValues> infoItemFieldValuesMap,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (!isMapped(jsonObject) &&
			!isAssetDisplayPage(fragmentEntryProcessorContext.getMode())) {

			return JSONFactoryUtil.createJSONObject();
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

		if (fragmentEntryProcessorContext.getPreviewClassPK() > 0) {
			infoItemIdentifier = new ClassPKInfoItemIdentifier(
				fragmentEntryProcessorContext.getPreviewClassPK());

			if (Validator.isNotNull(
					fragmentEntryProcessorContext.getPreviewVersion())) {

				infoItemIdentifier.setVersion(
					fragmentEntryProcessorContext.getPreviewVersion());
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

		String fieldId = jsonObject.getString("fieldId");

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(fieldId);

		if (infoFieldValue == null) {
			return null;
		}

		Object fieldValue = infoFieldValue.getValue(
			fragmentEntryProcessorContext.getLocale());

		if (fieldValue == null) {
			return null;
		}

		if (fieldValue instanceof ContentAccessor) {
			ContentAccessor contentAccessor = (ContentAccessor)fieldValue;

			fieldValue = contentAccessor.getContent();
		}

		if (fieldValue instanceof WebImage) {
			WebImage webImage = (WebImage)fieldValue;

			fieldValue = webImage.toJSONObject();
		}
		else {
			fieldValue = formatMappedValue(
				fieldValue, fragmentEntryProcessorContext.getLocale());
		}

		return fieldValue;
	}

	@Override
	public Object getMappedInfoItemFieldValue(
			JSONObject jsonObject,
			Map<Long, InfoItemFieldValues> infoDisplaysFieldValues, String mode,
			Locale locale, long previewClassPK, long previewClassNameId,
			int previewType)
		throws PortalException {

		DefaultFragmentEntryProcessorContext
			defaultFragmentEntryProcessorContext =
				new DefaultFragmentEntryProcessorContext(
					null, null, mode, locale);

		defaultFragmentEntryProcessorContext.setPreviewClassNameId(
			previewClassNameId);
		defaultFragmentEntryProcessorContext.setPreviewClassPK(previewClassPK);
		defaultFragmentEntryProcessorContext.setPreviewType(previewType);

		return getMappedInfoItemFieldValue(
			jsonObject, infoDisplaysFieldValues,
			defaultFragmentEntryProcessorContext);
	}

	@Override
	public Object getMappedLayoutValue(
			JSONObject jsonObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (!isMappedLayout(jsonObject)) {
			return StringPool.BLANK;
		}

		HttpServletRequest httpServletRequest =
			fragmentEntryProcessorContext.getHttpServletRequest();

		if (httpServletRequest == null) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		JSONObject layoutJSONObject = jsonObject.getJSONObject("layout");

		long groupId = layoutJSONObject.getLong("groupId");

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return StringPool.POUND;
		}

		Layout layout = _layoutLocalService.fetchLayout(
			groupId, layoutJSONObject.getBoolean("privateLayout"),
			layoutJSONObject.getLong("layoutId"));

		if (layout == null) {
			return StringPool.POUND;
		}

		return _portal.getLayoutRelativeURL(layout, themeDisplay);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getMappedInfoItemFieldValue(JSONObject, Map, FragmentEntryProcessorContext)}
	 */
	@Deprecated
	@Override
	public Object getMappedValue(
		JSONObject jsonObject,
		Map<Long, Map<String, Object>> infoDisplaysFieldValues,
		FragmentEntryProcessorContext fragmentEntryProcessorContext) {

		throw new UnsupportedOperationException(
			"This method is deprecated and replaced by " +
				"com.liferay.fragment.entry.processor.helper." +
					"FragmentEntryProcessorHelper.getMappedInfoItemFieldValue");
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getMappedInfoItemFieldValue(JSONObject, Map, String, Locale, long, long, int)}
	 */
	@Deprecated
	@Override
	public Object getMappedValue(
		JSONObject jsonObject,
		Map<Long, Map<String, Object>> infoDisplaysFieldValues, String mode,
		Locale locale, long previewClassPK, long previewClassNameId,
		int previewType) {

		throw new UnsupportedOperationException(
			"This method is deprecated and replaced by " +
				"com.liferay.fragment.entry.processor.helper." +
					"FragmentEntryProcessorHelper.getMappedInfoItemFieldValue");
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

	@Override
	public String processTemplate(
			String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		Template template = TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_FTL,
			new StringTemplateResource("template_id", "[#ftl] " + html), true);

		template.prepareTaglib(
			fragmentEntryProcessorContext.getHttpServletRequest(),
			fragmentEntryProcessorContext.getHttpServletResponse());

		template.put(TemplateConstants.WRITER, unsyncStringWriter);
		template.put("contentAccessorUtil", ContentAccessorUtil.getInstance());

		Optional<Map<String, Object>> fieldValuesOptional =
			fragmentEntryProcessorContext.getFieldValuesOptional();

		if (fieldValuesOptional.isPresent() &&
			MapUtil.isNotEmpty(fieldValuesOptional.get())) {

			template.putAll(fieldValuesOptional.get());
		}

		template.prepare(fragmentEntryProcessorContext.getHttpServletRequest());

		template.processTemplate(unsyncStringWriter);

		return unsyncStringWriter.toString();
	}

	private String _getEditableValueByLocale(
		JSONObject jsonObject, Locale locale) {

		String value = jsonObject.getString(
			LanguageUtil.getLanguageId(locale), null);

		if (value != null) {
			return value;
		}

		value = jsonObject.getString(
			LanguageUtil.getLanguageId(LocaleUtil.getSiteDefault()));

		if (Validator.isNull(value)) {
			value = jsonObject.getString("defaultValue");
		}

		return value;
	}

	private long _getFileEntryId(
		String className, Object displayObject, String fieldId, Locale locale) {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class, className);

		if (infoItemFieldValuesProvider == null) {
			return 0;
		}

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValuesProvider.getInfoFieldValue(
				displayObject, fieldId);

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

	private static final InfoCollectionTextFormatter<Object>
		_INFO_COLLECTION_TEXT_FORMATTER =
			new CommaSeparatedInfoCollectionTextFormatter();

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryProcessorHelperImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}