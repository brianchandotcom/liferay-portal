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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class BaseDDMStructureClassTypeReader implements ClassTypeReader {

	public BaseDDMStructureClassTypeReader(String className) {
		_className = className;
	}

	@Override
	public List<ClassType> getAvailableClassTypes(
		long[] groupIds, Locale locale) {

		List<ClassType> classTypes = new ArrayList<ClassType>();

		List<DDMStructure> ddmStructures =
			DDMStructureServiceUtil.getStructures(
				groupIds, PortalUtil.getClassNameId(_className));

		for (DDMStructure ddmStructure : ddmStructures) {
			classTypes.add(
				new DDMStructureClassType(
					ddmStructure.getStructureId(), ddmStructure.getName(locale),
					LocaleUtil.toLanguageId(locale)));
		}

		return classTypes;
	}

	@Override
	public ClassType getClassType(long classTypeId, Locale locale)
		throws PortalException {

		DDMStructure ddmStructure = DDMStructureServiceUtil.getStructure(
			classTypeId);

		return new DDMStructureClassType(
			classTypeId, ddmStructure.getName(locale),
			LocaleUtil.toLanguageId(locale));
	}

	private final String _className;

}