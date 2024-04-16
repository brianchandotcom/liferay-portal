import require from '../util/require.mjs';

/**
 * @returns 
 * {
 *	 name: 'xxx',
 *	 version: 'x.y.z',
 * }
 */
export default function getProjectDescription() {
	const {main, name, version} = require('./package.json');

	return {main, name, version};
}
