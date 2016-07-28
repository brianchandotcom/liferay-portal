'use strict';

var metalKarmaConfig = require('metal-karma-config/coverage');

module.exports = function (config) {
	metalKarmaConfig(config);

	config.files.push(
		'node_modules/resemblejs/resemble.js',
		'node_modules/simulate-dom-event/index.js',
		{
			included: false,
			pattern: 'test/**/*.png'
		}
	);
};