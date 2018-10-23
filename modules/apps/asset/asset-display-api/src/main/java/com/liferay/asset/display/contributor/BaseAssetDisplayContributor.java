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

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseAssetDisplayContributor<T>
	implements AssetDisplayContributor {

	@Override
	public Set<AssetDisplayField> getAssetDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException {

		// Fields for asset entry

		Set<AssetDisplayField> assetDisplayFields =
			_assetDisplayContributorFieldTracker.
				getAssetEntryAssetDisplayFields(locale);

		// Fields for the specific asset type

		Set<AssetDisplayField> journalArticleAssetDisplayFields =
			_assetDisplayContributorFieldTracker.getAssetDisplayFields(
				getClassName(), locale);

		assetDisplayFields.addAll(journalArticleAssetDisplayFields);

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

		// Field values for asset entry

		Map<String, Object> parameterMap =
			_assetDisplayContributorFieldTracker.
				getAssetEntryAssetDisplayFieldsValues(assetEntry, locale);

		// Field values for the specific asset type

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(
					assetEntry.getClassNameId());

		List<AssetDisplayContributorField> assetDisplayContributorFields =
			_assetDisplayContributorFieldTracker.
				getAssetDisplayContributorFields(getClassName());

		AssetRenderer<T> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetEntry.getClassPK());

		for (AssetDisplayContributorField assetDisplayContributorField :
				assetDisplayContributorFields) {

			parameterMap.put(
				assetDisplayContributorField.getKey(),
				assetDisplayContributorField.getValue(
					assetRenderer.getAssetObject(), locale));
		}

		// Field values for the class type

		Map<String, Object> classTypeValues = getClassTypeValues(
			assetRenderer.getAssetObject(), locale);

		parameterMap.putAll(classTypeValues);

		return parameterMap;
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

	@Reference
	private AssetDisplayContributorFieldTracker
		_assetDisplayContributorFieldTracker;

}