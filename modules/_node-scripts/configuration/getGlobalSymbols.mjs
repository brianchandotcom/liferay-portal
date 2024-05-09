import path from 'path';

import getRootDir from '../util/getRootDir.mjs';
import require from '../util/require.mjs';

/**
 * @returns
 * Something like:
 *
 * {
 *   '@clayui/charts': ['__esModule', 'bb', 'default']
 * }
 */
export default async function getGlobalSymbols() {
	const rootDir = await getRootDir();

	const {symbols} = require(path.join(rootDir, 'node-scripts.config.js'));

	return symbols || {};
}

