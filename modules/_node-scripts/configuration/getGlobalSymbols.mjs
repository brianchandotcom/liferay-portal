import path from 'path';

import getRootDir from '../util/getRootDir.mjs';
import projectScopeRequire from '../util/projectScopeRequire.mjs';

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

	const {symbols} = projectScopeRequire(path.join(rootDir, 'node-scripts.config.js'));

	return symbols || {};
}

