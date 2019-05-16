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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.model.VersionedAssetEntry;
import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.util.FragmentEntryProcessorUtil;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = FragmentEntryProcessorUtil.class)
public class FragmentEntryProcessorImpl implements FragmentEntryProcessorUtil {

	@Override
	public String getEditableValue(
		JSONObject jsonObject, Locale locale, long[] segmentsExperienceIds) {

		if (_isPersonalizationSupported(jsonObject)) {
			return _getEditableValueBySegmentsExperienceAndLocale(
				jsonObject, locale, segmentsExperienceIds);
		}

		return _getEditableValueByLocale(jsonObject, locale);
	}

	@Override
	public Object getMappedValue(
			JSONObject jsonObject,
			Map<Long, Map<String, Object>> assetEntriesFieldValues, String mode,
			Locale locale, long previewClassPK, int previewType)
		throws PortalException {

		if (!isMapped(jsonObject) && !isAssetDisplayPage(mode)) {
			return JSONFactoryUtil.createJSONObject();
		}

		long classNameId = jsonObject.getLong("classNameId");
		long classPK = jsonObject.getLong("classPK");

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			classNameId, classPK);

		if (assetEntry == null) {
			return null;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			assetEntry.getClassName());

		if ((trashHandler != null) &&
			trashHandler.isInTrash(assetEntry.getClassPK())) {

			return null;
		}

		Map<String, Object> fieldsValues = assetEntriesFieldValues.get(
			assetEntry.getEntryId());

		if (MapUtil.isEmpty(fieldsValues)) {
			InfoDisplayContributor infoDisplayContributor =
				_infoDisplayContributorTracker.getInfoDisplayContributor(
					assetEntry.getClassName());

			int versionType = AssetRendererFactory.TYPE_LATEST_APPROVED;

			if (previewClassPK == assetEntry.getEntryId()) {
				versionType = previewType;
			}

			fieldsValues = infoDisplayContributor.getInfoDisplayFieldsValues(
				new VersionedAssetEntry(assetEntry, versionType), locale);

			assetEntriesFieldValues.put(assetEntry.getEntryId(), fieldsValues);
		}

		String fieldId = jsonObject.getString("fieldId");

		Object fieldValue = fieldsValues.getOrDefault(fieldId, null);

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

	private String _getEditableValueByLocale(
		JSONObject jsonObject, Locale locale) {

		String value = jsonObject.getString(LanguageUtil.getLanguageId(locale));

		if (Validator.isNotNull(value)) {
			return value;
		}

		value = jsonObject.getString(
			LanguageUtil.getLanguageId(LocaleUtil.getSiteDefault()));

		if (Validator.isNull(value)) {
			value = jsonObject.getString("defaultValue");
		}

		return value;
	}

	private String _getEditableValueBySegmentsExperienceAndLocale(
		JSONObject jsonObject, Locale locale, long[] segmentsExperienceIds) {

		for (long segmentsExperienceId : segmentsExperienceIds) {
			String value = _getSegmentsExperienceValue(
				jsonObject, locale, segmentsExperienceId);

			if (Validator.isNotNull(value)) {
				return value;
			}
		}

		return jsonObject.getString("defaultValue");
	}

	private String _getSegmentsExperienceValue(
		JSONObject jsonObject, Locale locale, Long segmentsExperienceId) {

		JSONObject segmentsExperienceJSONObject = jsonObject.getJSONObject(
			_EDITABLE_VALUES_SEGMENTS_EXPERIENCE_ID_PREFIX +
				segmentsExperienceId);

		if (segmentsExperienceJSONObject == null) {
			return StringPool.BLANK;
		}

		String value = segmentsExperienceJSONObject.getString(
			LanguageUtil.getLanguageId(locale));

		if (Validator.isNotNull(value)) {
			return value;
		}

		value = segmentsExperienceJSONObject.getString(
			LanguageUtil.getLanguageId(LocaleUtil.getSiteDefault()));

		if (Validator.isNotNull(value)) {
			return value;
		}

		return StringPool.BLANK;
	}

	private boolean _isPersonalizationSupported(JSONObject jsonObject) {
		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			if (key.startsWith(
					_EDITABLE_VALUES_SEGMENTS_EXPERIENCE_ID_PREFIX)) {

				return true;
			}
		}

		return false;
	}

	private static final String _EDITABLE_VALUES_SEGMENTS_EXPERIENCE_ID_PREFIX =
		"segments-experience-id-";

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

}