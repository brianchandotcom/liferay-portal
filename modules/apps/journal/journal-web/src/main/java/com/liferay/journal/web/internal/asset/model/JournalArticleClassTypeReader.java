/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.asset.model;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.model.DDMStructureClassType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class JournalArticleClassTypeReader implements ClassTypeReader {

	public JournalArticleClassTypeReader(String className) {
		_className = className;
	}

	@Override
	public List<ClassType> getAvailableClassTypes(
		long[] groupIds, Locale locale) {

		List<ClassType> classTypes = new ArrayList<>();

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getStructures(
				_replaceGroupIds(groupIds),
				PortalUtil.getClassNameId(_className));

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

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			classTypeId);

		return new DDMStructureClassType(
			classTypeId, ddmStructure.getName(locale),
			LocaleUtil.toLanguageId(locale));
	}

	private long[] _replaceGroupIds(long[] groupIds) {
		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		groupIds = groupIds.clone();

		for (int i = 0; i < groupIds.length; i++) {
			groupIds[i] = stagingGroupHelper.getStagedPortletGroupId(
				groupIds[i], JournalPortletKeys.JOURNAL);
		}

		return groupIds;
	}

	private final String _className;

}