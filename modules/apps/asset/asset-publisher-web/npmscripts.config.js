const preset = require('liferay-npm-scripts/src/presets/standard');

module.exports = {
	preset: 'liferay-npm-scripts/src/presets/standard',
	build: {
		dependencies: [
			...preset.build.dependencies,
			'asset-taglib'
		]
	}
};