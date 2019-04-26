module.exports = {
	devtool: 'inline-source-map',
	mode: 'development',
	module: {
		rules: [
			{
				test: /\.js$/,
				exclude: /(node_modules)/,
				use: {
					loader: 'babel-loader',
					options: {
						compact: false,
					},
				},
			},
			{
				test: /\.js$/,
				exclude: /(test|node_modules)/,
				use: {
					loader: 'istanbul-instrumenter-loader',
					query: {
						esModules: true
					}
				},
			}
		]
	}
};