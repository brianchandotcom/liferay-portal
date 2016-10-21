'use strict';

var metalKarmaConfig = require('metal-karma-config/coverage');

module.exports = function(config) {
	metalKarmaConfig(config);

	config.files = [
		'node_modules/metal*/src/**/*.js',
		'src/**/*.es.js',
		'test/**/*.js'
	];
};