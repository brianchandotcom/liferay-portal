import require from '../util/require.mjs';

/**
 * @returns an object following npmscripts.config.js structure
 */
export default function getProjectNpmScriptsConfig() {
	const {npmscripts} = require('./node-scripts.config.js');

	return npmscripts;
}
