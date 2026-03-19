/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Splits an export into scope, package and module path parts.
 * @returns an object with fields: scope (starts with at sign), name and modulePath (starts with /)
 */
export default function splitProjectExport(projectExport) {
	let ret;

	let parts = projectExport.split('/');

	if (projectExport.startsWith('@')) {
		if (parts.length < 2) {
			throw new Error(`No package name found in: ${projectExport}`);
		}

		ret = {
			name: parts[1],
			scope: parts[0],
		};

		parts = parts.slice(2);
	}
	else {
		if (parts.length < 1) {
			throw new Error(`No package name found in: ${projectExport}`);
		}

		ret = {
			name: parts[0],
		};

		parts = parts.slice(1);
	}

	const modulePath = parts.join('/');

	if (modulePath !== '') {
		ret.modulePath = `/${modulePath}`;
	}
	else {
		ret.modulePath = '/index';
	}

	return ret;
}
