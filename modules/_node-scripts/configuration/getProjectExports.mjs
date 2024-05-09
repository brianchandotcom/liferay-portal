import require from '../util/require.mjs';

/**
 * @returns
 * Something like:
 *
 * [
 *   'html-to-image',
 *   'jspdf',
 *   '@liferay/js-api',
 *   '@liferay/js-api/data-set',
 * ]
 */
export default function getProjectExports() {
	const {exports} = require('./node-scripts.config.js');

	if (exports === undefined) {
		return [];
	}

	return exports;
}
