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

package com.liferay.portlet.documentlibrary.model.impl;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLink;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Alexander Chow
 * @author Máté Thurzó
 */
public class DLFileEntryTypeImpl extends DLFileEntryTypeBaseImpl {

	@Override
	public List<DDMStructure> getDDMStructures() {
		List<DDMStructureLink> ddmStructureLinks =
			DDMStructureLinkManagerUtil.getStructureLinks(
				PortalUtil.getClassNameId(DLFileEntryType.class),
				getFileEntryTypeId());

		return getDDMStructures(ddmStructureLinks);
	}

	@Override
	public String getName(Locale locale) {
		String name = super.getName(locale);

		if (getFileEntryTypeId() ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			name = LanguageUtil.get(locale, name);
		}

		return name;
	}

	@Override
	public String getName(String languageId) {
		String name = super.getName(languageId);

		if (getFileEntryTypeId() ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			name = LanguageUtil.get(LanguageUtil.getLocale(languageId), name);
		}

		return name;
	}

	@Override
	public String getUnambiguousName(
			List<DLFileEntryType> dlFileEntryTypes, long groupId, Locale locale)
		throws PortalException {

		String languageId = LocaleUtil.toLanguageId(locale);

		String name = getName(languageId);

		if ((getGroupId() == groupId) || (dlFileEntryTypes == null)) {
			return name;
		}

		long fileEntryTypeId = getFileEntryTypeId();

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if ((dlFileEntryType.getFileEntryTypeId() != fileEntryTypeId) &&
				name.equals(dlFileEntryType.getName(languageId))) {

				Group group = GroupLocalServiceUtil.getGroup(getGroupId());

				return group.getUnambiguousName(name, locale);
			}
		}

		return name;
	}

	@Override
	public boolean isExportable() {
		if (getFileEntryTypeId() ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			return false;
		}

		return true;
	}

	protected List<DDMStructure> getDDMStructures(
		List<DDMStructureLink> ddmStructureLinks) {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			DDMStructure ddmStructure = DDMStructureManagerUtil.fetchStructure(
				ddmStructureLink.getStructureId());

			if (ddmStructure != null) {
				ddmStructures.add(ddmStructure);
			}
		}

		return ddmStructures;
	}

}