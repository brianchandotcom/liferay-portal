/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import blogs from './blogs';
import custom from './custom';
import documents from './documents';
import documentsFragment from './documents-fragment';
import dxp from './dxp';
import forms from './forms';
import objectEntry from './object-entry';
import read from './read';
import scrolling from './scrolling';
import timing from './timing';
import visibility from './visibility';
import webContents from './web-contents';

export {
	blogs,
	documents,
	documentsFragment,
	dxp,
	forms,
	objectEntry,
	read,
	scrolling,
	timing,
	webContents,
};

export default [

	// Dxp should be before other events plugins, because it can dispose analytics

	dxp,

	blogs,
	custom,
	documents,
	documentsFragment,
	forms,
	objectEntry,
	read,
	scrolling,
	timing,
	visibility,
	webContents,
];
