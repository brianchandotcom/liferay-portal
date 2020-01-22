/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

var liferayKarmaAlloyConfig = require('liferay-karma-alloy-config');
var liferayKarmaConfig = require('liferay-karma-config');

const TIMEOUT = 10000;

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

	config.browserDisconnectTimeout = TIMEOUT;

	config.client.mocha = {
		timeout: TIMEOUT
	};

	config.singleRun = true;

	config.files.push(
		{
			included: true,
			pattern: 'src/main/resources/META-INF/resources/**/!(config).js'
		},
		{
			included: true,
			pattern: 'test/*_util.js'
		},
		'test/*_test.js'
	);
};
