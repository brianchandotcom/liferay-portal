const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')


module.exports = {
	entry: {
		'ODataParser': path.resolve(__dirname,'src','main','resources','META-INF','resources','js','libs','ODataParser.es.js'),
		'index.dev': path.resolve(__dirname,'src','main','resources','META-INF','resources','js','index.dev.js'),
		'style': path.resolve(__dirname,'src','main','resources','META-INF','resources','css','main.scss'),
		'clay': path.resolve(__dirname, 'node_modules', 'clay-css','lib','css', 'atlas.css')
	},
	mode: 'development',
	module: {
		rules: [
		  {
			test: /\.(js|jsx)$/,
			exclude: /node_modules/,
			use: [{
				loader: 'babel-loader',
				options: {
						presets: ['babel-preset-env', 'babel-preset-env'],
						"plugins": [
							"transform-class-properties",
							"transform-object-rest-spread",
							["module-resolver", {
								"root": ["./src/main/resources/META-INF/resources/js/"],
								"alias": {
									"test": "./test/js/"
								}
							}]
						]
					}
			  },'liferay-lang-key-dev-loader']
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
		extensions: ['*', '.js', '.jsx']
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
		})
	]
};
