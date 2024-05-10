import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns 
 * {
 *	 name: 'xxx',
 *	 version: 'x.y.z',
 * }
 */
export default function getProjectDescription() {
	const {main, name, version} = projectScopeRequire('./package.json');

	return {main, name, version};
}
