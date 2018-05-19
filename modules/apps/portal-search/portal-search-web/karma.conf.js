var liferayKarmaAlloyConfig = require('liferay-karma-alloy-config');
var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files = [];

	liferayKarmaAlloyConfig(config);

	config.autowatch = false;

	config.browserConsoleLogOptions = {
		format: '%b %T: %m',
		level: 'log',
		terminal: true
	};

	config.singleRun = true;

	config.files.push(
		{
			included: true,
			pattern: 'src/main/resources/META-INF/resources/**/!(config).js'
		},
		{
			included: true,
			pattern: 'src/test/testJS/*_util.js'
		},
		'src/test/testJS/*_test.js'
	);
};