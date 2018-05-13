var liferayKarmaAlloyConfig = require('liferay-karma-alloy-config');
var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files = [];

	liferayKarmaAlloyConfig(config);

	config.singleRun = true;

	config.files.push(
		{
			included: true,
			pattern: 'src/main/resources/META-INF/resources/**/!(config).js'
		},
		'src/test/testJS/*_test.js'
	);
};