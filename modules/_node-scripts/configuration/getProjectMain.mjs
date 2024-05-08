import require from '../util/require.mjs';

/**
 * @returns the proejct relative path of the main entry point
 */
export default function getProjectExports() {
	const {main} = require('./node-build.config.js');

	return main;
}
