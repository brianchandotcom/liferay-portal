const path = require('path');
const webpack = require('./webpack.config.test');

module.exports = function(config) {
	config.set({
		browsers: ['ChromeHeadless'],

		coverageIstanbulReporter: {
			dir: path.join(__dirname, 'test-coverage'),
			reports: ['html', 'lcovonly', 'text-summary']
		},

		coverageReporter: {
			reporters: [
				{
					subdir: 'lcov',
					type: 'lcov'
				},
				{
					type: 'text-summary'
				}
			]
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

		files: ['test/index.js'],

		frameworks: ['chai', 'mocha', 'sinon'],

		plugins: [
			'karma-chai',
			'karma-chrome-launcher',
			'karma-coverage-istanbul-reporter',
			'karma-mocha',
			'karma-sinon',
			'karma-sourcemap-loader',
			'karma-webpack'
		],

		preprocessors: {
			'test/index.js': ['webpack', 'sourcemap']
		},

		reporters: ['progress', 'coverage-istanbul'],

		singleRun: true,

		webpack
	});
};
