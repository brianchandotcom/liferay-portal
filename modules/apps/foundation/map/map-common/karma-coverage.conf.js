/* eslint-env node */

'use strict';

const metalKarmaConfig = require('metal-karma-config/coverage');

module.exports = function(config) {
	metalKarmaConfig(config);

	config.files = [
		'node_modules/metal*/src/**/*.js',
		'node_modules/metal-soy-bundle/build/bundle.js',
		'src/**/*.es.js',
		'test/**/*.js',
	];
};