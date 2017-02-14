'use strict';

var karmaHtml2JsPreprocessor = require('karma-html2js-preprocessor');
var metalKarmaConfig = require('metal-karma-config/coverage');

module.exports = function(config) {
	metalKarmaConfig(config);

	config.plugins.push(karmaHtml2JsPreprocessor);

	config.files = [
		'node_modules/html2incdom/src/*.js',
		'node_modules/metal*/src/**/*.js',
		'node_modules/metal-soy-bundle/build/bundle.js',
		'src/**/*.es.js',
		'test/**/*.js',
		'test/**/fixtures/*.html'
	];

	config.preprocessors['test/**/fixtures/*.html'] = ['html2js'];
};