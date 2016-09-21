'use strict';

var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files = [
		'node_modules/metal-soy-bundle/build/bundle.js',
		'node_modules/html2incdom/src/*.js',
		'node_modules/metal*/src/**/*.js',
		'src/**/*.es.js',
		'test/**/*.js'
	];
};