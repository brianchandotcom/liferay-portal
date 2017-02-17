'use strict';

var karmaHtml2JsPreprocessor = require('karma-html2js-preprocessor');
var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.plugins.push(karmaHtml2JsPreprocessor);

	config.files.push('test/**/fixtures/*.html');

	config.preprocessors['test/**/fixtures/*.html'] = ['html2js'];
};