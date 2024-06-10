/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import path from 'path';

const VALID_TYPES = ['main', 'exports'];

export const IMPORT_BRIDGE_FILTER =
	path.sep === '/' ? /\/\$\/bridge\/.*$/ : /.?.?\\\$\\bridge\\.*$/;

/**
 * Get a virtual import bridge path.
 *
 * Virtual means that the file won't exist anywhere but will be later interpreted by a plugin (in
 * this case `getImportBridgesPlugin()`) to feed esbuild an on-the-fly file.
 *
 * @param type
 * This designates the context where the path will be interpreted. It can be `exports` or `main`.
 * The former means the URL will appear in a npm export bundle, whereas the latter means it will
 * appear in the main entry point (index.js).
 */
export default function getImportBridgePath(moduleName, type) {
	if (!VALID_TYPES.includes(type)) {
		throw new Error(`Invalid type: ${type}`);
	}

	return `/$/bridge/for/${type}/${moduleName}`;
}

/**
 * Decode a virtual bridge path to retrieve the original information regarding how it should be
 * interpreted.
 *
 * @returns
 * An object containing two fields (moduleName and type) as they were passed to the
 * `getImportBridgePath()` method.
 */
export function decodeBridgePath(bridgePath) {
	const parts = bridgePath.split(path.sep);

	return {
		moduleName: parts.slice(5).join('/'),
		type: parts[4],
	};
}
