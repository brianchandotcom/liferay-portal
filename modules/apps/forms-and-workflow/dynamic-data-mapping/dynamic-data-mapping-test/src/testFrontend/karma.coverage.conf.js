var dependencies = require('./util/dependencies');
var path = require('path');
var replacer = require('./util/replacer');

module.exports = function(karmaConfig) {
	karmaConfig.set(
		{
			browsers: ['Chrome'],
			coverageReporter: {
				dir: path.resolve('build/coverage'),
				reporters: [
					{
						type: 'html'
					},
					{
						subdir: 'lcov',
						type: 'lcov'
					},
					{
						type: 'text-summary'
					}
				]
			},
			files: dependencies.concat(
				[
					'tests/**/*.js'
				]
			),
			frameworks: ['chai', 'commonjs', 'mocha', 'sinon'],
			preprocessors: {
				'/**/*.js': ['replacer'],
				'/**/dynamic-data-lists/**/resources/**/!(*.soy).js': ['coverage'],
				'/**/dynamic-data-mapping/**/resources/**/!(*.soy).js': ['coverage'],
				'mocks/*.js': ['replacer'],
				'tests/**/*.js': ['commonjs']
			},
			replacerPreprocessor: {
				replacer: replacer.replaceModulePath
			},
			reporters: ['coverage', 'progress', 'threshold'],
			thresholdReporter: {
				branches: 80,
				functions: 80,
				lines: 80,
				statements: 80
			}
		}
	);
};