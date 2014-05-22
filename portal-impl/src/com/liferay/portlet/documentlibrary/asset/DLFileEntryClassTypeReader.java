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

package com.liferay.portlet.documentlibrary.asset;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.asset.model.ClassType;
import com.liferay.portlet.asset.model.ClassTypeReader;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryClassTypeReader implements ClassTypeReader {

	@Override
	public List<ClassType> getAvailableClassTypes(
			long[] groupIds, Locale locale)
		throws SystemException {

		List<ClassType> classTypes = new ArrayList<ClassType>();

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
		throws PortalException, SystemException {

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

	protected ClassType getBasicDocumentClassType(Locale locale)
		throws SystemException {

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