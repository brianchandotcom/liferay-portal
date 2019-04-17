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

package com.liferay.asset.display.contributor;

import com.liferay.asset.display.contributor.util.AssetDisplayContributorFieldHelperUtil;
import com.liferay.asset.info.display.contributor.AssetInfoDisplayObjectProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.asset.info.display.contributor.BaseAssetInfoDisplayContributor}
 */
@Deprecated
public abstract class BaseAssetDisplayContributor<T>
	implements AssetDisplayContributor {

	@Override
	public Set<AssetDisplayField> getAssetDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		// Fields for asset entry

		Set<AssetDisplayField> assetDisplayFields = _getAssetDisplayFields(
			AssetEntry.class.getName(), locale);

		// Fields for the specific asset type

		Set<AssetDisplayField> assetTypeAssetDisplayFields =
			_getAssetDisplayFields(getClassName(), locale);

		assetDisplayFields.addAll(assetTypeAssetDisplayFields);

		// Fields for the class type

		List<AssetDisplayField> classTypeFields = getClassTypeFields(
			classTypeId, locale);

		assetDisplayFields.addAll(classTypeFields);

		return assetDisplayFields;
	}

	@Override
	public Map<String, Object> getAssetDisplayFieldsValues(
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
	public Map<String, Object> getAssetDisplayFieldsValues(
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

	@Override
	public Object getAssetDisplayFieldValue(
			AssetEntry assetEntry, String fieldName, Locale locale)
		throws PortalException {

		Map<String, Object> assetDisplayFieldsValues =
			getAssetDisplayFieldsValues(assetEntry, locale);

		Object fieldValue = assetDisplayFieldsValues.getOrDefault(
			fieldName, StringPool.BLANK);

		return fieldValue;
	}

	@Override
	public InfoDisplayObjectProvider<AssetEntry> getInfoDisplayObjectProvider(
			long classPK)
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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getAssetEntryModelFieldsMap()}
	 */
	@Deprecated
	protected String[] getAssetEntryModelFields() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Judson (7.1.x), }
	 */
	@Deprecated
	protected Map<String, String> getAssetEntryModelFieldsMap() {
		throw new UnsupportedOperationException();
	}

	protected Object getClassTypeFieldValue(
		T assetEntryObject, String fieldName, Locale locale) {

		Map<String, Object> classTypeValues = getClassTypeValues(
			assetEntryObject, locale);

		return classTypeValues.getOrDefault(fieldName, StringPool.BLANK);
	}

	protected abstract Map<String, Object> getClassTypeValues(
		T assetEntryObject, Locale locale);

	/**
	 * @deprecated As of Judson (7.1.x), }
	 */
	@Deprecated
	protected Object getFieldValue(
		T assetEntryObject, String field, Locale locale) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Judson (7.1.x), }
	 */
	@Deprecated
	protected ResourceBundle getResourceBundle(Locale locale) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced with {@link
	 *             #getResourceBundle(Locale)}
	 */
	@Deprecated
	protected void setResourceBundleLoader(
		ResourceBundleLoader resourceBundleLoader) {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Judson (7.1.x), }
	 */
	@Deprecated
	protected ResourceBundleLoader resourceBundleLoader;

	/**
	 * @deprecated As of Judson (7.1.x), }
	 */
	@Deprecated
	@Reference
	protected UserLocalService userLocalService;

	private List<AssetDisplayContributorField>
		_getAssetDisplayContributorFields(String className) {

		return AssetDisplayContributorFieldHelperUtil.
			getAssetDisplayContributorFields(className);
	}

	private Set<AssetDisplayField> _getAssetDisplayFields(
		String className, Locale locale) {

		Set<AssetDisplayField> assetDisplayFields = new LinkedHashSet<>();

		for (AssetDisplayContributorField assetDisplayContributorField :
				_getAssetDisplayContributorFields(className)) {

			assetDisplayFields.add(
				new AssetDisplayField(
					assetDisplayContributorField.getKey(),
					assetDisplayContributorField.getLabel(locale),
					assetDisplayContributorField.getType()));
		}

		return assetDisplayFields;
	}

	private Map<String, Object> _getAssetEntryAssetDisplayFieldsValues(
			AssetEntry assetEntry, Locale locale)
		throws PortalException {

		Map<String, Object> assetDisplayFieldsValues = new HashMap<>();

		for (AssetDisplayContributorField assetDisplayContributorField :
				_getAssetDisplayContributorFields(AssetEntry.class.getName())) {

			Object assetDisplayFieldValue =
				assetDisplayContributorField.getValue(assetEntry, locale);

			if (assetDisplayFieldValue instanceof String) {
				assetDisplayFieldValue = SanitizerUtil.sanitize(
					assetEntry.getCompanyId(), assetEntry.getGroupId(),
					assetEntry.getUserId(), AssetEntry.class.getName(),
					assetEntry.getEntryId(), ContentTypes.TEXT_HTML,
					Sanitizer.MODE_ALL, (String)assetDisplayFieldValue, null);
			}

			assetDisplayFieldsValues.putIfAbsent(
				assetDisplayContributorField.getKey(), assetDisplayFieldValue);
		}

		return assetDisplayFieldsValues;
	}

	private Map<String, Object> _getParameterMap(
			AssetEntry assetEntry, T assetObject, Locale locale)
		throws PortalException {

		// Field values for asset entry

		Map<String, Object> parameterMap =
			_getAssetEntryAssetDisplayFieldsValues(assetEntry, locale);

		// Field values for the specific asset type

		List<AssetDisplayContributorField> assetDisplayContributorFields =
			AssetDisplayContributorFieldHelperUtil.
				getAssetDisplayContributorFields(getClassName());

		for (AssetDisplayContributorField assetDisplayContributorField :
				assetDisplayContributorFields) {

			Object assetDisplayFieldValue =
				assetDisplayContributorField.getValue(assetObject, locale);

			if (assetDisplayFieldValue instanceof String) {
				assetDisplayFieldValue = SanitizerUtil.sanitize(
					assetEntry.getCompanyId(), assetEntry.getGroupId(),
					assetEntry.getUserId(), AssetEntry.class.getName(),
					assetEntry.getEntryId(), ContentTypes.TEXT_HTML,
					Sanitizer.MODE_ALL, (String)assetDisplayFieldValue, null);
			}

			parameterMap.putIfAbsent(
				assetDisplayContributorField.getKey(), assetDisplayFieldValue);
		}

		// Field values for the class type

		Map<String, Object> classTypeValues = getClassTypeValues(
			assetObject, locale);

		parameterMap.putAll(classTypeValues);

		return parameterMap;
	}

}