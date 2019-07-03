const path = require('path');

module.exports = {
	entry: './src/main/resources/META-INF/resources/js/libs/ODataParser.es.js',
	mode: 'production',
	output: {
		filename: 'odata-parser.js',
		libraryTarget: 'commonjs',
		path: path.resolve('./classes/META-INF/resources/js/libs')
	}
};
