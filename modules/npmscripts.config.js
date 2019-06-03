const preset = require('liferay-npm-scripts/src/presets/standard');

module.exports = {
	format: [
		'apps/**/src/**/*.js',
		'apps/**/src/**/*.scss',
		'!**/classes/**/*.js',
		'!**/classes/**/*.scss'
	],
	preset: 'liferay-npm-scripts/src/presets/standard',
	lint: [],
};