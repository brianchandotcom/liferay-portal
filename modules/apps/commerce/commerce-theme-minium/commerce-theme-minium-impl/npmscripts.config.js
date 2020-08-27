const preset = require('liferay-npm-scripts/src/presets/standard');

module.exports = {
	build: {
		dependencies: [...preset.build.dependencies, 'commerce-frontend-taglib']
	},
	preset: 'liferay-npm-scripts/src/presets/standard'
};
