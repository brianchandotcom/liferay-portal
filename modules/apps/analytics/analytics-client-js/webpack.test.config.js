module.exports = {
	devtool: 'inline-source-map',
	module: {
		loaders: [
			{ test: /\.js$/, loader: 'babel-loader' },
			{
				loader: 'istanbul-instrumenter-loader',
				exclude: /(test|node_modules|bower_components)\//,
				test: /\.js$/,
				enforce: 'post',
			}
		]
	}
};