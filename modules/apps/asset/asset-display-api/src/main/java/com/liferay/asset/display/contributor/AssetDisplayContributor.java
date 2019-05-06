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
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author     Jürgen Kappler
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.asset.info.display.contributor.AssetInfoDisplayContributor}
 */
@Deprecated
public interface AssetDisplayContributor {

	public Set<AssetDisplayField> getAssetDisplayFields(
			long classTypeId, Locale locale)
		throws PortalException;

	public Map<String, Object> getAssetDisplayFieldsValues(
			AssetEntry assetEntry, Locale locale)
		throws PortalException;

	public default Map<String, Object> getAssetDisplayFieldsValues(
			AssetEntry assetEntry, long versionClassPK, Locale locale)
		throws PortalException {

		return getAssetDisplayFieldsValues(assetEntry, locale);
	}

	public Object getAssetDisplayFieldValue(
			AssetEntry assetEntry, String fieldName, Locale locale)
		throws PortalException;

	public String getAssetURLSeparator();

	public String getClassName();

	public default List<AssetDisplayField> getClassTypeFields(
			long classTypeId, Locale locale)
		throws PortalException {

		if (classTypeId == 0) {
			return Collections.emptyList();
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return Collections.emptyList();
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(classTypeId, locale);

		if (classType == null) {
			return Collections.emptyList();
		}

		List<AssetDisplayField> classTypeFields = new ArrayList<>();

		for (ClassTypeField classTypeField : classType.getClassTypeFields()) {
			classTypeFields.add(
				new AssetDisplayField(
					classTypeField.getName(),
					LanguageUtil.get(locale, classTypeField.getLabel()),
					classTypeField.getType()));
		}

		return classTypeFields;
	}

	public default List<ClassType> getClassTypes(long groupId, Locale locale)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getClassName());

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return Collections.emptyList();
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		return classTypeReader.getAvailableClassTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), locale);
	}

	public InfoDisplayObjectProvider<AssetEntry> getInfoDisplayObjectProvider(
			long classPK)
		throws PortalException;

	public InfoDisplayObjectProvider<AssetEntry> getInfoDisplayObjectProvider(
			long groupId, String urlTitle)
		throws PortalException;

	public String getLabel(Locale locale);

}