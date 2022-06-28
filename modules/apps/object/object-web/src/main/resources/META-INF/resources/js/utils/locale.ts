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

export const availableLocales = Object.keys(Liferay.Language.available).map(
	(language) => ({
		label: language,
		symbol: language.replace('_', '-').toLowerCase(),
	})
);

export const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export const defaultLocale = availableLocales.find(
	(locale) => locale.label === defaultLanguageId
)!;

export const languageId = Liferay.ThemeDisplay.getLanguageId();

export const locale = availableLocales.find(
	(locale) => locale.label === languageId
)!;
