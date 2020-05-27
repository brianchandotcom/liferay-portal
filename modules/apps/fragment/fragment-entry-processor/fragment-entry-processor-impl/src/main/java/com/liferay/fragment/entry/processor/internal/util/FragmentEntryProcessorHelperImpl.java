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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.InfoFormValues;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.item.provider.InfoItemFormProviderTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

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

	@Override
	public Object getMappedCollectionValue(
			JSONObject jsonObject,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

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

		InfoItemFormProvider<Object> infoItemFormProvider =
			_infoItemFormProviderTracker.getInfoItemFormProvider(className);

		if (infoItemFormProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						className);
			}

			return null;
		}

		InfoFieldValue infoFieldValue = infoItemFormProvider.getInfoFieldValue(
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

		return value;
	}

	@Override
	public Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> infoDisplaysFieldValues,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		if (!isMapped(jsonObject) &&
			!isAssetDisplayPage(fragmentEntryProcessorContext.getMode())) {

			return JSONFactoryUtil.createJSONObject();
		}

		long classNameId = jsonObject.getLong("classNameId");

		String className = _portal.getClassName(classNameId);

		InfoDisplayContributor<Object> infoDisplayContributor =
			(InfoDisplayContributor<Object>)
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					className);

		if (infoDisplayContributor == null) {
			return null;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			className);

		long classPK = jsonObject.getLong("classPK");

		if ((trashHandler != null) && trashHandler.isInTrash(classPK)) {
			return null;
		}

		InfoDisplayObjectProvider<Object> infoDisplayObjectProvider = null;

		if ((fragmentEntryProcessorContext.getPreviewClassPK() > 0) &&
			_isSameClassedModel(
				classPK, fragmentEntryProcessorContext.getPreviewClassPK())) {

			infoDisplayObjectProvider =
				infoDisplayContributor.getPreviewInfoDisplayObjectProvider(
					fragmentEntryProcessorContext.getPreviewClassPK(),
					fragmentEntryProcessorContext.getPreviewType());
		}
		else {
			infoDisplayObjectProvider =
				infoDisplayContributor.getInfoDisplayObjectProvider(classPK);
		}

		if (infoDisplayObjectProvider == null) {
			return null;
		}

		InfoItemFormProvider infoItemFormProvider =
			_infoItemFormProviderTracker.getInfoItemFormProvider(className);

		if (infoItemFormProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get info item form provider for class " +
						className);
			}

			return null;
		}

		Object object = infoDisplayObjectProvider.getDisplayObject();

		Map<String, Object> fieldsValues = infoDisplaysFieldValues.get(classPK);

		if (fieldsValues == null) {
			InfoFormValues infoFormValues =
				infoItemFormProvider.getInfoFormValues(object);

			fieldsValues = infoFormValues.getMap(
				fragmentEntryProcessorContext.getLocale());

			infoDisplaysFieldValues.put(classPK, fieldsValues);
		}

		String fieldId = jsonObject.getString("fieldId");

		Object fieldValue = fieldsValues.getOrDefault(
			fieldId, StringPool.BLANK);

		if (fieldValue == null) {
			return null;
		}

		if (fieldValue instanceof ContentAccessor) {
			ContentAccessor contentAccessor = (ContentAccessor)fieldValue;

			fieldValue = contentAccessor.getContent();
		}

		return fieldValue;
	}

	@Override
	public Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> infoDisplaysFieldValues, String mode,
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

		return getMappedValue(
			jsonObject, infoDisplaysFieldValues,
			defaultFragmentEntryProcessorContext);
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

	private boolean _isSameClassedModel(long classPK, long previewClassPK) {
		AssetEntry assetEntry = _assetEntryLocalService.fetchAssetEntry(
			previewClassPK);

		if ((assetEntry == null) || (assetEntry.getClassPK() != classPK)) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryProcessorHelperImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private InfoItemFormProviderTracker _infoItemFormProviderTracker;

	@Reference
	private Portal _portal;

}