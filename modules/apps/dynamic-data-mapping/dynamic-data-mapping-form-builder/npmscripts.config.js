const standard = require('liferay-npm-scripts/src/presets/standard');

module.exports = Object.assign(standard, {
	build: Object.assign(standard.build, {
		dependencies: standard.build.dependencies.concat(['dynamic-data-mapping-form-renderer'])
	})
});