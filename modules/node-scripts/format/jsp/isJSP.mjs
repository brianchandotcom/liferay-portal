/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import hasExtension from './hasExtension.mjs';

const JSP_EXTENSIONS = new Set(['.jsp', '.jspf']);

/**
 * Returns true if `filepath` refers to a JSP file.
 */
function isJSP(filepath) {
	return hasExtension(filepath, JSP_EXTENSIONS);
}

export default isJSP;
