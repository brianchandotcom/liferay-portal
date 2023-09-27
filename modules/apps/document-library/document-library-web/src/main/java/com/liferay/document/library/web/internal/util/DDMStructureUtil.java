/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.util;

import com.liferay.document.library.display.context.DLEditFileEntryDisplayContext;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Alejandro Tardín
 */
public class DDMStructureUtil {

	public static List<String> getAvailableLanguageIds(
		ThemeDisplay themeDisplay) {

		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		return TransformUtil.transform(locales, LanguageUtil::getLanguageId);
	}

	public static List<Long> getDDMStructureIds(
		List<DDMStructure> ddmStructures) {

		return TransformUtil.transform(
			ddmStructures, DDMStructure::getStructureId);
	}

	public static List<String> getTranslatedLanguageIds(
			List<DDMStructure> ddmStructures,
			DLEditFileEntryDisplayContext dlEditFileEntryDisplayContext,
			long fileVersionId)
		throws PortalException {

		Set<String> translatedLanguageIds = new HashSet<>();

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata fileEntryMetadata =
				DLFileEntryMetadataLocalServiceUtil.fetchFileEntryMetadata(
					ddmStructure.getStructureId(), fileVersionId);

			if (fileEntryMetadata == null) {
				continue;
			}

			DDMFormValues ddmFormValues =
				dlEditFileEntryDisplayContext.getDDMFormValues(
					fileEntryMetadata.getDDMStorageId());

			for (Locale locale : ddmFormValues.getAvailableLocales()) {
				translatedLanguageIds.add(LanguageUtil.getLanguageId(locale));
			}
		}

		return new ArrayList<>(translatedLanguageIds);
	}

}