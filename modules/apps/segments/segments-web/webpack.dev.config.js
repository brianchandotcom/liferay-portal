const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const CopyFilesPlugin = require('webpack-copyfiles-plugin')

module.exports = {
	entry: {
		'ODataParser': path.resolve(__dirname,'src','main','resources','META-INF','resources','js','libs','ODataParser.es.js'),
		'index.dev': path.resolve(__dirname,'src','main','resources','META-INF','resources','js','index.dev.js'),
	},
	mode: 'development',
	module: {
		rules: [
		  	{
				test: /\.(js|jsx)$/,
				exclude: /node_modules/,
				use: [
					{
						loader: 'babel-loader',
					},
					'liferay-lang-key-dev-loader'
				]
			},
			{
				test: /\.(scss|css)$/,
				use: [
					{loader: "style-loader"},
					{loader: "css-loader"},
					{loader: "sass-loader"}
				]
			}
		]
	},
	resolve: {
		extensions: ['.js', '.jsx', '.svg']
	},
	output: {
		filename: `[name].js`,
		library: 'oDataParser',
		libraryTarget: 'window',
		path: path.resolve(__dirname,'dev-build')
	},
	devServer: {
		contentBase: path.join(__dirname, 'dev-build'),
		compress: false,
		port: 9000,
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: require('html-webpack-template'),
			appMountId: 'app',
		}),
		new CopyFilesPlugin([{
			sourceRoot: path.join(__dirname, '..', '..','frontend-theme','frontend-theme-admin','build','images','lexicon'),
			targetRoot: path.join(__dirname, "dev-build", "lexicon"),
			files: 'icons.svg',
			cleanDirs: [path.join(__dirname, "dev-build", "lexicon")]
		},{
			sourceRoot: path.join(__dirname, "src","main","resources","META-INF","resources","assets"),
			targetRoot: path.join(__dirname, "dev-build", "assets"),
			files: '*',
			cleanDirs: [path.join(__dirname, "dev-build", "assets")]
		}])
	]
};
