const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

module.exports = {
	devServer: {
		compress: false,
		contentBase: path.join(__dirname, 'dev-build'),
		open: true,
		openPage: 'o/segments-web/',
		port: 9000,
		proxy: {
			'**': 'http://0.0.0.0:8080'
		},
		publicPath: '/o/segments-web/'
	},
	entry: {
		'ODataParser': path.resolve(__dirname, 'src', 'main', 'resources', 'META-INF', 'resources', 'js', 'libs', 'ODataParser.es.js'),
		'index.dev': path.resolve(__dirname, 'src', 'main', 'resources', 'META-INF', 'resources', 'js', 'index.dev.es.js')
	},
	mode: 'development',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.(js|jsx)$/,
				use: [
					{
						loader: 'babel-loader'
					},
					{
						loader: 'liferay-lang-key-dev-loader',
						options: {
							path: '../segments-lang/src/main/resources/content/Language.properties'
						}
					}
				]
			},
			{
				test: /\.(scss|css)$/,
				use: [
					{loader: 'style-loader'},
					{loader: 'css-loader'},
					{loader: 'sass-loader'}
				]
			}
		]
	},
	output: {
		filename: `[name].js`,
		library: 'oDataParser',
		libraryTarget: 'window',
		path: path.resolve(__dirname,'dev-build')
	},
	plugins: [
		new HtmlWebpackPlugin({
			appMountId: 'app',
			template: require('html-webpack-template')
		})
	],
	resolve: {
		extensions: ['.js', '.jsx', '.svg']
	}
};