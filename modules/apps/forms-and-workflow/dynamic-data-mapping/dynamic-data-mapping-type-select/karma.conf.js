var liferayKarmaAlloyConfig = require('liferay-karma-alloy-config');
var liferayKarmaConfig = require('liferay-karma-config');
var path = require('path');

module.exports = function(config) {
	liferayKarmaConfig(config);

	config.files = [];

	liferayKarmaAlloyConfig(config);

	var resourcesPath = 'src/main/resources/META-INF/resources';

	config.files.push(
		{
			included: true,
			pattern: 'node_modules/soyutils-nogoog/index.js'
		},
		{
			included: true,
			pattern: '../dynamic-data-mapping-form-renderer/' + resourcesPath + '/**/!(config)*.js'
		},
		{
			included: true,
			pattern: resourcesPath + '/**/!(config)*.js'
		},
		'src/testFrontend/**/*.js'
	);

	config.preprocessors[resourcesPath + '/**/*.soy.js'] = ['replacer'];

	config.replacerPreprocessor = {
		replacer: function(file, content) {
			var filePath = file.path.split(path.sep);

			var fileName = filePath.pop();

			if (fileName === 'select.soy.js') {
				content = [
					'AUI.add(\'liferay-ddm-form-field-select-template\', function(A) {',
					content.replace(
						'(typeof ddm == \'undefined\') { var ddm = {}; }',
						'(typeof ddm == \'undefined\') { window.ddm = {}; }'
					),
					'}, \'\', {requires: []});'
				].join('');
			}

			return content;
		}
	};
};