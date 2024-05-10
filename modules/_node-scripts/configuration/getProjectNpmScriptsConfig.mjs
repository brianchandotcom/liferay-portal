import projectScopeRequire from '../util/projectScopeRequire.mjs';

/**
 * @returns an object following npmscripts.config.js structure
 */
export default function getProjectNpmScriptsConfig() {
	const {npmscripts} = projectScopeRequire('./node-scripts.config.js');

	return npmscripts;
}
