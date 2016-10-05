'use strict';

var liferayKarmaConfig = require('liferay-karma-config');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files.push(
		'node_modules/resemblejs/resemble.js',
		'node_modules/simulate-dom-event/index.js',
		{
			included: false,
			pattern: 'test/**/*.png'
		}
	);
};