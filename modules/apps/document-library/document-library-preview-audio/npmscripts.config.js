const standardPreset = require('liferay-npm-scripts/src/presets/standard/index');

module.exports = {
	preset: 'liferay-npm-scripts/src/presets/standard/index',
	format: [
		...standardPreset.format,
		'test/**/*.js',
	],
	lint: [
		...standardPreset.lint,
		'test/**/*.js',
	]
};