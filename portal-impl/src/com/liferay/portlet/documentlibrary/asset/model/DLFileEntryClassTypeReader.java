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

package com.liferay.portlet.documentlibrary.asset.model;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryClassType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryClassTypeReader implements ClassTypeReader {

	@Override
	public List<ClassType> getAvailableClassTypes(
		long[] groupIds, Locale locale) {

		List<ClassType> classTypes = new ArrayList<>();

		classTypes.add(getBasicDocumentClassType(locale));

		String languageId = LocaleUtil.toLanguageId(locale);

		List<DLFileEntryType> dlFileEntryTypes =
			DLFileEntryTypeServiceUtil.getFileEntryTypes(groupIds);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			classTypes.add(
				new DLFileEntryClassType(
					dlFileEntryType.getFileEntryTypeId(),
					dlFileEntryType.getName(locale), languageId));
		}

		return classTypes;
	}

	@Override
	public ClassType getClassType(long classTypeId, Locale locale)
		throws PortalException {

		if (classTypeId ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

			return getBasicDocumentClassType(locale);
		}

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeServiceUtil.getFileEntryType(classTypeId);

		return new DLFileEntryClassType(
			dlFileEntryType.getFileEntryTypeId(),
			dlFileEntryType.getName(locale), LocaleUtil.toLanguageId(locale));
	}

	protected ClassType getBasicDocumentClassType(Locale locale) {
		DLFileEntryType basicDocumentDLFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		return new DLFileEntryClassType(
			basicDocumentDLFileEntryType.getFileEntryTypeId(),
			LanguageUtil.get(
				locale, DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT),
			LocaleUtil.toLanguageId(locale));
	}

}