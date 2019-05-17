const preset = require('liferay-npm-scripts/src/presets/standard');

module.exports = {
	build: {
		dependencies: [
			...preset.build.dependencies,
			'asset-taglib'
		]
	},
	preset: 'liferay-npm-scripts/src/presets/standard'
};