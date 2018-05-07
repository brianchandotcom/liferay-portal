var liferayKarmaAlloyConfig = require('liferay-karma-alloy-config');
var liferayKarmaConfig = require('liferay-karma-config');
var path = require('path');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files = [];

	liferayKarmaAlloyConfig(config);

	var resourcesPath = 'src/main/resources/META-INF/resources';

	config.files.push(
		{
			included: true,
			pattern: 'node_modules/lodash/dist/*.js'
		},
		{
			included: true,
			pattern: 'node_modules/soyutils-nogoog/index.js'
		},
		{
			included: true,
			pattern: '../dynamic-data-mapping-form-renderer/' + resourcesPath + '/**/!(config)*.js'
		},
		{
			included: true,
			pattern: resourcesPath + '/**/!(config)*.js'
		},
		'src/test/testJS/**/*.js'
	);
};