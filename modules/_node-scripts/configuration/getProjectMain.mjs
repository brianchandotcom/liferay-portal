import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns the proejct relative path of the main entry point
 */
export default function getProjectMain() {
	const {main} = projectScopeRequire('./node-scripts.config.js');

	return main;
}
