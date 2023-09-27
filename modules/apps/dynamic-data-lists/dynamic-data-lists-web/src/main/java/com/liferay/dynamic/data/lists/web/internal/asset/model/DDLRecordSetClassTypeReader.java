/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.web.internal.asset.model;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
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