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

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class DLFileEntryTypeUtil {

	public static Locale[] getDLFileEntryTypeAvailableLocales(
			FileVersion fileVersion, DLFileEntryType dlFileEntryType,
			DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext,
			String defaultLanguageId)
		throws PortalException {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		if ((fileVersion == null) || (dlFileEntryType == null)) {
			return new Locale[] {defaultLocale};
		}

		for (DDMStructure ddmStructure : dlFileEntryType.getDDMStructures()) {
			DLFileEntryMetadata dlFileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					fileVersion.getFileVersionId());

			if (dlFileEntryMetadata == null) {
				continue;
			}

			DDMFormValues ddmFormValues =
				dlEditFileEntryDisplayContext.getDDMFormValues(
					dlFileEntryMetadata.getDDMStorageId());

			if (ddmFormValues != null) {
				Set<Locale> availableLocalesSet =
					ddmFormValues.getAvailableLocales();

				Locale[] availableLocales = availableLocalesSet.toArray(
					new Locale[availableLocalesSet.size()]);

				return availableLocales;
			}
		}

		return new Locale[] {defaultLocale};
	}

}