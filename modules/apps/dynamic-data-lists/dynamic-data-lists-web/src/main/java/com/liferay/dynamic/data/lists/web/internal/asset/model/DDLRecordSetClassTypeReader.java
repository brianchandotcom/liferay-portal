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

package com.liferay.dynamic.data.lists.web.internal.asset.model;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetServiceUtil;
import com.liferay.dynamic.data.lists.web.internal.asset.DDLRecordSetClassType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordSetClassTypeReader implements ClassTypeReader {

	@Override
	public List<ClassType> getAvailableClassTypes(
		long[] groupIds, Locale locale) {

		List<ClassType> classTypes = new ArrayList<>();

		List<DDLRecordSet> recordSets = DDLRecordSetServiceUtil.getRecordSets(
			groupIds);

		for (DDLRecordSet recordSet : recordSets) {
			if (recordSet.getScope() ==
					DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS) {

				classTypes.add(
					new DDLRecordSetClassType(
						recordSet.getRecordSetId(), recordSet.getName(locale),
						LocaleUtil.toLanguageId(locale)));
			}
		}

		return classTypes;
	}

	@Override
	public ClassType getClassType(long classTypeId, Locale locale)
		throws PortalException {

		DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(
			classTypeId);

		return new DDLRecordSetClassType(
			classTypeId, recordSet.getName(locale),
			LocaleUtil.toLanguageId(locale));
	}

}