const path = require('path');
const webpack = require('./webpack.test.config');

module.exports = function(config) {
	config.set({
		browsers: ['ChromeHeadless'],

		coverageReporter: {
			reporters: [{
				type: 'lcov',
				subdir: 'lcov',
			}, {
				type: 'text-summary',
			}, ],
		},

		customLaunchers: {
			ChromeHeadless: {
				base: 'Chrome',
				flags: [
					'--no-sandbox',
					'--headless',
					'--disable-gpu',
					'--disable-translate',
					'--disable-extensions',
					'--remote-debugging-port=9222'
				]
			}
		},

		files: [
			'node_modules/fetch-mock/es5/client-browserified.js',
			'test.webpack.js'
		],

		frameworks: ['chai', 'mocha', 'sinon'],

		preprocessors: {
			'test.webpack.js': ['webpack', 'sourcemap'],
		},

		reporters: ['progress', 'coverage-istanbul'],

		coverageIstanbulReporter: {
			dir: path.join(__dirname, 'test-coverage'),
			reports: ['html', 'lcovonly', 'text-summary']
		},

		webpack: webpack,

		singleRun: true
	});
};