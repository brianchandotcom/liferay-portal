/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

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
		terminal: false
	};
	config.singleRun = true;

	var resourcesPath = 'src/main/resources/META-INF/resources/';
	var resourcesTestPath = 'src/test/resources/';
	var testPath = 'src/test/testJS/';

	config.files.push(
		{
			included: true,
			pattern: resourcesPath + '/**/!(config)*.js'
		},
		{
			included: false,
			pattern: resourcesTestPath + '/*.xml',
			served: true
		},
		{
			included: true,
			pattern: testPath + '/*_util.js'
		},
		'src/test/testJS/*_test.js'
	);
};
