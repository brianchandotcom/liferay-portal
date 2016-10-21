'use strict';

var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files = [
		'node_modules/metal*/src/**/*.js',
		'src/**/*.es.js',
		'test/**/*.js'
	];
};