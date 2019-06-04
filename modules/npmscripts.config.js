const PRETTIER_GLOBS = [
	'*.js',
	'.*.js',
	'apps/**/{src,test}/**/*.{js,scss}',
	'!yarn-*.js',
	'!**/classes/**/*.{js,scss}',
	'!**/css/clay/**/*.scss',
	'!modules/tests/poshi-runner'
];

module.exports = {
	format: PRETTIER_GLOBS,
	preset: 'liferay-npm-scripts/src/presets/standard',
	lint: PRETTIER_GLOBS
};
