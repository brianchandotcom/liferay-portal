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

import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alejandro Tardín
 */
public class DDMStructureUtil {

	public static List<String> getAvailableLanguageIds(
		ThemeDisplay themeDisplay) {

		Set<Locale> locales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		Stream<Locale> stream = locales.stream();

		return stream.map(
			LanguageUtil::getLanguageId
		).collect(
			Collectors.toList()
		);
	}

	public static List<Long> getDDMStructureIds(
		List<DDMStructure> ddmStructures) {

		Stream<DDMStructure> stream = ddmStructures.stream();

		return stream.map(
			DDMStructure::getStructureId
		).collect(
			Collectors.toList()
		);
	}

}