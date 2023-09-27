/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const contextUrl = Liferay.ThemeDisplay.getPathContext();
const defaultLanguageId = themeDisplay.getLanguageId();

const editorConfig = {
	tabSpaces: 4,
	toolbar: [['Source']],
};

export {contextUrl, defaultLanguageId, editorConfig};
