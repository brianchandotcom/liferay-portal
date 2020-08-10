const {defineServerResponses} = require('./dev/fakeServerUtilities');

const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

const outputPath = path.resolve(__dirname, './dev/public');

module.exports = {
	entry: path.join(__dirname, './src/main/resources/META-INF/resources/js/index.dev.es.js'),
	mode: 'development',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.(js|jsx)$/,
				use: [
					{
						loader: 'babel-loader'
					}
				]
			},
			{
				test: /\.(scss|css)$/,
				use: [
					{ loader: 'style-loader' },
					{ loader: 'css-loader' },
					{ loader: 'sass-loader' }
				]
			}
		]
	},
	output: {
		filename: `bundle.js`,
		path: outputPath
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: path.resolve(__dirname, './dev/public/index.html'),
			inject: false
		})
	],
	resolve: {
		extensions: ['.js', '.jsx']
	},
	devServer: {
		compress: false,
		publicPath: '/',
		contentBase: './dev/public',
		filename: path.join(outputPath, '/bundle.js'),
		open: true,
		port: 9000,
		hot: true,
		historyApiFallback: true,
		before: function(app) {
			defineServerResponses(app);
		}
	},
};
