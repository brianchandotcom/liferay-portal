'use strict';

var karmaHtml2JsPreprocessor = require('karma-html2js-preprocessor');
var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.plugins.push(karmaHtml2JsPreprocessor);

	config.files = [
		'node_modules/metal*/src/**/*.js',
		'src/**/*.es.js',
		'test/**/*.js',
		'test/**/fixtures/*.html'
	];

	config.preprocessors['test/**/fixtures/*.html'] = ['html2js'];
};