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

package com.liferay.asset.info.display.contributor;

import com.liferay.asset.info.display.contributor.util.AssetInfoDisplayContributorFieldUtil;
import com.liferay.asset.info.display.contributor.util.ContentAccessor;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.display.contributor.InfoDisplayContributorField;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseAssetInfoDisplayContributor<T>
	implements AssetInfoDisplayContributor {

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		Set<InfoDisplayField> infoDisplayFields = _getInfoDisplayFields(
			AssetEntry.class.getName(), locale);

		Set<InfoDisplayField> assetTypeInfoDisplayFields =
			_getInfoDisplayFields(getClassName(), locale);

		infoDisplayFields.addAll(assetTypeInfoDisplayFields);

		List<InfoDisplayField> classTypeInfoDisplayFields =
			getClassTypeInfoDisplayFields(classTypeId, locale);

		infoDisplayFields.addAll(classTypeInfoDisplayFields);

		return infoDisplayFields;
	}

	@Override
	public Map<String, Object> getInfoDisplayFieldsValues(
			AssetEntry assetEntry, Locale locale)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					assetEntry.getClassNameId());

		AssetRenderer<T> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		T assetObject = assetRenderer.getAssetObject();

		return _getParameterMap(assetEntry, assetObject, locale);
	}

	@Override
	public Object getInfoDisplayFieldValue(
			AssetEntry assetEntry, String fieldName, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldsValues =
			getInfoDisplayFieldsValues(assetEntry, locale);

		Object fieldValue = infoDisplayFieldsValues.getOrDefault(
			fieldName, StringPool.BLANK);

		if (fieldValue instanceof ContentAccessor) {
			ContentAccessor contentAccessor = (ContentAccessor)fieldValue;

			fieldValue = contentAccessor.getContent();
		}

		return fieldValue;
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(long classPK)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					PortalUtil.getClassNameId(getClassName()));

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			classPK);

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			getClassName(), assetRenderer.getClassPK());

		return new AssetInfoDisplayObjectProvider(assetEntry);
	}

	@Override
	public InfoDisplayObjectProvider<AssetEntry> getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					PortalUtil.getClassNameId(getClassName()));

		AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
			groupId, urlTitle);

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			getClassName(), assetRenderer.getClassPK());

		return new AssetInfoDisplayObjectProvider(assetEntry);
	}

	@Override
	public String getLabel(Locale locale) {
		return ResourceActionsUtil.getModelResource(locale, getClassName());
	}

	@Override
	public Map<String, Object> getVersionInfoDisplayFieldsValues(
			AssetEntry assetEntry, long versionClassPK, Locale locale)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					assetEntry.getClassNameId());

		AssetRenderer<T> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		T assetObject = assetRenderer.getAssetObject(versionClassPK);

		if (assetObject == null) {
			throw new NoSuchEntryException(
				"No asset entry exists with version class PK " +
					versionClassPK);
		}

		return _getParameterMap(assetEntry, assetObject, locale);
	}

	protected Object getClassTypeFieldValue(
		T assetEntryObject, String fieldName, Locale locale) {

		Map<String, Object> classTypeValues = getClassTypeValues(
			assetEntryObject, locale);

		return classTypeValues.getOrDefault(fieldName, StringPool.BLANK);
	}

	protected abstract Map<String, Object> getClassTypeValues(
		T assetEntryObject, Locale locale);

	private Map<String, Object> _getAssetEntryInfoDisplayFieldsValues(
			AssetEntry assetEntry, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldsValues = new HashMap<>();

		for (InfoDisplayContributorField infoDisplayContributorField :
				_getInfoDisplayContributorFields(AssetEntry.class.getName())) {

			infoDisplayFieldsValues.putIfAbsent(
				infoDisplayContributorField.getKey(),
				_getInfoDisplayFieldValue(
					assetEntry, assetEntry, infoDisplayContributorField,
					locale));
		}

		return infoDisplayFieldsValues;
	}

	private List<InfoDisplayContributorField> _getInfoDisplayContributorFields(
		String className) {

		return AssetInfoDisplayContributorFieldUtil.
			getInfoDisplayContributorFields(className);
	}

	private Set<InfoDisplayField> _getInfoDisplayFields(
		String className, Locale locale) {

		Set<InfoDisplayField> infoDisplayFields = new LinkedHashSet<>();

		for (InfoDisplayContributorField infoDisplayContributorField :
				_getInfoDisplayContributorFields(className)) {

			infoDisplayFields.add(
				new InfoDisplayField(
					infoDisplayContributorField.getKey(),
					infoDisplayContributorField.getLabel(locale),
					infoDisplayContributorField.getType()));
		}

		return infoDisplayFields;
	}

	private <T> Object _getInfoDisplayFieldValue(
			T model, AssetEntry assetEntry,
			InfoDisplayContributorField infoDisplayContributorField,
			Locale locale)
		throws SanitizerException {

		String type = infoDisplayContributorField.getType();
		Object value = infoDisplayContributorField.getValue(model, locale);

		if (!type.equals("url") && (value instanceof String)) {
			return SanitizerUtil.sanitize(
				assetEntry.getCompanyId(), assetEntry.getGroupId(),
				assetEntry.getUserId(), AssetEntry.class.getName(),
				assetEntry.getEntryId(), ContentTypes.TEXT_HTML,
				Sanitizer.MODE_ALL, (String)value, null);
		}

		return value;
	}

	private Map<String, Object> _getParameterMap(
			AssetEntry assetEntry, T assetObject, Locale locale)
		throws PortalException {

		Map<String, Object> parameterMap =
			_getAssetEntryInfoDisplayFieldsValues(assetEntry, locale);

		List<InfoDisplayContributorField> infoDisplayContributorFields =
			AssetInfoDisplayContributorFieldUtil.
				getInfoDisplayContributorFields(getClassName());

		for (InfoDisplayContributorField infoDisplayContributorField :
				infoDisplayContributorFields) {

			parameterMap.putIfAbsent(
				infoDisplayContributorField.getKey(),
				_getInfoDisplayFieldValue(
					assetObject, assetEntry, infoDisplayContributorField,
					locale));
		}

		Map<String, Object> classTypeValues = getClassTypeValues(
			assetObject, locale);

		parameterMap.putAll(classTypeValues);

		return parameterMap;
	}

}