const config = require('./webpack.config');

module.exports = {
	...config,
	devServer: {
		port: 3000,
		proxy: {
			'**': 'http://0.0.0.0:8080'
		},
		publicPath: config.output.publicPath
	},
	devtool: 'inline-source-map',
	mode: 'development'
};
