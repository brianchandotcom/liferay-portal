var liferayKarmaAlloyConfig = require('liferay-karma-alloy-config');
var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files = [];

	liferayKarmaAlloyConfig(config);

	config.browserConsoleLogOptions = {
		format: '%b %T: %m',
		level: 'log',
		terminal: true
	};

	var resourcesPath = 'src/main/resources/META-INF/resources';

	config.files.push(
		{
			included: true,
			pattern: resourcesPath + '/**/!(config)*.js'
		},
		'src/test/testJS/*_test.js'
	);
};