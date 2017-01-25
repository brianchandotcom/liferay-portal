var defaultConfig = require('./karma.conf.js');
var karmaCoverage = require('karma-coverage');
var path = require('path');

module.exports = function(config) {
	defaultConfig(config);

	config.coverageReporter = {
		dir: path.resolve('build/coverage'),
		reporters: [
			{
				subdir: 'lcov',
				type: 'lcov'
			},
			{
				type: 'text-summary'
			}
		]
	};

	config.plugins.push(karmaCoverage);

	var resourcesPath = 'src/main/resources/META-INF/resources';

	config.preprocessors[resourcesPath + '/**/!(*soy)*.js'] = ['replacer', 'coverage'];

	config.reporters.push('coverage');
};