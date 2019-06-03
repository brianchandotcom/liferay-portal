const preset = require('liferay-npm-scripts/src/presets/standard');

module.exports = {
	format: [
		...preset.format,
		'test/**/*.js',
		'test/**/*.scss'
	],
	lint: [
		...preset.lint,
		'test/**/*.js',
		'test/**/*.scss'
	],
	preset: 'liferay-npm-scripts/src/presets/standard/index'
};