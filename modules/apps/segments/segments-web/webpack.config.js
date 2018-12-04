const path = require('path');
const webpack = require('webpack');

module.exports = {
	entry: './src/main/resources/META-INF/resources/js/libs/ODataParser.es.js',
	mode: 'production',
	output: {
		filename: 'odata-parser.js',
		library: 'oDataParser',
		libraryTarget: 'window',
		path: path.resolve('./src/main/resources/META-INF/resources/js/libs/')
	}
};