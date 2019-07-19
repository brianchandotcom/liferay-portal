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

import com.liferay.asset.info.display.contributor.field.util.ExpandoInfoDisplayFieldProviderUtil;
import com.liferay.asset.info.display.field.util.AssetEntryInfoDisplayFieldProviderUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.model.VersionedAssetEntry;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author     Jürgen Kappler
 * @deprecated As of Mueller (7.2.x), in favour of {@link
 *             com.liferay.info.display.contributor.InfoDisplayContributor}
 */
@Deprecated
public abstract class BaseAssetInfoDisplayContributor<T>
	implements AssetInfoDisplayContributor {

	@Override
	public Set<InfoDisplayField> getInfoDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		Set<InfoDisplayField> infoDisplayFields =
			AssetEntryInfoDisplayFieldProviderUtil.getInfoDisplayFields(
				locale, AssetEntry.class.getName(), getClassName());

		infoDisplayFields.addAll(
			getClassTypeInfoDisplayFields(classTypeId, locale));
		infoDisplayFields.addAll(
			ExpandoInfoDisplayFieldProviderUtil.getExpandoInfoDisplayFields(
				getClassName(), locale));

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

		AssetRenderer<T> assetRenderer = null;

		if (assetEntry instanceof VersionedAssetEntry) {
			VersionedAssetEntry versionedAssetEntry =
				(VersionedAssetEntry)assetEntry;

			assetRenderer = assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK(), versionedAssetEntry.getVersionType());
		}
		else {
			assetRenderer = assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK());
		}

		return _getParameterMap(
			assetEntry, assetRenderer.getAssetObject(), locale);
	}

	@Override
	public Object getInfoDisplayFieldValue(
			AssetEntry assetEntry, String fieldName, Locale locale)
		throws PortalException {

		Map<String, Object> infoDisplayFieldsValues =
			getInfoDisplayFieldsValues(assetEntry, locale);

		return infoDisplayFieldsValues.getOrDefault(
			fieldName, StringPool.BLANK);
	}

	@Override
	public InfoDisplayObjectProvider getInfoDisplayObjectProvider(
		long classPK) {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					PortalUtil.getClassNameId(getClassName()));

		try {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				classPK);

			AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
				getClassName(), assetRenderer.getClassPK());

			return new AssetInfoDisplayObjectProvider(assetEntry);
		}
		catch (Exception e) {
			return null;
		}
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

	private Map<String, Object> _getParameterMap(
			AssetEntry assetEntry, T assetObject, Locale locale)
		throws PortalException {

		Map<String, Object> parameterMap = new HashMap<>();

		parameterMap.putAll(
			AssetEntryInfoDisplayFieldProviderUtil.getInfoDisplayFields(
				AssetEntry.class.getName(), assetEntry, locale));
		parameterMap.putAll(
			AssetEntryInfoDisplayFieldProviderUtil.getInfoDisplayFields(
				getClassName(), assetObject, locale));
		parameterMap.putAll(getClassTypeValues(assetObject, locale));
		parameterMap.putAll(
			ExpandoInfoDisplayFieldProviderUtil.
				getExpandoInfoDisplayFieldsValues(
					getClassName(), assetObject, locale));

		return parameterMap;
	}

}