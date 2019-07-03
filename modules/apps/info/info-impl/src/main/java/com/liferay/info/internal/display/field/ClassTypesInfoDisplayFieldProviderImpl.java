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

package com.liferay.info.internal.display.field;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.field.ClassTypesInfoDisplayFieldProvider;
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
			ClassType classType, Locale locale)
		throws PortalException {

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
			long groupId, ClassTypeReader classTypeReader, Locale locale)
		throws PortalException {

		return classTypeReader.getAvailableClassTypes(
			_portal.getCurrentAndAncestorSiteGroupIds(groupId), locale);
	}

	@Reference
	private Portal _portal;

}