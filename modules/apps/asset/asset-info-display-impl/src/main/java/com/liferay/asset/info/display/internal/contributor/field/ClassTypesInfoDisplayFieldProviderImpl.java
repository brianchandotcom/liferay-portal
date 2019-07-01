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

package com.liferay.asset.info.display.internal.contributor.field;

import com.liferay.asset.info.display.field.ClassTypesInfoDisplayFieldProvider;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = ClassTypesInfoDisplayFieldProvider.class)
public class ClassTypesInfoDisplayFieldProviderImpl
	implements ClassTypesInfoDisplayFieldProvider {

	@Override
	public List<InfoDisplayField> getClassTypeInfoDisplayFields(
			String className, long classTypeId, Locale locale)
		throws PortalException {

		if (classTypeId == 0) {
			return Collections.emptyList();
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

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

		List<InfoDisplayField> classTypeInfoDisplayFields = new ArrayList<>();

		for (ClassTypeField classTypeField : classType.getClassTypeFields()) {
			classTypeInfoDisplayFields.add(
				new InfoDisplayField(
					classTypeField.getName(),
					LanguageUtil.get(locale, classTypeField.getLabel()),
					classTypeField.getType()));
		}

		return classTypeInfoDisplayFields;
	}

	@Override
	public List<ClassType> getClassTypes(
			long groupId, String className, Locale locale)
		throws PortalException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if ((assetRendererFactory == null) ||
			!assetRendererFactory.isSupportsClassTypes()) {

			return Collections.emptyList();
		}

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		return classTypeReader.getAvailableClassTypes(
			_portal.getCurrentAndAncestorSiteGroupIds(groupId), locale);
	}

	@Reference
	private Portal _portal;

}